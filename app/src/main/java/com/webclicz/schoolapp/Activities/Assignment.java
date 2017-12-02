package com.webclicz.schoolapp.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.webclicz.schoolapp.Adapters.AssignmentsAdapters;
import com.webclicz.schoolapp.Models.Assignments;
import com.webclicz.schoolapp.R;
import com.webclicz.schoolapp.Utilities.Constants;
import com.webclicz.schoolapp.Utilities.HttpRequest;
import com.webclicz.schoolapp.Utilities.RecyclerTouchListener;
import com.webclicz.schoolapp.Utilities.UserSessions;
import com.webclicz.schoolapp.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Assignment extends AppCompatActivity {
    private CompactCalendarView compactcalander;
    private SimpleDateFormat dateformat=new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
    private static final String TAG = "MainActivity";
    private String EpochString, currMonth, currYear;
    private TextView monthYear, currDate, noAssignments;
    private List<Assignments> assignmentList = new ArrayList<>();;
    private AssignmentsAdapters assAdapter;
    private RecyclerView recyclerView;
    ImageView backButton;
    JSONObject currentStudent = new JSONObject();
    UserSessions session;
    HttpRequest hr;
    Constants constants;
    JSONArray assignData = new JSONArray();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);

        session = new UserSessions(getApplicationContext());
        constants = new Constants();
        hr = new HttpRequest();

        recyclerView = (RecyclerView) findViewById(R.id.assignments);
        assAdapter = new AssignmentsAdapters(assignmentList);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(assAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        currentStudent = session.getCurrentStudentSession(getApplicationContext());
        Log.e("currentStudent", String.valueOf(currentStudent));
        currMonth = new SimpleDateFormat("MM").format(new Date());
        currYear = new SimpleDateFormat("yyyy").format(new Date());

        backButton = (ImageView) findViewById(R.id.backButton);
        monthYear = (TextView) findViewById(R.id.monthYear);
        currDate = (TextView) findViewById(R.id.currDate);
        noAssignments = (TextView) findViewById(R.id.noAssignments);
        monthYear.setText(new SimpleDateFormat("MMMM yyyy").format(new Date()));
        compactcalander=(CompactCalendarView)findViewById(R.id.compactcalendar_view);


        getAssignments(currMonth, currYear);

        compactcalander.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                compactcalander.setCurrentSelectedDayBackgroundColor(getResources().getColor(R.color.colorPrimary));
                try {
                    SimpleDateFormat formatnow = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZ yyyy", Locale.ENGLISH);
                    SimpleDateFormat formatneeded=new SimpleDateFormat("dd/MM/yyyy");
                    Date date1 = formatnow.parse(dateClicked.toString());
                    String date2 = formatneeded.format(date1);
                    currDate.setText(date2);
                    currDate.setVisibility(View.GONE);
                    assignmentList.clear();
                    String indexOfAssignment = getIndexOfArray(date2);
                    Log.e("indexOfAssignment", String.valueOf(indexOfAssignment));

                    if (!indexOfAssignment.equalsIgnoreCase("")){
                        noAssignments.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        String splitIndex[] = indexOfAssignment.split("\\|");
                        Log.e("splitIndex", String.valueOf(splitIndex.length));
                        for(int i = 0; i < splitIndex.length; i++){
                            JSONObject res = null;
                            try {
                                res = assignData.getJSONObject(Integer.parseInt(splitIndex[i]));
                                Log.e("res", String.valueOf(res));
                                Assignments assignModels = new Assignments(res.getString(constants.ASSIGNMENT_ID),
                                        res.getString(constants.ASSIGNMENT_DATE),
                                        res.getString(constants.ASSIGNMENT_NAME),
                                        res.getString(constants.SUBMISSION_DATE));
                                assignmentList.add(assignModels);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        assAdapter.notifyDataSetChanged();
                    }else {
                        noAssignments.setVisibility(View.VISIBLE);
                        noAssignments.setText("No Assignments Found!");
                        recyclerView.setVisibility(View.GONE);
                        Assignments assignModels = new Assignments("","","","");
                        assignmentList.add(assignModels);
                        assAdapter.notifyDataSetChanged();
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
//                noAssignments.setVisibility(View.VISIBLE);
//                noAssignments.setText("Please click on any of the dates");
//                recyclerView.setVisibility(View.GONE);
                currMonth = new SimpleDateFormat("MM").format(firstDayOfNewMonth);
                currYear = new SimpleDateFormat("yyyy").format(firstDayOfNewMonth);
                getAssignments(currMonth, currYear);
                monthYear.setText(dateformat.format(firstDayOfNewMonth));
                compactcalander.setCurrentDayBackgroundColor(getResources().getColor(R.color.transparent));
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

    private void getAssignments(String currMonth, String currYear) {
        Map<String, String> input = new HashMap<String, String>();
        JSONObject data = null;
        try {
            input.put(constants.SCHOOL_ID, currentStudent.getString(constants.SCHOOL_ID));
            input.put(constants.STUDENT_ID, currentStudent.getString(constants.STUDENT_ID));
            input.put(constants.MONTH, currMonth);
            input.put(constants.YEAR, currYear);
            data = new JSONObject(input);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("data", String.valueOf(data));
        hr.doPost(Assignment.this, constants.API_ASSIGNMENT, data, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                Log.e("result", String.valueOf(result));
                try {

                    assignmentList.clear();
                    String status = result.getString("code");
                    if(status.equalsIgnoreCase("200")){
                        assignData = new JSONArray();
                        assignData = result.getJSONArray("data");
                        setDatetoCalendar(assignData);
                        noAssignments.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);


                        for(int i = 0; i < assignData.length(); i++){
                            JSONObject res = null;
                            try {
                                res = assignData.getJSONObject(i);
                                Log.e("res", String.valueOf(res));
                                Assignments assignModels = new Assignments(res.getString(constants.ASSIGNMENT_ID),
                                        res.getString(constants.ASSIGNMENT_DATE),
                                        res.getString(constants.ASSIGNMENT_NAME),
                                        res.getString(constants.SUBMISSION_DATE));
                                assignmentList.add(assignModels);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        assAdapter.notifyDataSetChanged();
                    }else {
                        noAssignments.setVisibility(View.VISIBLE);
                        noAssignments.setText("No Exams Found!");
                        recyclerView.setVisibility(View.GONE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setDatetoCalendar(JSONArray assignData){
        String EpochString = "";

        compactcalander.removeAllEvents();
        for(int j=0; j < assignData.length(); j++){
            try {
                String ddd = assignData.getJSONObject(j).getString("AssignmentDate");

                try {
                    long epoch = new SimpleDateFormat("dd/MM/yyyy").parse(ddd).getTime() / 1000;
                    EpochString=String.valueOf(epoch)+"000";
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Event ev1=new Event(Color.RED,Long.parseLong(EpochString),"     ");
                compactcalander.addEvent(ev1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        compactcalander.setCurrentDayBackgroundColor(getResources().getColor(R.color.transparent));
        compactcalander.setUseThreeLetterAbbreviation(true);
    }

    public String getIndexOfArray(String date2){
        String returnVal = "";
        for(int j=0; j < assignData.length(); j++){
            try {
                String ddd = assignData.getJSONObject(j).getString("AssignmentDate");
                if (ddd.equalsIgnoreCase(date2)){
                    returnVal = j + "|" + returnVal;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return returnVal;
    }
}
