package com.webclicz.schoolapp;

import android.app.ProgressDialog;
import android.app.usage.UsageEvents;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.webclicz.schoolapp.Activities.Assignment;
import com.webclicz.schoolapp.Activities.Attendance;
import com.webclicz.schoolapp.Activities.ChangePassword;
import com.webclicz.schoolapp.Activities.Events;
import com.webclicz.schoolapp.Activities.Examination;
import com.webclicz.schoolapp.Activities.Fee;
import com.webclicz.schoolapp.Activities.LoginActivity;
import com.webclicz.schoolapp.Activities.MyProfile;
import com.webclicz.schoolapp.Activities.SchoolDetails;
import com.webclicz.schoolapp.Activities.StaffAttendance;
import com.webclicz.schoolapp.Activities.TimeTable;
import com.webclicz.schoolapp.Activities.faq;
import com.webclicz.schoolapp.Adapters.EventsAdapters;
import com.webclicz.schoolapp.Adapters.StudentListAdapter;
import com.webclicz.schoolapp.Models.EventsModel;
import com.webclicz.schoolapp.Models.StudentList;
import com.webclicz.schoolapp.Utilities.CircleTransform;
import com.webclicz.schoolapp.Utilities.Constants;
import com.webclicz.schoolapp.Utilities.HttpRequest;
import com.webclicz.schoolapp.Utilities.RecyclerTouchListener;
import com.webclicz.schoolapp.Utilities.UserSessions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    LinearLayout linAttendance, linAssignment, linEvents, linExam, linPayments, linTimetable;
    LinearLayout linStaffAttendance, linStaffExam, linStaffTimetable, linStaffEvents;
    Intent i;
    UserSessions session;
    HttpRequest hr;
    Constants constants;
    JSONObject userDetais = new JSONObject();
    JSONArray studentArray = new JSONArray();
    TextView studentName, studentClassDiv, paymentText;
    LinearLayout profileimage, holderStudents, arrow, notStaff, yesStaff;
    String schoolId, userType;
    ImageView paymentIcon;
    private List<StudentList> stuList = new ArrayList<>();;
    private StudentListAdapter stuAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setBackgroundResource(R.drawable.header_band);
        navigationView.setNavigationItemSelectedListener(this);


        session = new UserSessions(getApplicationContext());
        constants = new Constants();
        hr = new HttpRequest();

        linAttendance = (LinearLayout) findViewById(R.id.linAttendance);
        linAssignment = (LinearLayout) findViewById(R.id.linAssignment);
        linEvents = (LinearLayout) findViewById(R.id.linEvents);
        linExam = (LinearLayout) findViewById(R.id.linExam);
        linPayments = (LinearLayout) findViewById(R.id.linPayments);
        linTimetable = (LinearLayout) findViewById(R.id.linTimetable);

        notStaff = (LinearLayout) findViewById(R.id.notStaff);
        yesStaff = (LinearLayout) findViewById(R.id.yesStaff);
        linStaffAttendance = (LinearLayout) findViewById(R.id.linStaffAttendance);
        linStaffExam = (LinearLayout) findViewById(R.id.linStaffExam);
        linStaffTimetable = (LinearLayout) findViewById(R.id.linStaffTimetable);
        linStaffEvents = (LinearLayout) findViewById(R.id.linStaffEvents);

        paymentIcon = (ImageView) findViewById(R.id.paymentIcon);
        paymentText = (TextView) findViewById(R.id.paymentText);

        stuAdapter = new StudentListAdapter(stuList, getApplicationContext());



        //if(session.isUserLoggedIn()) {
        userType = session.getUserType();
        if (userType.equalsIgnoreCase(constants.PARENT)){
            notStaff.setVisibility(View.VISIBLE);
            userDetais = session.getSessionParentDetails();
            Map<String, String> input = new HashMap<String, String>();
            JSONObject data = null;
            try {
                input.put(constants.SCHOOL_ID, userDetais.getString(constants.SCHOOL_ID));
                input.put(constants.PARENT_ID, userDetais.getString(constants.PARENT_ID));
                data = new JSONObject(input);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            hr.doPost(MainActivity.this, constants.API_STU_BY_PAR, data, new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject result) {
                    try {
                        studentArray = result.getJSONArray("data");
                        JSONObject firstStuObj = studentArray.getJSONObject(0);
                        getStudentDetailsByStudentId(firstStuObj.getString("StudentID"));

                        for(int i = 0; i < studentArray.length(); i++){
                            JSONObject res = studentArray.getJSONObject(i);
                            StudentList stuModelss = new StudentList();
                            stuModelss.setPhotoFilePath(res.getString(constants.PROFILE_IMAGE));
                            stuModelss.setStudentName(res.getString(constants.STUDENT_NAME));
                            stuList.add(stuModelss);

                        }
                        stuAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            recyclerView = (RecyclerView) findViewById(R.id.allStudents);
                            recyclerView.setHasFixedSize(true);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(stuAdapter);

                            recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
                                @Override
                                public void onClick(View view, int position) {
                                    try {

                                        toggle.onDrawerClosed(view);
                                        holderStudents.setVisibility(View.GONE);
                                        JSONObject stuObj = studentArray.getJSONObject(position);
                                        getStudentDetailsByStudentId(stuObj.getString("StudentID"));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onLongClick(View view, int position) {

                                }
                            }));
                        }
                    },
                    1000);


        }else if (userType.equalsIgnoreCase(constants.STUDENT)){
            notStaff.setVisibility(View.VISIBLE);
            paymentIcon.setVisibility(View.GONE);
            paymentText.setVisibility(View.GONE);
            userDetais = session.getSessionStudentDetails();
            Log.e("studentDetais", String.valueOf(userDetais));
            try {
                getStudentDetailsByStudentId(userDetais.getString("StudentID"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else if (userType.equalsIgnoreCase(constants.STAFF)){
            yesStaff.setVisibility(View.VISIBLE);
            userDetais = session.getSessionStaffDetails();
            Log.e("staffDetails", String.valueOf(userDetais));

            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            setStaffHeader(userDetais);
                        }
                    },
                    500);

//            try {
//                getStudentDetailsByStudentId(userDetais.getString("StudentID"));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
        }

//        }else{
//            i = new Intent(MainActivity.this, LoginActivity.class);
//            startActivity(i);
//            MainActivity.this.finish();
//            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
//        }




        linAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(MainActivity.this, Attendance.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        linAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(MainActivity.this, Assignment.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        linPayments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(MainActivity.this, Fee.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        linEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(MainActivity.this, Events.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        linExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(MainActivity.this, Examination.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        linTimetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(MainActivity.this, TimeTable.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });


        // STAFF MENUS
        linStaffEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(MainActivity.this, Events.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        linStaffExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(MainActivity.this, Examination.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        linStaffTimetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(MainActivity.this, TimeTable.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        linStaffAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(MainActivity.this, StaffAttendance.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            i = new Intent(MainActivity.this, MyProfile.class);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        } else if (id == R.id.nav_change_password) {
            i = new Intent(MainActivity.this, ChangePassword.class);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        } else if (id == R.id.nav_aboutus) {
            i = new Intent(MainActivity.this, SchoolDetails.class);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        } else if (id == R.id.nav_faq) {
            i = new Intent(MainActivity.this, faq.class);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        } else if (id == R.id.logout) {
            session.logout();
            i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
            MainActivity.this.finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void getStudentDetailsByStudentId(String studentId){
        Map<String, String> input = new HashMap<String, String>();
        JSONObject data = null;
        try {
            schoolId = userDetais.getString(constants.SCHOOL_ID);
            input.put(constants.SCHOOL_ID, schoolId);
            input.put(constants.STUDENT_ID, studentId);
            data = new JSONObject(input);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final ProgressDialog pd = new ProgressDialog(MainActivity.this);

        hr.doPost(MainActivity.this, constants.API_STU_DETAILS_BY_PAR, data, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                pd.dismiss();
                try {
                    JSONArray studentDetailsArray = result.getJSONArray("data");
                    JSONObject StuDetailsObj = studentDetailsArray.getJSONObject(0);
                    session.setCurrentStudentSession(StuDetailsObj);
                    setToolHeader(StuDetailsObj);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void setToolHeader(JSONObject firstStuObj){
        profileimage = (LinearLayout) findViewById(R.id.profileimage);
        holderStudents = (LinearLayout) findViewById(R.id.holderStudents);
        arrow = (LinearLayout) findViewById(R.id.arrow);
        studentName = (TextView) findViewById(R.id.studentName);
        studentClassDiv = (TextView) findViewById(R.id.studentClassDiv);
        ImageView imageView = (ImageView) findViewById(R.id.imageView123);

        try {
            Picasso.with(MainActivity.this)
                    .load(firstStuObj.getString(constants.PROFILE_IMAGE))
                    .resize(140, 140)
                    .centerCrop()
                    .transform(new CircleTransform())
                    .into(imageView);
            toolbar.setTitle(firstStuObj.getString(constants.STUDENT_NAME) + ", " + firstStuObj.getString(constants.STUDENT_CLASS_DIVISION));

            Log.e("ProfileImage", firstStuObj.getString(constants.PROFILE_IMAGE));
            Log.e("firstStuObj", String.valueOf(firstStuObj));
            studentName.setText(firstStuObj.getString(constants.STUDENT_NAME));
            studentClassDiv.setText(firstStuObj.getString(constants.STUDENT_CLASS_DIVISION));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holderStudents.getVisibility() == View.VISIBLE){
                    holderStudents.setVisibility(View.GONE);
                }else{
                    holderStudents.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    private void setStaffHeader(JSONObject userDetais) {
        profileimage = (LinearLayout) findViewById(R.id.profileimage);
        holderStudents = (LinearLayout) findViewById(R.id.holderStudents);
        arrow = (LinearLayout) findViewById(R.id.arrow);
        studentName = (TextView) findViewById(R.id.studentName);
        studentClassDiv = (TextView) findViewById(R.id.studentClassDiv);
        ImageView imageView = (ImageView) findViewById(R.id.imageView123);

        holderStudents.setVisibility(View.GONE);
        arrow.setVisibility(View.GONE);
        studentClassDiv.setVisibility(View.GONE);

        try {
            Picasso.with(MainActivity.this)
                    .load(userDetais.getString(constants.PROFILE_IMAGE))
                    .resize(140, 140)
                    .centerCrop()
                    .transform(new CircleTransform())
                    .into(imageView);
            studentName.setText(userDetais.getString(constants.STAFF_NAME));
            toolbar.setTitle(userDetais.getString(constants.STAFF_NAME));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
