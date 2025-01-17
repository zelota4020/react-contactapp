import { ToastOptions } from "react-toastify";
import { toast } from "react-toastify";

const toastConfig: ToastOptions = {
    position: "top-right",
    autoClose: 1500,
    hideProgressBar: false,
    closeOnClick: true,
    pauseOnHover: true,
    draggable: true,
    progress: undefined,
    theme: "dark"
};

export function toastInfo( message: string ) {
    toast.info( message, toastConfig );
}

export function toastSuccess( message: string ) {
    toast.success( message, toastConfig );
}

export function toastWarning( message: string ) {
    toast.warn( message, toastConfig );
}

export function toastError( message: string ) {
    toast.error( message, toastConfig );
}
