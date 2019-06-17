package com.example.cookbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    private Context mContext;
    private ArrayList<RecyclerItemConstructor> mRecyclerList;
    private onItemClickListener mListener;


    public RecyclerAdapter(Context context, ArrayList<RecyclerItemConstructor> FileList) {
        mContext = context;
        mRecyclerList = FileList;
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.recycler_item, parent, false);
        return new RecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        RecyclerItemConstructor currentItem = mRecyclerList.get(position);

        String imageUrl = currentItem.getmImageUrl();
        String titleName = currentItem.getmTitle();
        String description = currentItem.getmDescription();
        String recipeUrl = currentItem.getmImageUrl();

        holder.mTextViewTitle.setText(titleName);
        holder.mTextViewDescription.setText(description);

        Picasso.get().load(imageUrl.replace("http", "https")).fit().centerCrop().into(holder.mImageView);
        //najgluplji problem ikad, ne zele se ucitati slike sa http ali redovno se ucitaju sa https
        //"bug" popravljen sa .replace()
    }

    @Override
    public int getItemCount() {
        return mRecyclerList.size(); //dohvati onolko itema kolko ima u listi
    }

    public interface onItemClickListener {
        void onItemClick(int position);
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView;
        public TextView mTextViewTitle;
        public TextView mTextViewDescription;
        public TextView mRecipeUrl;


        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view);
            mTextViewTitle = itemView.findViewById(R.id.text_title_view);
            mTextViewDescription = itemView.findViewById(R.id.text_detail_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
