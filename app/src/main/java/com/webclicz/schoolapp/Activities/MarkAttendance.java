package com.webclicz.schoolapp.Activities;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.webclicz.schoolapp.Adapters.MarkAttendanceAdapter;
import com.webclicz.schoolapp.Models.*;
import com.webclicz.schoolapp.R;
import com.webclicz.schoolapp.Utilities.Constants;
import com.webclicz.schoolapp.Utilities.HttpRequest;
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

public class MarkAttendance extends AppCompatActivity {
    private CompactCalendarView compactcalander;
    private SimpleDateFormat dateformat=new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
    private static final String TAG = "MainActivity";
    private TextView monthYear;
    ImageView backButton, calOpen, openFilter;
    TextView dateSummary;
    Spinner classSummary, sessionSummary;
    Button search, setAbsent;
    LinearLayout calHolder, searchForm;
    UserSessions session;
    HttpRequest hr;
    Constants constants;
    LinearLayout openForm;
    private List<com.webclicz.schoolapp.Models.Attendance> attendanceList = new ArrayList<>();;
    private MarkAttendanceAdapter attendanceAdapter;
    private RecyclerView recyclerView;
    String studAttId;

    JSONArray classList = new JSONArray();
    JSONArray sessionList = new JSONArray();
    JSONObject staffSession = new JSONObject();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_attendance);

        session = new UserSessions(getApplicationContext());
        constants = new Constants();
        hr = new HttpRequest();

        staffSession = session.getSessionStaffDetails();

        backButton = (ImageView) findViewById(R.id.backButton);
        calHolder = (LinearLayout) findViewById(R.id.calHolder);
        calOpen = (ImageView) findViewById(R.id.calOpen);
        openFilter = (ImageView) findViewById(R.id.openFilter);
        classSummary = (Spinner) findViewById(R.id.classSummary);
        sessionSummary = (Spinner) findViewById(R.id.sessionSummary);
        search = (Button) findViewById(R.id.search);
        setAbsent = (Button) findViewById(R.id.setAbsent);

        monthYear = (TextView) findViewById(R.id.monthYear);
        dateSummary = (TextView) findViewById(R.id.dateSummary);
        openForm = (LinearLayout) findViewById(R.id.openForm);
        searchForm = (LinearLayout) findViewById(R.id.searchForm);

        monthYear.setText(new SimpleDateFormat("MMMM yyyy").format(new Date()));
        compactcalander=(CompactCalendarView)findViewById(R.id.compactcalendar_view);


        recyclerView = (RecyclerView) findViewById(R.id.attendanceRe);
        attendanceAdapter = new MarkAttendanceAdapter(attendanceList);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(attendanceAdapter);

        SimpleDateFormat formatnow = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZ yyyy", Locale.ENGLISH);
        SimpleDateFormat formatneeded=new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = null;
