package com.example.hostelnepal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class ViewPropertyAdapter extends FirestoreRecyclerAdapter<PropertyModel,ViewPropertyAdapter.PropertyViewHolder> {

    private OnItemClickListener listener;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ViewPropertyAdapter(@NonNull FirestoreRecyclerOptions<PropertyModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull PropertyViewHolder holder, int position, @NonNull PropertyModel model) {
        holder.textName.setText(model.getNameOfHostel());
        holder.textLocality.setText(model.getCity());
        holder.typeOfHostel.setText(model.getHostelType());

    }

    @NonNull
    @Override
    public PropertyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_property_layout,parent,false);
        return new PropertyViewHolder(view);
    }

    public class PropertyViewHolder extends RecyclerView.ViewHolder {
        TextView textName,textLocality,typeOfHostel;

        public PropertyViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.name_of_hostel);
            textLocality = itemView.findViewById(R.id.location_of_hostel);
            typeOfHostel = itemView.findViewById(R.id.type_of_hostel);

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
