package com.example.chat_bot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.chat_bot.adapter.ChatBotAdapter;
import com.example.chat_bot.api.BrainShopApi;
import com.example.chat_bot.model.BrainShopResponse;
import com.example.chat_bot.model.MessageModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    List<MessageModel> messages=new ArrayList<>();
    private EditText messageET;
    private ImageButton sendBtn;
    private RecyclerView messagesRV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        messageET = findViewById(R.id.eTMsg);
        sendBtn=findViewById(R.id.btnSend);
        messagesRV=findViewById(R.id.recyclerView);
        Gson gson = new GsonBuilder().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.brainshop.ai/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        BrainShopApi brainShopApi = retrofit.create(BrainShopApi.class);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        ChatBotAdapter chatBotAdapter =new ChatBotAdapter(messages,this);
        messagesRV.setAdapter(chatBotAdapter);
        messagesRV.setLayoutManager(linearLayoutManager);
        sendBtn.setOnClickListener(
                view ->{
                    String message = messageET.getText().toString();
                    messages.add(new MessageModel(message,"user"));
                    chatBotAdapter.notifyDataSetChanged();
                    String url = "get?bid=181511&key=DF7Mqqg5MzwaFoJo&uid=[uid]&msg="+message;
                    Call<BrainShopResponse> call = brainShopApi.getMessage(url);
                    call.enqueue(new retrofit2.Callback<BrainShopResponse>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onResponse(Call<BrainShopResponse> call, retrofit2.Response<BrainShopResponse> response) {
                            messages.add(new MessageModel(response.body().getCnt(),"bot"));
                            chatBotAdapter.notifyDataSetChanged();
                            Log.i("info",response.body().getCnt());
                        }

                        @Override
                        public void onFailure(Call<BrainShopResponse> call, Throwable t) {
                            Log.e("info","erreur  de requete !");
                        }
                    });
                }
        );
    }
}