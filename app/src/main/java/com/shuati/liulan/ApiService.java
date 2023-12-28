package com.shuati.liulan;


import com.shuati.cangdun.bean.CDetailBean;
import com.shuati.liulan.list.ListBean;
import com.shuati.liulan.qbank.QbankBean;
import com.shuati.mucang.bean.MDetailBean;
import com.shuati.sinan.bean.SDetailBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    //https://liulianxz.com/api/qbank/get.json?access_token=db7a664f783172a8e527a41a12130e3a&scene=1089&id=53
    @GET("api/qbank/get.json")
    Call<QbankBean> qbank(
            @Query("access_token") String access_token,
            @Query("scene") String scene,
            @Query("id") String id
    );

    //https://liulianxz.com/api/question/list.json?v=2&access_token=db7a664f783172a8e527a41a12130e3a&scene=1089&chapter_id=302&post_id=53&serial_no=dhKBszuMSqsD
    @GET("api/question/list.json")
    Call<ListBean> list(
            @Query("v") int v,
            @Query("access_token") String access_token,
            @Query("scene") String scene,
            @Query("chapter_id") String chapter_id,
            @Query("post_id") String post_id,
            @Query("serial_no") String serial_no
    );

    //@Headers({"Host: liulianxz.com", "Content-Type: application/x-www-form-urlencoded", "Connection: keep-alive", "Accept: */*", "User-Agent: Mozilla/5.0 (iPhone; iPhone OS 11_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E217 MicroMessenger/6.8.0(0x16080000) NetType/WIFI Language/en Branch/Br_trunk MiniProgramEnv/Mac", "Referer: https://servicewechat.com/wx8a6b75190016132e/6/page-frame.html", "Accept-Language: zh-CN,zh-Hans;q=0.9", "Accept-Encoding: gzip, deflate, br"})
    @GET("api/question/get.json")
    Call<QuestionBean> listRepos(
            @Query("access_token") String access_token,
            @Query("scene") String scene,
            @Query("id") String id,
            @Query("serial_no") String serial_no
    );



    //https://zztk.cdky100.vip/apitk/detail?tlevel_id=1619&openid=oBSIe5V7NzlYpJN0w0xkJibF5LVY
    @GET("apitk/detail")
    Call<CDetailBean> cdetail(
            @Query("tlevel_id") String tlevel_id,
            @Query("openid") String openid
    );

    @GET("sinan_api/answer/getTopicListCatalogue")
    Call<SDetailBean> sdetail(
            @Query("book_id") String book_id,
            @Query("catalogue_path") String catalogue_path
    );

    @GET("api/index/subject_info")
    Call<MDetailBean> mudetail(
            @Query("member_id") String member_id,
            @Query("subject_id") String subject_id,
            @Query("key") String key,
            @Query("disid") String disid
    );



}
