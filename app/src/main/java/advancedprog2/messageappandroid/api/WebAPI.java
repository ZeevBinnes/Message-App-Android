package advancedprog2.messageappandroid.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface WebAPI {
    @POST("login")
    Call<Void> login(@Body ApiUser apiUser);

    @POST("register")
    Call<Void> register(@Body ApiUser apiUser);

    @GET("contacts")
    Call<List<ApiContact>> GetContacts(@Query("user") String user);

    @GET("contacts/{id}")
    Call<ApiContact> GetContact(@Query("user") String userid, @Path("id") String contactid);

    @POST("contacts")
    Call<Void> AddContact(@Query("user") String userid,@Body ApiContact contact);

    @POST("invitations")
    Call<Void> Invitation(@Body ApiFormat contact);

    @POST("transfer")
    Call<Void> transfer(@Body ApiFormat message);

    @GET("contacts/{id}/messages")
    Call<List<ApiMessage>> GetMessages(@Query("user") String userid, @Path("id") String contactid);

    @POST("contacts/{id}/messages")
    Call<Void> PostMessage(@Query("user") String userid, @Path("id") String contactid, @Body ApiFormat content);

    @DELETE("posts/{id}")
    Call<Void> deletePost(@Path("id") int id);
}
