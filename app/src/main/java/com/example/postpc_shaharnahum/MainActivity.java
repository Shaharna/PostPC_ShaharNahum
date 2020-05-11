package com.example.postpc_shaharnahum;

import androidx.annotation.NonNull;
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

    final ArrayList<Todo> todos = new ArrayList<>();

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        int size = todos.size();
        final boolean[] isDoneArray = new boolean[size];
        final String[] descriptionArray = new String[size];
        final String[] idArray = new String[size];

        for (int i =0; i < size; ++i)
        {
            isDoneArray[i] = todos.get(i)._isDone;
            descriptionArray[i] = todos.get(i)._description;
            idArray[i] = todos.get(i)._id;
        }
        outState.putBooleanArray("isDoneArray",isDoneArray);
        outState.putStringArray("descriptionArray", descriptionArray);
        outState.putStringArray("idArray", idArray);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TodoBoomApp myApp = (TodoBoomApp) getApplicationContext();
        boolean reverseLayout = false;
        final TodoAdapter adapter = new TodoAdapter();
        final EditText editText = findViewById(R.id.inputText);
        final Button createBtn = findViewById(R.id.createBtn);
        todos.clear();

        if(savedInstanceState == null) {
            todos.addAll(myApp.getItemsList());
        }
        else{
            boolean isDone;
            String description, id;
            final boolean[] isDoneArray = savedInstanceState.getBooleanArray("isDoneArray");
            final String[] descriptionArray = savedInstanceState.getStringArray("descriptionArray");
            final String[] idArray = savedInstanceState.getStringArray("idArray");
            for (int i =0; i < isDoneArray.length; ++i)
            {
                isDone = isDoneArray[i];
                description = descriptionArray[i];
                id = idArray[i];
                todos.add(new Todo(description, isDone, id));
            }
        }
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
                    int id = myApp.getItemsCounter();
                    todos.add(new Todo(editText.getText().toString(), String.valueOf(id)));
                    adapter.setTodoList(todos);
                    editText.getText().clear();
                    myApp.increaseItemsCounter();
                    myApp.updateIdList(id);
                    myApp._saver.updateListSaver(todos);
                }
            }
        });
        adapter.setOnTodoClickListener(new OnTodoClickListener() {
            @Override
            public void onTodoClicked(Todo todo) {
                if (! todo._isDone)
                {
                    todo._isDone = true;
                    adapter.setTodoList(todos);
                }
            }

            @Override
            public void onTodoLongClicked(Todo todo) {

            }
        });
    }
}
