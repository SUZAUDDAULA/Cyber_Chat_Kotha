package com.opus_bd.myapplication.APIClient;


import com.opus_bd.myapplication.Model.Group.GroupPost;
import com.opus_bd.myapplication.Model.User.RankModel;
import com.opus_bd.myapplication.Model.User.RegisterModel;
import com.opus_bd.myapplication.Model.User.SubUnitsModel;
import com.opus_bd.myapplication.Model.User.UnitModel;
import com.opus_bd.myapplication.Model.User.UserListModel;
import com.opus_bd.myapplication.Model.User.UserLoginModel;
import com.opus_bd.myapplication.Model.User.UserModel;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface RetrofitService {

    @POST("api/Auth/login")
    Call<UserModel> login(@Body UserLoginModel userLoginModel);

    @GET("api/Message/GetEmployeeInfoExceptMe/{userId}")
    Call<List<UserListModel>> GetEmployeeInfoExceptMe(@Path("userId") int userId);

    @GET("api/Message/GetGroupsForMe/{userId}")
    Call<List<UserModel>> GetGroupsForMe(@Path("userId") int userId);


    @GET("api/Message/GetMessageEmployeeInfoById/{Id}")
    Call<UserListModel> GetMessageEmployeeInfoById(@Path("Id") int Id);

    @GET("api/Message/GetAllMessageGroupMemberBygrpId/{groupId}")
    Call<List<UserModel>> GetAllMessageGroupMemberBygrpId(@Path("groupId") int groupId);

    @GET("api/Message/GetAllEmployeesNotInGroupBygrpId/{groupId}")
    Call<List<UserModel>> GetAllEmployeesNotInGroupBygrpId(@Path("groupId") int groupId);

    @GET("api/Message/GetAllMessageGroup")
    Call<List<UserModel>> GetAllMessageGroup();

    @GET("global/api/GetAllUnits")
    Call<List<UnitModel>> GetAllUnits();

    @GET("global/api/GetAllSubUnits")
    Call<List<SubUnitsModel>> GetAllSubUnits();

    @GET("global/api/RankListForCChat")
    Call<List<RankModel>> RankListForCChat();

    @POST("Auth/Account/CChatRegister")
    Call<String> CChatRegister(@Body RegisterModel registerModel);

    @Headers({"Accept: application/json"})
    @Multipart
    @POST("Auth/Account/CChatRegisterWithImage")
    Call<String> CChatRegisterWithImage(@Part MultipartBody.Part file);

    @POST("Message/MessageBox/GroupCreate")
    Call<Integer> GroupCreate(@Body GroupPost groupPost);


}