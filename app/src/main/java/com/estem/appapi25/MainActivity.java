package com.estem.appapi25;

import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.*;

public class MainActivity extends AppCompatActivity {

    EditText edtId, edtTitle, edtCompleted;
    Button btnPrev, btnNext, btnAdd, btnUpdate, btnDelete;

    List<Todo> todos;
    int index = 0;

    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtId = findViewById(R.id.edtId);
        edtTitle = findViewById(R.id.edtTitle);
        edtCompleted = findViewById(R.id.edtCompleted);

        btnPrev = findViewById(R.id.btnPrev);
        btnNext = findViewById(R.id.btnNext);
        btnAdd = findViewById(R.id.btnAdd);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        apiService = RetrofitClient.getInstance();

        loadTodos();

        btnPrev.setOnClickListener(v -> {
            if (todos != null && index > 0) {
                index--;
                displayTodo();
            }
        });

        btnNext.setOnClickListener(v -> {
            if (todos != null && index < todos.size() - 1) {
                index++;
                displayTodo();
            }
        });

        btnAdd.setOnClickListener(v -> createTodo());
        btnUpdate.setOnClickListener(v -> updateTodo());
        btnDelete.setOnClickListener(v -> deleteTodo());
    }

    void loadTodos() {
        apiService.getTodos().enqueue(new Callback<List<Todo>>() {
            public void onResponse(Call<List<Todo>> call, Response<List<Todo>> response) {
                todos = response.body();
                displayTodo();
            }
            public void onFailure(Call<List<Todo>> call, Throwable t) {}
        });
    }

    void displayTodo() {
        Todo t = todos.get(index);
        edtId.setText(String.valueOf(t.getId()));
        edtTitle.setText(t.getTitle());
        edtCompleted.setText(String.valueOf(t.isCompleted()));
    }

    void createTodo() {
        Todo t = new Todo();
        t.setTitle(edtTitle.getText().toString());
        t.setCompleted(false);

        apiService.createTodo(t).enqueue(new Callback<Todo>() {
            public void onResponse(Call<Todo> call, Response<Todo> response) {
                Toast.makeText(MainActivity.this,"Ajouté",Toast.LENGTH_SHORT).show();
            }
            public void onFailure(Call<Todo> call, Throwable t) {}
        });
    }

    void updateTodo() {
        Todo t = new Todo();
        t.setTitle(edtTitle.getText().toString());
        t.setCompleted(Boolean.parseBoolean(edtCompleted.getText().toString()));

        apiService.updateTodo(
                Integer.parseInt(edtId.getText().toString()), t
        ).enqueue(new Callback<Todo>() {
            public void onResponse(Call<Todo> call, Response<Todo> response) {
                Toast.makeText(MainActivity.this,"Modifié",Toast.LENGTH_SHORT).show();
            }
            public void onFailure(Call<Todo> call, Throwable t) {}
        });
    }

    void deleteTodo() {
        apiService.deleteTodo(
                Integer.parseInt(edtId.getText().toString())
        ).enqueue(new Callback<Void>() {
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(MainActivity.this,"Supprimé",Toast.LENGTH_SHORT).show();
            }
            public void onFailure(Call<Void> call, Throwable t) {}
        });
    }
}
