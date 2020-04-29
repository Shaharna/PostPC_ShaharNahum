package com.example.postpc_shaharnahum;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Hold one view of the template
 */
class TodoHolder extends RecyclerView.ViewHolder {

    final TextView text;
    final ImageView image;

    TodoHolder(@NonNull View itemView) {
        super(itemView);
        text = itemView.findViewById(R.id.todo_text);
        image = itemView.findViewById(R.id.todo_image);
    }
}
