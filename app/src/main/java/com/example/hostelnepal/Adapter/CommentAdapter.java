package com.example.hostelnepal.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hostelnepal.Model.CommentModel;
import com.example.hostelnepal.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class CommentAdapter extends FirestoreRecyclerAdapter<CommentModel, CommentAdapter.CommentViewHolder> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public CommentAdapter(@NonNull FirestoreRecyclerOptions<CommentModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull CommentViewHolder holder, int position, @NonNull CommentModel model) {
        holder.message.setText(model.getComment());
        holder.date.setText(model.getDate());
        holder.guestName.setText(model.getGuestName());

    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_layout,parent,false);
        return new CommentViewHolder(view);
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder{
        TextView guestName,date,message;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            guestName = itemView.findViewById(R.id.username);
            date = itemView.findViewById(R.id.date);
            message = itemView.findViewById(R.id.comment_message);
        }
    }
}
