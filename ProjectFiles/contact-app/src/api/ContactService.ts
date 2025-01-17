import axios from "axios";

const API_URL = "http://localhost:8080/api/v1/contacts";
const DEFAULT_CONFIG = { headers: {
        "Content-Type": "application/json",
    }, withCredentials: false };
const FILE_UPLOAD_CONFIG = { headers: {
        "Content-Type": "multipart/form-data",
    }, withCredentials: false };

export default class ContactService {
    public async getContacts( page: number = 0, size: number = 1 ): Promise<any> {
        return await axios.get( `${ API_URL }?page=${ page }&size=${ size }`, DEFAULT_CONFIG );
    }

    public async saveContact( contact: any ): Promise<any> {
        return await axios.post( API_URL, contact, DEFAULT_CONFIG );
    }

    public async updateContact( id: string, contact: any ): Promise<any> {
        return await axios.post( `${ API_URL }?id=${ id }`, contact, DEFAULT_CONFIG );
    }

    public async updatePhoto( formData: any ): Promise<any> {
        return await axios.post( `${ API_URL }/photo`, formData, FILE_UPLOAD_CONFIG );
    }

    public async delete( id: string ): Promise<void> {
        return await axios.delete( `${ API_URL }/${ id }` );
    }

    public async getContact( id: string ): Promise<any> {
        return await axios.get( `${ API_URL }/${ id }`, DEFAULT_CONFIG );
    }

}
