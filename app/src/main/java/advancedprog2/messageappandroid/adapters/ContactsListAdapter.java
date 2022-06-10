package advancedprog2.messageappandroid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import advancedprog2.messageappandroid.R;
import advancedprog2.messageappandroid.entities.Contact;

public class ContactsListAdapter extends RecyclerView.Adapter<ContactsListAdapter.ContactViewHolder> {

    class ContactViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvName;
        private final TextView tvContent;
        private final TextView tvTime;
        private final TextView tvPhoto;

        private ContactViewHolder(View itemview) {
            super(itemview);
            tvName = itemview.findViewById(R.id.tvContactName);
            tvContent = itemview.findViewById(R.id.tvContactContent);
            tvTime = itemview.findViewById(R.id.tvContactTime);
            tvPhoto = itemview.findViewById(R.id.tvContactPhoto);
        }
    }

    private final LayoutInflater mInflater;
    public List<Contact> contacts;

    public ContactsListAdapter(Context context) {mInflater = LayoutInflater.from(context);}

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.contect_layout, parent, false);
        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        if (contacts != null) {
            final Contact current = contacts.get(position);
            holder.tvName.setText(current.getName());
            holder.tvContent.setText(current.getLast());
            holder.tvTime.setText(current.getLastdate());
            holder.tvPhoto.setText("PP");
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
