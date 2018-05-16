package com.tilgnerka.pdf.file_handler;

public class FileFormat {
    private String title;
    private char topicID;
    private char questionID;
    private char answerID;
    private char subQuestionID;

    private String IDs;

    public FileFormat(String title, char topicID, char questionID, char answerID, char subQuestionID) {
        this.title = title;
        this.topicID = topicID;
        this.questionID = questionID;
        this.answerID = answerID;
        this.subQuestionID = subQuestionID;

        IDs = String.valueOf(topicID) + String.valueOf(questionID) + String.valueOf(answerID) + String.valueOf(subQuestionID);
    }

    public String getTitle() {
        return title;
    }

    public char getTopicID() {
        return topicID;
    }

    public char getQuestionID() {
        return questionID;
    }

    public char getAnswerID() {
        return answerID;
    }

    public char getSubQuestionID() {
        return subQuestionID;
    }

    public String getIDs() {
        return IDs;
    }
}

