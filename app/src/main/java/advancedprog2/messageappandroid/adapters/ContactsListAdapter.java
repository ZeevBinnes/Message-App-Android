package advancedprog2.messageappandroid.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import advancedprog2.messageappandroid.R;
import advancedprog2.messageappandroid.activities.ChatActivity;
import advancedprog2.messageappandroid.entities.Contact;

public class ContactsListAdapter extends RecyclerView.Adapter<ContactsListAdapter.ContactViewHolder> {

    class ContactViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvName;
        private final TextView tvContent;
        private final TextView tvTime;
        private final ImageView tvPhoto;

        private ContactViewHolder(@NonNull View itemview) {
            super(itemview);
            tvName = itemview.findViewById(R.id.tvContactName);
            tvContent = itemview.findViewById(R.id.tvContactContent);
            tvTime = itemview.findViewById(R.id.tvContactTime);
            tvPhoto = itemview.findViewById(R.id.tvContactPhoto);
        }
    }

    private final LayoutInflater mInflater;
    public List<Contact> contacts;
    private Context context;
    private String username;

    public ContactsListAdapter(Context context, String username) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.username = username;
    }

    @Override
    @NonNull
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.contact_layout, parent, false);
        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        if (contacts != null) {
            final Contact current = contacts.get(position);
            holder.tvName.setText(current.getName());
            holder.tvContent.setText(current.getLast());
            holder.tvTime.setText(current.getLastdate());
            //holder.tvPhoto.setText("PP");

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("contactName", current.getName());
                intent.putExtra("contactId", current.getId());
                intent.putExtra("username", username);
                intent.putExtra("contactServer", current.getServer());
                context.startActivity(intent);
            });
        }
    }

    public void setContacts(List<Contact> contactsList) {
        contacts = contactsList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (contacts != null) {
            return contacts.size();
        }
        else return 0;
    }

}
