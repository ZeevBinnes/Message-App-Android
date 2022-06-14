import React from "react";
import "./ChatBox.css";
import { GetPhoto, SendMessage } from '../../data/users';
import ChatDisp from "./ChatDisp";
import { formatDateTime } from "../formatDateTime";
import ChatInput from "./ChatInput";
import alt from '../../data/blank_contact.jpg'

function ChatBox({ contact, user, chat, did_data_change, set_did_data_change }) {


    const submitNewMessage = async (message_type, input, fileName) => {

        await SendMessage(user, contact, message_type, input, fileName);
        set_did_data_change(!did_data_change);
    }
        
    if (contact == null) {
        return (
            <div className="chatbox">
                <div className="fill"></div>
            </div>
        )
    } else {
        return (
            <div className="chatbox col-8 limit_column_height">
                <div className="chat_header">
                    <img src={GetPhoto(contact)} alt={alt} className="cont_imgs"></img>
                    <div className="chat_header_text">
                        <h2>{contact.name}</h2>
                    </div>
                </div>
                <div className="chat_body chat__content">
                    <ChatDisp chat={chat} />
                </div>
                    <ChatInput submitNewMessage={submitNewMessage} />
            </div>
        );
    }
}

export default ChatBox;