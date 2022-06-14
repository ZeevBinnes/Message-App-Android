import ContactItem from './ContactItem'
import React from "react";
import '../chatbox/formatTime'

function ContactsListResault({ toShow, allContacts, setDisplayedContact, setToShow, isInSearch }) {
    if (toShow == null) {
        setToShow(allContacts);
        return (<div></div>)
    }
    if (!isInSearch) {
        if (toShow != allContacts)
            setToShow(allContacts);
    }
    const contactsListDisp = toShow.map((contact, key) => {
        return <ContactItem
            Contact={contact}
            setDisplayedContact={setDisplayedContact} 
            key={key} />}
    );

    return(
        <div className='contacts_list'> {contactsListDisp} </div>
    );
}

export default ContactsListResault;