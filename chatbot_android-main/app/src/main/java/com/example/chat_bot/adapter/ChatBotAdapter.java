package com.example.chat_bot.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chat_bot.R;
import com.example.chat_bot.model.MessageModel;

import java.util.List;

public class ChatBotAdapter extends RecyclerView.Adapter {
    List<MessageModel> messageModels;
    Context context;

    public ChatBotAdapter(List<MessageModel> messageModels, Context context) {
        this.messageModels = messageModels;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case 0:
                view= LayoutInflater.from(context).inflate(R.layout.usr_msg_item,parent,false);
                return new UserViewHolder(view);
            case 1:
                view= LayoutInflater.from(context).inflate(R.layout.bot_msg_item,parent,false);
                return new BotViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageModel messageModel = messageModels.get(position);
        switch (messageModel.getSender()){
            case "user":
                ((UserViewHolder)holder).tvMsgUsr.setText(messageModel.getMessage());
                break;
            case "bot":
                ((BotViewHolder)holder).tvMsgBot.setText(messageModel.getMessage());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return messageModels.size();
    }

    @Override
    public int getItemViewType(int position) {
        String sender = messageModels.get(position).getSender();
        switch (sender){
            case "user":
                return 0;
            case "bot":
                return 1;
            default:return -1;
        }
    }

    static class UserViewHolder extends RecyclerView.ViewHolder{
        TextView tvMsgUsr;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvMsgUsr =itemView.findViewById(R.id.tvMsgUsr);
        }
    }
    static class BotViewHolder extends RecyclerView.ViewHolder{
        TextView tvMsgBot;
        public BotViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvMsgBot =itemView.findViewById(R.id.tvMsgBot);
        }
    }
}
