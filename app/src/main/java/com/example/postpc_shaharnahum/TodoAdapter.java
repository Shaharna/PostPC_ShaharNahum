package com.example.postpc_shaharnahum;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TodoAdapter extends RecyclerView.Adapter<TodoHolder> {

    private ArrayList<Todo> _todoList = new ArrayList<>();

    void setTodoList(ArrayList<Todo> todos){
        _todoList.clear();
        _todoList.addAll(todos);
        notifyDataSetChanged();
    }

    void addTodoToList(Todo todo){
        _todoList.add(todo);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TodoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_one_todo, parent, false);

        return new TodoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoHolder holder, int position) {
        Todo todo = _todoList.get(position);
        holder.text.setText(todo._description);
    }

    @Override
    public int getItemCount() {
        return _todoList.size();
    }
}
