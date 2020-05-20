package com.example.postpc_shaharnahum;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.Nullable;

public class TodosInFirebaseManager {

    private static final String LOG_TAG = "FirestoreTodosManager";
    private ArrayList<Todo> allTodo = new ArrayList<>();

    public TodosInFirebaseManager ()
    {
        createLiveQuery();
    }

    private void createLiveQuery() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference referenceToCollection = db.collection("todos");

        ListenerRegistration liveQuery = referenceToCollection.addSnapshotListener(new EventListener<QuerySnapshot>() {
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
                    Todo todo = document.toObject(Todo)
                }

            }
        });
    }


    public ArrayList<Todo> getAllTodos(){
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
        todo._firestoreDocumentId  = document.getId();

        document.set(todo);
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
        String documentId = oldTodo._firestoreDocumentId;
        if (documentId == null){
            Log.e(LOG_TAG, "can't edit todo: could not find this todo in firestore");
            return;
        }
        newTodo._firestoreDocumentId  = oldTodo._firestoreDocumentId;
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
        String documentId = todo._firestoreDocumentId;
        if (documentId == null){
            Log.e(LOG_TAG, "can't delete todo: could not find this todo in firestore");
            return;
        }
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("todos").document(documentId).delete();
    }

    private void

}
