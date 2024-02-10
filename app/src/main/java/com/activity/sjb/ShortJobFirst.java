package com.activity.sjb;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ShortJobFirst extends AppCompatActivity {
    private TextView resultTextView, orderTextView, process, completionTime, turnAroundTime, waitingTime;
    private Button startButton, createBtn;
    private TextInputEditText nProcess, arrivalTime, burstTime, arrivalTimeView, burstTimeView;
    private LinearLayout parentLayout, P_ID, AT, BT, CT, TAT, WT;
    String customProcessIdName, customArrivalIdName, customBurstIdName, customCompletionIdName, customTurnAroundIdName, customWaitingIdName;

    String stringProcess,textSpinner;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shortjobfirst);

        /*----------- HOOKS -----------*/
        orderTextView = findViewById(R.id.orderTextView);
        resultTextView = findViewById(R.id.resultTextView);
        startButton = findViewById(R.id.startButton);
        createBtn = findViewById(R.id.createBtn);
        nProcess = findViewById(R.id.nProcess);
        parentLayout = findViewById(R.id.parentLayout);
        spinner = findViewById(R.id.activityTitleSpinner);
        P_ID = findViewById(R.id.p_id);
        AT = findViewById(R.id.AT);
        BT = findViewById(R.id.BT);
        CT = findViewById(R.id.CT);
        TAT = findViewById(R.id.TAT);
        WT = findViewById(R.id.WT);

        /*----------- USED -----------*/
        String[] options = {"FIRST COME FIRST SERVE","SHORT JOB FIRST"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stringProcess = nProcess.getText().toString();
                if (!stringProcess.isEmpty()) {
                    createProcess();
                    startButton.setEnabled(true);
                } else {
                    nProcess.requestFocus();
                    nProcess.setError("Missing Field!");
                }
            }
        });
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringProcess = nProcess.getText().toString();
                if (!stringProcess.isEmpty()) {
                    Simulation();
                } else {
                    nProcess.requestFocus();
                    nProcess.setError("Missing Field!");
                }
            }
        });

    }

    private void createProcess() {
        int numProcess = Integer.parseInt(stringProcess);

        for (int i = 1; i <= numProcess; i++) {
            process = new TextView(this);
            arrivalTime = new TextInputEditText(this);
            burstTime = new TextInputEditText(this);
            completionTime = new TextView(this);
            turnAroundTime = new TextView(this);
            waitingTime = new TextView(this);

            //Process Text View
            LinearLayout.LayoutParams processLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            processLayout.setMargins(0, 0, 0, 55);
            process.setLayoutParams(processLayout);
            process.setText("P " + i);
            customProcessIdName = "P" + i;
            process.setId(View.generateViewId());
            process.setTag(customProcessIdName);
            P_ID.addView(process);

            // Arrival Edit Text
            arrivalTime.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            arrivalTime.setInputType(InputType.TYPE_CLASS_NUMBER);
            arrivalTime.setTextSize(12);
            arrivalTime.setHint("0");
            arrivalTime.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            customArrivalIdName = "A" + i;
            arrivalTime.setId(View.generateViewId());
            arrivalTime.setTag(customArrivalIdName);
            AT.addView(arrivalTime);

            // Burst Edit Text
            burstTime.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            burstTime.setTextSize(12);
            burstTime.setHint("0");
            burstTime.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            burstTime.setInputType(InputType.TYPE_CLASS_NUMBER);
            customBurstIdName = "B" + i;
            burstTime.setId(View.generateViewId());
            burstTime.setTag(customBurstIdName);
            BT.addView(burstTime);

            // Completion Time TextView
            LinearLayout.LayoutParams completionLayout = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            completionLayout.setMargins(0, 0, 0, 55);
            completionTime.setLayoutParams(completionLayout);
            completionTime.setText("0");
            customCompletionIdName = "C" + i;
            completionTime.setId(View.generateViewId());
            completionTime.setTag(customCompletionIdName);
            CT.addView(completionTime);
            Log.i("ID name", "Completion Time: " + customCompletionIdName);

            // Turnaround Time TextView
            LinearLayout.LayoutParams turnaroundLayout = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            turnaroundLayout.setMargins(0, 0, 0, 55);
            turnAroundTime.setLayoutParams(turnaroundLayout);
            turnAroundTime.setText("0");
            customTurnAroundIdName = "T" + i;
            turnAroundTime.setId(View.generateViewId());
            turnAroundTime.setTag(customTurnAroundIdName);
            TAT.addView(turnAroundTime);
            Log.i("ID name", "TurnAround Time: " + customTurnAroundIdName);

            // Waiting Time TextView
            LinearLayout.LayoutParams waitingLayout = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            waitingLayout.setMargins(0, 0, 0, 55);
            waitingTime.setLayoutParams(waitingLayout);
            waitingTime.setText("0");
            customWaitingIdName = "W" + i;
            waitingTime.setId(View.generateViewId());
            waitingTime.setTag(customWaitingIdName);
            WT.addView(waitingTime);
            Log.i("ID name", "Waiting Time: " + customWaitingIdName);
        }
    }

    private void Simulation() {
        List<Process> processes = new ArrayList<>();
        double numProcess = Double.parseDouble(nProcess.getText().toString());
        for (int i = 1; i <= numProcess; i++) {
            // Retrieve process name
            String customProcessName = "P" + i;
            View processView = P_ID.findViewWithTag(customProcessName);
            if (processView instanceof TextView) {
                String processName = ((TextView) processView).getText().toString();

                // Retrieve arrival time
                String customArrivalIdName = "A" + i;
                View arrivalView = AT.findViewWithTag(customArrivalIdName);
                if (arrivalView instanceof TextInputEditText) {
                    arrivalTimeView = (TextInputEditText) arrivalView;
                    String arrivalTime = arrivalTimeView.getText().toString();
                    if (!arrivalTime.isEmpty()) {
                        // Retrieve burst time
                        String customBurstIdName = "B" + i;
                        View burstView = BT.findViewWithTag(customBurstIdName);
                        if (burstView instanceof TextInputEditText) {
                            burstTimeView = ((TextInputEditText) burstView);
                            String burstTime = burstTimeView.getText().toString();
                            if (!burstTime.isEmpty()) {
                                // Create Process object and add it to the list
                                processes.add(new Process(processName, Integer.parseInt(arrivalTime), Integer.parseInt(burstTime), 0, 0, 0));
                            } else {
                                Log.e("Error", "burst time not found or missing");
                                burstTimeView.setError("Missing Fields Required!");
                            }
                        } else {
                            Log.e("Error", "burst time view not found");
                        }
                    } else {
                        Log.e("Error", "arrival time not found or missing");
                        arrivalTimeView.setError("Missing Fields Required!");
                    }
                } else {
                    Log.e("Error", "arrival time view not found");
                }
            } else {
                Log.e("Error", "process name not found or missing");
            }
        }
        textSpinner = (String) spinner.getSelectedItem();
        if(textSpinner == "SHORT JOB FIRST"){
            SJF(processes, numProcess);
        }
        else if(textSpinner == "FIRST COME FIRST SERVE"){
            FCFS(processes,numProcess);
        }
        else{
            Toast.makeText(this, "No Scheduling System Selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void SJF(List<Process> processes, double numProcess) {
        // Sort the processes based on arrival time first
        Collections.sort(processes, Comparator.comparingInt(p -> p.arrivalTime));

        // Sort the processes based on burst time for the remaining processes
        Collections.sort(processes.subList(1, processes.size()), Comparator.comparingInt(p -> p.burstTime));

        int currentTime = 0;
        double avgTurnaroundTime = 0;
        double avgWaitingTime = 0;
        double avgCompletionTime = 0;
        StringBuilder executionOrder = new StringBuilder();
        StringBuilder result = new StringBuilder();

        for (Process process : processes) {
            executionOrder.append(process.name).append(" -> ");

            // Compute completion time
            currentTime += process.burstTime;
            process.completionTime = currentTime;
            avgCompletionTime += process.completionTime / numProcess;

            // Compute turnaround time
            process.turnAroundTime = process.completionTime - process.arrivalTime;
            avgTurnaroundTime += process.turnAroundTime / numProcess;
            String.format("%.2f", avgTurnaroundTime);

            // Compute waiting time
            process.waitingTime = process.turnAroundTime - process.burstTime;
            avgWaitingTime += process.waitingTime / numProcess;
            String.format("%.2f", avgWaitingTime);

        }
//
//        result.append("Process | CT | TAT | WT\n");
//        for (Process process : processes) {
//            result.append(process.name).append(" | ").append(process.completionTime).append(" | ").append(process.turnAroundTime).append(" | ").append(process.waitingTime).append("\n");
//        }
        // Append total turnaround time and total waiting time
        result.append("Avg. Completion Time: ").append(String.format("%.2f", avgCompletionTime)).append("\n");
        result.append("Avg. Turnaround Time: ").append(String.format("%.2f", avgTurnaroundTime)).append("\n");
        result.append("Avg. Waiting Time: ").append(String.format("%.2f", avgWaitingTime));
        String res = "Execution Order: " + executionOrder.toString() + "All tasks completed. Total execution time: " + currentTime + " seconds.";
        resultTextView.setText(result);
        orderTextView.setText(res);
        fillOtherView(processes);
    }
    private void FCFS(List<Process> processes, double numProcess) {
        // Sort the processes based on arrival time first
        Collections.sort(processes, Comparator.comparingInt(p -> p.arrivalTime));

        int currentTime = 0;
        double avgTurnaroundTime = 0;
        double avgWaitingTime = 0;
        double avgCompletionTime = 0;
        StringBuilder executionOrder = new StringBuilder();
        StringBuilder result = new StringBuilder();

        for (Process process : processes) {
            executionOrder.append(process.name).append(" -> ");

            // Compute completion time
            currentTime += process.burstTime;
            process.completionTime = currentTime;
            avgCompletionTime += process.completionTime / numProcess;

            // Compute turnaround time
            process.turnAroundTime = process.completionTime - process.arrivalTime;
            avgTurnaroundTime += process.turnAroundTime / numProcess;
            String.format("%.2f", avgTurnaroundTime);

            // Compute waiting time
            process.waitingTime = process.turnAroundTime - process.burstTime;
            avgWaitingTime += process.waitingTime / numProcess;
            String.format("%.2f", avgWaitingTime);

        }
//
//        result.append("Process | CT | TAT | WT\n");
//        for (Process process : processes) {
//            result.append(process.name).append(" | ").append(process.completionTime).append(" | ").append(process.turnAroundTime).append(" | ").append(process.waitingTime).append("\n");
//        }
        // Append total turnaround time and total waiting time
        result.append("Avg. Completion Time: ").append(String.format("%.2f", avgCompletionTime)).append("\n");
        result.append("Avg. Turnaround Time: ").append(String.format("%.2f", avgTurnaroundTime)).append("\n");
        result.append("Avg. Waiting Time: ").append(String.format("%.2f", avgWaitingTime));
        String res = "Execution Order: " + executionOrder.toString() + "All tasks completed. Total execution time: " + currentTime + " seconds.";
        resultTextView.setText(result);
        orderTextView.setText(res);
        fillOtherView(processes);
    }

    private void fillOtherView(List<Process> processes) {
        int numProcess = processes.size();
        for (int i = 1; i <= numProcess; i++) {
            Process process = processes.get(i - 1);
            customProcessIdName = "P" + i;
            customArrivalIdName = "A" + i;
            customBurstIdName = "B" + i;
            customCompletionIdName = "C" + i;
            customTurnAroundIdName = "T" + i;
            customWaitingIdName = "W" + i;
            View processView = P_ID.findViewWithTag(customProcessIdName);
            View arrivalView = AT.findViewWithTag(customArrivalIdName);
            View burstView = BT.findViewWithTag(customBurstIdName);
            View completionView = CT.findViewWithTag(customCompletionIdName);
            View turnAroundView = TAT.findViewWithTag(customTurnAroundIdName);
            View waitingView = WT.findViewWithTag(customWaitingIdName);
            Log.i("ID name", "-------");

            if (processView instanceof TextView) {
                ((TextView) processView).setText(String.valueOf(process.name));
                Log.i("ID name", "Process Time: " + customProcessIdName);
            }
            if (arrivalView instanceof TextInputEditText) {
                ((TextInputEditText) arrivalView).setText(String.valueOf(process.arrivalTime));
                Log.i("ID name", "Arrival Time: " + customArrivalIdName);
            }
            if (burstView instanceof TextInputEditText) {
                ((TextInputEditText) burstView).setText(String.valueOf(process.burstTime));
                Log.i("ID name", "Burst Time: " + customBurstIdName);
            }
            if (completionView instanceof TextView) {
                ((TextView) completionView).setText(String.valueOf(process.completionTime));
                Log.i("ID name", "Completion Time: " + customCompletionIdName);
            }
            if (turnAroundView instanceof TextView) {
                ((TextView) turnAroundView).setText(String.valueOf(process.turnAroundTime));
                Log.i("ID name", "TurnAround Time: " + customTurnAroundIdName);
            }
            if (waitingView instanceof TextView) {
                ((TextView) waitingView).setText(String.valueOf(process.waitingTime));
                Log.i("ID name", "Waiting Time: " + customWaitingIdName);
            }
        }
    }

    private static class Process {
        String name;
        int arrivalTime, burstTime, completionTime, turnAroundTime, waitingTime;

        Process(String name, int arrivalTime, int burstTime, int completionTime, int turnAroundTime, int waitingTime) {
            this.name = name;
            this.burstTime = burstTime;
            this.arrivalTime = arrivalTime;
            this.completionTime = completionTime;
            this.turnAroundTime = turnAroundTime;
            this.waitingTime = waitingTime;
        }
    }
}
