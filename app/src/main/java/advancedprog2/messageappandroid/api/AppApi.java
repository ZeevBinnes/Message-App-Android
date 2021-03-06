package advancedprog2.messageappandroid.api;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.util.ArrayList;
import java.util.List;

import advancedprog2.messageappandroid.R;
import advancedprog2.messageappandroid.activities.ContactsActivity;
import advancedprog2.messageappandroid.activities.LoginActivity;
import advancedprog2.messageappandroid.database_classes.AppLocalDatabase;
import advancedprog2.messageappandroid.database_classes.Repository;
import advancedprog2.messageappandroid.entities.Contact;
import advancedprog2.messageappandroid.entities.Message;
import advancedprog2.messageappandroid.entities.Session;
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
                .baseUrl("http://" + Session.server + "/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webAPI = retrofit.create(WebAPI.class);

    }

    public void getContacts(String user) {
        Call<List<ApiContact>> call = webAPI.GetContacts(user);
        call.enqueue(new Callback<List<ApiContact>>() {
            @Override
            public void onResponse(Call<List<ApiContact>> call, Response<List<ApiContact>> response) {
                new Thread( () -> {
                    List<Contact> contacts = new ArrayList<>();
                    if (response.body() != null) {
                        for (ApiContact ac : response.body()) {
                            DateTime dt = new DateTime(ac.lastdate);
                            Contact c = new Contact(ac.id, ac.name, ac.server, ac.last, dt.toString(DateTimeFormat.forPattern("d.M.y H:m")), user);
                            contacts.add(c);
                        }
                    }
                    localDb.contactDao().clearContactsOfUser(user);
                    localDb.contactDao().insertList(contacts);
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
                           DateTime dt = new DateTime( am.created ) ;
                           Message m = new Message(am.content, dt.toString(DateTimeFormat.forPattern("d.M.y H:m")), am.sent, user_contact);
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
        Retrofit  retrofit2 = new Retrofit.Builder()
                .baseUrl("http://" + contactServer + "/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WebAPI webAPI2 = retrofit2.create(WebAPI.class);
        Call<Void> call = webAPI2.transfer(af);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {}

            @Override
            public void onFailure(Call<Void> call, Throwable t) { }
        });
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

    public void addContact(String username, Contact contact) {
        ApiFormat af = new ApiFormat(username, contact.getId(), null, Session.server);
        Retrofit  retrofit2 = new Retrofit.Builder()
                .baseUrl("http://" + contact.getServer() + "/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WebAPI webAPI2 = retrofit2.create(WebAPI.class);
        Call<Void> call1 = webAPI2.Invitation(af);
        call1.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    ApiContact ac = new ApiContact(contact.getId(), contact.getName(), contact.getServer(), contact.getLast(), contact.getLastdate());
                    Call<Void> call2 = webAPI.AddContact(username, ac);
                    call2.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call2, Response<Void> response) {
                            if (response.isSuccessful()) {
                                getContacts(username);
                                repository.errMsg.setValue("OK");
                            } else {
                                repository.errMsg.setValue("Failed to add contact");
                            }
                        }
                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            repository.errMsg.setValue("Failed to connect to server");
                        }
                    });
                } else {
                    repository.errMsg.setValue("Failed to add contact");
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                repository.errMsg.setValue("Failed to connect to contact's server");
            }
        });
        return;
    }

    public void login(User user, String token) {
        ApiUser au = new ApiUser(user.getUsername(), user.getPassword(), token);
        Call<Void> call = webAPI.login(au);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    if (repository.getUserById(user.getUsername()) == null) repository.insert(user);
                    repository.errMsg.setValue("OK");
//                    localDb.userDao().insert(user);
                } else {
                    repository.errMsg.setValue("incorrect username or password");
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                repository.errMsg.setValue("Failed to connect to server");
            }
        });
        return;
    }

    public void register(User user, String token) {
        ApiUser au = new ApiUser(user.getUsername(), user.getPassword(), token);
        Call<Void> call = webAPI.register(au);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    if (repository.getUserById(user.getUsername()) == null) repository.insert(user);
                    repository.errMsg.setValue("OK");
                }
                else repository.errMsg.setValue("Register Failed. try a different username.");
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                repository.errMsg.setValue("Failed to connect to server");
            }
        });
        return;
    }

}
