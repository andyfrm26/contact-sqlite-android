package com.example.sqlite.ui;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlite.database.Contact;
import com.example.sqlite.databinding.ItemNoteBinding;
import com.example.sqlite.helper.ContactDiffCallback;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.NoteViewHolder> {
    private final ArrayList<Contact> listContacts = new ArrayList<>();

    void setListContacts(List<Contact> listContacts) {
        final ContactDiffCallback diffCallback = new ContactDiffCallback(this.listContacts, listContacts);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        this.listContacts.clear();
        this.listContacts.addAll(listContacts);
        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNoteBinding binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.getContext() ), parent, false);
        return new NoteViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final NoteViewHolder holder, int position) {
        holder.bind(listContacts.get(position));
    }

    @Override
    public int getItemCount() {
        return listContacts.size();
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {
        final ItemNoteBinding binding;
        NoteViewHolder(ItemNoteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(Contact contact) {
            binding.tvItemName.setText(contact.getName());
            binding.tvItemNumber.setText(contact.getNumber());
            binding.cvItemContact.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), InsertUpdateActivity.class);
                intent.putExtra(InsertUpdateActivity.EXTRA_CONTACT, contact); v.getContext().startActivity(intent);
            });
        }
    }
}
