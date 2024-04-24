package com.example.chat_bot.api;

import com.example.chat_bot.model.BrainShopResponse;
import com.example.chat_bot.model.MessageModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface BrainShopApi {
    @GET
    Call<BrainShopResponse> getMessage(@Url String url);
}
