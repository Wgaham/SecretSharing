package com.wgaham.secretsharing;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * 主页面列表的适配器
 * @author Wgaham
 * Created by zh on 2018/3/13.
 */

public class SecretlistAdapter extends RecyclerView.Adapter<SecretlistAdapter.ViewHolder> {

    private Context mContext;

    private List<Secretlist> mSecretlists;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView secretName;

        public ViewHolder(View view) {
            super(view);
            secretName = (TextView) view.findViewById(R.id.secret_list);
        }
    }

    public SecretlistAdapter(List<Secretlist> secretlists) {
        mSecretlists = secretlists;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext==null){
            mContext=parent.getContext();
        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.secret_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Secretlist secretlist=mSecretlists.get(position);
        holder.secretName.setText(secretlist.getName());
    }

    @Override
    public int getItemCount() {
        return mSecretlists.size();
    }
}
