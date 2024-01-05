
package com.shuati.xunke;

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
import com.shuati.application.AppUtil;
import com.shuati.cangdun.bean.CDetailBean;
import com.shuati.kuozhi.bean.KuoBean;
import com.shuati.liulan.ApiService;
import com.shuati.xunke.bean.XunBean;

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

//讯课-可吖刷题
//精研刷题
public class XMainActivity extends AppCompatActivity {

    private String index = "20241";
    private EditText idEditText;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xunke);
        //获取题库详情
        findViewById(R.id.btn_get1).setOnClickListener(v -> {
            locationQuestions.clear(); //清楚本地的集合
            deleteFolder(); //删除之前的文件
            get1();
        });


        idEditText = findViewById(R.id.idEditText);
        idEditText.setText(index);
        //确定
        findViewById(R.id.btn_get4).setOnClickListener(v -> {
            index = idEditText.getText().toString().trim();
            Toast.makeText(XMainActivity.this, "修改成功，当前id = " + index, Toast.LENGTH_SHORT).show();
        });
    }


    //获取题库详情
    private void get1() {
        String jsonStr = AppUtil.getJson(this, "jiaoyu333/" + index + ".txt");
        Gson gson = new Gson();
        XunBean xunBean = gson.fromJson(jsonStr, XunBean.class);
        if (xunBean!=null && xunBean.getErrno() == 0){
            XunBean.DataXunBean data = xunBean.getData();
            List<XunBean.DataXunBean.QuestionsBean> questions = data.getQuestions();
            Log.e("xxx", "获取题目成功=" + questions.size());
            locationQuestions = questions;
        }

        new Handler().postDelayed(() -> {
            bianliLocation();
        }, 3000);
    }

    List<XunBean.DataXunBean.QuestionsBean> locationQuestions = new ArrayList<>();

    private void bianliLocation() {
        if (locationQuestions != null && locationQuestions.size() > 0) {
            locationQuestions.sort(Comparator.comparingInt(XunBean.DataXunBean.QuestionsBean::getId));

            int count = 1; // 计数器
            for (int i = 0; i < locationQuestions.size(); i++) {
                XunBean.DataXunBean.QuestionsBean question = locationQuestions.get(i);
                print(count, question);
                count++; // 计数器加1
            }
        }
    }

    //匹配数字打头
    private static boolean startsWithDigit(String input) {
        // 正则表达式 \d 匹配一个数字字符，^ 表示匹配字符串的开头
        //String regex = "^\\d.*";
        //return Pattern.matches(regex, input);

        // 使用正则表达式判断是否以数字开始
        //return input.matches("^\\d.*");

        // 使用Character.isDigit方法判断是否以数字字符开头
        return input.length() > 0 && Character.isDigit(input.charAt(0));
    }

    //移除所有标签
    public static String removeHtmlTags(String html) {
        // 定义HTML标签的正则表达式
        String regex = "<[^>]+>";
        // 使用正则表达式替换HTML标签
        String plainText = html.replaceAll(regex, "");
        return plainText;
    }

    public static String convertToString(List<Integer> dataList) {
        StringBuilder result = new StringBuilder();

        for (int value : dataList) {
            switch (value) {
                case 0:
                    result.append("A");
                    break;
                case 1:
                    result.append("B");
                    break;
                case 2:
                    result.append("C");
                    break;
                case 3:
                    result.append("D");
                    break;
                default:
                    result.append("选项异常");
                    break;
            }
        }

        return result.toString();
    }

    private void print(int count, XunBean.DataXunBean.QuestionsBean question) {
        //题干
        String title = question.getStem();
        title = filterTags(title);
        //title = title.replaceAll(" ", "");
        //+count+")"
        //Log.e("xxxx","--"+(title));
        //Log.e("xxxx","--"+startsWithDigit(title));
        if (startsWithDigit(title)) {
            questionFile(count + ". " + title);
        } else {
            questionFile(count + "." + title);
        }

        //选项
        List<String> options = question.getOptions();
        questionFile("A."+filterTags(options.get(0)));
        questionFile("B."+filterTags(options.get(1)));
        questionFile("C."+filterTags(options.get(2)));
        questionFile("D."+filterTags(options.get(3)));

        //答案
        List<Integer> answer = question.getAnswer();
        questionFile("答案：" + convertToString(answer));

        //解析
        String jiexi = question.getAnalysis();
        jiexi = StringEscapeUtils.unescapeHtml4(jiexi);
        jiexi = filterTags(jiexi);
        //jiexi = removeHtmlTags(jiexi);
        //jiexi = jiexi.replaceAll("点拨：", "【点拨】");
        //jiexi = jiexi.replaceAll("干扰项辨识", "【干扰项辨识】");
        //jiexi = jiexi.replaceAll("【干扰分析】", "\r\n" + "【干扰分析】" + "\r\n");
        jiexi = jiexi.replaceAll("<strong>【试题简析】","\r\n<strong>【试题简析】");
        jiexi = TextUtils.isEmpty(jiexi) ? "暂无解析" : jiexi;
        //jiexi = "\r\n【出处】" + question.getChuchu() +"\r\n" + jiexi;
        questionFile("解析：" + jiexi + "");
    }

    private static String filterTags(String input) {
        // 匹配<p>和<span>标签的正则表达式
        String regex = "<p[^>]*>|<\\/p>|<span[^>]*>|<\\/span>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        // 用空字符串替换匹配到的标签
        String result = matcher.replaceAll("");
        result =result.replaceAll("\n","");
        return result;
    }

    public void questionFile(String conent) {
        try {
            Log.e("xxxx", "" + conent);
            FileWriter writer = new FileWriter(getFilesDir() + "example.txt", true);
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
