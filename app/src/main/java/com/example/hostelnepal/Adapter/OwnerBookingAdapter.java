package com.example.hostelnepal.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hostelnepal.Model.BookingOwner;
import com.example.hostelnepal.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class OwnerBookingAdapter extends FirestoreRecyclerAdapter<BookingOwner, OwnerBookingAdapter.OwnerBookingViewHolder> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    OnItemClickListener listener;
    public OwnerBookingAdapter(@NonNull FirestoreRecyclerOptions<BookingOwner> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull OwnerBookingViewHolder holder, int position, @NonNull BookingOwner model) {
        holder.guestName.setText(model.getGuestName());
        holder.hostelName.setText(model.getHostelName());

    }

    @NonNull
    @Override
    public OwnerBookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_owner,parent,false);
        return new OwnerBookingViewHolder(view);
    }

    public class OwnerBookingViewHolder extends RecyclerView.ViewHolder {
        TextView guestName,hostelName;

        public OwnerBookingViewHolder(@NonNull View itemView) {
            super(itemView);
            guestName = itemView.findViewById(R.id.guest_name);
            hostelName = itemView.findViewById(R.id.name_hostel);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(getSnapshots().getSnapshot(position),position);
                    }
                }
            });
        }
    }
    public interface OnItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot , int position);
    }
    public void setOnClickListener(OnItemClickListener listener){
        this.listener = listener;

    }
}
