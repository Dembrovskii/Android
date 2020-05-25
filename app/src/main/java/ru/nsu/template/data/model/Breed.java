package ru.nsu.template.data.model;

        import com.google.gson.annotations.SerializedName;

        import java.io.Serializable;

public class Breed implements Serializable {
    @SerializedName("name")
    public String name;

    @SerializedName("picture")
    public Avatar picture;

    public String getName() {
        return name;
    }

    public Avatar getPicture() {
        return picture;
    }

    public Breed(String name, Avatar avatar) {
        this.name = name;
        this.picture = avatar;
    }
}
