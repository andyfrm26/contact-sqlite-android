package com.example.sqlite.ui;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.sqlite.database.Contact;
import com.example.sqlite.repository.ContactRepository;

import java.util.List;

public class ContactMainViewModel extends ViewModel {
    private final ContactRepository mContactRepository;

    public ContactMainViewModel(Application application) {
        mContactRepository = new ContactRepository(application);
    }
    LiveData<List<Contact>> getAllContacts() {
        return mContactRepository.getAllContacts();
    }
}
