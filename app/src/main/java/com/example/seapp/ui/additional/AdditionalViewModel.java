package com.example.seapp.ui.additional;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AdditionalViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AdditionalViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is additional fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}