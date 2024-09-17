package dev.jay.ultimatepokedex.model.dto.response.pokemon;

import com.google.gson.annotations.SerializedName;

public class Image {
    public String sprite;
    public String thumbnail;

    @SerializedName("hi_res")
    public String hiRes;

    public Image(String sprite, String thumbnail, String hiRes) {
        this.sprite = sprite;
        this.thumbnail = thumbnail;
        this.hiRes = hiRes;
    }

    public String getSprite() {
        return sprite;
    }

    public void setSprite(String sprite) {
        this.sprite = sprite;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getHiRes() {
        return hiRes;
    }

    public void setHiRes(String hiRes) {
        this.hiRes = hiRes;
    }

    @Override
    public String toString() {
        return "Image{" +
                "sprite='" + sprite + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", hiRes='" + hiRes + '\'' +
                '}';
    }
}
