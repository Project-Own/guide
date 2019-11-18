package com.example.guide.ui.about;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.guide.Model.Users;

import java.util.ArrayList;
import java.util.List;

public class AboutViewModel extends AndroidViewModel {

    private MutableLiveData<List<Users>> usersList;
    public AboutViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<Users>> loadUser(){
        if(usersList == null){
            usersList = new MutableLiveData<List<Users>>();
            loadUsersList();
        }
        return usersList;
    }

    private void loadUsersList() {
        List users = new ArrayList<>();
        users.add(new Users("NIRAJAN PRJAPATI","KHCE 074 BCT 024","prajapatinirajan0@gmail.com","photo"));
        users.add(new Users("NIRJAL PRAJAPATI","KHCE 074 BCT 026","nirjalprajapati@gmail.com","nirjal"));
        users.add(new Users("ROHIT PRAJAPATI","KHCE074 BCT 033","roht.praz@gmail.com","rohit"));
        users.add(new Users("SAHAS PRAJAPATI","KHCE074 BCT 037","sahas_1999@hotmail.com","sahas"));
        usersList.setValue(users);
    }

}
