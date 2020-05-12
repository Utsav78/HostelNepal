package com.example.hostelnepal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class HostelLocationAdapter extends RecyclerView.Adapter<HostelLocationAdapter.HostelLocationViewHolder> {
    Context context;
    ArrayList<Integer> locationImages = new ArrayList<>();
    ArrayList<String> locationNames = new ArrayList<>();

    public HostelLocationAdapter(Context context, ArrayList<Integer> locationImages, ArrayList<String> locationNames) {
        this.context = context;
        this.locationImages = locationImages;
        this.locationNames = locationNames;
    }

    @NonNull
    @Override
    public HostelLocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_for_location_images_recyclerview,parent,false);
        return new HostelLocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HostelLocationViewHolder holder, int position) {
        holder.imgIcon.setImageResource(locationImages.get(position));
        holder.txtName.setText(locationNames.get(position));

    }

    @Override
    public int getItemCount() {
        return locationNames.size();
    }

    public class HostelLocationViewHolder extends RecyclerView.ViewHolder{
        CircleImageView imgIcon;
        TextView txtName;
        LinearLayout parent_layout;

        public HostelLocationViewHolder(@NonNull View itemView) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.imgIcon);
            txtName = itemView.findViewById(R.id.txtName);
            parent_layout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
