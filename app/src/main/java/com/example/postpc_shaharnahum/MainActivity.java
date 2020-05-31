package com.example.postpc_shaharnahum;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    TodoBoomApp myApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myApp = (TodoBoomApp) getApplicationContext();

        boolean reverseLayout = false;
        final EditText editText = findViewById(R.id.inputText);
        final Button createBtn = findViewById(R.id.createBtn);

        RecyclerView todoRecycler = findViewById(R.id.todo_recycler);
        todoRecycler.setAdapter(myApp._adapter);
        todoRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, reverseLayout));
        myApp._adapter.setTodoList(myApp.getItemsList());

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(editText.getText())) {
                    Context context = getApplicationContext();
                    CharSequence text = "You can't create an empty TODO item, oh silly!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else {
                    Todo newTodo = new Todo(editText.getText().toString());
                    editText.getText().clear();
                    myApp.addTodoToList(newTodo);
                    myApp._adapter.setTodoList(myApp.getItemsList());
                }
            }
        });
        myApp._adapter.setOnTodoClickListener(new OnTodoClickListener() {
            @Override
            public void onTodoClicked(Todo todo) {
                Intent intent;
                if (!todo._isDone) {
                    intent = new Intent(MainActivity.this, EditTodoActivity.class);
                }
                else{
                        intent = new Intent(MainActivity.this, PressedTodoActivity.class);
                    }
                intent.putExtra("selected todo id", todo._id);
                startActivity(intent);
                }
        });
        registerReceiver(myReceiver, new IntentFilter("got_data_from_Firebase"));
}

    @Override
    protected void onResume(){
        super.onResume();

        myApp._adapter.setTodoList(myApp.getItemsList());
    }

    private BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null)
            {
                return;
            }
            if (intent.getAction() != "got_data_from_Firebase")
            {
                return;
            }
            myApp._adapter.setTodoList(myApp.getItemsList());
        }
    };

    @Override
    public void onDestroy(){
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }


}

