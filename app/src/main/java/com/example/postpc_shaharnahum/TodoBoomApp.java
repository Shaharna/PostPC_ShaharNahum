package com.example.postpc_shaharnahum;

import android.app.Application;
import android.util.Log;
import java.util.ArrayList;

public class TodoBoomApp extends Application {

    TodoListSaver _saver;

    @Override
    public void onCreate() {
        super.onCreate();
        _saver = new TodoListSaver(this);

        Log.i("App launches","Hello, The size of the current todo list is" +
                String.valueOf(_saver.getAppList().size()) );
    }

    void increaseItemsCounter()
    {
        _saver.increaseItemsCounter();
    }

    void addIdToList(int id)
    {
       _saver.addIdToList(id);
    }

    void deleteIdFromList(int id)
    {
        _saver.deleteIdFromList(id);
    }

    ArrayList<Todo> getItemsList()
    {
      return _saver.getAppList();
    }

    int getItemsCounter()
    {
        return _saver.getItemsCounter();
    }
}
