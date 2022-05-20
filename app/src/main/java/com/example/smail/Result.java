package com.example.smail;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Result {


    private List<List<Integer>> boxes = null;
    private List<String> recognition_words = null;

    public List<List<Integer>> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<List<Integer>> boxes) {
        this.boxes = boxes;
    }

    public List<String> getRecognitionWords() {
        return recognition_words;
    }

    public void setRecognitionWords(List<String> recognitionWords) {
        this.recognition_words = recognitionWords;
    }

}
