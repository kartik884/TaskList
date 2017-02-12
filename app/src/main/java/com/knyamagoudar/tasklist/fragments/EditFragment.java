package com.knyamagoudar.tasklist.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.knyamagoudar.tasklist.R;
import com.knyamagoudar.tasklist.models.TaskList;
import com.knyamagoudar.tasklist.interfaces.CustomInterface;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * Created by knyamagoudar on 1/16/17.
 */

public class EditFragment extends android.support.v4.app.DialogFragment {

    private EditText editTaskName,editTaskNotes;
    private DatePicker datePicker;
    private Spinner priority,status;
    TaskList task;
    CustomInterface customInterface;

    private ImageButton fragmentBackButton,cancelFragment,saveTaskData;
    public EditFragment(){

    }

    public static EditFragment newInstance(TaskList task,CustomInterface customInterface) {
        EditFragment frag = new EditFragment();
        frag.customInterface = customInterface;
        Bundle args = new Bundle();
        args.putSerializable("Task",task);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_task, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        //mEditText = (EditText) view.findViewById(R.id.txt_your_name);
        // Fetch arguments from bundle and set title
//        String title = getArguments().getString("title", "Enter Name");
//        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
//        mEditText.requestFocus();
        //getDialog().getWindow().setSoftInputMode(
        //        WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        cancelFragment = (ImageButton) view.findViewById(R.id.fragment_cancel_action);
        cancelFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        fragmentBackButton = (ImageButton) view.findViewById(R.id.backButton_fragment_action);
        fragmentBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });


        saveTaskData = (ImageButton) view.findViewById(R.id.fragment_save_action);
        saveTaskData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TaskList newTask = new TaskList();

                EditText etTaskName = (EditText) getDialog().findViewById(R.id.etFrgTaskName);
                String taskName = etTaskName.getText().toString();

                DatePicker datePicker = (DatePicker) getDialog().findViewById(R.id.dpeditDueDate);
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth() + 1;
                int year = datePicker.getYear();

                EditText etTaskNotes = (EditText) getDialog().findViewById(R.id.eteditTaskNotes);
                String taskNotes = etTaskNotes.getText().toString();

                Spinner spPriority=(Spinner) getDialog().findViewById(R.id.speditPriority);
                String prority = spPriority.getSelectedItem().toString();

                Spinner spStatus=(Spinner) getDialog().findViewById(R.id.speditStatus);
                String status = spStatus.getSelectedItem().toString();

                Date date = new Date(datePicker.getYear() - 1900, datePicker.getMonth(), datePicker.getDayOfMonth());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String dateStr = sdf.format(date);

                newTask.setId(task.getId());
                newTask.setTaskName(taskName);
                newTask.setTaskNotes(taskNotes);
                newTask.setPriority(prority);
                newTask.setStatus(status);
                newTask.setDueDate(date);

                System.out.println("Edit Fragment Task Name" + taskName);

                customInterface.saveEditTaskData(newTask);
                getDialog().dismiss();

            }
        });

        task = (TaskList) getArguments().getSerializable("Task");

        editTaskName = (EditText) view.findViewById(R.id.etFrgTaskName);
        editTaskName.setText(task.getTaskName());

        editTaskNotes = (EditText) view.findViewById(R.id.eteditTaskNotes);
        editTaskNotes.setText(task.getTaskNotes());

        datePicker = (DatePicker) view.findViewById(R.id.dpeditDueDate);
        Date date = task.getDueDate();

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        System.out.println(year);
        System.out.println(month);
        System.out.println(day);

        datePicker.init(year,month,day,null);

        priority = (Spinner) view.findViewById(R.id.speditPriority);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.priority_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        String[] priorityArray = getResources().getStringArray(R.array.priority_array);
        priority.setAdapter(adapter);
        priority.setSelection(getIndex(priorityArray,task.getPriority()));

        status = (Spinner) view.findViewById(R.id.speditStatus);
        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.status_array, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        status.setAdapter(statusAdapter);
        String[] statusArray = getResources().getStringArray(R.array.status_array);
        status.setSelection(getIndex(statusArray,task.getStatus()));

    }

    private int getIndex(String[] strArray,String str){

        for (int i=0;i<strArray.length;i++){
            if(Objects.equals(new String(strArray[i]),new String(str))){
                return i;
            }
        }
        return 0;
    }


    @Override
    public void onResume() {
        // Get existing layout params for the window
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        // Assign window properties to fill the parent
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        // Call super onResume after sizing
        super.onResume();
    }

}
