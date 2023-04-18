package com.example.flashcardapp;

public class firebaseModel {

    private String title;
    private String question;
    private String answer;

    public firebaseModel(){

    }

    public firebaseModel(String title, String question, String answer){
        this.title = title;
        this.question = question;
        this.answer = answer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
