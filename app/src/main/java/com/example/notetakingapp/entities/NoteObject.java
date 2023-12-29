package com.example.notetakingapp.entities;

import lombok.Getter;
import lombok.Setter;


public class NoteObject {
    @Setter
    @Getter
    private String title;

    @Setter
    @Getter
    private String content;

    public NoteObject(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @Override
    public String toString() {
        return title;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
