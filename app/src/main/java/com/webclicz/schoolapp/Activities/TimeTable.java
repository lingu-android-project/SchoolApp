package com.webclicz.schoolapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;

import com.webclicz.schoolapp.Adapters.TimeTableAdapter;
import com.webclicz.schoolapp.R;
import com.webclicz.schoolapp.Utilities.Constants;
import com.webclicz.schoolapp.Utilities.HttpRequest;
import com.webclicz.schoolapp.Utilities.UserSessions;
import com.webclicz.schoolapp.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeTable extends AppCompatActivity {
    TimeTableAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    ImageView backButton;
    JSONObject currentStudent = new JSONObject();
    UserSessions session;
    HttpRequest hr;
    Constants constants;
    JSONArray timeData = new JSONArray();
    String userType;
    JSONObject staffSession = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

        session = new UserSessions(getApplicationContext());
        constants = new Constants();
        hr = new HttpRequest();
        backButton = (ImageView) findViewById(R.id.backButton);



        expListView = (ExpandableListView) findViewById(R.id.timetable);



        userType = session.getUserType();
        if(userType.equalsIgnoreCase(constants.STAFF)){
            staffSession = session.getSessionStaffDetails();
            prepareStaffListData();
        }else{
            currentStudent = session.getCurrentStudentSession(getApplicationContext());
            prepareStudentListData();
        }
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                return false;
            }
        });

        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int i) {
                //Toast.makeText(getApplicationContext(),listDataHeader.get(i) + " Expanded", Toast.LENGTH_SHORT).show();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }

    private void prepareStudentListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        Map<String, String> input = new HashMap<String, String>();
        JSONObject data = null;
        try {
            input.put(constants.SCHOOL_ID, currentStudent.getString(constants.SCHOOL_ID));
            input.put(constants.STUDENT_ID, currentStudent.getString(constants.STUDENT_ID));
            data = new JSONObject(input);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("data", String.valueOf(data));
        hr.doPost(TimeTable.this, constants.API_TIME_TABLE, data, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                Log.e("result", String.valueOf(result));

                try {
                    timeData = new JSONArray();
                    timeData = result.getJSONArray("data");
                    List<String> per = new ArrayList<String>();
                    for(int i = 0; i < timeData.length();i++){
                        JSONObject currObj = timeData.getJSONObject(i);
                        String day = currObj.getString("Day");
                        Log.e("day", day);
                        listDataHeader.add(day);
                    }
                    for(int j = 0; j < timeData.length();j++){
                        JSONObject currArray = timeData.getJSONObject(j);
                        JSONArray currPerObj = currArray.getJSONArray("period");
                        per = new ArrayList<String>();
                        for(int k = 0; k < currPerObj.length(); k++){
                            JSONObject peroidObj = currPerObj.getJSONObject(k);
                            String type = peroidObj.getString("type");
                            String subject = null;
                            if (type.equalsIgnoreCase("period")){
                                subject = peroidObj.getString("subject") + " - " + peroidObj.getString("time") + " - " + peroidObj.getString("teacher");
                            }else{
                                subject = "Break" + " - " + peroidObj.getString("time");
                            }
                            Log.e("subject", subject);
                            per.add(subject);
                        }
                        List<String> test = per;
                        listDataChild.put(listDataHeader.get(j), test);
                    }

                    listAdapter = new TimeTableAdapter(TimeTable.this, listDataHeader, listDataChild);
                    expListView.setAdapter(listAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void prepareStaffListData(){
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        Map<String, String> input = new HashMap<String, String>();
        JSONObject data = null;
        try {
            input.put(constants.SCHOOL_ID, staffSession.getString(constants.SCHOOL_ID));
            input.put(constants.STAFF_ID, staffSession.getString(constants.STAFF_ID));
            data = new JSONObject(input);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("data", String.valueOf(data));
        hr.doPost(TimeTable.this, constants.API_STAFF_TIME_TABLE, data, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                Log.e("result", String.valueOf(result));

                try {
                    timeData = new JSONArray();
                    timeData = result.getJSONArray("data");
                    List<String> per = new ArrayList<String>();
                    for(int i = 0; i < timeData.length();i++){
                        JSONObject currObj = timeData.getJSONObject(i);
                        String day = currObj.getString("Day");
                        Log.e("day", day);
                        listDataHeader.add(day);
                    }
                    Log.e("timeData", String.valueOf(timeData));
                    for(int j = 0; j < timeData.length();j++){
                        JSONObject currArray = timeData.getJSONObject(j);
                        JSONArray currPerObj = currArray.getJSONArray("period");
                        per = new ArrayList<String>();
                        for(int k = 0; k < currPerObj.length(); k++){
                            JSONObject peroidObj = currPerObj.getJSONObject(k);
                            String type = peroidObj.getString("type");
                            String subject = null;
                            if (type.equalsIgnoreCase("period")){
                                subject = peroidObj.getString("subject") + " - " + peroidObj.getString("time") + " - " + peroidObj.getString("classdivision");
                            }else{
                                subject = "Break" + " - " + peroidObj.getString("time");
                            }
                            per.add(subject);
                        }
                        List<String> test = per;
                        listDataChild.put(listDataHeader.get(j), test);
                    }

                    listAdapter = new TimeTableAdapter(TimeTable.this, listDataHeader, listDataChild);
                    expListView.setAdapter(listAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
