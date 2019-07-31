package com.example.meli;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

public class InstallmentsRecyclerAdapter extends RecyclerView.Adapter<InstallmentsRecyclerAdapter.RecyclerViewHolder> {
    private int mSelectedItem = -1;
    private Context mContext;
    private List<InstallmentsModel> list;

    public InstallmentsRecyclerAdapter(Context mContext, List<InstallmentsModel> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        final View row = inflater.inflate(R.layout.item_installments, viewGroup, false);

        InstallmentsRecyclerAdapter.RecyclerViewHolder vh = new InstallmentsRecyclerAdapter.RecyclerViewHolder(row);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull InstallmentsRecyclerAdapter.RecyclerViewHolder viewHolder, int i) {
        String text = list.get(i).getRecommended_message() + " (" + list.get(i).getInstallment_rate()
                + "% recharge)";

        viewHolder.mText.setText(text);
        viewHolder.mRadio.setChecked(i == mSelectedItem);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public RadioButton mRadio;
        public TextView mText;

        public RecyclerViewHolder(final View view) {
            super(view);
            mText = (TextView) view.findViewById(R.id.recycler_textview);
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
    }

    public InstallmentsModel getSelected() {
        return this.list.get(mSelectedItem);
    }
}
