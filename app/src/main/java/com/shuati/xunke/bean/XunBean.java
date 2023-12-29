package com.shuati.xunke.bean;

import java.io.Serializable;
import java.util.List;

public class XunBean implements Serializable {

    public DataXunBean data;
    public int errno;

    public DataXunBean getData() {
        return data;
    }

    public void setData(DataXunBean data) {
        this.data = data;
    }

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public static class DataXunBean {
        public List<DataXunBean.QuestionsBean> questions;
        public String title;

        public List<QuestionsBean> getQuestions() {
            return questions;
        }

        public void setQuestions(List<QuestionsBean> questions) {
            this.questions = questions;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public static class QuestionsBean {
            public int id;
            public String type;
            public String stem;
            public List<Integer> answer;
            public String analysis;
            public List<String> options;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getStem() {
                return stem;
            }

            public void setStem(String stem) {
                this.stem = stem;
            }

            public List<Integer> getAnswer() {
                return answer;
            }

            public void setAnswer(List<Integer> answer) {
                this.answer = answer;
            }

            public String getAnalysis() {
                return analysis;
            }

            public void setAnalysis(String analysis) {
                this.analysis = analysis;
            }

            public List<String> getOptions() {
                return options;
            }

            public void setOptions(List<String> options) {
                this.options = options;
            }
        }
    }
}
