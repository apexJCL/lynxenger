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
import me.apexjcl.lynxenger.activities.MainActivity;
import me.apexjcl.lynxenger.realm.models.contactRealm.Contact;

/**
 * Adapter for contacts list
 * Created by apex on 24/05/2017.
 */

public class ContactsAdapter extends RealmRecyclerViewAdapter<Contact, ContactsAdapter.ViewHolder> {


    public ContactsAdapter(@Nullable OrderedRealmCollection<Contact> data, boolean autoUpdate) {
        super(data, autoUpdate);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.update(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mUsername;
        private String mUserId;

        public ViewHolder(View itemView) {
            super(itemView);
            mUsername = (TextView) itemView.findViewById(R.id.username);
            itemView.setOnClickListener(this);
        }

        public void update(int position) {
            Contact c = getItem(position);
            mUsername.setText(c.getUsername());
            mUserId = c.getUserId();
        }

        @Override
        public void onClick(View v) {
            ((MainActivity) v.getContext()).startChat(mUserId, mUsername.getText().toString());
        }
    }

}
