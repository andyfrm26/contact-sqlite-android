package com.example.sqlite.repository;


import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.sqlite.database.ContactRoomDB;
import com.example.sqlite.database.DAOContact;
import com.example.sqlite.database.Contact;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ContactRepository {
    private final DAOContact mDaoContacts;

    private final ExecutorService executorService; public ContactRepository(Application application) {
        executorService = Executors.newSingleThreadExecutor();
        ContactRoomDB db = ContactRoomDB.getDatabase(application);
        mDaoContacts = db.daoContact();
    }
    public LiveData<List<Contact>> getAllContacts() {
        return mDaoContacts.getAllContacts();
    }
    public void insert(final Contact contact) {
        executorService.execute(() -> mDaoContacts.insert(contact));
    }
    public void delete(final Contact contact){
        executorService.execute(() -> mDaoContacts.delete(contact));
    }
    public void update(final Contact contact){
        executorService.execute(() -> mDaoContacts.update(contact));
    }
}
