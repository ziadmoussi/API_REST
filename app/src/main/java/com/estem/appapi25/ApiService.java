package com.estem.appapi25;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    @GET("todos")
    Call<List<Todo>> getTodos();

    @POST("todos")
    Call<Todo> createTodo(@Body Todo todo);

    @PUT("todos/{id}")
    Call<Todo> updateTodo(@Path("id") int id, @Body Todo todo);

    @DELETE("todos/{id}")
    Call<Void> deleteTodo(@Path("id") int id);
}
