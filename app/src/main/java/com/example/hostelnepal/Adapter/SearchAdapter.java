package com.example.hostelnepal.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hostelnepal.Model.PropertyModel;
import com.example.hostelnepal.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class SearchAdapter extends FirestoreRecyclerAdapter<PropertyModel, SearchAdapter.ViewHolder> {
    OnItemClickListener listener;


    public SearchAdapter(@NonNull FirestoreRecyclerOptions<PropertyModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull PropertyModel model) {

        holder.hostelName.setText(model.getNameOfHostel());
        holder.hostelType.setText(model.getHostelType());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_all_hostel,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView hostelName,hostelType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hostelName = itemView.findViewById(R.id.hostel_name);
            hostelType = itemView.findViewById(R.id.hostel_type);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION){
                        listener.onClick(getSnapshots().getSnapshot(position),position);
                    }
                }
            });
        }
    }
    public interface OnItemClickListener{
         void onClick(DocumentSnapshot documentSnapshot, int position);
    }
    public void setOnClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
