package com.example.TODO.Entity;


import com.example.TODO.enums.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Todo {
    public String title;
    public String content;
    public Status status;

    public String date;

    public Todo(){}
    public Todo(String title, String content) {
        this.title = title;
        this.content = content;
        this.status = Status.ACTIVE;
        this.date= LocalDate.now().toString();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", Status=" + status +
                '}';
    }
}
