import ContactsListResault from './ContactsListResault';
import SearchContacts from './SearchContacts';
import AddContact from './AddContact';
import React, { useEffect, useState } from 'react';
import { GetContacts, GetNickName, GetPhoto } from '../../data/users';
import "./contacts.css"
import alt from '../../data/blank_contact.jpg'


function ContactsBar({ user, contacts, setDisplayedContact, setDataChanged }) {

    //const [contacts, SetContacts] = useState(recContacts);

    const [contactsToShow, setContactsToShow] = useState(null);

    const [inSearch, setInSearch] = useState(false);

    const SetContacts = (contList) => {
        setDataChanged(true);
    }


    const doSearch = function (q) {
        setInSearch(q != '');
        setContactsToShow(contacts.filter((contact) => contact.name.includes(q)));
    }

    if (contacts != null) {
        return (
            <aside className="sidebar col-4 limit_column_height">
                <div className="user_bar">
                    <img src={GetPhoto(user)} alt={alt} className="proph_imgs"></img>
                    <div className="chat__contact-wrapper">
                        <h1 className="chat__contact-name"> {user} </h1>
                    </div>
                    <AddContact user={user} contacts={contacts} setContacts={SetContacts} setContactsToShow={setContactsToShow} />
                </div>
                <SearchContacts doSearch={doSearch} />
                <ContactsListResault toShow={contactsToShow} user={user} allContacts={contacts}
                    setDisplayedContact={setDisplayedContact} setToShow={setContactsToShow}
                    isInSearch={inSearch}/>
            </aside>
        )
    } else {
        setDataChanged(true);
        return(<div></div>)
    }
}

export default ContactsBar;