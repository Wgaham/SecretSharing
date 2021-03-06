package com.wgaham.secretsharing;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * 主页面列表的适配器
 *
 * @author Wgaham
 * Created by Wgaham on 2018/3/13.
 */

public class SecretlistAdapter extends RecyclerView.Adapter<SecretlistAdapter.ViewHolder> {

    private Context mContext;

    private List<Secretlist> mSecretlists;

    private static final int VIEW_ITEM = 1;
    private static final int VIEW_EMPTY = 0;
    private boolean empty = false;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView secretName;
        View secretView;

        ViewHolder(View view) {
            super(view);
            secretView = view;
            secretName = (TextView) view.findViewById(R.id.secret_list);
        }
    }

    SecretlistAdapter(List<Secretlist> secretlists) {
        mSecretlists = secretlists;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        if (viewType == VIEW_EMPTY) {
            View viewEmpty = LayoutInflater.from(mContext).inflate(R.layout.empty_item, parent, false);
            return new ViewHolder(viewEmpty);
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.secret_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.secretView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secretClick(holder);
            }
        });
        holder.secretName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secretClick(holder);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (!empty) {
            Secretlist secretlist = mSecretlists.get(position);
            holder.secretName.setText(secretlist.getName());
        }
    }

    @Override
    public int getItemCount() {
        if (mSecretlists.size() == 0) {
            return 1;
        }
        return mSecretlists.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mSecretlists.size() == 0) {
            empty = true;
            return VIEW_EMPTY;
        }
        empty = false;
        return VIEW_ITEM;
    }

    private void secretClick(ViewHolder holder) {
        int possition = holder.getAdapterPosition();
        Secretlist secretlist = mSecretlists.get(possition);
        Intent intent = new Intent(mContext, SecretShowActivity.class);
        intent.putExtra(SecretShowActivity.SECRET_ID, secretlist.getId());
        intent.putExtra(SecretShowActivity.SECRET_NAME, secretlist.getName());
        mContext.startActivity(intent);
    }
}