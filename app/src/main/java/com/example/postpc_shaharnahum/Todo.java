package com.example.postpc_shaharnahum;

import java.util.Calendar;

public class Todo {

    /**
     * A string describing the the Todoo
     */
    private String _content;

    /**
     * A Boolean saying whether or not the Todoo is done.
     */
    boolean _isDone;

    /**
     * The id of the item
     */
    private String _id;

    /**
     * The creation time stamp of the todo.
     */
    private String  _creationTimestamp;

    /**
     * The update time stamp of the todo
     */
    private String  _editTimestamp;

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
         setIsDone(true);
        _editTimestamp = Calendar.getInstance().getTime().toString();
    }

    void markUnDone()
    {
        setIsDone(false);
        _editTimestamp = Calendar.getInstance().getTime().toString();
    }

    void applyTodoContent(String content)
    {
        setContent(content);
        _editTimestamp = Calendar.getInstance().getTime().toString();
    }

    void setContent(String newContent){
        _content = newContent;
    }

    public String getContent(){
        return _content;
    }

    public String getId(){
        return _id;
    }

    public String getCreationTimestamp(){
        return _creationTimestamp;
    }

    public String getEditTimestamp(){
        return _editTimestamp;
    }

    public boolean getIsDone(){
        return _isDone;
    }

    public void setId(String id)
    {
        _id = id;
    }

    public void setCreationTimestamp(String _creationTimestamp) {
        this._creationTimestamp = _creationTimestamp;
    }

    public void setEditTimestamp(String _editTimestamp) {
        this._editTimestamp = _editTimestamp;
    }

    public void setIsDone(boolean isDone)
    {
        _isDone = isDone;
    }

    }
