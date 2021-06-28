package com.khalej.joud.model;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface apiinterface_home {


    @FormUrlEncoded
    @POST("api/login")
    Call<contact_general_user> getcontacts_login(@Field("phone") String kayWord, @Field("password") String password);

    @FormUrlEncoded
    @POST("api/contacts")
    Call<ResponseBody> CallUs(@Field("name") String name, @Field("email") String address,
                              @Field("message") String message);

    @FormUrlEncoded
    @POST("api/forget/password")
    Call<Reset>getcontacts_ResetPassword(@Field("email") String kayWord);


    @FormUrlEncoded
    @POST("api/forget/password/new")
    Call<Reset>getcontacts_updatePassword(@Field("email") String kayWord,@Field("password") String password);

    @FormUrlEncoded
    @POST("api/forget/password/reset")
    Call<Reset>getcontacts_tokenPassword(@Field("email") String kayWord,@Field("token") String password);


    @GET("api/general?lang=ar")
    Call<contact_general> getcontacts_generalData();
    @GET("api/categories")
    Call<contact_general_category> getcontacts_categores();

    @GET("api/categories/{category_id}/cards?lang=ar")
    Call<contact_general_sub_category> getSubCategory(@Path("category_id") String category_id,@Query("lang") String lang);

    @GET("api/companies")
    Call<contact_general_company> getcontacts_companies(@Query("lang") String lang);

    @GET("api/general")
    Call<contact_general_> getcontacts_g(@Query("lang") String lang);
    @FormUrlEncoded
    @POST("maishwary/api/canceling_order")
    Call<ResponseBody> getcontacts_CancelOrder(@Field("order_id") int order_id, @Field("user_id") int user_id);

    @FormUrlEncoded
    @POST("maishwary/api/rate")
    Call<ResponseBody> getcontacts_AddRate(@Field("to_id") int to_id, @Field("form_id") int form_id, @Field("rate") Float rate,
                                           @Field("des") String des);


    @FormUrlEncoded
    @POST("maishwary/api/delete_notification")
    Call<ResponseBody> getcontacts_CancelNotification(@Field("notification_id") int order_id, @Field("user_id") int user_id);






    @FormUrlEncoded
    @POST("api/orders")
    Call<ResponseBody> content_addOrder(@HeaderMap Map<String, String> headers, @Field("user_id") String user_id, @Field("card_id") String card_id,
                                        @Field("payment")boolean payment
            );

    @FormUrlEncoded
    @POST("api/register")
    Call<contact_general_user> getcontacts_newaccount(@Field("full_name") String name, @Field("password") String password, @Field("email") String address,
                                                  @Field("phone") String phone, @Field("phone_code") String phone_code ,
                                                      @Field("type") String type);



    @GET("api/profile/{id}")
    Call<contact_general_Mycards> getcards(@HeaderMap Map<String, String> headers,@Path("id") String user_id,@Query("lang") String lang);

    @FormUrlEncoded
    @PATCH("api/profile/update")
    Call <contact_general_user_update> updateProfile(@HeaderMap Map<String, String> headers,@Field("full_name") String name, @Field("phone") String phone);

    @GET("api/notifications/")
    Call<contact_notification> getnotification(@HeaderMap Map<String, String> headers);


    @Multipart
    @POST("api/profile/update/logo")
    Call<contact_general_user_update> getcontacts_updateProfile(@HeaderMap Map<String, String> headers,@Part MultipartBody.Part image);

    @FormUrlEncoded
    @PATCH("api/profile/update/password")
    Call <contact_general_user_update> updateProfile_pass(@HeaderMap Map<String, String> headers,
                                              @Field("password") String password);

}

