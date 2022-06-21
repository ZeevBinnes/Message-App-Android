package advancedprog2.messageappandroid.api;

import java.util.ArrayList;
import java.util.List;

import advancedprog2.messageappandroid.R;
import advancedprog2.messageappandroid.database_classes.AppLocalDatabase;
import advancedprog2.messageappandroid.entities.Contact;
import advancedprog2.messageappandroid.entities.Message;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppApi {
    Retrofit retrofit;
    WebAPI webAPI;
    AppLocalDatabase localDb;

    public AppApi(AppLocalDatabase localDb){
        this.localDb = localDb;

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
                            Contact c = new Contact(ac.Id, ac.Name, ac.Server, ac.Last, ac.Lastdate, user);
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
        Call<List<ApiMessage>> call = webAPI.GetMessages(user, contact);
        call.enqueue(new Callback<List<ApiMessage>>() {
            @Override
            public void onResponse(Call<List<ApiMessage>> call, Response<List<ApiMessage>> response) {
                String user_contact = user+"-"+contact;
                new Thread(() -> {
                   List<Message> messages = new ArrayList<>();
                   if (response.body() != null) {
                       for (ApiMessage am : response.body()) {
                           Message m = new Message(am.Content, am.Created, am.Sent, user_contact);
                           messages.add(m);
                       }
                   }
                   localDb.messageDao().clearMessagesOfContact(user_contact);
                   localDb.messageDao().insertList(messages);
                });
            }

            @Override
            public void onFailure(Call<List<ApiMessage>> call, Throwable t) { }
        });
    }

    public void sendMessage(String user, String contact, String contactServer, Message message) {
        ApiFormat af = new ApiFormat();
        af.from = user;
        af.to = contact;
        af.server = contactServer;
        af.content = message.getContent();
        Call<Void> call = webAPI.transfer(af);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {}

            @Override
            public void onFailure(Call<Void> call, Throwable t) { }
        });
        // do I need to set "af" again?
        Call<Void> call2 = webAPI.PostMessage(user, contact, af);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                getMessages(user, contact);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) { }
        });
    }

}
