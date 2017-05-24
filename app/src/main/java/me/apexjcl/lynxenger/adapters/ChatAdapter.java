package me.apexjcl.lynxenger.adapters;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import me.apexjcl.lynxenger.R;
import me.apexjcl.lynxenger.realm.models.lynxenger.Message;

/**
 * Created by apex on 24/05/2017.
 */

public class ChatAdapter extends RealmRecyclerViewAdapter<Message, ChatAdapter.ViewHolder> {


    public ChatAdapter(@Nullable OrderedRealmCollection<Message> data, boolean autoUpdate) {
        super(data, autoUpdate);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.update(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mMessage;

        public ViewHolder(View itemView) {
            super(itemView);
            mMessage = (TextView) itemView.findViewById(R.id.message);
        }

        public void update(int pos) {
            Message m = getItem(pos);
            mMessage.setText(m.getContent());
        }
    }

}
