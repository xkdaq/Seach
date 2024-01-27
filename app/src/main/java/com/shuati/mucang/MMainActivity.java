
package com.shuati.mucang;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dalipan.search.R;
import com.google.gson.Gson;
import com.shuati.liulan.ApiService;
import com.shuati.mucang.bean.MDetailBean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//木仓专业课刷题
public class MMainActivity extends AppCompatActivity {

    //主页课
//    public String BASE_URL = "https://st3.quyanedu.com/";
    //政治
    public String BASE_URL = "https://st2st2api2023.quyanedu.com/";
    public String member_id = "183923"; //某一年
    public int subject_id = 44961; //开始题目
    //43337
    //43370
    //43403

    public String key = "c68219d5f6feb7b3e12aa01b4eb6bcc8"; //这个应该用户的key

    public String jiexiEx = "可以在微信公众号【木仓考研】回复“米一”免费领米一电子版（附赠魔笛背诵重点+选择题答题模板）。";

    OkHttpClient client = new OkHttpClient.Builder()
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

    private int number = 33;
    private EditText idEditText;
    private EditText editText;
    private EditText editTextNumber;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mucang);

        //获取题库详情
        findViewById(R.id.btn_get1).setOnClickListener(v -> {
            locationQuestions.clear(); //清楚本地的集合
            deleteFolder(); //删除之前的文件
            //get1();
            bianli();
        });

        //将获取到的题目集合排序之后写入文件
        findViewById(R.id.btn_get22).setOnClickListener(v -> {
            bianliLocation();
        });

        findViewById(R.id.btn_get222).setOnClickListener(v -> {

        });

        editTextNumber = findViewById(R.id.editTextNumber);
        editTextNumber.setText("" + number);

        editText = findViewById(R.id.editText);
        editText.setText(member_id);

        idEditText = findViewById(R.id.idEditText);
        idEditText.setText("" + subject_id);
        //确定
        findViewById(R.id.btn_get4).setOnClickListener(v -> {
            number = Integer.parseInt(editTextNumber.getText().toString().trim());
            member_id = editText.getText().toString().trim();
            subject_id = Integer.parseInt(idEditText.getText().toString().trim());
            Toast.makeText(MMainActivity.this, "修改成功，当前member_id = " + member_id + ",当前id = " + subject_id, Toast.LENGTH_SHORT).show();
        });
    }


    private void bianli() {
        for (int i = 0; i < number; i++) {
            subject_id = subject_id + 1;
            get1("" + (subject_id - 1));
        }

        new Handler().postDelayed(() -> {
            bianliLocation();
        }, 3000);
    }

    //获取题库详情
    private void get1(String subject_id_new) {
//        Log.e("xxxxx","==="+subject_id_new);
        Call<MDetailBean> octocat = service.mudetail(member_id, subject_id_new, key, "40");
        //https://st3.quyanedu.com/api/index/subject_info?member_id=110095&subject_id=15035&key=1b8e71ba5347e0b2450af18a593b1d8b
        octocat.enqueue(new Callback<MDetailBean>() {
            @Override
            public void onResponse(Call<MDetailBean> call, Response<MDetailBean> response) {
                if (response != null) {
                    MDetailBean body = response.body();
                    if (body != null) {
                        int code = body.getCode();
                        if (code == 200) {
//                            SDetailBean.DataBean data = body.getData();
//                            List<SDetailBean.DataBean.ListBean> list = data.getList();
                            Log.e("xxx", "获取题目成功=" + new Gson().toJson(body.getDatas()));
//                            locationQuestions = list;
                            MDetailBean.DatasBean datas = body.getDatas();
                            locationQuestions.add(datas);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MDetailBean> call, Throwable t) {
                t.printStackTrace();
                Log.e("xxx", "获取题目失败=" + t.getMessage());
            }
        });
    }

    List<MDetailBean.DatasBean> locationQuestions = new ArrayList<>();

    private void bianliLocation() {
        Log.e("xxxxx", "locationQuestions 大小 =" + locationQuestions.size());
        if (locationQuestions != null && locationQuestions.size() > 0) {
            Collections.sort(locationQuestions, new Comparator<MDetailBean.DatasBean>() {
                @Override
                public int compare(MDetailBean.DatasBean o1, MDetailBean.DatasBean o2) {
                    return o1.getId() - o2.getId();
                }
            });

            int count = 1; // 计数器
            for (int i = 0; i < locationQuestions.size(); i++) {
                MDetailBean.DatasBean question = locationQuestions.get(i);
                print(count, question);
                count++; // 计数器加1
            }
        }
    }

    private void print(int count, MDetailBean.DatasBean question) {
        //if (question.getType() != 1) return;
        String title = question.getSubject_str();
        title = title.replaceAll(" ", "");
        title = title.replaceAll("（）", "( )");
        title = title.replaceAll("\\(\\)", "( )");
        title = title.replaceAll("\\(　　\\)", "( )");
        title = title.replaceAll("（\t）", "( )");
        questionFile(count + "." + title);

        String option_a = question.getOption_str().get(0).getContent();
        questionFile("A." + option_a);
        String option_b = question.getOption_str().get(1).getContent();
        questionFile("B." + option_b);
        String option_c = question.getOption_str().get(2).getContent();
        questionFile("C." + option_c);
        String option_d = question.getOption_str().get(3).getContent();
        questionFile("D." + option_d);

        String right_text = question.getCorrect_answer();
        right_text = right_text.replaceAll(",", "");
        questionFile("答案：" + right_text);

        String jiexi = question.getAnalysis_content();
        jiexi = jiexi.replaceAll(" ", "");
        jiexi = jiexi.replaceAll("<br/>", "\r\n");
        jiexi = jiexi.replaceAll("<br />", "\r\n");
        jiexi = jiexi.replaceAll("点拨：", "【点拨】");
//            jiexi = jiexi.replaceAll("A.", "选项A ");
//            jiexi = jiexi.replaceAll("B.", "选项B ");
//            jiexi = jiexi.replaceAll("C.", "选项C ");
//            jiexi = jiexi.replaceAll("D.", "选项D ");
        jiexi = jiexi.replaceAll("解析", "\r\n");
        jiexi = jiexi.replaceAll(jiexiEx, "");
//            jiexi = StringEscapeUtils.unescapeHtml4(jiexi);
        if (TextUtils.isEmpty(jiexi)) {
            jiexi = "暂无解析";
        }
//            String point = question.getPoint();
//            if (TextUtils.isEmpty(point)) {
//                point = "无";
//            }
//            }
        questionFile("解析：");
        questionFile(jiexi);
    }


    public void questionFile(String conent) {
        try {
            FileWriter writer = new FileWriter(getFilesDir() + "example" + member_id + ".txt", true);
            writer.write(conent + "\r\n");
            Log.e("xxxx", conent + "\r\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void deleteFolder() {
        File file1 = new File(getFilesDir() + "example" + member_id + ".txt");
        deleteFolder(file1);
    }

    public void deleteFolder(File file) {
        if (!file.exists())
            return;
        if (file.isDirectory()) {
            File files[] = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                deleteFolder(files[i]);
            }
        }
        file.delete();
    }
}
