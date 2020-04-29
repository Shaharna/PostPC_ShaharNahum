package com.example.postpc_shaharnahum;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean reverseLayout = false;

        final ArrayList<Todo> todos = new ArrayList<>();
        final TodoAdapter adapter = new TodoAdapter();

        final EditText editText = findViewById(R.id.inputText);
        final Button createBtn = findViewById(R.id.createBtn);

        todos.clear();
        todos.addAll(Todo.createTodoList());
        adapter.setTodoList(todos);

        RecyclerView todoRecycler = findViewById(R.id.todo_recycler);
        todoRecycler.setAdapter(adapter);
        todoRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, reverseLayout));

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(editText.getText())){
                    Context context = getApplicationContext();
                    CharSequence text = "You can't create an empty TODO item, oh silly!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                else {
                    todos.add(new Todo(editText.getText().toString()));
                    adapter.setTodoList(todos);
                    editText.getText().clear();
                }
            }
        });
        adapter.setOnTodoClickListener(new OnTodoClickListener() {
            @Override
            public void onTodoClicked(Todo todo) {
                todo._isDone = true;
                adapter.setTodoList(todos);
            }
        });
    }
}
