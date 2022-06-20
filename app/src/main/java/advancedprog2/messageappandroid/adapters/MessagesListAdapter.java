package advancedprog2.messageappandroid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import advancedprog2.messageappandroid.R;
import advancedprog2.messageappandroid.entities.Contact;
import advancedprog2.messageappandroid.entities.Message;

public class MessagesListAdapter extends RecyclerView.Adapter<MessagesListAdapter.MessageViewHolder> {

    class MessageViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout lMsgRecv;
        private final LinearLayout lMsgSent;
        private final TextView tvRecvContent;
        private final TextView tvSentContent;
        private final TextView tvRecvDate;
        private final TextView tvSentDate;
        //private final TextView tvTime;

        private MessageViewHolder(View itemview) {
            super(itemview);
            lMsgRecv = itemview.findViewById(R.id.message_received_layout);
            lMsgSent = itemview.findViewById(R.id.message_sent_layout);
            tvRecvContent = itemview.findViewById(R.id.message_received_content);
            tvSentContent = itemview.findViewById(R.id.message_sent_content);
            tvRecvDate = itemview.findViewById(R.id.message_received_date);
            tvSentDate = itemview.findViewById(R.id.message_sent_date);
        }
    }

    private final LayoutInflater mInflater;
    public List<Message> messages;

    public MessagesListAdapter(Context context) {mInflater = LayoutInflater.from(context);}

    @Override
    public MessagesListAdapter.MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.message_layout, parent, false);
        return new MessagesListAdapter.MessageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MessagesListAdapter.MessageViewHolder holder, int position) {
        if (messages != null) {
            final Message current = messages.get(position);
            if (current.isSent()) {
                holder.lMsgSent.setVisibility(View.VISIBLE);
                holder.tvSentContent.setText(current.getContent());
                holder.tvSentDate.setText(current.getCreated());
                holder.lMsgRecv.setVisibility(View.INVISIBLE);
            } else {
                holder.lMsgSent.setVisibility(View.INVISIBLE);
                holder.lMsgRecv.setVisibility(View.VISIBLE);
                holder.tvRecvContent.setText(current.getContent());
                holder.tvRecvDate.setText(current.getCreated());
            }
        }
    }

    public void setMessages(List<Message> messageList) {
        messages = messageList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (messages != null) {
            return messages.size();
        }
        else return 0;
    }

}