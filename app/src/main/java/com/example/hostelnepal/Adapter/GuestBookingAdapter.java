package com.example.hostelnepal.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hostelnepal.Model.Booking;
import com.example.hostelnepal.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class GuestBookingAdapter extends FirestoreRecyclerAdapter<Booking,GuestBookingAdapter.GuestBookingViewHolder> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    OnItemClickListener listener;
    public GuestBookingAdapter(@NonNull FirestoreRecyclerOptions<Booking> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull GuestBookingViewHolder holder, int position, @NonNull Booking model) {
        holder.hostelName.setText(model.getHostelName());
        holder.roomType.setText(model.getRoomType());

    }

    @NonNull
    @Override
    public GuestBookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_guest,parent,false);
        return new GuestBookingViewHolder(view);
    }

    public class GuestBookingViewHolder extends RecyclerView.ViewHolder {
        TextView hostelName,roomType;

        public GuestBookingViewHolder(@NonNull View itemView) {
            super(itemView);
            hostelName = itemView.findViewById(R.id.name_of_hostel_guest);
            roomType = itemView.findViewById(R.id.type_of_room);

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
