package com.example.postpc_shaharnahum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;
import java.util.ArrayList;
import android.view.ViewGroup.LayoutParams;


public class MainActivity extends AppCompatActivity {

    final ArrayList<Todo> todos = new ArrayList<>();
    Button closePopupBtn, deletePopupBtn;
    PopupWindow popupWindow;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        int size = todos.size();
        final boolean[] isDoneArray = new boolean[size];
        final String[] descriptionArray = new String[size];
        final String[] idArray = new String[size];

        for (int i = 0; i < size; ++i) {
            isDoneArray[i] = todos.get(i)._isDone;
            descriptionArray[i] = todos.get(i)._description;
            idArray[i] = todos.get(i)._id;
        }
        outState.putBooleanArray("isDoneArray", isDoneArray);
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

        if (savedInstanceState == null) {
            todos.addAll(myApp.getItemsList());
        } else {
            boolean isDone;
            String description, id;
            final boolean[] isDoneArray = savedInstanceState.getBooleanArray("isDoneArray");
            final String[] descriptionArray = savedInstanceState.getStringArray("descriptionArray");
            final String[] idArray = savedInstanceState.getStringArray("idArray");
            for (int i = 0; i < isDoneArray.length; ++i) {
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
                if (TextUtils.isEmpty(editText.getText())) {
                    Context context = getApplicationContext();
                    CharSequence text = "You can't create an empty TODO item, oh silly!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else {
                    int id = myApp.getItemsCounter();
                    todos.add(new Todo(editText.getText().toString(), String.valueOf(id)));
                    adapter.setTodoList(todos);
                    editText.getText().clear();
                    myApp.increaseItemsCounter();
                    myApp.addIdToList(id);
                    myApp._saver.updateListSaver(todos);
                }
            }
        });
        adapter.setOnTodoClickListener(new OnTodoClickListener() {
            @Override
            public void onTodoClicked(Todo todo) {
                if (!todo._isDone) {
                    todo._isDone = true;
                    adapter.setTodoList(todos);
                    myApp._saver.updateListSaverAfterChecked(todo);
                }
            }

            @Override
            public void onTodoLongClicked(final Todo todo) {

                View activity_main = findViewById(R.id.activity_main_xml);
                LayoutInflater layoutInflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.popup_window, null);

                closePopupBtn = customView.findViewById(R.id.popup_close_btn);
                deletePopupBtn = customView.findViewById(R.id.popup_delete_btn);

                //instantiate popup window
                popupWindow = new PopupWindow(customView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

                //display the popup window
                popupWindow.showAtLocation(activity_main, Gravity.CENTER, 0, 0);

                //close the popup window on button click
                closePopupBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });

                deletePopupBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        todos.remove(todo);
                        adapter.setTodoList(todos);
                        myApp.deleteIdFromList(Integer.parseInt(todo._id));
                        myApp._saver.updateListSaver(todos);
                        popupWindow.dismiss();

                        Context context = getApplicationContext();
                        CharSequence text = "Item deleted";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                });

            }
        });
    }
}
