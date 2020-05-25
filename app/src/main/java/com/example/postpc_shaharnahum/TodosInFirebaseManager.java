package com.example.postpc_shaharnahum;

import android.util.Log;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;

 class TodosInFirebaseManager {

     private static final String LOG_TAG = "FirestoreTodosManager";
     private static TodosInFirebaseManager single_instance = null;
    private ArrayList<Todo> allTodo = new ArrayList<>();

    private TodosInFirebaseManager()
    {
        createLiveQuery();
    }
    static TodosInFirebaseManager getInstance()
     {
         if (single_instance == null)
             single_instance = new TodosInFirebaseManager();

         return single_instance;
     }

     private void createLiveQuery() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("todos").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null){
                    Log.d(LOG_TAG, "exception in snapshot" + e.getMessage());
                    return;
                }
                if (queryDocumentSnapshots == null){
                    Log.d(LOG_TAG, "value is null");
                    return;
                }
                TodosInFirebaseManager.this.allTodo.clear();
                for (QueryDocumentSnapshot document : queryDocumentSnapshots){
                    String content = document.getString("content");
                    String isDone =  document.getString("is done");
                    boolean isDonebool = false;
                    if (isDone.equals("true"))
                    {
                        isDonebool = true;
                    }
                    String id = document.getString("id");
                    String creationTimestamp = document.getString("creation time stamp");
                    String editTimestamp = document.getString("edit time stamp");
                    Todo newTodo = new Todo(content, isDonebool, id, creationTimestamp, editTimestamp);
                    allTodo.add(newTodo);
                }
            }
        });
    }

    Todo getTodoFromId(String id){
        for (Todo item: allTodo)
        {
            if (item._id.equals(id) ){
                return item;
            }
        }
        return null;
    }

    ArrayList<Todo> getAllTodos(){
        ArrayList< Todo> copy = new ArrayList<>(allTodo);
        return copy;
    }

    void addTodo(Todo todo){
        if (allTodo.contains(todo))
        {
            Log.w(LOG_TAG, "pet already in the todo's list");
        }
        //local
        allTodo.add(todo);

        //global
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference document = db.collection("todos").document();
        todo._id  = document.getId();
        Map<String, String> mapTodo = new HashMap<>();
        mapTodo.put("id", todo._id);
        mapTodo.put("content", todo._content);
        if (todo._isDone)
        {
            mapTodo.put("is done", "true");
        }
        mapTodo.put("is done", "false");
        mapTodo.put("creation time stamp", todo._creationTimestamp);
        mapTodo.put("edit time stamp", todo._editTimestamp);

        document.set(mapTodo);
    }

    void markTodoAsDone(Todo todo){

        int index = allTodo.indexOf(todo);
        if (index == -1){
            Log.e(LOG_TAG, "can't mark todo as done: could not find this todo");
            return;
        }
        todo.markAsDone();
        //local change
        allTodo.set(index, todo);

        //global change
        String documentId = todo._id;
        if (documentId == null){
            Log.e(LOG_TAG, "can't mark todo as done: could not find this todo in firestore");
            return;
        }
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("todos").document(documentId).set(todo);
    }

    void editTodo(Todo oldTodo, Todo newTodo){

        int index = allTodo.indexOf(oldTodo);
        if (index == -1){
            Log.e(LOG_TAG, "can't edit todo: could not find this todo");
            return;
        }
        allTodo.remove(oldTodo);
        allTodo.add(index, newTodo);

        // global
        String documentId = oldTodo._id;
        if (documentId == null){
            Log.e(LOG_TAG, "can't edit todo: could not find this todo in firestore");
            return;
        }
        newTodo._id  = oldTodo._id;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("todos").document(documentId).set(newTodo);
    }

    void deleteTodo(Todo todo){
        //delete local
        int index = allTodo.indexOf(todo);
        if (index == -1){
            Log.e(LOG_TAG, "can't delete todo: could not find this todo");
            return;
        }
        allTodo.remove(todo);

        // delete global
        String documentId = todo._id;
        if (documentId == null){
            Log.e(LOG_TAG, "can't delete todo: could not find this todo in firestore");
            return;
        }
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("todos").document(documentId).delete();
    }
}
