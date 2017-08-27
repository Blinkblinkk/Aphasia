package com.example.musicmanpop.aphasiatalkhelper;

public class History {
    String sentence;
    int id;
    byte[] image;

    public History(int id, byte[] image, String sentence){
        this.setId(id);
        this.setImage(image);
        this.setSentence(sentence);
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
