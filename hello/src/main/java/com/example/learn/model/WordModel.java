package com.example.learn.model;

import java.io.Serializable;

public class WordModel implements Serializable {


    private int id;
    private String english;
    private String chinesea;
    private String chineseb;
    private String chinesec;
    private String chinesed;
    private String answer;
    private String learntime;
    private int iscorrect;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getChinesea() {
        return chinesea;
    }

    public void setChinesea(String chinesea) {
        this.chinesea = chinesea;
    }

    public String getChineseb() {
        return chineseb;
    }

    public void setChineseb(String chineseb) {
        this.chineseb = chineseb;
    }

    public String getChinesec() {
        return chinesec;
    }

    public void setChinesec(String chinesec) {
        this.chinesec = chinesec;
    }

    public String getChinesed() {
        return chinesed;
    }

    public void setChinesed(String chinesed) {
        this.chinesed = chinesed;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getLearntime() {
        return learntime;
    }

    public void setLearntime(String learntime) {
        this.learntime = learntime;
    }

    public int getIscorrect() {
        return iscorrect;
    }

    public void setIscorrect(int iscorrect) {
        this.iscorrect = iscorrect;
    }
}
