package com.example.postpc_shaharnahum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class EditTodoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo);
        final TodoBoomApp myAppContext = (TodoBoomApp) getApplicationContext();
        final TextView textView = findViewById(R.id.edit_todo_text_view);

        Intent intentCreatedMe = getIntent();
        String todoId = intentCreatedMe.getStringExtra("selected todo id");
        Todo todo = myAppContext.getTodoItemById(todoId);

        textView.setText(todo._content);
    }
}