//            date1 = formatnow.parse(new Date().toString());
        String date2 = formatneeded.format(new Date());
        dateSummary.setText(date2);


        calOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(calHolder.getVisibility() == View.VISIBLE ){
                    calHolder.setVisibility(View.GONE);
                }else{
                    calHolder.setVisibility(View.VISIBLE);
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
//                    openForm.setVisibility(View.VISIBLE);
//                    searchForm.setVisibility(View.GONE);
                    setAbsent.setVisibility(View.VISIBLE);

                    String date = dateSummary.getText().toString();
                    String class_division = classList.getJSONObject((int) classSummary.getSelectedItemId()).getString("ClassDivisionID");
                    String session = sessionList.getJSONObject((int) sessionSummary.getSelectedItemId()).getString("SessionID");


                    Map<String, String> input = new HashMap<String, String>();

                    try {
                        input.put(constants.SCHOOL_ID, staffSession.getString(constants.SCHOOL_ID));
                        input.put(constants.CLASS_DIVISION_ID, class_division);
                        input.put(constants.ATTENDANCE_DATE, date);
                        input.put(constants.SESSION_ID, session);
                        JSONObject data = new JSONObject(input);

                        hr.doPost(MarkAttendance.this, constants.API_ATTENDANCE_SUMMARY, data, new VolleyCallback() {
                            @Override
                            public void onSuccess(JSONObject result) {
                                Log.e("summary", String.valueOf(result));
                                try {
                                    String status = result.getString("code");
                                    attendanceList.clear();
                                    if (status.equalsIgnoreCase("200")){
                                        JSONArray data = result.getJSONArray("data");
                                        studAttId = data.getJSONObject(0).getString("StudentAttendanceID");
                                        for(int i = 0; i < data.length(); i++){
                                            JSONObject res = null;
                                            try {
                                                res = data.getJSONObject(i);
                                                Log.e("studnets", String.valueOf(res));
                                                com.webclicz.schoolapp.Models.Attendance att = new com.webclicz.schoolapp.Models.Attendance(
                                                        res.getString("StudentName"),
                                                        res.getString("AttendanceSession"),
                                                        res.getString("IsAbsent"),
                                                        res.getString("StudentID")
                                                );
                                                attendanceList.add(att);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        attendanceAdapter.notifyDataSetChanged();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        compactcalander.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                compactcalander.setCurrentSelectedDayBackgroundColor(getResources().getColor(R.color.colorPrimary));
                SimpleDateFormat formatnow = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZ yyyy", Locale.ENGLISH);
                SimpleDateFormat formatneeded=new SimpleDateFormat("dd/MM/yyyy");
//                    Date date1 = formatnow.parse(dateClicked.toString());
                String date2 = formatneeded.format(dateClicked);
                dateSummary.setText(date2);
                calHolder.setVisibility(View.GONE);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                monthYear.setText(dateformat.format(firstDayOfNewMonth));
            }
        });

        openFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openForm.setVisibility(View.GONE);
                searchForm.setVisibility(View.VISIBLE);
            }
        });

        setAbsent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, String> input = new HashMap<String, String>();

                try {
                    String selectedIds = "";
                    String date = dateSummary.getText().toString();
                    String class_division = classList.getJSONObject((int) classSummary.getSelectedItemId()).getString("ClassDivisionID");
                    String session = sessionList.getJSONObject((int) sessionSummary.getSelectedItemId()).getString("SessionID");

                    for (int j = 0; j < attendanceList.size(); j++ ){
                        String isAb = attendanceList.get(j).getIsAbsent();
                        if (isAb.equalsIgnoreCase("1")){
                            selectedIds = selectedIds + attendanceList.get(j).getStudentID() + ",";
                        }
                    }
                    selectedIds = selectedIds.replaceAll(",$","");
                    input.put(constants.SCHOOL_ID, staffSession.getString(constants.SCHOOL_ID));
                    input.put(constants.STAFF_ID, staffSession.getString(constants.STAFF_ID));
                    input.put("StudentAttendanceID", studAttId);
                    input.put(constants.CLASS_DIVISION_ID, class_division);
                    input.put(constants.ATTENDANCE_DATE, date);
                    input.put(constants.SESSION_ID, session);
                    input.put("AbsentList", selectedIds);
                    JSONObject data = new JSONObject(input);
                    Log.e("data", String.valueOf(data));
                    hr.doPost(MarkAttendance.this, constants.API_ATTENDANCE_SAVE, data, new VolleyCallback() {
                        @Override
                        public void onSuccess(JSONObject result) {
                            Log.e("result", String.valueOf(result));
                            try {
                                String status = result.getString("code");
                                if(status.equalsIgnoreCase("200")){
                                    JSONArray dd = result.getJSONArray("data");
                                    String msg = dd.getJSONObject(0).getString("Message");
                                    Toast.makeText(getApplicationContext(), msg,
                                            Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(getApplicationContext(), "Something went wrong!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        getSession();


    }

    private void getClassDivision() {
        Map<String, String> input = new HashMap<String, String>();

        try {
            input.put(constants.SCHOOL_ID, staffSession.getString(constants.SCHOOL_ID));
            input.put(constants.STAFF_ID, staffSession.getString(constants.STAFF_ID));
            JSONObject data = new JSONObject(input);

            hr.doPost(MarkAttendance.this, constants.API_CLASS_DIVISION, data, new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject result) {
                    Log.e("result", String.valueOf(result));
                    try {
                        String status = result.getString("code");
                        if(status.equalsIgnoreCase("200")){
                            classList = result.getJSONArray("data");
                            List<String> classDivision = new ArrayList<String>();
                            for (int i = 0; i < classList.length(); i++){
                                classDivision.add(classList.getJSONObject(i).getString("ClassDivision"));
                            }
                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(MarkAttendance.this, android.R.layout.simple_spinner_item, classDivision);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            classSummary.setAdapter(dataAdapter);
                        }else {

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getSession() {
        Log.e("staffSession", String.valueOf(staffSession));

        Map<String, String> input = new HashMap<String, String>();

        try {
            input.put(constants.SCHOOL_ID, staffSession.getString(constants.SCHOOL_ID));
            JSONObject data = new JSONObject(input);

            hr.doPost(MarkAttendance.this, constants.API_SESSION, data, new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject result) {
                    Log.e("result", String.valueOf(result));
                    try {
                        String status = result.getString("code");
                        if(status.equalsIgnoreCase("200")){
                            sessionList = result.getJSONArray("data");
                            List<String> sess = new ArrayList<String>();
                            for (int i = 0; i < sessionList.length(); i++){
                                sess.add(sessionList.getJSONObject(i).getString("SessioneName"));
                            }
                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(MarkAttendance.this, android.R.layout.simple_spinner_item, sess);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            sessionSummary.setAdapter(dataAdapter);
                            getClassDivision();
                        }else {

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
