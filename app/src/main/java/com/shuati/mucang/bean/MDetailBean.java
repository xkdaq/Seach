
package com.shuati.mucang.bean;

import java.io.Serializable;
import java.util.List;

public class MDetailBean implements Serializable {


    /**
     * code : 200
     * datas : {"id":15035,"library_id":62,"course_id":151,"chapter_id":1110,"type":1,"subject_type":1,"subject_str":"赫尔巴特所代表的传统教育思想的核心一般被概括为：教材中心、课堂中心和（\t）","knowledge_str":"","option_type":1,"option_str":[{"is_correct":"1","option":"A","content":"教师中心"},{"is_correct":"2","option":"B","content":"学校中心"},{"is_correct":"2","option":"C","content":"学生中心"},{"is_correct":"2","option":"D","content":"活动中心"}],"is_little":2,"fraction_total":1,"is_fraction":2,"correct_answer":"A","correct_num":227,"error_num":52,"total_num":279,"little_question_json":[],"analysis_content":"以赫尔巴特为代表的传统教育思想的三个中心是教材中心、课堂中心与教师中心，而以杜威为代表的现代教育思想则强调经验中心、活动中心和学生中心。所以选A教师中心。","sort":99,"add_time":1656992114,"update_time":null,"note_num":0,"comment_num":0,"disid":40,"subject_log":{"id":12632316,"member_id":110095,"library_id":62,"course_id":151,"chapter_id":1110,"subject_id":15035,"my_answer":"A","is_correct":1,"little_question_json":null,"add_time":1693476802,"update_time":1693476802,"disid":0},"is_wrong":2,"subject_knowledge":[]}
     */

    private int code;
    private DatasBean datas;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DatasBean getDatas() {
        return datas;
    }

    public void setDatas(DatasBean datas) {
        this.datas = datas;
    }

    public static class DatasBean implements Serializable {
        /**
         * id : 15035
         * library_id : 62
         * course_id : 151
         * chapter_id : 1110
         * type : 1
         * subject_type : 1
         * subject_str : 赫尔巴特所代表的传统教育思想的核心一般被概括为：教材中心、课堂中心和（	）
         * knowledge_str :
         * option_type : 1
         * option_str : [{"is_correct":"1","option":"A","content":"教师中心"},{"is_correct":"2","option":"B","content":"学校中心"},{"is_correct":"2","option":"C","content":"学生中心"},{"is_correct":"2","option":"D","content":"活动中心"}]
         * is_little : 2
         * fraction_total : 1
         * is_fraction : 2
         * correct_answer : A
         * correct_num : 227
         * error_num : 52
         * total_num : 279
         * little_question_json : []
         * analysis_content : 以赫尔巴特为代表的传统教育思想的三个中心是教材中心、课堂中心与教师中心，而以杜威为代表的现代教育思想则强调经验中心、活动中心和学生中心。所以选A教师中心。
         * sort : 99
         * add_time : 1656992114
         * update_time : null
         * note_num : 0
         * comment_num : 0
         * disid : 40
         * subject_log : {"id":12632316,"member_id":110095,"library_id":62,"course_id":151,"chapter_id":1110,"subject_id":15035,"my_answer":"A","is_correct":1,"little_question_json":null,"add_time":1693476802,"update_time":1693476802,"disid":0}
         * is_wrong : 2
         * subject_knowledge : []
         */

        private int id;
        private int library_id;
        private int course_id;
        private int chapter_id;
        private int type;
        private int subject_type;
        private String subject_str;
        private String knowledge_str;
        private int option_type;
        private int is_little;
        private int fraction_total;
        private int is_fraction;
        private String correct_answer;
        private int correct_num;
        private int error_num;
        private int total_num;
        private String analysis_content;
        private int sort;
        private int add_time;
        private Object update_time;
        private int note_num;
        private int comment_num;
        private int disid;
        private SubjectLogBean subject_log;
        private int is_wrong;
        private List<OptionStrBean> option_str;
        private List<?> little_question_json;
        private List<?> subject_knowledge;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getLibrary_id() {
            return library_id;
        }

        public void setLibrary_id(int library_id) {
            this.library_id = library_id;
        }

        public int getCourse_id() {
            return course_id;
        }

        public void setCourse_id(int course_id) {
            this.course_id = course_id;
        }

        public int getChapter_id() {
            return chapter_id;
        }

