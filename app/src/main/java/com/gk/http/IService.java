package com.gk.http;

import com.gk.beans.CommonBean;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by JDRY_SJM on 2017/4/17.
 */

public interface IService {
    @FormUrlEncoded
    @POST("app/login/getSalt")
    Call<CommonBean> getSalt(@Field("data") String jsonParameter);

    @FormUrlEncoded
    @POST("app/login/login")
    Call<CommonBean> login(@Field("data") String jsonParameter);

    @FormUrlEncoded
    @POST("app/login/verifyLogin")
    Call<CommonBean> verifyLogin(@Field("data") String jsonParameter);

    @FormUrlEncoded
    @POST("app/login/getVerifyCode")
    Call<CommonBean> getVerityfyCode(@Field("data") String jsonParameter);

    @FormUrlEncoded
    @POST("app/login/updatePassword")
    Call<CommonBean> updatePassword(@Field("data") String jsonParameter);

    @FormUrlEncoded
    @POST("app/login/confirmUpdatePassword")
    Call<CommonBean> confirmUpdatePassword(@Field("data") String data);

    @FormUrlEncoded
    @POST("app/login/updateUserInfo")
    Call<CommonBean> updateUserInfo(@Field("data") String data);

    @Multipart
    @POST("app/api/upload/{image}")
    Call<ResponseBody> uploadImage(@Path("image") String image, @Part("description") RequestBody description,
                                   @Part MultipartBody.Part file);

    /**
     * 获取视频列表
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("app/api/video/getVideoList")
    Call<CommonBean> getVideoList(@Field("data") String data);

    /**
     * 获取资料列表
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("app/api/materials/getMaterialsList")
    Call<CommonBean> getMaterialsList(@Field("data") String data);

    /**
     * 通过资料类型查询列表
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("app/api/materials/getMaterialsByType")
    Call<CommonBean> getMaterialsByType(@Field("data") String data);

    /**
     * 获取评论列表
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("app/api/video/getVideoCommentList")
    Call<CommonBean> getVideoCommentList(@Field("data") String data);

    /**
     * 增加视频评论
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("app/api/video/addVideoComment")
    Call<CommonBean> addVideoComment(@Field("data") String data);

    /**
     * 增加视频点赞接口
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("app/api/video/addVideoZan")
    Call<CommonBean> addVideoZan(@Field("data") String data);

    /**
     * 视频关注数增加
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("app/api/video/addVideoAttention")
    Call<CommonBean> addVideoAttention(@Field("data") String data);

    /**
     * 获取广告
     *
     * @return
     */
    @GET("app/api/ads/getAdsInfoList")
    Call<CommonBean> getAdsInfoList();

    /**
     * 专业列表
     *
     * @return
     */
    @GET("app/api/major/getMajorTypeList")
    Call<ResponseBody> getMajorTypeList();

    /**
     * 专业信息
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("app/api/major/getMajorInfoList")
    Call<CommonBean> getMajorInfoList(@Field("data") String data);

    /**
     * 专业查询接口
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST(" app/api/major/getMajorListByName")
    Call<ResponseBody> getMajorListByName(@Field("data") String data);
}
