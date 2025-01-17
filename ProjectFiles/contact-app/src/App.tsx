import "./App.css"
import { useRef } from "react";
import { RefObject } from "react";
import { useEffect } from "react";
import { useState } from "react";
import { Navigate } from "react-router-dom";
import { Route, Routes } from "react-router-dom";
import { ToastContainer } from "react-toastify";
import ContactService from "./api/ContactService.ts";
import { toastSuccess } from "./api/ToasService.ts";
import { toastError } from "./api/ToasService.ts";
import ContactDetail from "./components/ContactDetail.tsx";
import ContactList from "./components/ContactList.tsx";
import Header from "./components/Header.tsx";

function App() {
    const service: ContactService = new ContactService();
    const modalRef: RefObject<any> = useRef();
    const photoRef: RefObject<any> = useRef();
    const [ data, setData ] = useState( {} );
    const [ currentPage, setCurrentPage ] = useState( 0 );
    const [ photo, setPhoto ] = useState<File>();
    const [ values, setValues ] = useState( {
        name: "",
        email: "",
        title: "",
        phone: "",
        address: "",
        status: "",
    } );

    const getAllContacts = async ( page = 0, size = 10 ) => {
        try {
            setCurrentPage( page );
            const { data } = await service.getContacts( page, size );
            setData( data );
        } catch ( error ) {
            toastError( error.message );
        }
    };

    useEffect( () => {
        getAllContacts();
    }, [] );

    const toggleModal = ( show: any ) => {
        modalRef.current[ show ? "showModal" : "close" ]();
        if ( show ) {
            setValues( {
                name: "",
                email: "",
                phone: "",
                address: "",
                title: "",
                status: "",
            } );
        }
    };

    const onChange = ( event: any ) => {
        setValues( { ...values, [ event.target.name ]: event.target.value } );
    };

    const updateContact = async ( contact: any ) => {
        try {
            const { data } = await service.updateContact( contact.id, contact );
            await getAllContacts();
        } catch ( error ) {
            console.log( error );
            toastError( error.message );
        }
    };

    const updatePhoto = async ( formData: any ) => {
        try {
            const { data: photoUrl } = await service.updatePhoto( formData );
        } catch ( error ) {
            console.log( error );
            toastError( error.message );
        }
    };

    const deleteContact = async ( id: string ) => {
        try {
            await service.delete( id );
        } catch ( error ) {
            console.log( error );
            toastError( error.message );
        }
    };

    const handleNewContact = async ( event ) => {
        event.preventDefault();
        try {
            const { data } = await service.saveContact( values );
            const formData = new FormData();
            formData.append( "id", data.id );
            formData.append( "file", photo );
            const { data: photoUrl } = await service.updatePhoto( formData );
            toggleModal( false );
            setPhoto( undefined );
            photoRef.current.value = null;
            setValues( {
                name: "",
                email: "",
                phone: "",
                address: "",
                title: "",
                status: "",
            } )
            getAllContacts();
            toastSuccess( "Contact added" );
        } catch ( error ) {
            console.log( error );
            toastError(error.message);
        }
    };

    return (
        <>
            <Header toggleModal={ toggleModal } nbOfContacts={ data.totalElements }/>
            <main className="main">
                <div className="container">
                    <Routes>
                        <Route path="/" element={ <Navigate to={ "/contacts" }/> }/>
                        <Route path="/contacts" element={ <ContactList data={ data } currentPage={ currentPage }
                                                                       getAllContacts={ getAllContacts }/> }/>
                        <Route path="/contacts/:id" element={ <ContactDetail updateContact={ updateContact }
                                                                             updateImage={ updatePhoto }
                                                                             deleteContact={ deleteContact }/> }/>
                    </Routes>
                </div>
            </main>

            {/* Modal */ }
            <dialog ref={ modalRef } className="modal" id="modal">
                <div className="modal__header">
                    <h3>New Contact</h3>
                    <i onClick={ () => toggleModal( false ) } className="bi bi-x-lg"></i>
                </div>
                <div className="divider"></div>
                <div className="modal__body">
                    <form onSubmit={ handleNewContact }>
                        <div className="user-details">
                            <div className="input-box">
                                <span className="details">Name</span>
                                <input type="text" value={ values.name } onChange={ onChange } name="name" required/>
                            </div>
                            <div className="input-box">
                                <span className="details">Email</span>
                                <input type="text" value={ values.email } onChange={ onChange } name="email" required/>
                            </div>
                            <div className="input-box">
                                <span className="details">Title</span>
                                <input type="text" value={ values.title } onChange={ onChange } name="title" required/>
                            </div>
                            <div className="input-box">
                                <span className="details">Phone Number</span>
                                <input type="text" value={ values.phone } onChange={ onChange } name="phone" required/>
                            </div>
                            <div className="input-box">
                                <span className="details">Address</span>
                                <input type="text" value={ values.address } onChange={ onChange } name="address"
                                       required/>
                            </div>
                            <div className="input-box">
                                <span className="details">Account Status</span>
                                <input type="text" value={ values.status } onChange={ onChange } name="status"
                                       required/>
                            </div>
                            <div className="file-input">
                                <span className="details">Profile Photo</span>
                                <input type="file" onChange={ ( event ) => setPhoto( event.target.files[ 0 ] ) }
                                       ref={ photoRef } name="photo" required/>
                            </div>
                        </div>
                        <div className="form_footer">
                            <button onClick={ () => toggleModal( false ) } type="button"
                                    className="btn btn-danger">Cancel
                            </button>
                            <button type="submit" className="btn">Save</button>
                        </div>
                    </form>
                </div>
            </dialog>
            <ToastContainer />
        </>
    )
}

export default App
