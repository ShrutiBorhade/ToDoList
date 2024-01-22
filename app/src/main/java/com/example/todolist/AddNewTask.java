package com.example.todolist;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.core.content.ContextCompat;
import com.example.todolist.Model.ToDoModel;
import com.example.todolist.Utils.DatabaseHandler;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class AddNewTask extends BottomSheetDialogFragment {

    public static final String TAG = "ActionBottomDialog";
    private EditText newTaskText;
    private Button newTaskSaveButton;
    private EditText newTaskTitle;
    private DatabaseHandler db;
    private boolean isUpdate = false;
    private int taskId;
    private String title;

    public static AddNewTask newInstance() {
        return new AddNewTask();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_task, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        newTaskTitle = view.findViewById(R.id.newTaskTitle);
        newTaskText = view.findViewById(R.id.newTaskText);
        newTaskSaveButton = view.findViewById(R.id.newTaskBtn);
        title = newTaskTitle.getText().toString();

        db = new DatabaseHandler(getActivity());
        db.openDatabase();

        Bundle bundle = getArguments();
        if (bundle != null) {
            isUpdate = true;
            String task = bundle.getString("task");
            taskId = bundle.getInt("id");
            newTaskText.setText(task);

            title = bundle.getString("title", "");
            newTaskTitle.setText(title);

            if (task.length() > 0) {
                newTaskSaveButton.setTextColor(ContextCompat.getColor(getContext(), R.color.dark));
            }
        }

        newTaskText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateUI();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        newTaskTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateUI();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        newTaskSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = newTaskText.getText().toString();
                title = newTaskTitle.getText().toString();

                if (isUpdate) {
                    ToDoModel task = new ToDoModel();
                    task.setTitle(title);
                    task.setTask(text);
                    task.setId(taskId);
                    db.updateTask(task.getId(), task.getTitle(), task.getTask());
                } else {
                    ToDoModel task = new ToDoModel();
                    task.setTitle(title);
                    task.setTask(text);
                    task.setStatus(0);
                    db.insertTask(task);
                }

                if (getDialog() != null) {
                    getDialog().setTitle(title);
                }

                dismiss();
            }
        });
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

        Activity activity = getActivity();
        if (activity instanceof DialogCloseListener) {
            ((DialogCloseListener) activity).handleDialogClose(dialog);
        }

        if (getDialog() != null) {
            getDialog().setTitle(title);
        }
    }

    private void updateUI() {
        String text = newTaskText.getText().toString();
        title = newTaskTitle.getText().toString();

        if (text.isEmpty() || title.isEmpty()) {
            newTaskSaveButton.setEnabled(false);
            newTaskSaveButton.setTextColor(ContextCompat.getColor(getContext(), R.color.gray));
            newTaskSaveButton.setText("Add");
        } else {
            newTaskSaveButton.setEnabled(true);
            newTaskSaveButton.setTextColor(ContextCompat.getColor(getContext(), R.color.dark));
            newTaskSaveButton.setText(text);
        }

        if (getDialog() != null) {
            getDialog().setTitle(title);
        }
    }
}



