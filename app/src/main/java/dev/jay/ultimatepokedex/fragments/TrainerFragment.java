package dev.jay.ultimatepokedex.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import dev.jay.ultimatepokedex.LoginActivity;
import dev.jay.ultimatepokedex.R;
import dev.jay.ultimatepokedex.api.API;
import dev.jay.ultimatepokedex.model.dto.request.SignUpDto;
import dev.jay.ultimatepokedex.model.dto.request.UpdateUserDto;
import dev.jay.ultimatepokedex.model.dto.response.SuccessDto;
import dev.jay.ultimatepokedex.model.dto.response.UserDto;
import dev.jay.ultimatepokedex.secure_local_db.dao.UserAccessTokenDao;
import dev.jay.ultimatepokedex.secure_local_db.dao.UserDataDao;
import dev.jay.ultimatepokedex.secure_local_db.entity.dto.UserDataDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TrainerFragment extends Fragment {

    ImageView ivAvatar;
    EditText  editPassword, editName, editAge, editAdd;

    public TrainerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trainer, container, false);

        ivAvatar = view.findViewById(R.id.ivAvatar);
        editPassword =  view.findViewById(R.id.editPassword);
        editName =  view.findViewById(R.id.editName);
        editAge =  view.findViewById(R.id.editAge);
        editAdd =  view.findViewById(R.id.editAdd);

        view.findViewById(R.id.btnUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = editPassword.getText().toString();
                String name = editName.getText().toString();
                String age = editAge.getText().toString();
                String add = editAdd.getText().toString();
                API.userApi().updateMe(new UpdateUserDto(password, name, add, Integer.parseInt(age))).enqueue(new Callback<UserDto>() {
                    @Override
                    public void onResponse(Call<UserDto> call, Response<UserDto> response) {
                        if(response.isSuccessful()) {
                            Toast.makeText(getActivity(), "Profile updated successfully", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserDto> call, Throwable t) {

                    }
                });
            }
        });
        view.findViewById(R.id.btnLogout).setOnClickListener(btView -> {
            API.userApi().logout().enqueue(new Callback<SuccessDto>() {
                @Override
                public void onResponse(Call<SuccessDto> call, Response<SuccessDto> response) {
                    if(response.isSuccessful()) {
                        SuccessDto successDto = response.body();
                        Toast.makeText(getActivity(), successDto.getMessage(), Toast.LENGTH_SHORT).show();
                        UserAccessTokenDao.deleteLastToken();
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        getActivity().finish();

                    }
                }

                @Override
                public void onFailure(Call<SuccessDto> call, Throwable t) {

                }
            });
        });
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        API.userApi().me().enqueue(new Callback<UserDto>() {
            @Override
            public void onResponse(Call<UserDto> call, Response<UserDto> response) {
                if(response.isSuccessful()) {
                    UserDto userDto = response.body();
                    String imageUrl = UserDataDao.getImageUrl(userDto.getUsername());
                    editName.setText(userDto.getName());
                    editAdd.setText(userDto.getAddress());
                    editAge.setText(String.valueOf(userDto.getAge()));
                    Glide.with(getActivity()).load(imageUrl).circleCrop().into(ivAvatar);
                }
            }

            @Override
            public void onFailure(Call<UserDto> call, Throwable t) {

            }
        });
    }
}