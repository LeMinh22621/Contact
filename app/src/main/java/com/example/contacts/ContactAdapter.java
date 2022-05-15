package com.example.contacts;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contacts.databinding.ContactItemBinding;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder>
{
    ActivityResultLauncher<Intent> launchSomeActivity;
    public ContactAdapter(ArrayList<ContactEntity> contactList, ActivityResultLauncher<Intent> launchSomeActivity) {
        this.contactList = contactList;
        this.launchSomeActivity = launchSomeActivity;
    }

    private ArrayList<ContactEntity> contactList;

    public void setData(ArrayList<ContactEntity> contactList)
    {
        this.contactList = contactList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String fn = contactList.get(position).getFirstName().trim();
        String ln = contactList.get(position).getLastName().trim();
        holder.binding.tvName.setText(fn +" "+ ln);
        String tv = "" + fn.charAt(0) + ln.charAt(0);
        holder.binding.tvAvatar.setText(tv);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position1, boolean isNew) {
                if (isNew) {

                } else {
                    Intent intent = new Intent(view.getContext(), PersonDetail.class);

                    ContactEntity contactEntity = new ContactEntity(contactList.get(position1).getFirstName(),
                            contactList.get(position1).getLastName(),
                            contactList.get(position1).getPhone(),
                            contactList.get(position1).getEmail());
                    intent.putExtra("contactEntity", contactEntity);
                    intent.putExtra("index", position1 + "");
                    launchSomeActivity.launch(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ContactItemBinding binding;
        private ItemClickListener itemClickListener;

        public ViewHolder(View view) {
            super(view);
            binding = ContactItemBinding.bind(view);

            view.setOnClickListener(this);
        }
        public void setItemClickListener(ItemClickListener itemClickListener)
        {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(), false);
        }
    }
}
