package com.excel.test;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;

import java.util.Date;

@ContentRowHeight(10)
@HeadRowHeight(20)
@ColumnWidth(25)
public class DemoData {

    @ColumnWidth(50)
    @ExcelProperty("字符串标题")
    private String string;

    @ColumnWidth(50)
    @ExcelProperty("日期标题")
    private Date date;

    @ColumnWidth(50)
    @ExcelProperty("数字标题")
    private Double doubleData;


    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getDoubleData() {
        return doubleData;
    }

    public void setDoubleData(Double doubleData) {
        this.doubleData = doubleData;
    }
}
