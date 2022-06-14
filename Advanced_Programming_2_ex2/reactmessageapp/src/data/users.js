import blank_contact from './blank_contact.jpg'

export async function FindUser(username){
    let ret = 0
    const requestOptions = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'accept': '*/*',
        },
        body: JSON.stringify({ id: username , pass: '0'})
    };
    await fetch('http://localhost:5180/api/login', requestOptions)
        .catch(err => setTimeout(() => console.clear())).then(response => ret = response.status);

    return ret == 400;
}

export async function VerifyPassword(username, pass) {
    let ret
    const requestOptions = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'accept': '*/*',
        },
        body: JSON.stringify({ id: username, pass: pass })
    };
    await fetch('http://localhost:5180/api/login', requestOptions)
        .then(response => ret = response.status).catch(err => setTimeout(() => console.clear()));

    return ret == 200;
}

export function GetPhoto(id) {
    return blank_contact;
}

export async function GetContacts(username) {
    let contacts
    await fetch('http://localhost:5180/api/contacts/?user=' + username)
        .then((response) => { return response.json(); })
        .then((data) => contacts = data);

    return contacts;
}


export async function GetChat(myId, contact){
    let chat
    await fetch('http://localhost:5180/api/contacts/' + contact.id + '/messages?user=' + myId)
        .then((response) => { return response.json(); })
        .then((data) => chat = data);
    return chat;
}

export async function AddContactToUser(user, newContact, nickname, contactServer){
    let ret;

     let requestOptions = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'accept': '*/*',
        },
        body: JSON.stringify({
            id: newContact, name: nickname, server: contactServer
        })
    };
    await fetch('http://localhost:5180/api/contacts?user=' + user, requestOptions)
        .then(response => ret = response.status);

    return ret == 201
}


export async function AddUser(userName, password, nickName, profImg) {
    let ret
    const requestOptions = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'accept': '*/*',
        },
        body: JSON.stringify({ id: userName, pass: password })
    };
    await fetch('http://localhost:5180/api/register', requestOptions)
        .then(response => ret = response.status);

    return ret == 200;
}

export async function SendMessage(fromUser, toContact, type, content) {

    const request1Options = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'accept': '*/*',
        },
        body: JSON.stringify({ from: fromUser, to: toContact.id , content: content})
    };
    await fetch('http://' + toContact.server + '/api/transfer', request1Options)
        

    const request2Options = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'accept': '*/*',
        },
        body: JSON.stringify({ content: content })
    };
    await fetch('http://localhost:5180/api/contacts/' + toContact.id + '/messages/?user=' + fromUser, request2Options)
}