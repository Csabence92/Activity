package com.bonc.activity.Others;

public class Cards {
    public Cards(String word, int point, int id) {
        this.word = word;
        this.point = point;
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String word;
    private int point;
    private int id;
}
