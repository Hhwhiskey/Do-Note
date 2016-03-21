package com.kevinhodges.donote.model;

/**
 * Created by Kevin on 3/20/2016.
 */
public class Note {

    private String author;
    private String content;
//    private String time;


    public Note() {

    }

    public Note(String author, String content, String time) {
        this.author = author;
        this.content = content;
//        this.time = time;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

//    public String getTime() {
//        return time;
//    }
//
//    public void setTime(String time) {
//        this.time = time;
//    }


}
