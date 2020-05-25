package ru.nsu.template.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class BreedList implements Serializable {
    public BreedList() {
        message = Collections.emptyList();
    }

    @SerializedName("message")
    private List<String> message;

    public List<String> getMessage() {
        return message;
    }
}
