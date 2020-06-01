package com.example.postpc_shaharnahum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditTodoActivity extends AppCompatActivity {

    TodoBoomApp myApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo);
        final TodoBoomApp myApp = (TodoBoomApp) getApplicationContext();

        final TextView contentTextView = findViewById(R.id.edit_todo_content_text_view);
        final TextView creationStampTextView = findViewById(R.id.edit_todo_creationTimestamp_text_view);
        final TextView editStampTextView = findViewById(R.id.edit_todo_editTimestamp_text_view);
        final Button applyBtn = findViewById(R.id.edit_todo_apply_btn);
        final Button markDoneBtn = findViewById(R.id.edit_todo_mark_done_btn);
        final EditText editText = findViewById(R.id.edit_todo_edit_text);

        Intent intentCreatedMe = getIntent();
        final String todoId = intentCreatedMe.getStringExtra("selected todo id");
        Todo todo = myApp.getTodoItemById(todoId);

        contentTextView.setText(todo.getContent());
        creationStampTextView.setText(todo.getCreationTimestamp());
        editStampTextView.setText(todo.getEditTimestamp());

        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Todo todo = myApp.getTodoItemById(todoId);
                if (TextUtils.isEmpty(editText.getText())) {
                    Context context = getApplicationContext();
                    CharSequence text = "You can't apply an empty text to a TODO item, oh silly!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else {
                    myApp.setTodoContent(todo, editText.getText().toString());
                    editText.getText().clear();
                    contentTextView.setText(todo.getContent());
                    myApp._adapter.setTodoList(myApp.getItemsList());
                    Context context = getApplicationContext();
                    CharSequence text = "Edition was made successfully";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });

        markDoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Todo todo = myApp.getTodoItemById(todoId);
                if ( !(todo.getIsDone())) {
                    myApp.markTodoAsDone(todo);
                    myApp._adapter.setTodoList(myApp.getItemsList());

                    finish();
                }
            }
        });

    }
}
