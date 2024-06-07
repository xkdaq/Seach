
package com.shuati.xunke;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dalipan.search.R;
import com.google.gson.Gson;
import com.shuati.application.AppUtil;
import com.shuati.xunke.bean.XunBean;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//讯课-可吖刷题
//精研刷题
public class XMainActivity extends AppCompatActivity {

    //333新大纲/1中教史 2外教史 3教心 4教原
    private String lujin = "zhengzhi/xiao/1000/1/";
    private String index = "00";

    //如果带颜色的解析，就把这个开关关了,手动输入
    //选择题默认打开这个开关
    private static boolean isOpenFilterTags = true;

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


        Switch switchButton = findViewById(R.id.switchButton);
        switchButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                isOpenFilterTags = true;
            } else {
                isOpenFilterTags = false;
            }
        });
    }

    //获取题库详情
    private void get1() {
        String jsonStr = AppUtil.getJson(this, lujin + index + ".txt");
        Gson gson = new Gson();
        XunBean xunBean = gson.fromJson(jsonStr, XunBean.class);
        if (xunBean != null && xunBean.getErrno() == 0) {
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

            boolean foundFirstTypeOne = false;

            int count = 1; // 计数器
            for (int i = 0; i < locationQuestions.size(); i++) {
                XunBean.DataXunBean.QuestionsBean question = locationQuestions.get(i);

                //生成多选题的第一个时候 加一个标题
                if (question.getType().equals("2") && !foundFirstTypeOne) {
                    questionFile(question.getType(), "一、选择题");
                    foundFirstTypeOne = true;
                }

                print(count, question, 0);
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

    public static String convertToString(List<String> dataList) {
        StringBuilder result = new StringBuilder();

        for (String value : dataList) {
            switch (value) {
                case "0":
                    result.append("A");
                    break;
                case "1":
                    result.append("B");
                    break;
                case "2":
                    result.append("C");
                    break;
                case "3":
                    result.append("D");
                    break;
                default:
                    result.append(filterXuanXian(value));
                    break;
            }
        }

        return result.toString();
    }

    //mType 0加count 1不加count
    private void print(int count, XunBean.DataXunBean.QuestionsBean question, int mType) {
        String type = question.getType(); //"1"单选 "2"多选
        if (mType == 0) {
            if (count == 1) {
                if ("1".equals(question.getType()) || "2".equals(question.getType())) {
                    questionFile(type, "一、选择题");
                } else if ("5".equals(question.getType())) {
                    questionFile(type, "一、问答题");
                } else if ("6".equals(question.getType())) {
                    questionFile(type, "一、材料题");
                }
            }
        }

        //题干
        String title = question.getStem();
        title = filterTagsNewJiexi(title);
        //title = title.replaceAll(" ", "");
        //+count+")"
        //Log.e("xxxx","--"+(title));
        //Log.e("xxxx","--"+startsWithDigit(title));

        if (mType == 0) {
            if (startsWithDigit(title)) {
                questionFile(type, count + ". " + title);
            } else {
                questionFile(type, count + "." + title);
            }
        } else {
            questionFile(type, title);
        }

        if ("1".equals(question.getType()) || "2".equals(question.getType())) {
            //1单选题 2多选题
            //选项
            List<String> options = question.getOptions();
            if (options != null && options.size() > 0) {
                questionFile(type, "A." + filterXuanXian(options.get(0)));
                questionFile(type, "B." + filterXuanXian(options.get(1)));
                questionFile(type, "C." + filterXuanXian(options.get(2)));
                questionFile(type, "D." + filterXuanXian(options.get(3)));
            } else {
                questionFile(type, "A.");
                questionFile(type, "B.");
                questionFile(type, "C.");
                questionFile(type, "D.");
            }
        }

        if ("6".equals(question.getType())) {
            List<XunBean.DataXunBean.QuestionsBean> subQuestion = question.getSubQuestion();
            for (int i = 0; i < subQuestion.size(); i++) {
                XunBean.DataXunBean.QuestionsBean questionsBean = subQuestion.get(i);
                print(i + 1000, questionsBean, 1);
            }
        } else {
            //答案
            List<String> answer = question.getAnswer();
            questionFile(type, "答案：" + convertToString(answer));

            //解析
            String jiexi = question.getAnalysis();
            jiexi = jiexi.replaceAll("☺", "");
            jiexi = jiexi.replaceAll("精研", "猴哥");
            jiexi = StringEscapeUtils.unescapeHtml4(jiexi);
            jiexi = filterTagsNewJiexi(jiexi);
            //jiexi = removeHtmlTags(jiexi);
            //jiexi = jiexi.replaceAll("点拨：", "【点拨】");
            //jiexi = jiexi.replaceAll("干扰项辨识", "【干扰项辨识】");
            //jiexi = jiexi.replaceAll("【干扰分析】", "\r\n" + "【干扰分析】" + "\r\n");
            //jiexi = jiexi.replaceAll("<strong>【试题简析】", "\r\n<strong>【试题简析】");
            //jiexi = TextUtils.isEmpty(jiexi) ? "暂无解析" : jiexi;
            //jiexi = "\r\n【出处】" + question.getChuchu() +"\r\n" + jiexi;
            //jiexi = jiexi + "[公众号：猴子不吃柠檬]"+"\r\n";
            questionFile(type, "解析：" + jiexi + "");
        }
    }


    //标题
    private static String filterTags(String input) {
        //匹配<p>和<span>标签的正则表达式
        String regex = "<p[^>]*>|<\\/p>|<span[^>]*>|<\\/span>|<div[^>]*>|<\\/div>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        //用空字符串替换匹配到的标签
        String result = matcher.replaceAll("");
        return result;
    }


    //选项 直接去掉p标签和换行
    private static String filterXuanXian(String input) {
        //匹配<p>和<span>标签的正则表达式
        String regex = "<p[^>]*>|<\\/p>|<span[^>]*>|<\\/span>|<div[^>]*>|<\\/div>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        //用空字符串替换匹配到的标签
        String result = matcher.replaceAll("");
        //去掉换行
        result = result.replaceAll("\n", "");
        return result;
    }

    public static String filterP(String html) {
        // 使用Jsoup解析HTML字符串
        Document doc = Jsoup.parse(html);
        // 选择所有的<p>标签
        Elements paragraphs = doc.select("p");
        // 使用StringBuilder构建结果
        StringBuilder result = new StringBuilder();
        // 遍历每个<p>标签，提取文本内容并添加换行
        for (Element paragraph : paragraphs) {
            String paragraphContent = paragraph.html();
            result.append(paragraphContent).append("\r\n");
        }
        // 删除最后多余的换行
        if (result.length() >= 2) {
            result.delete(result.length() - 2, result.length());
        }
        return result.toString();
    }

    private static String filterTagsNewJiexi(String input) {
        String result = "";
        if (isOpenFilterTags) {
            //先把p标签转成换行的文本
            input = filterP(input);

            // 使用正则表达式替换<span>指定颜色标签为<strong>标签
            Pattern pattern0 = Pattern.compile("<span style=\"[^\"]*color: #00B0F0;[^\"]*\">(.*?)</span>");
            Matcher matcher0 = pattern0.matcher(input);
            StringBuffer sb = new StringBuffer();
            while (matcher0.find()) {
                // Replace with <strong> tags, maintaining the original text
                matcher0.appendReplacement(sb, "<strong>$1</strong>");
            }
            matcher0.appendTail(sb);
            result = sb.toString();

            // 匹配<p>和<span>标签的正则表达式
            String regex = "<p[^>]*>|<\\/p>|<span[^>]*>|<\\/span>|<div[^>]*>|<\\/div>";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(result);
            result = matcher.replaceAll("");

            result = result.replaceAll("<br>", "");

            // 使用正则表达式替换<u>标签为<strong>标签
            result = result.replaceAll("<u>(.*?)</u>", "<strong>$1</strong>");

            // 匹配style样式 全部替换
            String regex1 = "\\s*style=\"[^\"]*\"\\s*";
            Pattern pattern1 = Pattern.compile(regex1);
            Matcher matcher1 = pattern1.matcher(result);
            result = matcher1.replaceAll("");

            // 正则表达式替换空内容的<strong>标签
            String emptyStrongRegex = "<strong></strong>";
            result = result.replaceAll(emptyStrongRegex, "");

            // 正则表达式替换只包含换行的<strong>标签
            String strongWithNewlineRegex = "<strong>\\s*\\n\\s*</strong>";
            result = result.replaceAll(strongWithNewlineRegex, "");

            // 正则表达式在“猴哥”前添加换行符
//            String monkeyRegex = "(猴哥补充)";
//            result = result.replaceAll(monkeyRegex, "\n$1");
//
//            String monkeyRegex1 = "(拓展与点拨)";
//            result = result.replaceAll(monkeyRegex1, "\n$1");

            //<br><br/>
//            String monkeyRegex1 = "(简析：)";
//            result = result.replaceAll(monkeyRegex1, "\n$1");
//
            String monkeyRegex2 = "(<strong>点拨：)";
            result = result.replaceAll(monkeyRegex2, "------\n$1");

            result = result.replaceAll("精讲出处", "精讲出处：");

        } else {
            result = input;
        }
        return result;
    }

    public void questionFile(String type, String conent) {
        Log.e("xxxx", "" + conent);
        try {
            FileWriter writer = null;
            if ("1".equals(type)) {
                writer = new FileWriter(getFilesDir() + "example1.txt", true);
            } else if ("2".equals(type)) {
                writer = new FileWriter(getFilesDir() + "example2.txt", true);
            } else {
                writer = new FileWriter(getFilesDir() + "example.txt", true);
            }
            writer.write(conent + "\r\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteFolder() {
        File file = new File(getFilesDir() + "example.txt");
        File file1 = new File(getFilesDir() + "example1.txt");
        File file2 = new File(getFilesDir() + "example2.txt");
        deleteFolder(file);
        deleteFolder(file1);
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
