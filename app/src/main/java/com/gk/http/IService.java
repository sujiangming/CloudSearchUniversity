package com.gk.http;

import com.gk.beans.CommonBean;
import com.gk.beans.MaterialItemBean;

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
import retrofit2.http.Url;

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
    Call<ResponseBody> verifyLogin(@Field("data") String jsonParameter);

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
     * 获取首页-资料-资料列表
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("app/api/materials/getMaterialsList")
    Call<CommonBean> getMaterialsList(@Field("data") String data);

    /**
     * 获取资料类型（名师讲堂、历史真题、模拟试卷）列表
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("app/api/materials/getMaterialsByType")
    Call<CommonBean> getMaterialsByType(@Field("data") String data);

    /**
     * 获取科目（语文、数学、英语等）资料列表
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("app/api/materials/getMaterialsByCourse")
    Call<MaterialItemBean> getMaterialsByCourse(@Field("data") String data);

    /**
     * 通过资料名称获取资料列表（模糊查询)
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("app/api/materials/getMaterialsByName")
    Call<CommonBean> getMaterialsByName(@Field("data") String data);

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
    Call<ResponseBody> getMajorInfoList(@Field("data") String data);

    /**
     * 专业查询接口
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST(" app/api/major/getMajorListByName")
    Call<ResponseBody> getMajorListByName(@Field("data") String data);

    /**
     * 招生计划查询接口
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("app/api/university/getUniRecruitPlanList")
    Call<CommonBean> getUniRecruitPlanList(@Field("data") String data);


    /**
     * 高校查询接口
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("app/api/university/getUniversityList")
    Call<ResponseBody> getUniversityList(@Field("data") String data);

    /**
     * 大学排名查询接口
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("app/api/university/getUniRankingList")
    Call<CommonBean> getUniRankingList(@Field("data") String data);


    /**
     * 查询问题列表接口
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("app/api/authority/getQuestionList")
    Call<CommonBean> getQuestionList(@Field("data") String data);

    /**
     * 增加浏览问题数量
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("app/api/authority/addViewTimes")
    Call<CommonBean> addViewTimes(@Field("data") String data);

    /**
     * 增加关注问题数量
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("app/api/authority/addAttentionTimes")
    Call<CommonBean> addAttentionTimes(@Field("data") String data);

    /**
     * 查询回答列表接口
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("app/api/authority/getAnswerList")
    Call<CommonBean> getAnswerList(@Field("data") String data);

    /**
     * 发表回答接口
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("app/api/authority/addAnswer")
    Call<CommonBean> addAnswer(@Field("data") String data);

    /**
     * 获取客服对话列表接口
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("app/api/sys/getMyMessageList")
    Call<CommonBean> getMyMessageList(@Field("data") String data);


    /**
     * 联系客服发送消息接口
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("app/api/sys/sendMessage")
    Call<CommonBean> sendMessage(@Field("data") String data);

    /**
     * 同分去向接口
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("app/api/university/getSameScoreDirection")
    Call<CommonBean> getSameScoreDirection(@Field("data") String data);


    /**
     * 心理测试霍兰德试题接口
     *
     * @return
     */
    @GET("app/api/career/getCareerTestHld")
    Call<CommonBean> getCareerTestHld();

    /**
     * 心理测试 生成霍兰德测试报告接口
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("app/api/career/getHldTestReport")
    Call<CommonBean> getHldTestReport(@Field("data") String data);

    /**
     * 心理测试 通过用户查询霍兰德测试报告接口
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("app/api/career/getHldTestReportByUser")
    Call<CommonBean> getHldTestReportByUser(@Field("data") String data);


    /**
     * 心理测试 MBTI试题接口
     *
     * @return
     */
    @GET("app/api/career/getCareerTestMbti")
    Call<CommonBean> getCareerTestMbti();

    /**
     * 心理测试 生成MBTI测试报告接口
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("app/api/career/getMbtiTestReport")
    Call<CommonBean> getMbtiTestReport(@Field("data") String data);

    /**
     * 心理测试 通过用户查询MBTI测试报告接口
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("app/api/career/getMbtiTestReportByUser")
    Call<CommonBean> getMbtiTestReportByUser(@Field("data") String data);


    /**
     * 获取微信调用access_token接口
     *
     * @return
     */
    @GET
    Call<ResponseBody> getAccessToken(@Url String url);

    /**
     * 获取微信调用用户信息接口
     *
     * @return
     */
    @GET
    Call<ResponseBody> getWXUserInfo(@Url String url);

    /**
     * 微信登录接口
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("app/login/weixinLogin")
    Call<CommonBean> weixinLogin(@Field("data") String data);

    /**
     * 微信绑定登录接口
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("app/login/userBindingWeixin")
    Call<CommonBean> userBindingWeixin(@Field("data") String data);

    /**
     * 生成志愿报告接口
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("app/api/report/generate")
    Call<CommonBean> generateWishReport(@Field("data") String data);

    /**
     * 录取风险接口
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("app/api/adrisk/evaluateReport")
    Call<CommonBean> evaluateReport(@Field("data") String data);

    /**
     * 更新考生意向高校接口
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("app/api/intent/updateUserIntentSch")
    Call<CommonBean> updateUserIntentSch(@Field("data") String data);

    /**
     * 更新考生意向省份接口
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("app/api/intent/updateUserIntentArea")
    Call<CommonBean> updateUserIntentArea(@Field("data") String data);

    /**
     * 忘记密码接口
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("app/login/forgetPassword")
    Call<CommonBean> forgetPassword(@Field("data") String data);

    /**
     * VIP充值 生成签名订单接口
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("app/api/order/addUserOrder")
    Call<CommonBean> addUserOrder(@Field("data") String data);


}
