package com.example.duantn.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.duantn.R;
import com.example.duantn.morder.Tour;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TourAdapter extends RecyclerView.Adapter<TourAdapter.ViewHolder>  {
    private List<Tour> tourList;
    private Context context;

    public interface OnClickItemListener {
        void onClicked(int position);
        void onSwitched(boolean isChecked);
    }
    private OnClickItemListener onClickItemListener;
    public TourAdapter(List<Tour> tourList, Context context, OnClickItemListener onClickItemListener) {
        this.tourList = tourList;
        this.context = context;
        this.onClickItemListener = onClickItemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tour, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tv_tour_title.setText(tourList.get(position).getCateName());
        Glide.with(context).load("https://webtourintro.herokuapp.com/"+tourList.get(position).getAvatar()).into(holder.img_tour);

       List<ImageView> imageViewList = Arrays.asList(new ImageView[]{holder.img_star1, holder.img_star2, holder.img_star3, holder.img_star4, holder.img_star5});
        for (int i=0;i<imageViewList.size();i++){
            imageViewList.get(i).setImageResource(R.drawable.star2);
        }
       for (int i=0;i<5;i++){
           imageViewList.get(i).setImageResource(R.drawable.star);
       }

       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (onClickItemListener != null)
                   onClickItemListener.onClicked(position);
           }
       });
    }

    @Override
    public int getItemCount() {
        return tourList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ShapeableImageView img_tour;
        private TextView tv_tour_title;
        private ImageView img_star1,img_star2,img_star3,img_star4,img_star5;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_tour = itemView.findViewById(R.id.img_tour);
            tv_tour_title = itemView.findViewById(R.id.tv_title_tour);
            img_star1 = itemView.findViewById(R.id.img_star1);
            img_star2 = itemView.findViewById(R.id.img_star2);
            img_star3 = itemView.findViewById(R.id.img_star3);
            img_star4 = itemView.findViewById(R.id.img_star4);
            img_star5 = itemView.findViewById(R.id.img_star5);
        }
    }


}