        public void setChapter_id(int chapter_id) {
            this.chapter_id = chapter_id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getSubject_type() {
            return subject_type;
        }

        public void setSubject_type(int subject_type) {
            this.subject_type = subject_type;
        }

        public String getSubject_str() {
            return subject_str;
        }

        public void setSubject_str(String subject_str) {
            this.subject_str = subject_str;
        }

        public String getKnowledge_str() {
            return knowledge_str;
        }

        public void setKnowledge_str(String knowledge_str) {
            this.knowledge_str = knowledge_str;
        }

        public int getOption_type() {
            return option_type;
        }

        public void setOption_type(int option_type) {
            this.option_type = option_type;
        }

        public int getIs_little() {
            return is_little;
        }

        public void setIs_little(int is_little) {
            this.is_little = is_little;
        }

        public int getFraction_total() {
            return fraction_total;
        }

        public void setFraction_total(int fraction_total) {
            this.fraction_total = fraction_total;
        }

        public int getIs_fraction() {
            return is_fraction;
        }

        public void setIs_fraction(int is_fraction) {
            this.is_fraction = is_fraction;
        }

        public String getCorrect_answer() {
            return correct_answer;
        }

        public void setCorrect_answer(String correct_answer) {
            this.correct_answer = correct_answer;
        }

        public int getCorrect_num() {
            return correct_num;
        }

        public void setCorrect_num(int correct_num) {
            this.correct_num = correct_num;
        }

        public int getError_num() {
            return error_num;
        }

        public void setError_num(int error_num) {
            this.error_num = error_num;
        }

        public int getTotal_num() {
            return total_num;
        }

        public void setTotal_num(int total_num) {
            this.total_num = total_num;
        }

        public String getAnalysis_content() {
            return analysis_content;
        }

        public void setAnalysis_content(String analysis_content) {
            this.analysis_content = analysis_content;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getAdd_time() {
            return add_time;
        }

        public void setAdd_time(int add_time) {
            this.add_time = add_time;
        }

        public Object getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(Object update_time) {
            this.update_time = update_time;
        }

        public int getNote_num() {
            return note_num;
        }

        public void setNote_num(int note_num) {
            this.note_num = note_num;
        }

        public int getComment_num() {
            return comment_num;
        }

        public void setComment_num(int comment_num) {
            this.comment_num = comment_num;
        }

        public int getDisid() {
            return disid;
        }

        public void setDisid(int disid) {
            this.disid = disid;
        }

        public SubjectLogBean getSubject_log() {
            return subject_log;
        }

        public void setSubject_log(SubjectLogBean subject_log) {
            this.subject_log = subject_log;
        }

        public int getIs_wrong() {
            return is_wrong;
        }

        public void setIs_wrong(int is_wrong) {
            this.is_wrong = is_wrong;
        }

        public List<OptionStrBean> getOption_str() {
            return option_str;
        }

        public void setOption_str(List<OptionStrBean> option_str) {
            this.option_str = option_str;
        }

        public List<?> getLittle_question_json() {
            return little_question_json;
        }

        public void setLittle_question_json(List<?> little_question_json) {
            this.little_question_json = little_question_json;
        }

        public List<?> getSubject_knowledge() {
            return subject_knowledge;
        }

        public void setSubject_knowledge(List<?> subject_knowledge) {
            this.subject_knowledge = subject_knowledge;
        }

        public static class SubjectLogBean implements Serializable {
            /**
             * id : 12632316
             * member_id : 110095
             * library_id : 62
             * course_id : 151
             * chapter_id : 1110
             * subject_id : 15035
             * my_answer : A
             * is_correct : 1
             * little_question_json : null
             * add_time : 1693476802
             * update_time : 1693476802
             * disid : 0
             */

            private int id;
            private int member_id;
            private int library_id;
            private int course_id;
            private int chapter_id;
            private int subject_id;
            private String my_answer;
            private int is_correct;
            private Object little_question_json;
            private int add_time;
            private int update_time;
            private int disid;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getMember_id() {
                return member_id;
            }

            public void setMember_id(int member_id) {
                this.member_id = member_id;
            }

            public int getLibrary_id() {
                return library_id;
            }

            public void setLibrary_id(int library_id) {
                this.library_id = library_id;
            }

            public int getCourse_id() {
                return course_id;
            }

            public void setCourse_id(int course_id) {
                this.course_id = course_id;
            }

            public int getChapter_id() {
                return chapter_id;
            }

            public void setChapter_id(int chapter_id) {
                this.chapter_id = chapter_id;
            }

            public int getSubject_id() {
                return subject_id;
            }

            public void setSubject_id(int subject_id) {
                this.subject_id = subject_id;
            }

            public String getMy_answer() {
                return my_answer;
            }

            public void setMy_answer(String my_answer) {
                this.my_answer = my_answer;
            }

            public int getIs_correct() {
                return is_correct;
            }

            public void setIs_correct(int is_correct) {
                this.is_correct = is_correct;
            }

            public Object getLittle_question_json() {
                return little_question_json;
            }

            public void setLittle_question_json(Object little_question_json) {
                this.little_question_json = little_question_json;
            }

            public int getAdd_time() {
                return add_time;
            }

            public void setAdd_time(int add_time) {
                this.add_time = add_time;
            }

            public int getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(int update_time) {
                this.update_time = update_time;
            }

            public int getDisid() {
                return disid;
            }

            public void setDisid(int disid) {
                this.disid = disid;
            }
        }

        public static class OptionStrBean implements Serializable {
            /**
             * is_correct : 1
             * option : A
             * content : 教师中心
             */

            private String is_correct;
            private String option;
            private String content;

            public String getIs_correct() {
                return is_correct;
            }

            public void setIs_correct(String is_correct) {
                this.is_correct = is_correct;
            }

            public String getOption() {
                return option;
            }

            public void setOption(String option) {
                this.option = option;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }
    }
}
