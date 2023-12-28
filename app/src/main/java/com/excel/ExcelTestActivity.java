package com.excel;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.ListUtils;
import com.dalipan.search.R;
import com.excel.test.DemoData;
import com.excel.util.TestFileUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExcelTestActivity extends AppCompatActivity {

    private List<DemoData> data() {
        List<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excel_test);
        findViewById(R.id.btn_get1).setOnClickListener(v -> {
            simpleWrite();
        });
    }

    public void simpleWrite() {
        // 注意 simpleWrite在数据量不大的情况下可以使用（5000以内，具体也要看实际情况），数据量大参照 重复多次写入

        // 写法1 JDK8+
        // since: 3.0.0-beta1
        //String filePath = "path/to/save/excel/file.xlsx";
        String filePath = getFilesDir() + "file.xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
//        EasyExcel.write(fileName, DemoData.class)
//                .sheet("模板")
//                .doWrite(() -> {
//                    // 分页查询数据
//                    return data();
//                });

        EasyExcel.write(filePath, DemoData.class).sheet("Sheet1").doWrite(data());

    }
}
