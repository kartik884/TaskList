package com.knyamagoudar.tasklist.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.knyamagoudar.tasklist.R;
import com.knyamagoudar.tasklist.models.TaskList;

import java.util.ArrayList;

/**
 * Created by knyamagoudar on 1/15/17.
 */

public class TaskAdapter extends ArrayAdapter<TaskList>{

    public TaskAdapter(Context context, ArrayList<TaskList> taskLists) {
        super(context, 0,taskLists);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TaskList task = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_task, parent, false);
        }


        // Lookup view for data population
        TextView tvTaskName = (TextView) convertView.findViewById(R.id.tvTaskName);
        TextView tvTaskPriority = (TextView) convertView.findViewById(R.id.tvTaskPriority);
        // Populate the data into the template view using the data object
        tvTaskName.setText(task.getTaskName());
        tvTaskPriority.setText(task.getPriority());

        if(new String(task.getPriority()).equals("High")){
            tvTaskPriority.setTextColor(Color.parseColor("#E62D10"));
        }if(new String(task.getPriority()).equals("Medium")){
            tvTaskPriority.setTextColor(Color.parseColor("#5990F0"));
        }if(new String(task.getPriority()).equals("Low")){
            tvTaskPriority.setTextColor(Color.parseColor("#76E385"));
        }
        // Return the completed view to render on screen
        return convertView;

    }
}
