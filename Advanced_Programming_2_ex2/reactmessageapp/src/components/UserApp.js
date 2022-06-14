import React, { useState, useEffect, useRef } from 'react';
import ContactsBar from './contacts/ContactsBar';
import ChatBox from './chatbox/ChatBox'
import { GetChat, GetContacts } from '../data/users'
import { HubConnectionBuilder, LogLevel } from '@microsoft/signalr';

function UserApp({ user }) {

    // add here a use State (maybe with ref) for updating user.
    const [did_data_change, set_did_data_change] = useState(false);

    // this useState updates the contact that should be showd in the chat box
    const [displayedContact, setDisplayedContact] = useState(null);

    const stateRef = useRef();

    stateRef.current = displayedContact;

    const [chat, setChat] = useState(null);

    const [contacts, setContacts] = useState(null);

    const [connection, setConnection] = useState()

    // join signalR
    const joinHub = async (user) => {
        try {
            const connection = new HubConnectionBuilder()
                .withUrl("http://localhost:5180/login")
                .configureLogging(LogLevel.Information)
                .build();

            connection.on("ReceiveMessage", () => {
                setDataChanged();
            });

            await connection.start();
            await connection.invoke("Login", user )

            setConnection(connection);
        } catch (e) {
            console.log(e);
        }
    }

    useEffect(() => {
        joinHub(user);
    }, [])

    const changeDisplayedContact = async function (contact) {
        let mychat = await GetChat(user, contact)
        setChat(mychat);
        setDisplayedContact(contact);
    }

    const setDataChanged = async function (c) {
        if (stateRef.current != null)
            await changeDisplayedContact(stateRef.current);
        let myContacts = await GetContacts(user);
        setContacts(myContacts);
        set_did_data_change(!did_data_change);
    }

    return (
        <div className="user_app">
            <ContactsBar user={user} contacts={contacts}
                setDisplayedContact={changeDisplayedContact} setDataChanged={setDataChanged} />
            <ChatBox contact={displayedContact} user={user} chat={chat}
                did_data_change={did_data_change} set_did_data_change={setDataChanged} />
        </div>
    );
}

export default UserApp;