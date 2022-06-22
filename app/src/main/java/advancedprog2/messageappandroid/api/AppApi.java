package advancedprog2.messageappandroid.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import advancedprog2.messageappandroid.R;
import advancedprog2.messageappandroid.database_classes.AppLocalDatabase;
import advancedprog2.messageappandroid.database_classes.Repository;
import advancedprog2.messageappandroid.entities.Contact;
import advancedprog2.messageappandroid.entities.Message;
import advancedprog2.messageappandroid.entities.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppApi {
    Retrofit retrofit;
    WebAPI webAPI;
    AppLocalDatabase localDb;
    private Repository repository;

    public AppApi(AppLocalDatabase localDb, Repository repo){
        this.localDb = localDb;
        this.repository = repo;

        retrofit = new Retrofit.Builder()
                .baseUrl(ContextApplication.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webAPI = retrofit.create(WebAPI.class);

    }
    // any methods needed

    public void getContacts(String user) {
        Call<List<ApiContact>> call = webAPI.GetContacts(user);
        call.enqueue(new Callback<List<ApiContact>>() {
            @Override
            public void onResponse(Call<List<ApiContact>> call, Response<List<ApiContact>> response) {
                new Thread( () -> {
                    List<Contact> contacts = new ArrayList<>();
                    if (response.body() != null) {
                        for (ApiContact ac : response.body()) {
                            Contact c = new Contact(ac.id, ac.name, ac.server, ac.last, ac.lastdate, user);
                            contacts.add(c);
                        }
                    }
                    repository.clearContactsOfUser(user);
                    repository.insertContactList(contacts);
//                    localDb.contactDao().clearContactsOfUser(user);
//                    localDb.contactDao().insertList(contacts);
                }).start();
            }

            @Override
            public void onFailure(Call<List<ApiContact>> call, Throwable t) { }
        });
    }

    public void getMessages(String user, String contact) {
        Call<List<ApiMessage>> call = webAPI.GetMessages(contact, user);
        call.enqueue(new Callback<List<ApiMessage>>() {
            @Override
            public void onResponse(Call<List<ApiMessage>> call, Response<List<ApiMessage>> response) {
                String user_contact = user+"-"+contact;
                new Thread(() -> {
                   List<Message> messages = new ArrayList<>();
                   if (response.body() != null) {
                       for (ApiMessage am : response.body()) {
                           Message m = new Message(am.content, am.created, am.sent, user_contact);
                           messages.add(m);
                       }
                   }
                   localDb.messageDao().clearMessagesOfContact(user_contact);
                   localDb.messageDao().insertList(messages);
                }).start();
            }

            @Override
            public void onFailure(Call<List<ApiMessage>> call, Throwable t) { }
        });
    }

    public void sendMessage(String user, String contact, String contactServer, Message message) {
        ApiFormat af = new ApiFormat(user, contact, message.getContent(), contactServer);
        Call<Void> call = webAPI.transfer(af);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {}

            @Override
            public void onFailure(Call<Void> call, Throwable t) { }
        });
        // do I need to set "af" again?
        Call<Void> call2 = webAPI.PostMessage(contact, user, af);
        call2.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call2, Response<Void> response) {
                getMessages(user, contact);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) { }
        });
    }

    public boolean addContact(String username, Contact contact) {
        final boolean[] didAdd = {false};
        ApiFormat af = new ApiFormat(username, contact.getId(), null, contact.getServer());
        Call<Void> call1 = webAPI.Invitation(af);
        call1.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {}

            @Override
            public void onFailure(Call<Void> call, Throwable t) {}
        });

        ApiContact ac = new ApiContact(contact.getId(), contact.getName(), contact.getServer(), contact.getLast(), contact.getLastdate());
        Call<Void> call2 = webAPI.AddContact(username, ac);
        call2.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call2, Response<Void> response) {
                getContacts(username);
                didAdd[0] = true;
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {}
        });
        return didAdd[0];

    }

    public boolean login(User user, String token, boolean toLogin) {
        boolean didLogin[] = {false, false};
        ApiUser au = new ApiUser(user.getUsername(), user.getPassword(), token);
        Call<Void> call = webAPI.login(au);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    didLogin[0] = true;
                    didLogin[1] = true;
                    if (repository.getUserById(user.getUsername()) == null) repository.insert(user);
//                    localDb.userDao().insert(user);
                } else if(response.code() == 400) {
                    didLogin[1] = true;
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
        if (toLogin) return didLogin[0];
        else return didLogin[1];
    }

    public boolean findUser(User user) {
        int retval = 0;
        ApiUser au = new ApiUser(user.getUsername(), user.getPassword(), null);
        Call<Void> call = webAPI.login(au);
        try {
            retval = call.execute().code();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (retval == 400) return true;
        else return false;
    }

    public boolean register(User user, String token) {
        ApiUser au = new ApiUser(user.getUsername(), user.getPassword(), token);
        Call<Void> call = webAPI.register(au);
        int retval = 0;
        try {
            retval = call.execute().code();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (retval == 200) return true;
        else return false;
    }

}
