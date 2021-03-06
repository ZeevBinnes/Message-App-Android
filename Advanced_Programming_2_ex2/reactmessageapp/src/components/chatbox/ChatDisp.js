//import React, { useEffect, useState } from "react";
import formatTime from "./formatTime";
import { formatDateTime } from "../formatDateTime";

function ChatDisp({ chat }) {
    if (chat != null) {
        const chat_disp = chat.map((message, key) => {
            const content = message.content;
            if (true) {
                return (
                    <div className="chat__msg-group" key={key}>
                        <p className={`chat__msg ${message.sent ? "chat__msg--sent" : "chat__msg--rxd"}`}>
                            <span className="mes">{content}</span>
                            <span className="chat__msg-filler"> </span>
                            <span className="chat__msg-footer">
                                {formatDateTime(new Date(message.created))}
                            </span>
                        </p>
                    </div>
                )
            } else if (message.type === 'Image') {
                return (
                    <div className="chat__msg-group" key={key}>
                        <div className={`chat__msg chat__img-wrapper ${message.sender ? "chat__msg--sent" : "chat__msg--rxd"}`}>
                            <img src={message.content} alt="" className="chat__img" />
                            <span className="chat__msg-footer">
                                {formatTime(message.time)}
                            </span>
                        </div>
                    </div>
                )
            } else if (message.type === 'Video') {
                return (
                    <div className="chat__msg-group" key={key}>
                        <div className={`chat__msg chat__img-wrapper ${message.sender ? "chat__msg--sent" : "chat__msg--rxd"}`}>
                            <video src={message.content} className="chat__img" controls />
                            <span className="chat__msg-footer">
                                {formatTime(message.time)}
                            </span>
                        </div>
                    </div>
                )
            } else if (message.type === 'File') {
                return (
                    <div className="chat__msg-group" key={key}>
                        <div className={`chat__msg ${message.sender ? "chat__msg--sent" : "chat__msg--rxd"}`}>
                            {/*<iframe src={message.content} width="800px" height="2100px" />*/}
                            <div>
                                <a href={message.content} download={message.fileName} id='fileDownload'>
                                    <button type="button" className="btn btn-primary bi bi-file-earmark-arrow-down"></button>
                                </a>
                                {" " + message.fileName}
                            </div>
                            <span className="chat__msg-filler"> </span>
                            <span className="chat__msg-footer">
                                {formatTime(message.time)}
                            </span>
                        </div>
                    </div>
                )
            } else if (message.type === 'Audio') {
                return (
                    <div className="chat__msg-group" key={key}>
                        <div className={`chat__msg ${message.sender ? "chat__msg--sent" : "chat__msg--rxd"}`}>
                            <audio controls src={message.content} />
                            <span className="chat__msg-filler"> </span>
                            <span className="chat__msg-footer">
                                {formatTime(message.time)}
                            </span>
                        </div>
                    </div>)
            } else {
                return ("")
            }
        }

        );
        return (chat_disp);
    }
}

export default ChatDisp;
