// noinspection JSPrimitiveTypeWrapperUsage

import React, { useRef, useState } from "react";
import { AddContactToUser, GetContacts } from "../../data/users"

function AddContact({ user, contacts, setContacts, setContactsToShow }) {

    const [errors, setErrors] = useState('');

    const findContactsNames = contacts.map((contact, key) => {
        return contact.id;
    })

    var contactsNames = findContactsNames;

    const addContactLogic = async function () {
        setErrors('');
        const newContact = addBox.current.value;
        const newNickname = newNickName.current.value;
        const ncServer = server.current.value;
        if (newContact == '' || newNickName == '' || ncServer == '')
            setErrors('Please fill all three fields')
        else if (contactsNames.includes(newContact))
            setErrors('you added this contact already')
        else if (user === newContact)
            setErrors('you can\'t add yourself as a contact')
        else if (await sendInvitation(newContact, ncServer)) {
            addBox.current.value = '';
            newNickName.current.value = '';
            server.current.value = '';
            if (await AddContactToUser(user, newContact, newNickname, ncServer)) {
                setContacts(await GetContacts(user));
                setContactsToShow(await GetContacts(user))
            }
        } else {
            if (newContact !== '') {
                setErrors('no such user in server ' + ncServer)
            }
        }
    }

    const sendInvitation = async function (name, ncServer) {
        var ret = 0;
        const requestOptions = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'accept': '*/*',
            },
            body: JSON.stringify({ from: user, to: name, server: 'localhost:5180' })
        }
        try {
            await fetch('http://' + ncServer + '/api/invitations', requestOptions)
                .then(response => ret = response.status);
        } catch (error) {
            //setErrors('problem with server, are you sure the server is correct?')
        }
        return (ret == 201);
    }

    const addBox = useRef('');
    const newNickName = useRef('');
    const server = useRef('');


    return (
        <div>
            <button type="submit" className="bi bi-person-plus rounded-pill" id="add_contact" data-bs-toggle="modal" data-bs-target="#exampleModal">
            </button>
            <div className="modal fade" id="exampleModal" tabIndex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div className="modal-dialog">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h5 className="modal-title">Add New Contact</h5>
                            <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div className="modal-body">
                            <form className="d-flex" >
                                <input ref={addBox} onKeyDown={(e) => { if (e.key === "Enter") { e.preventDefault(); addContactLogic(e) } }} className="form-control me-2" type="Type-message" placeholder="Write contact's username here" aria-label="Type-message"></input>
                            </form>
                            <form className="d-flex" >
                                <input ref={newNickName} onKeyDown={(e) => { if (e.key === "Enter") { e.preventDefault(); addContactLogic(e) } }} className="form-control me-2" type="Type-message" placeholder="nickName for your contact" aria-label="Type-message"></input>
                            </form>
                            <form className="d-flex" >
                                <input ref={server} onKeyDown={(e) => { if (e.key === "Enter") { e.preventDefault(); addContactLogic(e) } }} className="form-control me-2" type="Type-message" placeholder="server where contact is registereg" aria-label="Type-message"></input>
                            </form>
                            <div className="err_alert" style={errors ? { display: "flex" } : { display: "none" }}>
                                {errors}
                            </div>
                        </div>
                        <div className="modal-footer">
                            {/*<button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Close</button>*/}
                            <button type="button" onClick={addContactLogic} className="btn btn-primary">Add Contact</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default AddContact;