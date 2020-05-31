package com.example.postpc_shaharnahum;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import javax.annotation.Nullable;

 class TodosInFirebaseManager {

    private TodoBoomApp myApp;
     private static final String LOG_TAG = "FirestoreTodosManager";
     private static TodosInFirebaseManager single_instance = null;
    private ArrayList<Todo> allTodo = new ArrayList<>();

    private TodosInFirebaseManager(TodoBoomApp app)
    {
        myApp = app;
        createLiveQuery();
    }
    
    static TodosInFirebaseManager getInstance(TodoBoomApp app)
     {
         if (single_instance == null)
             single_instance = new TodosInFirebaseManager(app);

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
                    Todo newTodo = document.toObject(Todo.class);
                    allTodo.add(newTodo);
                }
                Intent intent = new Intent();
                intent.setAction("got_data_from_Firebase");
                myApp.getApplicationContext().sendBroadcast(intent);
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

        document.set(todo);
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

     void markTodoUnDone(Todo todo){

         int index = allTodo.indexOf(todo);
         if (index == -1){
             Log.e(LOG_TAG, "can't mark todo as done: could not find this todo");
             return;
         }
         todo.markUnDone();
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


     void setTodoContent(Todo oldTodo, String newContent){

        int index = allTodo.indexOf(oldTodo);
        if (index == -1){
            Log.e(LOG_TAG, "can't edit todo: could not find this todo");
            return;
        }
        oldTodo.setTodoContent(newContent);
        // global
        String documentId = oldTodo._id;
        if (documentId == null){
            Log.e(LOG_TAG, "can't edit todo: could not find this todo in firestore");
            return;
        }
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("todos").document(documentId).set(oldTodo);
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
