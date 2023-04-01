package com.evan.wj.pojo;

import com.alibaba.excel.annotation.ExcelProperty;

public class BookVO {
    //    @ExcelProperty(index = 0,value = "书名")
//    private int id;
    @ExcelProperty(index = 2,value = "书名1")
    private String cover;
    @ExcelProperty(index = 3,value = "书名2")
    private String title;
    @ExcelProperty(index = 4,value = "书名3")
    private String author;
    @ExcelProperty(index = 5,value = "书名4")
    private String date;
    @ExcelProperty(index = 6,value = "书名5")
    private String press;
    @ExcelProperty(index = 7,value = "书名6")
    private String abs;
    @ExcelProperty(index = 8,value = "书名7")
    private int cid;

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public String getAbs() {
        return abs;
    }

    public void setAbs(String abs) {
        this.abs = abs;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    @Override
    public String toString() {
        return "Book{" +
//                "id=" + id +
                ", cover='" + cover + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", date='" + date + '\'' +
                ", press='" + press + '\'' +
                ", abs='" + abs + '\'' +
                ", cid=" + cid +
                '}';
    }
}
