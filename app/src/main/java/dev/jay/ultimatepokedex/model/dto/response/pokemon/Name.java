package dev.jay.ultimatepokedex.model.dto.response.pokemon;

public class Name{
    public String english;
    public String japanese;
    public String chinese;
    public String french;

    public Name(String english) {
        this.english = english;
    }

    public Name(String english, String japanese, String chinese, String french) {
        this.english = english;
        this.japanese = japanese;
        this.chinese = chinese;
        this.french = french;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getJapanese() {
        return japanese;
    }

    public void setJapanese(String japanese) {
        this.japanese = japanese;
    }

    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    public String getFrench() {
        return french;
    }

    public void setFrench(String french) {
        this.french = french;
    }

    @Override
    public String toString() {
        return "Name{" +
                "english='" + english + '\'' +
                ", japanese='" + japanese + '\'' +
                ", chinese='" + chinese + '\'' +
                ", french='" + french + '\'' +
                '}';
    }
}
