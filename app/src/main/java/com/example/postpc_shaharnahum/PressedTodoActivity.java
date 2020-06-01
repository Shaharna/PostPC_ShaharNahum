package com.example.postpc_shaharnahum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.view.ViewGroup.LayoutParams;


public class PressedTodoActivity extends AppCompatActivity {

    Button closePopupBtn, deletePopupBtn;
    PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pressed_todo);

        final TodoBoomApp myApp = (TodoBoomApp) getApplicationContext();

        final TextView contentTextView = findViewById(R.id.pressed_todo_content_text_view);
        final TextView creationStampTextView = findViewById(R.id.pressed_todo_creationTimestamp_text_view);
        final TextView editStampTextView = findViewById(R.id.pressed_todo_editTimestamp_text_view);
        final Button deleteBtn = findViewById(R.id.pressed_todo_delete_btn);
        final Button markUndoneBtn = findViewById(R.id.pressed_todo_mark_undone_btn);

        Intent intentCreatedMe = getIntent();
        String todoId = intentCreatedMe.getStringExtra("selected todo id");
        final Todo todo = myApp.getTodoItemById(todoId);

        contentTextView.setText(todo.getContent());
        creationStampTextView.setText(todo.getCreationTimestamp());
        editStampTextView.setText(todo.getEditTimestamp());

        markUndoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( todo.getIsDone()) {
                    myApp.markTodoUnDone(todo);
                    myApp._adapter.setTodoList(myApp.getItemsList());

                    finish();
                }
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View activity_main = findViewById(R.id.activity_pressed_todo);
                LayoutInflater layoutInflater = (LayoutInflater) PressedTodoActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
                        myApp.deleteIdFromList(todo);
                        myApp._adapter.setTodoList(myApp.getItemsList());
                        finish();
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
