package dev.jay.ultimatepokedex.interceptor;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;

import dev.jay.ultimatepokedex.api.API;
import dev.jay.ultimatepokedex.model.dto.response.RefreshTokenDto;
import dev.jay.ultimatepokedex.prefs.AppPreferences;
import dev.jay.ultimatepokedex.secure_local_db.dao.UserAccessTokenDao;
import retrofit2.Call;
import retrofit2.Callback;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request originalRequest = chain.request();
        String url = originalRequest.url().toString();
        String accessToken = UserAccessTokenDao.getLastToken();
        Log.d("accessToken", accessToken + ">>>")
;
        if (!url.endsWith("/refresh-token") && accessToken != null) refreshToken();

        Request newRequest = originalRequest.newBuilder()
                .header("Authorization", "Bearer " + accessToken)
                .build();

        return chain.proceed(newRequest);
    }

    private void refreshToken() {
        API.userApi().refreshToken().enqueue(new Callback<RefreshTokenDto>() {
            @Override
            public void onResponse(@NonNull Call<RefreshTokenDto> call, @NonNull retrofit2.Response<RefreshTokenDto> response) {
                if (response.isSuccessful()) {
                    RefreshTokenDto refreshTokenDto = response.body();
                    if (refreshTokenDto != null) {
                        UserAccessTokenDao.insertToken(refreshTokenDto.getAccessToken());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<RefreshTokenDto> call, @NonNull Throwable t) {
                UserAccessTokenDao.deleteLastToken();
            }
        });
    }
}
