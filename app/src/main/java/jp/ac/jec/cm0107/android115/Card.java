package jp.ac.jec.cm0107.android115;

public class Card {
    private String japanese; // 日本語
    private String english; // 英語
    private int id; // カードにidを付けておく

    public Card(String japanese, String english, int id) {
        this.japanese = japanese;
        this.english = english;
        this.id = id;
    }

    public Card(String japanese, String english) {
        this.japanese = japanese;
        this.english = english;
    }

    public String getJapanese() {
        return japanese;
    }

    public void setJapanese(String japanese) {
        this.japanese = japanese;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
