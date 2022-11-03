package com.example.sqlite.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Contact.class}, version = 1)
public abstract class ContactRoomDB extends RoomDatabase {
    public abstract DAOContact daoContact();
    private static volatile ContactRoomDB INSTANCE;
    public static ContactRoomDB getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (ContactRoomDB.class){
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ContactRoomDB.class, "contact_db").build();
            }
        }
        return INSTANCE;
    }
}
