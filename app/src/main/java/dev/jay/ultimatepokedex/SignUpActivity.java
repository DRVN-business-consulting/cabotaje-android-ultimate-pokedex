package dev.jay.ultimatepokedex;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputLayout;

import dev.jay.ultimatepokedex.api.API;
import dev.jay.ultimatepokedex.model.dto.request.SignUpDto;
import dev.jay.ultimatepokedex.model.dto.response.ErrorDto;
import dev.jay.ultimatepokedex.model.dto.response.RefreshTokenDto;
import dev.jay.ultimatepokedex.model.dto.response.UserDto;
import dev.jay.ultimatepokedex.prefs.AppPreferences;
import dev.jay.ultimatepokedex.secure_local_db.dao.UserAccessTokenDao;
import dev.jay.ultimatepokedex.secure_local_db.dao.UserDataDao;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    EditText editUsername, editPassword, editName, editAge, editAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });


        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        editName = findViewById(R.id.editName);
        editAge = findViewById(R.id.editAge);
        editAdd = findViewById(R.id.editAdd);


        findViewById(R.id.btnSignIn).setOnClickListener(btnView -> finish());
        findViewById(R.id.btnSignUp).setOnClickListener(btnView -> onSignUp());
    }

    private void onSignUp() {

        String uname = editUsername.getText().toString();
        String pword = editPassword.getText().toString();
        String name = editName.getText().toString();
        int age = Integer.valueOf(editAge.getText().toString());
        String add = editAdd.getText().toString();

        API.userApi().signup(new SignUpDto(uname, pword, name, add, age)).enqueue(new Callback<RefreshTokenDto>() {
            @Override
            public void onResponse(Call<RefreshTokenDto> call, Response<RefreshTokenDto> response) {
                if (response.isSuccessful()) {
                    RefreshTokenDto refreshTokenDto = response.body();
                    if (refreshTokenDto != null) {

                        UserAccessTokenDao.insertToken(refreshTokenDto.getAccessToken());
//                                Log.d("JIANDDEBUG", "Access Token: " + refreshTokenDto.getAccessToken());
//                                Log.d("JIANDDEBUG", "Token Type: " + refreshTokenDto.getTokenType());
                        // AppPreferences.getInstance().setAccessToken(refreshTokenDto.getAccessToken());
                        me();
                    }
                } else {
                    ResponseBody errorBody = null;
                    try {
                        errorBody = response.errorBody();
                        if (errorBody != null) {
                            String json = errorBody.string();
                            ErrorDto errorDto = API.gson.fromJson(json, ErrorDto.class);
                            Toast.makeText(SignUpActivity.this, errorDto.getDetail(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (errorBody != null) {
                            errorBody.close();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<RefreshTokenDto> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void me() {
        API.userApi().me().enqueue(new Callback<UserDto>() {
            @Override
            public void onResponse(@NonNull Call<UserDto> call, @NonNull Response<UserDto> response) {
                if (response.isSuccessful()) {
                    UserDto userDto = response.body();
                    if (userDto != null) {
                        Log.d("JIANDDEBUG", "Username: " + userDto.getUsername());
                        UserDataDao.upsertData(userDto.getUsername(), "https://cdn-icons-png.flaticon.com/512/362/362003.png");
                        startActivity(new Intent(SignUpActivity.this, PokedexActivity.class));
                    }
                } else {
                    ResponseBody errorBody = null;
                    try {
                        errorBody = response.errorBody();
                        if (errorBody != null) {
                            String json = errorBody.string();
                            ErrorDto errorDto = API.gson.fromJson(json, ErrorDto.class);
                            Toast.makeText(SignUpActivity.this, errorDto.getDetail(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (errorBody != null) {
                            errorBody.close();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<UserDto> call, Throwable t) {
                Log.e("JIANDDEBUG", "Failed to fetch user", t);
                Toast.makeText(SignUpActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}