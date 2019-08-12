package vn.edu.poly.socketdemo.adapter;

import android.view.View;
import android.widget.TextView;

import vn.edu.poly.socketdemo.R;
import vn.edu.poly.socketdemo.holder.ChatHolder;

class ChatHolder2 extends ChatHolder {
    public TextView tvName;
    public TextView tvMessage;
    public ChatHolder2(View view) {
        super(view);
        tvName = itemView.findViewById(R.id.tvName);
        tvMessage = itemView.findViewById(R.id.tvMessage);
    }
}
