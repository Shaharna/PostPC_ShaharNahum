package com.example.postpc_shaharnahum;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean reverseLayout = false;

        final TodoAdapter adapter = new TodoAdapter();
        adapter.setTodoList(Todo.createTodoList());

        RecyclerView todoRecycler = findViewById(R.id.todo_recycler);

        todoRecycler.setAdapter(adapter);
        todoRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, reverseLayout));

        final EditText editText = findViewById(R.id.inputText);
        final Button createBtn = findViewById(R.id.createBtn);

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (TextUtils.isEmpty(editText.getText())){
//
//                }
                adapter.addTodoToList(new Todo(editText.getText().toString()));
                editText.getText().clear();
            }
        });
    }
}
