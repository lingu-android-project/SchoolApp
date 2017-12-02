package com.webclicz.schoolapp.Activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.webclicz.schoolapp.Adapters.ExaminationAdapters;
import com.webclicz.schoolapp.Models.Examinations;
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

public class Attendance extends AppCompatActivity {
    private CompactCalendarView compactcalander;
    private SimpleDateFormat dateformat=new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
    private static final String TAG = "MainActivity";
    private String EpochString, currMonth, currYear;
    private TextView monthYear, currDate, month_total, month_absent, ac_total_year, ac_absent_year;
    private List<Examinations> examList = new ArrayList<>();;
    private ExaminationAdapters assAdapter;
    private RecyclerView recyclerView;
    ImageView backButton;
    JSONObject currentStudent = new JSONObject();
    UserSessions session;
    HttpRequest hr;
    Constants constants;
    JSONArray assignData = new JSONArray();
    JSONArray summaryData = new JSONArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        session = new UserSessions(getApplicationContext());
        constants = new Constants();
        hr = new HttpRequest();

        currentStudent = session.getCurrentStudentSession(getApplicationContext());
        Log.e("currentStudent", String.valueOf(currentStudent));
        currMonth = new SimpleDateFormat("MM").format(new Date());
        currYear = new SimpleDateFormat("yyyy").format(new Date());

        backButton = (ImageView) findViewById(R.id.backButton);
        month_total = (TextView) findViewById(R.id.month_total);
        month_absent = (TextView) findViewById(R.id.month_absent);
        ac_total_year = (TextView) findViewById(R.id.ac_total_year);
        ac_absent_year = (TextView) findViewById(R.id.ac_absent_year);
        monthYear = (TextView) findViewById(R.id.monthYear);
        monthYear.setText(new SimpleDateFormat("MMMM yyyy").format(new Date()));
        compactcalander=(CompactCalendarView)findViewById(R.id.compactcalendar_view);

        compactcalander.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                Log.e(TAG, "Day was clicked: " + dateClicked );
                // EEE MMM dd HH:mm:ss ZZZ yyyy is the format given in output i.e in dataClicked variable.

                try {

                    SimpleDateFormat formatnow = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZ yyyy", Locale.ENGLISH);
                    SimpleDateFormat formatneeded=new SimpleDateFormat("dd-MM-yyyy");
                    Date date1 = formatnow.parse(dateClicked.toString());
                    String date2 = formatneeded.format(date1);
                    Date date3= formatneeded.parse(date2);
                    Log.e("Date1",String.valueOf(date1));
                    Log.e("Date2",date2);
                    Log.e("Date3",String.valueOf(date3));
                    System.out.println(date2);
                    //Toast.makeText(Attendance.this,date2, Toast.LENGTH_SHORT).show();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                Log.e(TAG, "Month was scrolled to: " + firstDayOfNewMonth);
                currMonth = new SimpleDateFormat("MM").format(firstDayOfNewMonth);
                currYear = new SimpleDateFormat("yyyy").format(firstDayOfNewMonth);
                getExaminations(currMonth, currYear);
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

        getExaminations(currMonth, currYear);
    }
    private void getExaminations(String currMonth, String currYear) {
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
        hr.doPost(Attendance.this, constants.API_ATTENDANCE, data, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                Log.e("result", String.valueOf(result));
                try {
                    assignData = result.getJSONArray("data");
                    summaryData = result.getJSONArray("summary");
                    setDatetoCalendar(assignData);
                    setSummary(summaryData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setSummary(JSONArray summaryData) {
        try {
            JSONObject summaryObj = summaryData.getJSONObject(0);
            month_total.setText(summaryObj.getString(constants.MONTH_TOTAL_DAYS));
            month_absent.setText(summaryObj.getString(constants.MONTH_ABSENT_DAYS));
            ac_total_year.setText(summaryObj.getString(constants.ACC_YEAR_TOTAL_DAYS));
            ac_absent_year.setText(summaryObj.getString(constants.ACC_YEAR_ABSENT_DAYS));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setDatetoCalendar(JSONArray assignData){
        String EpochString = "";
        compactcalander.removeAllEvents();
        for(int j=0; j < assignData.length(); j++){
            try {
                String ddd = assignData.getJSONObject(j).getString("AttendanceDate");

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

}
