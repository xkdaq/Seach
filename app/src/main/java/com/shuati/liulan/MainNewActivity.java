package com.shuati.liulan;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dalipan.search.R;
import com.shuati.liulan.list.ListBean;
import com.shuati.liulan.qbank.QbankBean;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//榴莲小站
public class MainNewActivity extends AppCompatActivity {

    public String BASE_URL = "https://liulianxz.com/";
    public String serial_no = "z0abkYgmICBf";
    public String scene = "1089"; //小程序的id
    public String access_token = "7dae7991cb64e77904144f7e9bd0487b";

    public String id = "83"; //首页module的id "53"表示是"形势与政策"

    private int index = 0;

    //72 马原
    //73 毛概
    //74 史纲
    //75 思修
    //86 习概
    //87 专项训练

    public static Interceptor MODIFIED_INTERCEPTOR = chain -> {
        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder()
                .header("Host", "liulianxz.com")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Connection", "keep-alive")
                .header("Accept", "*/*")
                .header("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 11_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E217 MicroMessenger/6.8.0(0x16080000) NetType/WIFI Language/en Branch/Br_trunk MiniProgramEnv/Mac")
                //.header("Referer", "https://servicewechat.com/wx8a6b75190016132e/6/page-frame.html")
//                .header("Referer", "https://servicewechat.com/wx8a6b75190016132e/6/page-frame.html")
                .header("Referer", "https://servicewechat.com/wx8a6b75190016132e/6/page-frame.html")
                .header("Accept-Language", "zh-CN,zh-Hans;q=0.9")
                .header("Accept-Encoding", "gzip, deflate, br");
        Request requestNew = requestBuilder.build();
        return chain.proceed(requestNew);
    };


    OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(MODIFIED_INTERCEPTOR)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build();

    ApiService service = retrofit.create(ApiService.class);

    //开关 是否写入文件-问题
    private boolean openQuestion = true;
    //开关 是否写入文件-答案
    private boolean openAnswer = true;
    private EditText idEditText;
    private EditText editText;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取题库详情
        findViewById(R.id.btn_get1).setOnClickListener(v -> {
            locationQuestions.clear();
            get1();
        });

        //获取某一节的题目列表
//        findViewById(R.id.btn_get2).setOnClickListener(v -> {
//            locationQuestions.clear();
//            get2("302");
//        });
        //将获取到的题目集合排序之后写入文件
        findViewById(R.id.btn_get22).setOnClickListener(v -> {
            bianliLocation();
        });

        //将答案的文件写入问题的文件
        findViewById(R.id.btn_get222).setOnClickListener(v -> {
            read();
        });

        //获取题目
//        findViewById(R.id.btn_get3).setOnClickListener(v -> {
//            locationQuestions.clear();
//            get3("9554");
//        });


        idEditText = findViewById(R.id.idEditText);
        editText = findViewById(R.id.editText);

        //设置默认值
        idEditText.setText(id);

