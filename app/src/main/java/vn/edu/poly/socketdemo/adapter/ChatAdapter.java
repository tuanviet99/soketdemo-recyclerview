package vn.edu.poly.socketdemo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import vn.edu.poly.socketdemo.MainActivity;
import vn.edu.poly.socketdemo.R;
import vn.edu.poly.socketdemo.holder.ChatHolder;
import vn.edu.poly.socketdemo.model.Chat;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public Context context;
    public List<Chat> chats;
    public static String name;

    int ITEM = 1;
    int LOAD_MORE = 2;

    public ChatAdapter(Context context, List<Chat> chats) {
        this.context = context;
        this.chats = chats;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM) {
            View view = LayoutInflater.from(context)
                    .inflate(R.layout.item_chat, parent, false);
            return new ChatHolder(view);
        } else {
            View view = LayoutInflater.from(context)
                    .inflate(R.layout.iten_chat_2, parent, false);
            return new ChatHolder2(view);
        }

//        View root = LayoutInflater.from(context).inflate(R.layout.item_chat, viewGroup, false);
//        return new ChatHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ChatHolder) {
            Chat chat = chats.get(position);
            name = chat.getName();
            ((ChatHolder) holder).tvName.setText(chat.getName());
            ((ChatHolder) holder).tvMessage.setText(chat.message);
        } else if (holder instanceof ChatHolder2) {

            Chat chat2 = chats.get(position);
            ((ChatHolder2) holder).tvName.setText(chat2.getName());
            ((ChatHolder2) holder).tvMessage.setText(chat2.message);
        }

    }


    @Override
    public int getItemViewType(int position) {
        Chat comment = chats.get(position);

        if (!comment.getName().equals("Tuấn Việt")) {
            return ITEM;
        } else {

            return LOAD_MORE;
        }

    }



    @Override
    public int getItemCount() {
        return chats.size();
    }


//    @Override
//    public void onBindViewHolder(@NonNull ChatHolder chatHolder, int i) {
//        Chat chat = chats.get(i);
//        chatHolder.tvName.setText(chat.name);
//        chatHolder.tvMessage.setText(chat.message);
//    }

    public void updateData(List<Chat> chats) {
        this.chats = chats;
        notifyDataSetChanged();
    }


}
