package com.tilgnerka.pdf.file_handler;

public class FileFormat {
    private String title;
    private char topicID;
    private char questionID;
    private char answerID;

    public FileFormat(String title, char topicID, char questionID, char answerID) {
        this.title = title;
        this.topicID = topicID;
        this.questionID = questionID;
        this.answerID = answerID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public char getTopicID() {
        return topicID;
    }

    public void setTopicID(char topicID) {
        this.topicID = topicID;
    }

    public char getQuestionID() {
        return questionID;
    }

    public void setQuestionID(char questionID) {
        this.questionID = questionID;
    }

    public char getAnswerID() {
        return answerID;
    }

    public void setAnswerID(char answerID) {
        this.answerID = answerID;
    }

    public String getIDs() {
        return String.valueOf(topicID) + String.valueOf(questionID) + String.valueOf(answerID);
    }
}

