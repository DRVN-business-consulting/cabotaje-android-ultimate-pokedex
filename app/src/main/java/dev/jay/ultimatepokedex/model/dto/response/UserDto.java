package dev.jay.ultimatepokedex.model.dto.response;

import com.google.gson.annotations.SerializedName;

public class UserDto {

    private String username;
    private String name;
    private String address;
    private int age;
    @SerializedName("auth_token")
    private String authToken;

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getAge() {
        return age;
    }

    public String getAuthToken() {
        return authToken;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", age=" + age +
                ", authToken='" + authToken + '\'' +
                '}';
    }
}