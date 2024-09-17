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
import dev.jay.ultimatepokedex.model.dto.request.LoginDto;
import dev.jay.ultimatepokedex.model.dto.response.ErrorDto;
import dev.jay.ultimatepokedex.model.dto.response.RefreshTokenDto;
import dev.jay.ultimatepokedex.model.dto.response.UserDto;
import dev.jay.ultimatepokedex.prefs.AppPreferences;
import dev.jay.ultimatepokedex.secure_local_db.contract.UserAccessTokenContract;
import dev.jay.ultimatepokedex.secure_local_db.dao.UserAccessTokenDao;
import dev.jay.ultimatepokedex.secure_local_db.dao.UserDataDao;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText editUsername, editPassword;
    TextInputLayout tiUname, tiPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (UserAccessTokenDao.getLastToken() != null) {
            startActivity(new Intent(LoginActivity.this, PokedexActivity.class));
            finish();
        }

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        tiUname = findViewById(R.id.tiUname);
        tiPw = findViewById(R.id.tiPw);

        editUsername = tiUname.getEditText();
        editPassword = tiPw.getEditText();

        findViewById(R.id.btnLogin).setOnClickListener(btnView -> onLogin());
        findViewById(R.id.btnSignUp).setOnClickListener(btnView -> startActivity(new Intent(this, SignUpActivity.class)));
    }


    private void onLogin(){
        String uname = editUsername.getText().toString();
        String pword = editPassword.getText().toString();

        setError(tiUname, null);
        setError(tiPw, null);

        boolean isUnameEmpty = uname.isEmpty();
        boolean isPwEmpty = pword.isEmpty();


        if(isUnameEmpty){
            setError(tiUname, "No Username");
        }

        if(isPwEmpty){
            setError(tiPw, "No Password");
        }

        if(!isUnameEmpty && !isPwEmpty) {
            login(uname, pword);
        }

    }

    private void setError(TextInputLayout textInputLayout, String errMsg){
        textInputLayout.setError(errMsg);
    }

    private void login(String uname, String pword) {
        API.userApi()
                .login(new LoginDto(uname, pword))
                .enqueue(new Callback<RefreshTokenDto>() {
                    @Override
                    public void onResponse(@NonNull Call<RefreshTokenDto> call, @NonNull Response<RefreshTokenDto> response) {
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
                                    setError(tiPw, errorDto.getDetail());
                                    //Toast.makeText(LoginActivity.this, errorDto.getDetail(), Toast.LENGTH_SHORT).show();
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
                        Log.e("JIANDDEBUG", "Failed to login", t);
                        Toast.makeText(LoginActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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
                        UserDataDao.upsertData(userDto.getUsername());
                        startActivity(new Intent(LoginActivity.this, PokedexActivity.class));
                    }
                } else {
                    ResponseBody errorBody = null;
                    try {
                        errorBody = response.errorBody();
                        if (errorBody != null) {
                            String json = errorBody.string();
                            ErrorDto errorDto = API.gson.fromJson(json, ErrorDto.class);
                            Toast.makeText(LoginActivity.this, errorDto.getDetail(), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(LoginActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}