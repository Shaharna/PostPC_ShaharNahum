package com.example.postpc_shaharnahum;

import android.app.Application;
import android.util.Log;
import java.util.ArrayList;

public class TodoBoomApp extends Application {

    TodosInFirebaseManager _firebaseManager;
    TodoAdapter _adapter;


    @Override
    public void onCreate() {
        super.onCreate();
        _firebaseManager = TodosInFirebaseManager.getInstance(this);
        _adapter =  new TodoAdapter();

        Log.i("App launches","Hello, The size of the current todo list is" +
                String.valueOf(_firebaseManager.getAllTodos().size()) );
    }

    void addTodoToList(Todo todo)
    {
       _firebaseManager.addTodo(todo);
    }

    void deleteIdFromList(Todo todo)
    {
        _firebaseManager.deleteTodo(todo);
    }

    ArrayList<Todo> getItemsList()
    {
      return _firebaseManager.getAllTodos();
    }

     Todo getTodoItemById(String id) {
        return _firebaseManager.getTodoFromId(id);
    }

    void markTodoAsDone(Todo todo)
    {
        _firebaseManager.markTodoAsDone(todo);
    }

    void markTodoUnDone(Todo todo)
    {
        _firebaseManager.markTodoUnDone(todo);
    }

    void setTodoContent(Todo todo, String newContent)
    {
        _firebaseManager.setTodoContent(todo, newContent);
    }

}
