
package com.shuati.cangdun;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dalipan.search.R;
import com.shuati.cangdun.bean.CDetailBean;
import com.shuati.liulan.ApiService;

import org.apache.commons.lang3.StringEscapeUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//仓顿-可吖刷题
public class CMainActivity extends AppCompatActivity {

    public String BASE_URL = "https://zztk.cdky100.vip/";
    public int tlevel_id = 3153; //题库的id 3335
    private static final int TOTAL_EXECUTIONS = 2; //数量 8

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

    private static final int DELAY_MILLIS = 5000; // 延迟时间，单位：毫秒

    // 创建一个Handler，关联主线程的Looper
    Handler handler = new Handler(Looper.getMainLooper());


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cangdun);
        //获取题库详情
        findViewById(R.id.btn_get1).setOnClickListener(v -> {
            locationQuestions.clear(); //清楚本地的集合
            deleteFolder(); //删除之前的文件
            get0();
        });

        //将获取到的题目集合排序之后写入文件
        findViewById(R.id.btn_get22).setOnClickListener(v -> {
            //bianliLocation();
        });

        idEditText = findViewById(R.id.idEditText);
        idEditText.setText("" + tlevel_id);
        //确定
        findViewById(R.id.btn_get4).setOnClickListener(v -> {
            tlevel_id = Integer.parseInt(idEditText.getText().toString().trim());
            Toast.makeText(CMainActivity.this, "修改成功，当前id = " + tlevel_id, Toast.LENGTH_SHORT).show();
        });

    }

    private static int counter = 0;

    private void get0() {
       //get1();

        // 定义要执行的任务
        Runnable task = new Runnable() {
            @Override
            public void run() {
                // 这里放入你需要执行的循环内的方法
                get1();

                // 增加计数
                counter++;
                tlevel_id++;

                // 判断是否达到总执行次数，达到则停止任务
                if (counter >= TOTAL_EXECUTIONS) {
                    handler.removeCallbacks(this); // 移除未执行的任务
                } else {
                    // 在这里可以再次调度任务，实现循环
                    handler.postDelayed(this, DELAY_MILLIS);
                }
            }
        };
        // 第一次执行任务
        handler.postDelayed(task, DELAY_MILLIS);
    }


    //获取题库详情
    private void get1() {
        Call<CDetailBean> octocat = service.cdetail("" + tlevel_id, open_id);
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
                            Log.e("xxx", "tlevel_id = " + tlevel_id + ",获取题目成功=" + timu.size());
                            locationQuestions = timu;
                            bianliLocation();
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

//        new Handler().postDelayed(() -> {
//            bianliLocation();
//        }, 3000);
    }

    List<CDetailBean.DetailBean.TimuBean> locationQuestions = new ArrayList<>();

    private void bianliLocation() {
        if (locationQuestions != null && locationQuestions.size() > 0) {
            locationQuestions.sort(Comparator.comparingInt(CDetailBean.DetailBean.TimuBean::getTimu_id));

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
//        // 正则表达式 \d 匹配一个数字字符，^ 表示匹配字符串的开头
//        String regex = "^\\d.*";
//        return Pattern.matches(regex, input);

        // 使用Character.isDigit方法判断是否以数字字符开头
        return input.length() > 0 && Character.isDigit(input.charAt(0));
    }


    private static String filterTags(String input) {
        // 匹配<p>和<span>标签的正则表达式
        String regex = "<p[^>]*>|<\\/p>|<span[^>]*>|<\\/span>|<div[^>]*>|<\\/div>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        // 用空字符串替换匹配到的标签
        String result = matcher.replaceAll("");
        result =result.replaceAll("\n","");
        return result;
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
        //题干
        String title = question.getTitle();
        title = title.replaceAll(" ", "");
        //+count+")"
        if (startsWithDigit(title)) {
            questionFile(count + ". " + title);
        } else {
            questionFile(count + "." + title);
        }

        //选项
        String xuan_text = question.getXuan_text();
        String[] arr = xuan_text.split("\\|"); //拆分字符串
        List<String> strings = Arrays.asList(arr);
        for (String option : strings) {
            option = option.replaceAll(" ", "");
            questionFile(option);
        }

        //答案
        String right_text = question.getRight_text();
        questionFile("答案：" + right_text);

        //解析
        String jiexi = question.getJiexi();
        jiexi = StringEscapeUtils.unescapeHtml4(jiexi);
        //jiexi = removeHtmlTags(jiexi);
        jiexi = filterTags(jiexi);
        jiexi = jiexi.replaceAll("点拨：", "【点拨】");
        jiexi = jiexi.replaceAll("干扰项辨识", "【干扰项辨识】");
        jiexi = jiexi.replaceAll("【干扰分析】", "\r\n" + "【干扰分析】" + "\r\n");
        jiexi = TextUtils.isEmpty(jiexi) ? "暂无解析" : jiexi;
        //jiexi = "\r\n【出处】" + question.getChuchu() +"\r\n" + jiexi;
        questionFile("解析：" + jiexi + "");
    }

    public void questionFile(String conent) {
        try {
            Log.e("xxxx", "" + conent);
            FileWriter writer = new FileWriter(getFilesDir() + "example_" + (tlevel_id -1) + ".txt", true);
            writer.write(conent + "\r\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteFolder() {
        File file1 = new File(getFilesDir() + "example.txt");
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
