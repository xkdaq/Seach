
package com.shuati.cangdun;

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
import com.shuati.cangdun.bean.CDetailBean;
import com.shuati.liulan.ApiService;

//import org.apache.commons.lang3.StringEscapeUtils;

import org.apache.commons.lang3.StringEscapeUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//仓顿
public class CMainActivity extends AppCompatActivity {

    public String BASE_URL = "https://zztk.cdky100.vip/";
    public String tlevel_id = "3336"; //题库的id 3337 3338 3339
    public String open_id = "oBSIe5V7NzlYpJN0w0xkJibF5LVY";

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

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cangdun);
        //获取题库详情
        findViewById(R.id.btn_get1).setOnClickListener(v -> {
            locationQuestions.clear(); //清楚本地的集合
            deleteFolder(); //删除之前的文件
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

        idEditText = findViewById(R.id.idEditText);
        idEditText.setText(tlevel_id);
        //确定
        findViewById(R.id.btn_get4).setOnClickListener(v -> {
            tlevel_id = idEditText.getText().toString().trim();
            Toast.makeText(CMainActivity.this, "修改成功，当前id = " + tlevel_id, Toast.LENGTH_SHORT).show();
        });
    }


    //获取题库详情
    private void get1() {
        Call<CDetailBean> octocat = service.cdetail(tlevel_id, open_id);
        //https://zztk.cdky100.vip/apitk/detail?tlevel_id=1619&openid=oBSIe5V7NzlYpJN0w0xkJibF5LVY
        //https://zztk.cdky100.vip/apitk/detail?tlevel_id=3154&openid=oBSIe5V7NzlYpJN0w0xkJibF5LVY
        octocat.enqueue(new Callback<CDetailBean>() {
            @Override
            public void onResponse(Call<CDetailBean> call, Response<CDetailBean> response) {
                if (response != null) {
                    CDetailBean body = response.body();
                    if (body != null) {
                        int code = body.getCode();
                        if (code == 0) {
                            CDetailBean.DetailBean detail = body.getDetail();
                            List<CDetailBean.DetailBean.TimuBean> timu = detail.getTimu();
                            Log.e("xxx", "获取题目成功=" + timu.size());
                            locationQuestions = timu;
                        }
                        Toast.makeText(CMainActivity.this, body.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CDetailBean> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(CMainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        new Handler().postDelayed(() -> {
            bianliLocation();
            read();
        }, 3000);
    }

    List<CDetailBean.DetailBean.TimuBean> locationQuestions = new ArrayList<>();

    private void bianliLocation() {
        if (locationQuestions != null && locationQuestions.size() > 0) {
            Collections.sort(locationQuestions, new Comparator<CDetailBean.DetailBean.TimuBean>() {
                @Override
                public int compare(CDetailBean.DetailBean.TimuBean o1, CDetailBean.DetailBean.TimuBean o2) {
                    return o1.getTimu_id() - o2.getTimu_id();
                }
            });

            int count = 1; // 计数器
            for (int i = 0; i < locationQuestions.size(); i++) {
                CDetailBean.DetailBean.TimuBean question = locationQuestions.get(i);
                print(count, question);
                count++; // 计数器加1
            }
        }
    }

    //匹配数字打头
    private static boolean startsWithDigit(String input) {
        // 正则表达式 \d 匹配一个数字字符，^ 表示匹配字符串的开头
        String regex = "^\\d.*";
        return Pattern.matches(regex, input);
    }

    //移除所有标签
    public static String removeHtmlTags(String html) {
        // 定义HTML标签的正则表达式
        String regex = "<[^>]+>";
        // 使用正则表达式替换HTML标签
        String plainText = html.replaceAll(regex, "");
        return plainText;
    }


    private void print(int count, CDetailBean.DetailBean.TimuBean question) {
        if (openQuestion) {
            String title = question.getTitle();
            title = title.replaceAll(" ", "");
            //+count+")"
            if (startsWithDigit(title)) {
                questionFile(count + ". " + title);
            }else{
                questionFile(count + "." + title);
            }
            Log.e("xxxx", count + ". " + title);
        }
        String xuan_text = question.getXuan_text();
        String[] arr = xuan_text.split("\\|"); //拆分字符串
        List<String> strings = Arrays.asList(arr);
        for (String option : strings) {
            option = option.replaceAll(" ", "");
            if (openQuestion) {
                questionFile(option);
            }
            Log.e("xxxx", option);
        }

        if (openAnswer) {
            String jiexi = question.getJiexi();
            //jiexi = jiexi.replaceAll(" ", "");
            jiexi = StringEscapeUtils.unescapeHtml4(jiexi);
            jiexi = removeHtmlTags(jiexi);

            String right_text = question.getRight_text();
            anwserFile(count + ".答案：" + right_text);

            if (TextUtils.isEmpty(jiexi)) {
                jiexi = "暂无解析【小程序：猴哥刷题】";
            }
            //jiexi = "\r\n【出处】" + question.getChuchu() +"\r\n" + jiexi;
            anwserFile("解析：" + jiexi + "");
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
            if (conent.contains("点拨：")) {
                conent = conent.replaceAll("点拨：", "【点拨】");
            }
            if (conent.contains("干扰项辨识")) {
                conent = conent.replaceAll("干扰项辨识", "【干扰项辨识】");
            }
            if (conent.contains("【干扰分析】")) {
                conent = conent.replaceAll("【干扰分析】", "\r\n"+"【干扰分析】"+"\r\n");
            }
            FileWriter writer = new FileWriter( getFilesDir() + "anwser.txt", true);
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
            FileReader fileReader = new FileReader( getFilesDir() + "anwser.txt");
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

    public void deleteFolder(){
        File file1 = new File( getFilesDir() + "example.txt");
        deleteFolder(file1);

        File file2 = new File( getFilesDir() + "anwser.txt");
        deleteFolder(file2);
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
