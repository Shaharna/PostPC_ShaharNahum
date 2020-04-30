package com.example.postpc_shaharnahum;

import java.util.ArrayList;

class Todo {

    /**
     * A string describing the the Todoo
     */
    String _description;

    /**
     * A Boolean saying whether or not the Todoo is done.
     */
    boolean _isDone;

    /**
     * A default constructor/
     * @param description A string for the todoo description.
     * @param isDone A boolean for is the todoo done.
     */
    Todo(String description, boolean isDone){
        _description = description;
        _isDone = isDone;
    }
    /**
     * A constructor with only String description.
     * @param description A string for description.
     */
    Todo(String description){
        _description = description;
        _isDone = false;
    }

    /**
     * This function creates an empty list of Todos.
     * @return new ArrayList<Todoo>
     */
    static ArrayList<Todo> createTodoList(){

        ArrayList<Todo> list = new ArrayList<>();
        list.add(new Todo("Do homework"));
        list.add(new Todo("Clean apartment"));
        return list;
    }
}