        findViewById(R.id.btn_get4).setOnClickListener(v -> {
            id = idEditText.getText().toString();
            String s = editText.getText().toString();
            index = Integer.parseInt(s);
            Toast.makeText(this, "重置成功！当前id=" + id + ",index=" + index, Toast.LENGTH_SHORT).show();
        });
    }


    //获取题库详情
    private void get1() {
        //https://liulianxz.com/api/qbank/get.json?access_token=db7a664f783172a8e527a41a12130e3a&scene=1089&id=53
        //https://liulianxz.com/api/qbank/get.json?access_token=14a40aefd2dfe68b29890614c2c2fd3a&scene=1089&id=72
        Call<QbankBean> octocat = service.qbank(
                access_token,
                scene,
                id
        );

        octocat.enqueue(new Callback<QbankBean>() {
            @Override
            public void onResponse(Call<QbankBean> call, Response<QbankBean> response) {
                QbankBean body = response.body();
                if (body != null) {
                    String errcode = body.getErrcode();
                    Log.e("get1", "errcode1 = " + errcode);

                    //章 集合
                    List<QbankBean.Chapters> chapters = body.getChapters();
                    //第一章
                    QbankBean.Chapters chapters0 = chapters.get(5);
                    List<QbankBean.Chapters.Children> children = chapters0.getChildren();
                    //取第一节
                    QbankBean.Chapters.Children children1 = children.get(index);
                    String id = children1.getId();
                    get2(id);

//                    //直接取题目
//                    QbankBean.Chapters chapters1 = chapters.get(index);
//                    int id1 = chapters1.getId();
//                    get2(""+id1);
                }
            }

            @Override
            public void onFailure(Call<QbankBean> call, Throwable t) {
                Log.e("get1", "errcode1 = " + t.getMessage());
            }
        });
    }

    private List<ListBean.Questions> questions;

    //获取某一节的题目列表
    private void get2(String chapter_id) {
        //https://liulianxz.com/api/question/list.json?v=2&access_token=db7a664f783172a8e527a41a12130e3a&scene=1089&chapter_id=302&post_id=53&serial_no=dhKBszuMSqsD
        Call<ListBean> octocat = service.list(2,
                access_token,
                scene,
                chapter_id,
                id,
                serial_no
        );
        octocat.enqueue(new Callback<ListBean>() {

            @Override
            public void onResponse(Call<ListBean> call, Response<ListBean> response) {
                ListBean body = response.body();
                if (body != null) {
                    String errcode = body.getErrcode();
                    Log.e("xxx", "errcode2 = " + errcode);

                    questions = body.getQuestions();

                    for (ListBean.Questions question : questions) {
                        get3(question.getId());
                    }
                }
            }

            @Override
            public void onFailure(Call<ListBean> call, Throwable t) {
                Log.e("xxx", "errcode2 = " + t.getMessage());
            }
        });
    }

    List<QuestionBean.Question> locationQuestions = new ArrayList<>();


    //获取题目 9554 dhKBszuMSqsD HTTP/1.
    private void get3(String id) {
        Call<QuestionBean> octocat = service.listRepos(
                access_token,
                scene,
                id,
                serial_no
        );

        octocat.enqueue(new Callback<QuestionBean>() {
            @Override
            public void onResponse(Call<QuestionBean> call, retrofit2.Response<QuestionBean> response) {
                if (response != null) {
                    QuestionBean body = response.body();
                    if (body != null) {
                        QuestionBean.Question question = body.getQuestion();
                        locationQuestions.add(question);
                        //print(question);
                    }
                }
            }

            @Override
            public void onFailure(Call<QuestionBean> call, Throwable t) {
                Log.e("xxx", "errcode3 = " + t.getMessage());
            }
        });

    }


    private void bianliLocation() {
        if (locationQuestions != null && locationQuestions.size() > 0) {
            Collections.sort(locationQuestions, new Comparator<QuestionBean.Question>() {
                @Override
                public int compare(QuestionBean.Question question1, QuestionBean.Question question2) {
                    return question1.getId() - question2.getId();
                }
            });
//            for (QuestionBean.Question question : locationQuestions) {
//                print(question);
//            }

            int count = 1; // 计数器
            for (int i = 0; i < locationQuestions.size(); i++) {
                QuestionBean.Question question = locationQuestions.get(i);
                print(count, question);
                count++; // 计数器加1
            }
        }
    }

    private void print(int count, QuestionBean.Question question) {
        int id = question.getId();
        if (openQuestion) {
            questionFile(count + "." + question.getQuestion());
        }
        int type = question.getType(); //type 1为单选题 2为多选题

        Log.e("xxxx", count + "." + question.getQuestion());
        List<QuestionBean.Question.Options> options = question.getOptions();
        for (QuestionBean.Question.Options option : options) {
            String option1 = option.getOption();
            option1 = option1.replace("<p class='option option-A'>", "");
            option1 = option1.replace("<p class='option option-B'>", "");
            option1 = option1.replace("<p class='option option-C'>", "");
            option1 = option1.replace("<p class='option option-D'>", "");
            option1 = option1.replace(" </p>", ".");
            if (openQuestion) {
                questionFile(option1);
            }
            Log.e("xxxx", option1);
        }
        if (openAnswer) {
            String correct_answer = question.getCorrect_answer();
            if (correct_answer.contains(",")) {
                correct_answer = correct_answer.replace(",", "");
            }
            anwserFile(count + ".答案：" + correct_answer);
            String explain = question.getExplain();
            if (TextUtils.isEmpty(explain)) {
                explain = "来源：24肖秀荣《形势与政策》无解析；";
            }
            anwserFile("解析：" + explain + "【小程序：猴哥刷题】");
        }
    }


    public void questionFile(String conent) {
        try {
            FileWriter writer = new FileWriter(getFilesDir() + "example.txt", true);
            writer.write(conent + "\r\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void anwserFile(String conent) {
        try {
            if (conent.contains("<br/>")) {
                conent = conent.replaceAll("<br/>", "\r\n");
            }
            if (conent.contains("<br />")) {
                conent = conent.replaceAll("<br />", "\r\n");
            }
            if (conent.contains("点拨：")) {
                conent = conent.replaceAll("点拨：", "【点拨】");
            }

            conent = conent.replaceAll("（正文位置  分享位置）", "【正文位置】");
            conent = conent.replaceAll("导论", "\n【分享位置】导论");
            conent = conent.replaceAll("第一章", "\n【分享位置】第一章");
            conent = conent.replaceAll("第二章", "\n【分享位置】第二章");
            conent = conent.replaceAll("第三章", "\n【分享位置】第三章");
            conent = conent.replaceAll("第四章", "\n【分享位置】第四章");
            conent = conent.replaceAll("第五章", "\n【分享位置】第五章");
            conent = conent.replaceAll("第六章", "\n【分享位置】第六章");
            conent = conent.replaceAll("第七章", "\n【分享位置】第七章");
            conent = conent.replaceAll("第八章", "\n【分享位置】第八章");
            conent = conent.replaceAll("第九章", "\n【分享位置】第九章");
            conent = conent.replaceAll("第十章", "\n【分享位置】第十章");
            conent = conent.replaceAll("第十一章", "\n【分享位置】第十一章");
            conent = conent.replaceAll("第十二章", "\n【分享位置】第十二章");
            conent = conent.replaceAll("第十三章", "\n【分享位置】第十三章");
            conent = conent.replaceAll("第十四章", "\n【分享位置】第十四章");
            conent = conent.replaceAll("第十五章", "\n【分享位置】第十五章");
            conent = conent.replaceAll("第十六章", "\n【分享位置】第十六章");
            conent = conent.replaceAll("第十七章", "\n【分享位置】第十七章");

            FileWriter writer = new FileWriter(getFilesDir() + "anwser.txt", true);
            writer.write(conent + "\r\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void read() {
        try {
            questionFile("参考答案：");
            questionFile("一、单项选择题");
            FileReader fileReader = new FileReader(getFilesDir() + "anwser.txt");
            BufferedReader breader = new BufferedReader(fileReader);
            String str;
            while ((str = breader.readLine()) != null) {
                questionFile(str);
            }
            Log.e("xxxx", "写入文件完成");
            fileReader.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }


}
