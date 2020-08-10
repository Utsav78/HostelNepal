package com.example.hostelnepal.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hostelnepal.Model.PropertyModel;
import com.example.hostelnepal.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

public class ForYouAdapter extends FirestoreRecyclerAdapter<PropertyModel,ForYouAdapter.ViewHolder> {


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ForYouAdapter(@NonNull FirestoreRecyclerOptions<PropertyModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull PropertyModel model) {
        Picasso.get().load(model.getUriOfBuilding()).into(holder.ivBuilding);
        holder.tvName.setText(model.getNameOfHostel());
        holder.tvType.setText(model.getHostelType());

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_for_you_item,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivBuilding;
        TextView tvName;
        TextView tvType;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivBuilding = itemView.findViewById(R.id.building_image);
            tvName= itemView.findViewById(R.id.rv_hostel_name);
            tvType = itemView.findViewById(R.id.rv_hostel_type);
        }
    }
}
