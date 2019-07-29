package com.example.meli;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class BankRecyclerAdapter extends RecyclerView.Adapter<BankRecyclerAdapter.RecyclerViewHolder> {
    private int mSelectedItem = -1;
    private Context mContext;
    private List<BankModel> list;

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public RadioButton mRadio;
        public TextView mText;
        public ImageView mImage;

        public RecyclerViewHolder(final View view) {
            super(view);
            mText = (TextView) view.findViewById(R.id.recycler_textview);
            mImage = (ImageView) view.findViewById(R.id.recycler_imageview);
            mRadio = (RadioButton) view.findViewById(R.id.recycler_radiobutton);
            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelectedItem = getAdapterPosition();
                    notifyDataSetChanged();
                }
            };
            itemView.setOnClickListener(clickListener);
            mRadio.setOnClickListener(clickListener);
        }

        public TextView getmText() {
            return mText;
        }

        public ImageView getmImage() {
            return mImage;
        }
    }

    public BankRecyclerAdapter(Context mContext, List<BankModel> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public BankRecyclerAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        final View row = inflater.inflate(R.layout.item_imagetext, viewGroup, false);

        BankRecyclerAdapter.RecyclerViewHolder vh = new BankRecyclerAdapter.RecyclerViewHolder(row);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull BankRecyclerAdapter.RecyclerViewHolder viewHolder, int i) {
        viewHolder.mText.setText(list.get(i).getName());
        Picasso.with(mContext)
                .load(list.get(i).getImageURL())
                .into(viewHolder.mImage);
        viewHolder.mRadio.setChecked(i == mSelectedItem);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public BankModel getSelected() {
        return this.list.get(mSelectedItem);
    }
}
