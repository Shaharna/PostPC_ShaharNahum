//package com.example.postpc_shaharnahum;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.preference.PreferenceManager;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import java.util.ArrayList;
//
//class TodoListSaver {
//
//    private static final String NUMBER_OF_ITEMS = "number of items";
//    private static final String CURRENT_IDS = "current ids";
//    private TodoBoomApp _myAppContext;
//    private ArrayList<Todo> _appList;
//    private ArrayList<Integer> _currentIdList;
//    private int _itemsCounter;
//
//    TodoListSaver(Context appContext)
//    {
//        _myAppContext = (TodoBoomApp) appContext;
//        _appList = new ArrayList<>();
//        getLastSavedItems();
//    }
//
//    ArrayList<Todo> getAppList()
//    {
//        return _appList;
//    }
//
//    int getItemsCounter()
//    {
//        return _itemsCounter;
//    }
//
//    ArrayList<Integer> getCurrentId(){
//        return _currentIdList;
//    }
//
//    void increaseItemsCounter()
//    {
//        _itemsCounter++;
//    }
//
//    void addIdToList(int id)
//    {
//        _currentIdList.add(id);
//    }
//
//    void deleteIdFromList(Integer id)
//    {
//        _currentIdList.remove(id);
//    }
//
//    private void getLastSavedItems()
//    {
//        Gson gson = new Gson();
//        SharedPreferences spForTodoList = PreferenceManager.getDefaultSharedPreferences(_myAppContext);
//        _currentIdList = gson.fromJson(spForTodoList.getString(CURRENT_IDS, ""),
//                new TypeToken<ArrayList<Integer>>(){}.getType());
//        _itemsCounter = spForTodoList.getInt(NUMBER_OF_ITEMS, 0);
//        if (_currentIdList != null)
//        {
//            for (Integer id: _currentIdList)
//            {
//                String itemStr = spForTodoList.getString(String.valueOf(id), "");
//                Todo item = gson.fromJson(itemStr ,new TypeToken<Todo>(){}.getType());
//                _appList.add(item);
//            }
//        }
//        else
//        {
//            _currentIdList = new ArrayList<>();
//        }
//    }
//    void updateListSaver(ArrayList<Todo> lst)
//    {
//        SharedPreferences spForTodoList = PreferenceManager.getDefaultSharedPreferences(_myAppContext);
//        SharedPreferences.Editor edit = spForTodoList.edit();
//        edit.clear();
//        Gson gson = new Gson();
//        edit.putString(CURRENT_IDS,gson.toJson(_currentIdList));
//        edit.putInt(NUMBER_OF_ITEMS, _myAppContext.getItemsCounter());
//        for (Todo item : lst)
//        {
//            edit.putString(item._id, gson.toJson(item));
//        }
//        edit.apply();
//    }
//
//    void updateListSaverAfterChecked(Todo todo)
//    {
//        SharedPreferences spForTodoList = PreferenceManager.getDefaultSharedPreferences(_myAppContext);
//        SharedPreferences.Editor edit = spForTodoList.edit();
//        Gson gson = new Gson();
//        edit.putString(CURRENT_IDS,gson.toJson(_currentIdList));
//        edit.putInt(NUMBER_OF_ITEMS, _myAppContext.getItemsCounter());
//        edit.remove(todo._id);
//        edit.putString(todo._id, gson.toJson(todo));
//        edit.apply();
//    }
//
//}
