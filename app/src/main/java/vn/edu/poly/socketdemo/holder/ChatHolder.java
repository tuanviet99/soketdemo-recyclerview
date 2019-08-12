package vn.edu.poly.socketdemo.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import vn.edu.poly.socketdemo.R;

public class ChatHolder extends RecyclerView.ViewHolder {


    public TextView tvName;
    public TextView tvMessage;

    public ChatHolder(@NonNull View itemView) {
        super(itemView);
        tvName = itemView.findViewById(R.id.tvName);
        tvMessage = itemView.findViewById(R.id.tvMessage);
    }
}
