package dev.jay.ultimatepokedex.secure_local_db.entity.dto;

import android.database.Cursor;

import dev.jay.ultimatepokedex.secure_local_db.contract.FavoritePokemonContact;
import dev.jay.ultimatepokedex.secure_local_db.contract.UserDataContract;

public class UserDataDTO {

    private String username;
    private String image_url;

    public UserDataDTO() {}

    public UserDataDTO(String username, String image_url) {
        this.username = username;

        this.image_url = image_url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public static UserDataDTO fromCursor(Cursor cursor) {
        String username = cursor.getString(cursor.getColumnIndexOrThrow(UserDataContract.UserDataEntry.COLUMN_USERNAME));
        String imageUrl = cursor.getString(cursor.getColumnIndexOrThrow(UserDataContract.UserDataEntry.COLUMN_IMAGE_URL));

        return new UserDataDTO(username, imageUrl);
    }

    @Override
    public String toString() {
        return "UserDataDTO{" +
                "username='" + username + '\'' +
                ", image_url='" + image_url + '\'' +
                '}';
    }
}
