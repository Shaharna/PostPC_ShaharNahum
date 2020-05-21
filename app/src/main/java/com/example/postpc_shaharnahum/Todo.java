package com.example.postpc_shaharnahum;

import java.util.Date;
import java.util.Calendar;

class Todo {

    /**
     * A string describing the the Todoo
     */
    String _content;

    /**
     * A Boolean saying whether or not the Todoo is done.
     */
    boolean _isDone;

    /**
     * The id of the item
     */
    String _id;

    /**
     * The id of the firestore document.
     */
    String _firestoreDocumentId;

    /**
     * The creation time stamp of the todo.
     */
    Date  _creationTimestamp;

    /**
     * The update time stamp of the todo
     */
    Date  _editTimestamp;

    /**
     * A default constructor/
     * @param content A string for the todoo description.
     * @param isDone A boolean for is the todoo done.
     */
    Todo(String content, boolean isDone, String id){
        _content = content;
        _isDone = isDone;
        _id = id;
        _creationTimestamp = Calendar.getInstance().getTime();
        _editTimestamp = _creationTimestamp;
    }
    /**
     * A constructor with only String description.
     * @param description A string for description.
     */
    Todo(String description, String id){
        _content = description;
        _isDone = false;
        _id = id;
    }

//    /**
//     * This function creates an empty list of Todos.
//     * @return new ArrayList<Todoo>
//     */
//    static ArrayList<Todo> createTodoList(){
//
//        ArrayList<Todo> list = new ArrayList<>();
//        list.add(new Todo("Do homework"));
//        list.add(new Todo("Clean apartment"));
//        return list;
//    }
}
