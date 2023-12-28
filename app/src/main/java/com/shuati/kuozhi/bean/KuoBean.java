package com.shuati.kuozhi.bean;

import java.io.Serializable;
import java.util.List;

public class KuoBean implements Serializable {

    private int errcode;
    private List<QuestionContentBean> question_content;
    private List<String> question_answer;
    private UserDetailBean user_detail;

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public List<QuestionContentBean> getQuestion_content() {
        return question_content;
    }

    public void setQuestion_content(List<QuestionContentBean> question_content) {
        this.question_content = question_content;
    }

    public List<String> getQuestion_answer() {
        return question_answer;
    }

    public void setQuestion_answer(List<String> question_answer) {
        this.question_answer = question_answer;
    }

    public UserDetailBean getUser_detail() {
        return user_detail;
    }

    public void setUser_detail(UserDetailBean user_detail) {
        this.user_detail = user_detail;
    }

    public class UserDetailBean implements Serializable{
        private List<String> userchoice;
        private List<Integer> qtime;
        private List<Integer> isright;
        private List<Integer> favorite;
        private String starttime;
        private int finish_status;
        private int usetime;

        public List<String> getUserchoice() {
            return userchoice;
        }

        public void setUserchoice(List<String> userchoice) {
            this.userchoice = userchoice;
        }

        public List<Integer> getQtime() {
            return qtime;
        }

        public void setQtime(List<Integer> qtime) {
            this.qtime = qtime;
        }

        public List<Integer> getIsright() {
            return isright;
        }

        public void setIsright(List<Integer> isright) {
            this.isright = isright;
        }

        public List<Integer> getFavorite() {
            return favorite;
        }

        public void setFavorite(List<Integer> favorite) {
            this.favorite = favorite;
        }

        public String getStarttime() {
            return starttime;
        }

        public void setStarttime(String starttime) {
            this.starttime = starttime;
        }

        public int getFinish_status() {
            return finish_status;
        }

        public void setFinish_status(int finish_status) {
            this.finish_status = finish_status;
        }

        public int getUsetime() {
            return usetime;
        }

        public void setUsetime(int usetime) {
            this.usetime = usetime;
        }
    }

    public class QuestionContentBean implements Serializable{
        private int question_id;
        private int global_id;
        private String question;
        private int issingle;
        private int moni_id;
        private String answer;
        private String choiceA;
        private String choiceB;
        private String choiceC;
        private String choiceD;
        private String explain;
        private int teacher;

        public int getQuestion_id() {
            return question_id;
        }

        public void setQuestion_id(int question_id) {
            this.question_id = question_id;
        }

        public int getGlobal_id() {
            return global_id;
        }

        public void setGlobal_id(int global_id) {
            this.global_id = global_id;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public int getIssingle() {
            return issingle;
        }

        public void setIssingle(int issingle) {
            this.issingle = issingle;
        }

        public int getMoni_id() {
            return moni_id;
        }

        public void setMoni_id(int moni_id) {
            this.moni_id = moni_id;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public String getChoiceA() {
            return choiceA;
        }

        public void setChoiceA(String choiceA) {
            this.choiceA = choiceA;
        }

        public String getChoiceB() {
            return choiceB;
        }

        public void setChoiceB(String choiceB) {
            this.choiceB = choiceB;
        }

        public String getChoiceC() {
            return choiceC;
        }

        public void setChoiceC(String choiceC) {
            this.choiceC = choiceC;
        }

        public String getChoiceD() {
            return choiceD;
        }

        public void setChoiceD(String choiceD) {
            this.choiceD = choiceD;
        }

        public String getExplain() {
            return explain;
        }

        public void setExplain(String explain) {
            this.explain = explain;
        }

        public int getTeacher() {
            return teacher;
        }

        public void setTeacher(int teacher) {
            this.teacher = teacher;
        }
    }
}
