package com.webclicz.schoolapp.Activities;

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
import com.webclicz.schoolapp.Adapters.ExaminationAdapters;
import com.webclicz.schoolapp.Models.Assignments;
import com.webclicz.schoolapp.Models.Examinations;
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

public class Examination extends AppCompatActivity {
    private CompactCalendarView compactcalander;
    private SimpleDateFormat dateformat=new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
    private static final String TAG = "MainActivity";
    private String EpochString, currMonth, currYear;
    private TextView monthYear, currDate, noExams;
    private List<Examinations> examList = new ArrayList<>();;
    private ExaminationAdapters assAdapter;
    private RecyclerView recyclerView;
    ImageView backButton;
    JSONObject currentStudent = new JSONObject();
    UserSessions session;
    HttpRequest hr;
    Constants constants;
    JSONArray assignData = new JSONArray();
    JSONObject data = null;
    String userType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examination);

        session = new UserSessions(getApplicationContext());
        constants = new Constants();
        hr = new HttpRequest();

        recyclerView = (RecyclerView) findViewById(R.id.examination);
        assAdapter = new ExaminationAdapters(examList);

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

        Log.e("currentStudent", String.valueOf(currentStudent));
        currMonth = new SimpleDateFormat("MM").format(new Date());
        currYear = new SimpleDateFormat("yyyy").format(new Date());

        backButton = (ImageView) findViewById(R.id.backButton);
        currDate = (TextView) findViewById(R.id.currDate);
        monthYear = (TextView) findViewById(R.id.monthYear);
        noExams = (TextView) findViewById(R.id.noExams);
        monthYear.setText(new SimpleDateFormat("MMMM yyyy").format(new Date()));
        compactcalander=(CompactCalendarView)findViewById(R.id.compactcalendar_view);



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
                    examList.clear();
                    String indexOfAssignment = getIndexOfArray(date2);
                    Log.e("indexOfAssignment", String.valueOf(indexOfAssignment));

                    if (!indexOfAssignment.equalsIgnoreCase("")){
                        noExams.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        String splitIndex[] = indexOfAssignment.split("\\|");
                        Log.e("splitIndex", String.valueOf(splitIndex.length));
                        for(int i = 0; i < splitIndex.length; i++){
                            JSONObject res = null;
                            try {
                                res = assignData.getJSONObject(Integer.parseInt(splitIndex[i]));
                                Log.e("res", String.valueOf(res));
                                Examinations assignModels = new Examinations(res.getString(constants.ACEDAMIC_YEAR_ID),
                                        res.getString(constants.CLASS),
                                        res.getString(constants.EXAM_NAME),
                                        res.getString(constants.SUBJECT_NAME),
                                        res.getString(constants.EXAM_DATE),
                                        res.getString(constants.START_TIME),
                                        res.getString(constants.END_TIME),
                                        res.getString(constants.SUPERVISOR),
                                        res.getString(constants.SYLLABUS)
                                );
                                examList.add(assignModels);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        assAdapter.notifyDataSetChanged();
                    }else {
                        noExams.setVisibility(View.VISIBLE);
                        noExams.setText("No Exams Found!");
                        recyclerView.setVisibility(View.GONE);
                        Examinations assignModels = new Examinations("","","","","","","","","");
                        examList.add(assignModels);
                        assAdapter.notifyDataSetChanged();
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
//                noExams.setVisibility(View.VISIBLE);
//                noExams.setText("Please click on any of the dates");
//                recyclerView.setVisibility(View.GONE);
                currMonth = new SimpleDateFormat("MM").format(firstDayOfNewMonth);
                currYear = new SimpleDateFormat("yyyy").format(firstDayOfNewMonth);
//                getExaminations(currMonth, currYear);
                callExams();
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


        callExams();
    }
    private void getExaminations(String currMonth, String currYear) {
        Log.e("data", String.valueOf(data));
        hr.doPost(Examination.this, constants.API_EXAM_DETAILS, data, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                Log.e("result", String.valueOf(result));
                try {

                    examList.clear();
                    String status = result.getString("code");
                    if(status.equalsIgnoreCase("200")){
                        assignData = result.getJSONArray("data");
                        setDatetoCalendar(assignData);
                        noExams.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        for(int i = 0; i < assignData.length(); i++){
                            JSONObject res = null;
                            try {
                                res = assignData.getJSONObject(i);
                                Log.e("res", String.valueOf(res));
                                Examinations assignModels = new Examinations(res.getString(constants.ACEDAMIC_YEAR_ID),
                                        res.getString(constants.CLASS),
                                        res.getString(constants.EXAM_NAME),
                                        res.getString(constants.SUBJECT_NAME),
                                        res.getString(constants.EXAM_DATE),
                                        res.getString(constants.START_TIME),
                                        res.getString(constants.END_TIME),
                                        res.getString(constants.SUPERVISOR),
                                        res.getString(constants.SYLLABUS)
                                );
                                examList.add(assignModels);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        assAdapter.notifyDataSetChanged();
                    }else {
                        noExams.setVisibility(View.VISIBLE);
                        noExams.setText("No Exams Found!");
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
                String ddd = assignData.getJSONObject(j).getString("ExamDate");

                try {
                    long epoch = new SimpleDateFormat("dd/MM/yyyy").parse(ddd).getTime() / 1000;
                    EpochString=String.valueOf(epoch)+"000";
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Event ev1=new Event(Color.RED,Long.parseLong(EpochString),"Holiday");
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
                String ddd = assignData.getJSONObject(j).getString("ExamDate");
                if (ddd.equalsIgnoreCase(date2)){
                    returnVal = j + "|" + returnVal;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return returnVal;
    }

    public void callExams(){
        userType = session.getUserType();
        if(userType.equalsIgnoreCase(constants.STAFF)){
            JSONObject staffSession = session.getSessionStaffDetails();
            Log.e("staffSession", String.valueOf(staffSession));

            Map<String, String> input = new HashMap<String, String>();

            try {
                input.put(constants.SCHOOL_ID, staffSession.getString(constants.SCHOOL_ID));
                input.put(constants.STAFF_ID, staffSession.getString(constants.STAFF_ID));
                input.put(constants.MONTH, currMonth);
                input.put(constants.YEAR, currYear);
                data = new JSONObject(input);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("data", String.valueOf(data));
            getExaminations(currMonth, currYear);
        }else {
            currentStudent = session.getCurrentStudentSession(getApplicationContext());
            Log.e("currentStudent", String.valueOf(currentStudent));

            Map<String, String> input = new HashMap<String, String>();

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
            getExaminations(currMonth, currYear);
        }
    }
}
