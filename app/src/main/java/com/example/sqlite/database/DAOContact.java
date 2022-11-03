package com.example.sqlite.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DAOContact {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Contact contact);
    @Update()
    void update(Contact contact);
    @Delete()
    void delete(Contact contact);
    @Query("SELECT * from contact ORDER BY id ASC")
    LiveData<List<Contact>> getAllContacts();
}
