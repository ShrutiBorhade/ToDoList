package com.example.todolist.Model;

public class ToDoModel {
    private int id,status;
    private String task;
    private String title;
    public ToDoModel() {
    }

    public ToDoModel(String task, int status, String title, int id) {
        this.task = task;
        this.status = status;
        this.title=title;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}