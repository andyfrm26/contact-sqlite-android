package com.example.sqlite.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.sqlite.R;
import com.example.sqlite.database.Contact;
//import com.example.sqlite.databinding.ActivityInsertUpdateBinding;
import com.example.sqlite.databinding.ActivityInsertUpdateBinding;
import com.example.sqlite.helper.ViewModelFactory;

public class InsertUpdateActivity extends AppCompatActivity {

    public static final String EXTRA_CONTACT = "extra_contact";
    private final int ALERT_DIALOG_CLOSE = 10;
    private final int ALERT_DIALOG_DELETE = 20;
    private boolean isEdit = false;
    private Contact contact;
    private ContactInsertUpdateViewModel contactInsertUpdateViewModel;
    private ActivityInsertUpdateBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityInsertUpdateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        contactInsertUpdateViewModel = obtainViewModel(InsertUpdateActivity.this);
        contact = getIntent().getParcelableExtra(EXTRA_CONTACT);

        if (contact != null) {
            isEdit = true;
        } else {
            contact = new Contact();
        }

        String actionBarTitle;
        String btnTitle;

        if (isEdit) {
            binding.savedContact.setVisibility(View.VISIBLE);
            actionBarTitle = getString(R.string.change);
            btnTitle = getString(R.string.update);
            if (contact != null) {
                binding.edtName.setText(contact.getName());
                binding.edtNumber.setText(contact.getNumber());
                binding.edtInstagram.setText(contact.getInstagram());
                binding.edtGroup.setText(contact.getGroup());
            }
        } else {
            binding.savedContact.setVisibility(View.GONE);
            actionBarTitle = getString(R.string.add);
            btnTitle = getString(R.string.save);
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(actionBarTitle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        binding.btnSubmit.setText(btnTitle);

        binding.btnSms.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("sms:"+contact.getNumber()));
            startActivity(intent);
        });
        binding.btnTelepon.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:"+contact.getNumber()));
            startActivity(intent);
        });
        binding.btnInstagram.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://instagram.com/"+contact.getInstagram()));
            startActivity(intent);
        });
        binding.btnWhatsapp.setOnClickListener(view -> {
            Intent sendIntent = new Intent("android.intent.action.MAIN");
            sendIntent.setAction(Intent.ACTION_VIEW);
            sendIntent.setPackage("com.whatsapp");
            String url = "https://api.whatsapp.com/send?phone="+contact.getNumber()+"&text=";
            sendIntent.setData(Uri.parse(url));
            startActivity(sendIntent);
        });
        binding.btnSubmit.setOnClickListener(view -> {
            String name = binding.edtName.getText().toString().trim();
            String number = binding.edtNumber.getText().toString().trim();
            String instagram = binding.edtInstagram.getText().toString().trim();
            String group = binding.edtGroup.getText().toString().trim();

            if (name.isEmpty()) {
                binding.edtName.setError(getString(R.string.empty));
            } else if (number.isEmpty()) {
                binding.edtNumber.setError(getString(R.string.empty));
            } else if (instagram.isEmpty()) {
                binding.edtInstagram.setError(getString(R.string.empty));
            } else if (group.isEmpty()) {
                binding.edtGroup.setError(getString(R.string.empty));
            } else {
                contact.setInstagram(instagram);
                contact.setGroup(group);
                contact.setName(name);
                contact.setNumber(number);

                Intent intent = new Intent();
                intent.putExtra(EXTRA_CONTACT, contact);
                if (isEdit) {
                    contactInsertUpdateViewModel.update(contact);
                    showToast(getString(R.string.changed));
                } else {
                    contactInsertUpdateViewModel.insert(contact);
                    showToast(getString(R.string.added));
                }
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isEdit) {
            getMenuInflater().inflate(R.menu.menu_form, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_delete) {
            showAlertDialog(ALERT_DIALOG_DELETE);
        } else if (item.getItemId() == android.R.id.home) {
            showAlertDialog(ALERT_DIALOG_CLOSE);
        } else {
            return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item); }

    @Override
    public void onBackPressed() {
        showAlertDialog(ALERT_DIALOG_CLOSE);
    }

    private void showAlertDialog(int type) {
        final boolean isDialogClose = type == ALERT_DIALOG_CLOSE;
        String dialogTitle, dialogMessage;
        if (isDialogClose) {
            dialogTitle = getString(R.string.cancel);
            dialogMessage = getString(R.string.message_cancel);
        } else {
            dialogMessage = getString(R.string.message_delete); dialogTitle = getString(R.string.delete);
        }
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder.setMessage(dialogMessage).setCancelable(false) .setPositiveButton(getString(R.string.yes), (dialog, id) -> {
                            if (!isDialogClose) {
                                contactInsertUpdateViewModel.delete(contact);
                                showToast(getString(R.string.deleted));
                            }
                            finish(); })
                .setNegativeButton(getString(R.string.no), (dialog, id) -> dialog.cancel());
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    private void showToast(String message) {
        Toast.makeText(this, message,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @NonNull
    private static ContactInsertUpdateViewModel obtainViewModel(AppCompatActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return new ViewModelProvider(activity, (ViewModelProvider.Factory) factory).get(ContactInsertUpdateViewModel.class);
    }
}