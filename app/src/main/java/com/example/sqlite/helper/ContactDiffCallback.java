package com.example.sqlite.helper;

import androidx.recyclerview.widget.DiffUtil;

import com.example.sqlite.database.Contact;

import java.util.List;

public class ContactDiffCallback extends DiffUtil.Callback{
    private final List<Contact> mOldContactList;
    private final List<Contact> mNewContactList;
    public ContactDiffCallback(List<Contact> oldContactList, List<Contact> newContactList) {
        this.mOldContactList = oldContactList;
        this.mNewContactList = newContactList;
    }
    @Override
    public int getOldListSize() {
        return mOldContactList.size();
    }
    @Override
    public int getNewListSize() {
        return mNewContactList.size();
    }
    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldContactList.get(oldItemPosition).getId() == mNewContactList.get(newItemPosition).getId();
    }
    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final Contact oldContact = mOldContactList.get(oldItemPosition);
        final Contact newContact = mNewContactList.get(newItemPosition);
        return oldContact.getName().equals(newContact.getName()) && oldContact.getNumber().equals(newContact.getNumber());
    }
}
