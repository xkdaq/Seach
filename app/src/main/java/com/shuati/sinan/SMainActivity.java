
package com.shuati.sinan;

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
import com.shuati.liulan.ApiService;
import com.shuati.sinan.bean.SDetailBean;

//import org.apache.commons.lang3.StringEscapeUtils;

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

//司南教育学
public class SMainActivity extends AppCompatActivity {

    public String BASE_URL = "https://msky.tykyedu.com/";
    public String book_id = "44"; //题库的id
    public String catalogue_path = "178-"; //章节

    //https://msky.tykyedu.com/sinan_api/answer/getTopicListCatalogue?book_id=41&catalogue_path=78-&topic_type=-1&topic_count=0&mstr=7162119405342a8a62361d923d7d866e585c0c50065d352d612248337889378f738e983f8b0777610322667831104601462993908741025311707081ad1af773e56c423051b45b48096a
    //https://msky.tykyedu.com/sinan_api/answer/getTopicListCatalogue?book_id=41&catalogue_path=86-&topic_type=-1&topic_count=0&mstr=7162119405342a8a62361d923d7d866e585c0c50065d352d612248337889378f738e983f8b0777610322667831104601462993908741025311707081ad1af773e56c423051b45b48096a

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
    private EditText idEditText;
    private EditText editText;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sinan);
        //获取题库详情
        findViewById(R.id.btn_get1).setOnClickListener(v -> {
            locationQuestions.clear(); //清楚本地的集合
            //deleteFolder(); //删除之前的文件
            get1();
        });

        //将获取到的题目集合排序之后写入文件
        findViewById(R.id.btn_get22).setOnClickListener(v -> {
            bianliLocation();
        });

        //将答案的文件写入问题的文件
        findViewById(R.id.btn_get222).setOnClickListener(v -> {
            read();
        });

        editText = findViewById(R.id.editText);
        editText.setText(book_id);

        idEditText = findViewById(R.id.idEditText);
        idEditText.setText(catalogue_path);
        //确定
        findViewById(R.id.btn_get4).setOnClickListener(v -> {
            book_id = editText.getText().toString().trim();
            catalogue_path = idEditText.getText().toString().trim();
            Toast.makeText(SMainActivity.this, "修改成功，当前book = "+book_id+",当前id = " + catalogue_path, Toast.LENGTH_SHORT).show();
        });
    }


    //获取题库详情
    private void get1() {
        Call<SDetailBean> octocat = service.sdetail(book_id, catalogue_path);
        //https://zztk.cdky100.vip/apitk/detail?tlevel_id=1619&openid=oBSIe5V7NzlYpJN0w0xkJibF5LVY
        octocat.enqueue(new Callback<SDetailBean>() {
            @Override
            public void onResponse(Call<SDetailBean> call, Response<SDetailBean> response) {
                if (response != null) {
                    SDetailBean body = response.body();
                    if (body != null) {
                        int code = body.getErrcode();
                        if (code == 0) {
                            SDetailBean.DataBean data = body.getData();
                            List<SDetailBean.DataBean.ListBean> list = data.getList();
                            Log.e("xxx", "获取题目成功=" + list.size());
                            locationQuestions = list;
                        }
                        Toast.makeText(SMainActivity.this, body.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SDetailBean> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(SMainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        new Handler().postDelayed(() -> {
            bianliLocation();
            read();
        }, 3000);
    }

    List<SDetailBean.DataBean.ListBean> locationQuestions = new ArrayList<>();

    private void bianliLocation() {
        if (locationQuestions != null && locationQuestions.size() > 0) {
            Collections.sort(locationQuestions, new Comparator<SDetailBean.DataBean.ListBean>() {
                @Override
                public int compare(SDetailBean.DataBean.ListBean o1, SDetailBean.DataBean.ListBean o2) {
                    return o1.getTopic_id() - o2.getTopic_id();
                }
            });

            int count = 1; // 计数器
            for (int i = 0; i < locationQuestions.size(); i++) {
                SDetailBean.DataBean.ListBean question = locationQuestions.get(i);
                print(count, question);
                count++; // 计数器加1
            }
        }
    }

    private void print(int count, SDetailBean.DataBean.ListBean question) {
        if (question.getType() != 1) return;
        if (openQuestion) {
            String title = question.getName();
            title = title.replaceAll(" ", "");
            title = title.replaceAll("（）", "( )");
            title = title.replaceAll("\\(\\)", "( )");
            title = title.replaceAll("\\(　　\\)", "( )");
            questionFile(count + "." + title);
        }

        String option_a = question.getOption_a();
        questionFile("A." + option_a);
        String option_b = question.getOption_b();
        questionFile("B." + option_b);
        String option_c = question.getOption_c();
        questionFile("C." + option_c);
        String option_d = question.getOption_d();
        questionFile("D." + option_d);

        if (openAnswer) {
            String jiexi = question.getParsing();
            jiexi = jiexi.replaceAll(" ", "");
            if (jiexi.contains("<p>")) {
                jiexi = jiexi.replaceAll("<p>", "");
            }
            if (jiexi.contains("</p>")) {
                jiexi = jiexi.replaceAll("</p>", "");
            }
            if (jiexi.contains("<strong>")) {
                jiexi = jiexi.replaceAll("<strong>", "");
            }
            if (jiexi.contains("</strong>")) {
                jiexi = jiexi.replaceAll("</strong>", "");
            }
            if (jiexi.contains("1.")) {
                jiexi = jiexi.replaceAll("1.", "(1).");
            }
            if (jiexi.contains("2.")) {
                jiexi = jiexi.replaceAll("2.", "(2).");
            }
            if (jiexi.contains("3.")) {
                jiexi = jiexi.replaceAll("3.", "(3).");
            }
            if (jiexi.contains("4.")) {
                jiexi = jiexi.replaceAll("4.", "(4).");
            }
            if (jiexi.contains("5.")) {
                jiexi = jiexi.replaceAll("5.", "(5).");
            }
            if (jiexi.contains("7.")) {
                jiexi = jiexi.replaceAll("7.", "(7).");
            }
            if (jiexi.contains("8.")) {
                jiexi = jiexi.replaceAll("8.", "(8).");
            }
            if (jiexi.contains("9.")) {
                jiexi = jiexi.replaceAll("9.", "(9).");
            }
//            jiexi = StringEscapeUtils.unescapeHtml4(jiexi);

            String right_text = question.getAnswers();
            anwserFile(count + ".答案：" + right_text);

            if (TextUtils.isEmpty(jiexi)) {
                jiexi = "暂无解析【小程序：猴哥刷题】";
            }
            String point = question.getPoint();
            if (TextUtils.isEmpty(point)) {
                point = "无";
            }
            anwserFile("解析：" + "【考点】" + point);
            anwserFile(jiexi + "【小程序：猴哥刷题】");
        }
    }


    public void questionFile(String conent) {
        try {
            FileWriter writer = new FileWriter(getFilesDir() + "example" + catalogue_path + ".txt", true);
            writer.write(conent + "\r\n");
            Log.e("xxxx", conent + "\r\n");
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


            FileWriter writer = new FileWriter(getFilesDir() + "anwser" + catalogue_path + ".txt", true);
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
            FileReader fileReader = new FileReader(getFilesDir() + "anwser" + catalogue_path + ".txt");
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

//    public void deleteFolder() {
//        File file1 = new File(getFilesDir() + "example.txt");
//        deleteFolder(file1);
//
//        File file2 = new File(getFilesDir() + "anwser.txt");
//        deleteFolder(file2);
//    }

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
