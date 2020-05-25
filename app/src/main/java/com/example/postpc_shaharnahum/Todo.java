package com.example.postpc_shaharnahum;

import java.util.Calendar;

public class Todo {

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
     * The creation time stamp of the todo.
     */
    String  _creationTimestamp;

    /**
     * The update time stamp of the todo
     */
    String  _editTimestamp;

    /**
     * No value constructor for firebase.
     */
    Todo (){}
    /**
     * A default constructor/
     * @param content A string for the todoo description.
     * @param isDone A boolean for is the todoo done.
     */
    Todo(String content, boolean isDone){
        _content = content;
        _isDone = isDone;
        _creationTimestamp = Calendar.getInstance().getTime().toString();
        _editTimestamp = _creationTimestamp;
    }

    /**
     * A default constructor/
     * @param content A string for the todoo description.
     * @param isDone A boolean for is the todoo done.
     *
     */
    Todo(String content, boolean isDone, String id, String creationTimestamp, String editTimestamp ){
        _content = content;
        _isDone = isDone;
        _id = id;
        _creationTimestamp = creationTimestamp;
        _editTimestamp = editTimestamp;
    }

    /**
     * A constructor with only String description.
     * @param description A string for description.
     */
    Todo(String description){
        _content = description;
        _isDone = false;
        _creationTimestamp = Calendar.getInstance().getTime().toString();
        _editTimestamp = _creationTimestamp;
    }

    /**
     * Copy constructor.
     * @param oldTodo the Todoo needed to be copied.
     */
    Todo(Todo oldTodo){
        _content = oldTodo._content;
        _isDone = oldTodo._isDone;
        _creationTimestamp = oldTodo._creationTimestamp;
        _editTimestamp = _creationTimestamp;
        _id = oldTodo._id;
    }

    void markAsDone()
    {
        _isDone = true;
        _editTimestamp = Calendar.getInstance().getTime().toString();
    }

    void setTodoContent(String newContent){
        _content = newContent;
    }

    public String get_content(){
        return _content;
    }

    public String get_id(){
        return _id;
    }

    public String get_creationTimestamp(){
        return _creationTimestamp;
    }

    public String get_editTimestamp(){
        return _editTimestamp;
    }

    public boolean get_isDone(){
        return _isDone;
    }

}
