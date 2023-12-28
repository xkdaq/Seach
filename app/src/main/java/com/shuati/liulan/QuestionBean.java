package com.shuati.liulan;

import java.io.Serializable;
import java.util.List;

public class QuestionBean implements Serializable {

    private String errcode;
    private CurrentUser current_user;
    private String page_title;
    private String share_title;
    private String share_image;
    private List<Questions> questions;
    private Question question;
    private Chapter chapter;
    private Qbank qbank;

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public CurrentUser getCurrent_user() {
        return current_user;
    }

    public void setCurrent_user(CurrentUser current_user) {
        this.current_user = current_user;
    }

    public String getPage_title() {
        return page_title;
    }

    public void setPage_title(String page_title) {
        this.page_title = page_title;
    }

    public String getShare_title() {
        return share_title;
    }

    public void setShare_title(String share_title) {
        this.share_title = share_title;
    }

    public String getShare_image() {
        return share_image;
    }

    public void setShare_image(String share_image) {
        this.share_image = share_image;
    }

    public List<Questions> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Questions> questions) {
        this.questions = questions;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public Qbank getQbank() {
        return qbank;
    }

    public void setQbank(Qbank qbank) {
        this.qbank = qbank;
    }

    public static class CurrentUser {
        private String openid;
        private String nickname;
        private String avatarurl;
        private int phone;
        private int user_id;
        private String appid;
        private int blog_id;
        private int account_id;
        private boolean expired;
        private int time;
        private int modified;
        private boolean is_checkin;
        private int checkin_days;
        private String platform;
        private String user_email;
        private int id;
        private int country_code;
        private String display_name;
        private String email;
        private boolean anonymous;
        private int profile_status;

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatarurl() {
            return avatarurl;
        }

        public void setAvatarurl(String avatarurl) {
            this.avatarurl = avatarurl;
        }

        public int getPhone() {
            return phone;
        }

        public void setPhone(int phone) {
            this.phone = phone;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public int getBlog_id() {
            return blog_id;
        }

        public void setBlog_id(int blog_id) {
            this.blog_id = blog_id;
        }

        public int getAccount_id() {
            return account_id;
        }

        public void setAccount_id(int account_id) {
            this.account_id = account_id;
        }

        public boolean isExpired() {
            return expired;
        }

        public void setExpired(boolean expired) {
            this.expired = expired;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public int getOdified() {
            return modified;
        }

        public void setOdified(int odified) {
            modified = odified;
        }

        public boolean isIs_checkin() {
            return is_checkin;
        }

        public void setIs_checkin(boolean is_checkin) {
            this.is_checkin = is_checkin;
        }

        public int getCheckin_days() {
            return checkin_days;
        }

        public void setCheckin_days(int checkin_days) {
            this.checkin_days = checkin_days;
        }

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

        public String getUser_email() {
            return user_email;
        }

        public void setUser_email(String user_email) {
            this.user_email = user_email;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCountry_code() {
            return country_code;
        }

        public void setCountry_code(int country_code) {
            this.country_code = country_code;
        }

        public String getDisplay_name() {
            return display_name;
        }

        public void setDisplay_name(String display_name) {
            this.display_name = display_name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public boolean isAnonymous() {
            return anonymous;
        }

        public void setAnonymous(boolean anonymous) {
            this.anonymous = anonymous;
        }

        public int getProfile_status() {
            return profile_status;
        }

        public void setProfile_status(int profile_status) {
            this.profile_status = profile_status;
        }
    }

    public static class Question {
        private int id;
        private int post_id;
        private int chapter_id;
        private int apply_for;
        private int difficulty;
        private int correct_count;
        private int answer_count;
        private int score;
        private int type;
        private String type_name;
        private int time;
        private int status;
        private String difficulty_str;
        private String correct_answer;
        private String question;
        private String explain; //解析
        private List<Options> options;
        private String tags;
        private Object record;
        private Object serial_no;
        private boolean faved;
        private List<?> comments;
        private List<?> notes;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPost_id() {
            return post_id;
        }

        public void setPost_id(int post_id) {
            this.post_id = post_id;
        }

        public int getChapter_id() {
            return chapter_id;
        }

        public void setChapter_id(int chapter_id) {
            this.chapter_id = chapter_id;
        }

        public int getApply_for() {
            return apply_for;
        }

        public void setApply_for(int apply_for) {
            this.apply_for = apply_for;
        }

        public int getDifficulty() {
            return difficulty;
        }

        public void setDifficulty(int difficulty) {
            this.difficulty = difficulty;
        }

        public int getCorrect_count() {
            return correct_count;
        }

        public void setCorrect_count(int correct_count) {
            this.correct_count = correct_count;
        }

        public int getAnswer_count() {
            return answer_count;
        }

        public void setAnswer_count(int answer_count) {
            this.answer_count = answer_count;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getDifficulty_str() {
            return difficulty_str;
        }

        public void setDifficulty_str(String difficulty_str) {
            this.difficulty_str = difficulty_str;
        }

        public String getCorrect_answer() {
            return correct_answer;
        }

        public void setCorrect_answer(String correct_answer) {
            this.correct_answer = correct_answer;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getExplain() {
            return explain;
        }

        public void setExplain(String explain) {
            this.explain = explain;
        }

        public List<Options> getOptions() {
            return options;
        }

        public void setOptions(List<Options> options) {
            this.options = options;
        }

        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        public Object getRecord() {
            return record;
        }

        public void setRecord(Object record) {
            this.record = record;
        }

        public Object getSerial_no() {
            return serial_no;
        }

        public void setSerial_no(Object serial_no) {
            this.serial_no = serial_no;
        }

        public boolean isFaved() {
            return faved;
        }

        public void setFaved(boolean faved) {
            this.faved = faved;
        }

        public List<?> getComments() {
            return comments;
        }

        public void setComments(List<?> comments) {
            this.comments = comments;
        }

        public List<?> getNotes() {
            return notes;
        }

        public void setNotes(List<?> notes) {
            this.notes = notes;
        }

        public static class Options {
            private String option;
            private String text;

            public String getOption() {
                return option;
            }

            public void setOption(String option) {
                this.option = option;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }
        }
    }

    public static class Chapter {
        private int id;
        private int post_id;
        private int level;
        private int parent;
        private int count;
        private String name;
        private boolean is_trial;
        private int mode;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPost_id() {
            return post_id;
        }

        public void setPost_id(int post_id) {
            this.post_id = post_id;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getParent() {
            return parent;
        }

        public void setParent(int parent) {
            this.parent = parent;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isIs_trial() {
            return is_trial;
        }

        public void setIs_trial(boolean is_trial) {
            this.is_trial = is_trial;
        }

        public int getOde() {
            return mode;
        }

        public void setOde(int ode) {
            mode = ode;
        }
    }

    public static class Qbank {
        private int id;
        private String post_type;
        private String type;
        private String status;
        private String title;
        private String excerpt;
        private int timestamp;
        private String time;
        private String date;
        private String day;
        private String modified_time;
        private String modified_date;
        private String thumbnail;
        private int user_id;
        private int views;
        private int fav_count;
        private String fav_status;
        private boolean editable;
        private String subtitle;
        private String description;
        private String content;
        private Subject subject;
        private String banner;
        private double price;
        private List<String> obtain_way;
        private int expire_notify_days;
        private int sales;
        private String color;
        private boolean owned;
        private int answer_count;
        private int inc_answer_count;
        private Progress progress;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPost_type() {
            return post_type;
        }

        public void setPost_type(String post_type) {
            this.post_type = post_type;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getExcerpt() {
            return excerpt;
        }

        public void setExcerpt(String excerpt) {
            this.excerpt = excerpt;
        }

        public int getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(int timestamp) {
            this.timestamp = timestamp;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getOdified_time() {
            return modified_time;
        }

        public void setOdified_time(String odified_time) {
            modified_time = odified_time;
        }

        public String getOdified_date() {
            return modified_date;
        }

        public void setOdified_date(String odified_date) {
            modified_date = odified_date;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getViews() {
            return views;
        }

        public void setViews(int views) {
            this.views = views;
        }

        public int getFav_count() {
            return fav_count;
        }

        public void setFav_count(int fav_count) {
            this.fav_count = fav_count;
        }

        public String getFav_status() {
            return fav_status;
        }

        public void setFav_status(String fav_status) {
            this.fav_status = fav_status;
        }

        public boolean isEditable() {
            return editable;
        }

        public void setEditable(boolean editable) {
            this.editable = editable;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Subject getSubject() {
            return subject;
        }

        public void setSubject(Subject subject) {
            this.subject = subject;
        }

        public String getBanner() {
            return banner;
        }

        public void setBanner(String banner) {
            this.banner = banner;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public List<String> getObtain_way() {
            return obtain_way;
        }

        public void setObtain_way(List<String> obtain_way) {
            this.obtain_way = obtain_way;
        }

        public int getExpire_notify_days() {
            return expire_notify_days;
        }

        public void setExpire_notify_days(int expire_notify_days) {
            this.expire_notify_days = expire_notify_days;
        }

        public int getSales() {
            return sales;
        }

        public void setSales(int sales) {
            this.sales = sales;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public boolean isOwned() {
            return owned;
        }

        public void setOwned(boolean owned) {
            this.owned = owned;
        }

        public int getAnswer_count() {
            return answer_count;
        }

        public void setAnswer_count(int answer_count) {
            this.answer_count = answer_count;
        }

        public int getInc_answer_count() {
            return inc_answer_count;
        }

        public void setInc_answer_count(int inc_answer_count) {
            this.inc_answer_count = inc_answer_count;
        }

        public Progress getProgress() {
            return progress;
        }

        public void setProgress(Progress progress) {
            this.progress = progress;
        }

        public static class Subject {
            private int id;
            private int parent_id;
            private String name;
            private String description;
            private int price;
            private String obtain_way;
            private int expire_notify_days;
            private int sales;
            private boolean owned;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getParent_id() {
                return parent_id;
            }

            public void setParent_id(int parent_id) {
                this.parent_id = parent_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public String getObtain_way() {
                return obtain_way;
            }

            public void setObtain_way(String obtain_way) {
                this.obtain_way = obtain_way;
            }

            public int getExpire_notify_days() {
                return expire_notify_days;
            }

            public void setExpire_notify_days(int expire_notify_days) {
                this.expire_notify_days = expire_notify_days;
            }

            public int getSales() {
                return sales;
            }

            public void setSales(int sales) {
                this.sales = sales;
            }

            public boolean isOwned() {
                return owned;
            }

            public void setOwned(boolean owned) {
                this.owned = owned;
            }
        }

        public static class Progress {
            private int correct;
            private int current;
            private int total;

            public int getCorrect() {
                return correct;
            }

            public void setCorrect(int correct) {
                this.correct = correct;
            }

            public int getCurrent() {
                return current;
            }

            public void setCurrent(int current) {
                this.current = current;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }
        }
    }

    public static class Questions {
        private int id;
        private int post_id;
        private int chapter_id;
        private int apply_for;
        private int difficulty;
        private int correct_count;
        private int answer_count;
        private int score;
        private int type;
        private String type_name;
        private int time;
        private int status;
        private String difficulty_str;
        private String correct_answer;
        private String question;
        private String explain;
        private List<Options> options;
        private String tags;
        private Object record;
        private Object serial_no;
        private boolean faved;
        private List<?> comments;
        private List<?> notes;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPost_id() {
            return post_id;
        }

        public void setPost_id(int post_id) {
            this.post_id = post_id;
        }

        public int getChapter_id() {
            return chapter_id;
        }

        public void setChapter_id(int chapter_id) {
            this.chapter_id = chapter_id;
        }

        public int getApply_for() {
            return apply_for;
        }

        public void setApply_for(int apply_for) {
            this.apply_for = apply_for;
        }

        public int getDifficulty() {
            return difficulty;
        }

        public void setDifficulty(int difficulty) {
            this.difficulty = difficulty;
        }

        public int getCorrect_count() {
            return correct_count;
        }

        public void setCorrect_count(int correct_count) {
            this.correct_count = correct_count;
        }

        public int getAnswer_count() {
            return answer_count;
        }

        public void setAnswer_count(int answer_count) {
            this.answer_count = answer_count;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getDifficulty_str() {
            return difficulty_str;
        }

        public void setDifficulty_str(String difficulty_str) {
            this.difficulty_str = difficulty_str;
        }

        public String getCorrect_answer() {
            return correct_answer;
        }

        public void setCorrect_answer(String correct_answer) {
            this.correct_answer = correct_answer;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getExplain() {
            return explain;
        }

        public void setExplain(String explain) {
            this.explain = explain;
        }

        public List<Options> getOptions() {
            return options;
        }

        public void setOptions(List<Options> options) {
            this.options = options;
        }

        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        public Object getRecord() {
            return record;
        }

        public void setRecord(Object record) {
            this.record = record;
        }

        public Object getSerial_no() {
            return serial_no;
        }

        public void setSerial_no(Object serial_no) {
            this.serial_no = serial_no;
        }

        public boolean isFaved() {
            return faved;
        }

        public void setFaved(boolean faved) {
            this.faved = faved;
        }

        public List<?> getComments() {
            return comments;
        }

        public void setComments(List<?> comments) {
            this.comments = comments;
        }

        public List<?> getNotes() {
            return notes;
        }

        public void setNotes(List<?> notes) {
            this.notes = notes;
        }

        public static class Options {
            private String option;
            private String text;

            public String getOption() {
                return option;
            }

            public void setOption(String option) {
                this.option = option;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }
        }
    }
}
