# Message-App-Android

This app contains two parts:
A server, which runs both the Api and the internet app,
and an android application.

## Installing and Running Instructions:

clone the repository, and open it with android studio. make sure you have `Firebase` installed on your IDE.

With Visual Studio, open the sub directory `Advanced_Programming_2_ex2` and open the solution `MessageApp.sln`.
In the package manager, type "Update-Database", to update your database to fit ours.

Run the server from VS, and run the application on android studio.
In order to get to the browser app, use your browser and browse to "localhost:5180".

## notes

### server conection:

The server runs on `localhost:5180`.
you can run the app also without connectiong to the server. However, you can't register or add a contact without connecting to it.
Once you registered, you can login and see your contacts and messages also without the server.
When you connect to the server again, most changes you have done unconnected will be erased.

### adding contacts:

You can add contacts from the android app or the desktop app.

When adding from the android app, the server should be "10.0.2.2:5180", 
and when adding from the desktop app the server should be "localhost:5180".

However, if a android user addes a desktop user, he can only send messages to him, and not receive.
Similarly, a desktop user can add an android user and send messages to him, but can't receive from him.
This is due to the way we were told to save the contacts servers,
and beacuse the browser doesn't recognize "10.0.2.2", and the app doesn't recognize "localhost".

An android user can add another android user, and they can communicate regularly. Similarly, a desktop suer can add another desktop user withou problems.

### some bugs:

* When entering a contact, sometines the messages don't appear in the first time. 
In that case, simply go back to the contacts page, and click on the wanted contact again.
* For one of us, the firebase worked properly, and the smartphone got a notification when it received a message.
for the other one of us, it didn't work, and the phone didn't get the notification (but got the message).
We hope it will work for you.

Have fun! Hopefully more than we had...
