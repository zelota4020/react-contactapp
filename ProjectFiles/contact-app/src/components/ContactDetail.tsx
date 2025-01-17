import { useState } from "react";
import { useRef } from "react";
import { useEffect } from "react";
import { useParams } from "react-router-dom";
import { Link } from "react-router-dom";
import ContactService from "../api/ContactService.ts";
import { toastError } from "../api/ToasService.ts";
import { toastSuccess } from "../api/ToasService.ts";

interface ContactDetailProps {
    updateContact: ( contact: any ) => void;

    updateImage: ( formData: any ) => void;

    deleteContact: ( id: string ) => void;
}

function ContactDetail( { updateContact, updateImage, deleteContact }: ContactDetailProps ) {
    const service: ContactService = new ContactService();
    const inputRef = useRef();
    const [ contact, setContact ] = useState( {
        id: "",
        name: "",
        email: "",
        phone: "",
        address: "",
        title: "",
        status: "",
        photoUrl: ""
    } );

    const fetchContact = async ( contactId ) => {
        try {
            const { data } = await service.getContact( contactId );
            setContact( data );
        } catch ( error ) {
            console.log( error );
            toastError( error.message );
        }
    };

    const selectImage = () => {
        inputRef.current.click();
    };

    const udpatePhoto = async ( file ) => {
        try {
            const formData = new FormData();
            formData.append( "file", file );
            formData.append( "id", id );
            await updateImage( formData );
            setContact( ( prev ) => ({
                ...prev,
                photoUrl: `${ prev.photoUrl }?updated_at=${ new Date().getTime() }`
            }) );
            toastSuccess( "Photo updated" );
        } catch ( error ) {
            console.log( error );
            toastError( error.message );
        }
    };

    const onChange = ( event ) => {
        setContact( { ...contact, [ event.target.name ]: event.target.value } );
    };

    const onUpdateContact = async ( event ) => {
        event.preventDefault();
        await updateContact( contact );
        await fetchContact( id );
        toastSuccess('Contact Updated');
    };

    const { id } = useParams();

    useEffect( () => {
        fetchContact( id );
    }, [] );

    return (
        <>
            <Link to={ "/contacts" } className="link"><i className="bi bi-arrow-left"></i> Back to list</Link>
            <div className="profile">
                <div className="profile__details">
                    <img src={ contact.photoUrl } alt={ `Profile photo of ${ contact.name }` }/>
                    <div className="profile__metadata">
                        <p className="profile__name">{ contact.name }</p>
                        <p className="profile__muted">JPG, GIF, or PNG. Max size of 10MG</p>
                        <button onClick={ selectImage } className="btn"><i className="bi bi-cloud-upload"></i> Change
                            Photo
                        </button>
                    </div>
                </div>
                <div className="profile__settings">
                    <div>
                        <form onSubmit={ onUpdateContact } className="form">
                            <div className="user-details">
                                <input type="hidden" defaultValue={ contact.id } name="id" required/>
                                <div className="input-box">
                                    <span className="details">Name</span>
                                    <input type="text" value={ contact.name } onChange={ onChange } name="name"
                                           required/>
                                </div>
                                <div className="input-box">
                                    <span className="details">Email</span>
                                    <input type="text" value={ contact.email } onChange={ onChange } name="email"
                                           required/>
                                </div>
                                <div className="input-box">
                                    <span className="details">Phone</span>
                                    <input type="text" value={ contact.phone } onChange={ onChange } name="phone"
                                           required/>
                                </div>
                                <div className="input-box">
                                    <span className="details">Address</span>
                                    <input type="text" value={ contact.address } onChange={ onChange } name="address"
                                           required/>
                                </div>
                                <div className="input-box">
                                    <span className="details">Title</span>
                                    <input type="text" value={ contact.title } onChange={ onChange } name="title"
                                           required/>
                                </div>
                                <div className="input-box">
                                    <span className="details">Status</span>
                                    <input type="text" value={ contact.status } onChange={ onChange } name="status"
                                           required/>
                                </div>
                            </div>
                            <div className="form_footer">
                                <button type="submit" className="btn">Save</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

            <form style={ { display: "none" } }>
                <input type="file" ref={ inputRef } onChange={ ( event ) => udpatePhoto( event.target.files[ 0 ] ) }
                       name="file" accept="image/*"/>
            </form>
        </>
    )

}

export default ContactDetail
