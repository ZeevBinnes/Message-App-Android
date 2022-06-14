import { formatDateTime } from "../formatDateTime";
import { GetChat, GetNickName, GetPhoto } from '../../data/users';

import React from 'react'
function ContactItem({ Contact, setDisplayedContact }) {

    const clickedContact = function(){
        setDisplayedContact(Contact);
    }

    return(
        <li className="chat_list_item list-group-item d-flex justify-content-between align-items-center container-fluid"
            onClick={clickedContact}>
            <img src={GetPhoto(Contact)} alt={" "} className="proph_imgs"></img>
            <div className="contacts_content">
                <div className="contacts_spaces">
                    <span className="fs-5 name_in_chat_list col-sm-10">{Contact.name}</span>
                    <span className="hour_and_time col-sm-2">{Contact.lastdate != null ?formatDateTime(new Date(Contact.lastdate)) : ''}</span>
                </div>
                <span className="contacts_spaces message_preview"
                    title={Contact.last}>{Contact.last}</span>
            </div>
        </li>
    );
}
export default ContactItem;