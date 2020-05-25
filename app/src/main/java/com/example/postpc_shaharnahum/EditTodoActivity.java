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

        final TextView textView = findViewById(R.id.edit_todo_content_text_view);
        final Button applyBtn = findViewById(R.id.edit_todo_apply_btn);
        final Button markDoneBtn = findViewById(R.id.edit_todo_mark_done_btn);
        final EditText editText = findViewById(R.id.edit_todo_edit_text);

        Intent intentCreatedMe = getIntent();
        String todoId = intentCreatedMe.getStringExtra("selected todo id");
        final Todo todo = myApp.getTodoItemById(todoId);

        textView.setText(todo._content);

        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(editText.getText())) {
                    Context context = getApplicationContext();
                    CharSequence text = "You can't apply an empty text to a TODO item, oh silly!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else {
                    myApp.setTodoContent(todo, editText.getText().toString());
                    editText.getText().clear();
                    textView.setText(todo._content);
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
                if ( !todo._isDone) {
                    myApp.markTodoAsDone(todo);
                    myApp._adapter.setTodoList(myApp.getItemsList());

                    finish();
                }
            }
        });

    }
}
