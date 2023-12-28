
package com.fenxi.kuozhi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dalipan.search.R;
import com.fenxi.kuozhi.bean.FenxiKuoBean;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

//阔智
public class FKMainActivity extends AppCompatActivity {

    private EditText idEditText;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fenxi_kuozhi);

        //获取题库详情
        findViewById(R.id.btn_get1).setOnClickListener(v -> {
            locationQuestions.clear(); //清楚本地的集合
            deleteFolder(); //删除之前的文件
            getData();
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
        idEditText.setText("" + index);
        //确定
        findViewById(R.id.btn_get4).setOnClickListener(v -> {
            index = idEditText.getText().toString().trim();
            Toast.makeText(FKMainActivity.this, "修改成功，当前id = " + index, Toast.LENGTH_SHORT).show();
        });
    }

    private String index = "1";

    List<FenxiKuoBean.LearnCardlistBean> locationQuestions = new ArrayList<>();

    private void getData() {
        String jsonStr = getJson(this, "fenxi/kuozhi/" + index + ".txt");
        Gson gson = new Gson();
        FenxiKuoBean fenxiKuoBean = gson.fromJson(jsonStr, FenxiKuoBean.class);
        if (fenxiKuoBean != null && fenxiKuoBean.getErrcode() == 0) {
            List<FenxiKuoBean.LearnCardlistBean> questionContent = fenxiKuoBean.getLearn_cardlist();
            Log.e("xxx", "获取题目成功=" + questionContent.size());
            locationQuestions = questionContent;
        }
        new Handler().postDelayed(() -> {
            bianliLocation();
            //read();
        }, 1000);
    }

    private void bianliLocation() {
        if (locationQuestions != null && locationQuestions.size() > 0) {
            Collections.sort(locationQuestions, new Comparator<FenxiKuoBean.LearnCardlistBean>() {
                @Override
                public int compare(FenxiKuoBean.LearnCardlistBean o1, FenxiKuoBean.LearnCardlistBean o2) {
                    return o1.getCardid() - o2.getCardid();
                }
            });

            int count = 1; // 计数器
            for (int i = 0; i < locationQuestions.size(); i++) {
                FenxiKuoBean.LearnCardlistBean question = locationQuestions.get(i);
                print(count, question);
                count++; // 计数器加1
            }
        }
    }

    //开关 是否写入文件-问题
    private boolean openQuestion = true;
    //开关 是否写入文件-答案
    private boolean openAnswer = true;

    private void print(int count, FenxiKuoBean.LearnCardlistBean question) {
        List<String> content = question.getContent();
        if (content.size() > 1) {
            String s0 = content.get(0);
            String s1 = content.get(1);
            questionFile(s0);
            questionFile(removeHtmlTags(s1));
        }
    }

    public static String removeHtmlTags(String html) {
        // 定义HTML标签的正则表达式
        String regex = "<[^>]+>";
        // 使用正则表达式替换HTML标签
        String plainText = html.replaceAll(regex, "");
        return plainText;
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

    public void questionFile(String conent) {
        try {
            FileWriter writer = new FileWriter(getFilesDir() + "example.txt", true);
            conent = conent.replaceAll("\n", "");
            writer.write(conent + "\r\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void anwserFile(String conent) {
        try {
            conent = conent.replaceAll("<br/>", "\r\n");
            conent = conent.replaceAll("<br />", "\r\n");
            conent = conent.replaceAll("点拨：", "【点拨】");
            FileWriter writer = new FileWriter(getFilesDir() + "anwser.txt", true);
            writer.write(conent + "\r\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteFolder() {
        File file1 = new File(getFilesDir() + "example.txt");
        deleteFolder(file1);

        File file2 = new File(getFilesDir() + "anwser.txt");
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

    public static String getJson(Context context, String name) {
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getAssets().open(name));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
