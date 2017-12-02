package com.webclicz.schoolapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.webclicz.schoolapp.MainActivity;
import com.webclicz.schoolapp.R;
import com.webclicz.schoolapp.Utilities.CircleTransform;
import com.webclicz.schoolapp.Utilities.Constants;
import com.webclicz.schoolapp.Utilities.HttpRequest;
import com.webclicz.schoolapp.Utilities.UserSessions;

import org.json.JSONException;
import org.json.JSONObject;

public class MyProfile extends AppCompatActivity {
    ImageView backButton, profileImage;
    UserSessions session;
    HttpRequest hr;
    Constants constants;
    String schoolId, userType;
    JSONObject userDetais = new JSONObject();
    TextView logName, logMblNumber, logEmail, logAddress, logStuName, logStuClass, logStuDic, logStaffName;
    LinearLayout parentSection, studentSection, staffSection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        session = new UserSessions(getApplicationContext());
        constants = new Constants();
        hr = new HttpRequest();
        userType = session.getUserType();
        backButton = (ImageView) findViewById(R.id.backButton);
        profileImage = (ImageView) findViewById(R.id.profileImage);
        logName = (TextView) findViewById(R.id.logName);
        logMblNumber = (TextView) findViewById(R.id.logMblNumber);
        logEmail = (TextView) findViewById(R.id.logEmail);
        logAddress = (TextView) findViewById(R.id.logAddress);
        logStuName = (TextView) findViewById(R.id.logStuName);
        logStuClass = (TextView) findViewById(R.id.logStuClass);
        logStuDic = (TextView) findViewById(R.id.logStuDic);
        logStaffName = (TextView) findViewById(R.id.logStaffName);

        parentSection = (LinearLayout) findViewById(R.id.parentSection);
        studentSection = (LinearLayout) findViewById(R.id.studentSection);
        staffSection = (LinearLayout) findViewById(R.id.staffSection);

        if (userType.equalsIgnoreCase(constants.PARENT)) {
            userDetais = session.getSessionParentDetails();
            Log.e("userDetais", String.valueOf(userDetais));
            parentSection.setVisibility(View.VISIBLE);
            try {
                logName.setText(userDetais.getString(constants.PARENT_NAME));
                logMblNumber.setText(userDetais.getString("CellPhone"));
                logEmail.setText(userDetais.getString("Email"));
                logAddress.setText(userDetais.getString("Address"));
                Picasso.with(MyProfile.this)
                        .load(userDetais.getString(constants.PROFILE_IMAGE))
                        .resize(250, 250)
                        .centerCrop()
                        .transform(new CircleTransform())
                        .into(profileImage);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else if (userType.equalsIgnoreCase(constants.STUDENT)) {
            userDetais = session.getSessionStudentDetails();
            Log.e("userDetais", String.valueOf(userDetais));
            studentSection.setVisibility(View.VISIBLE);
            try {
                logStuName.setText(userDetais.getString(constants.STUDENT_NAME));
                logStuClass.setText(userDetais.getString(constants.CLASS));
                logStuDic.setText(userDetais.getString(constants.DIVISION));
                Picasso.with(MyProfile.this)
                        .load(userDetais.getString(constants.PROFILE_IMAGE))
                        .resize(250, 250)
                        .centerCrop()
                        .transform(new CircleTransform())
                        .into(profileImage);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else if (userType.equalsIgnoreCase(constants.STAFF)) {
            userDetais = session.getSessionStaffDetails();
            staffSection.setVisibility(View.VISIBLE);
            Log.e("userDetais", String.valueOf(userDetais));

            try {
                logStaffName.setText(userDetais.getString(constants.STAFF_NAME));
                Picasso.with(MyProfile.this)
                        .load(userDetais.getString(constants.PROFILE_IMAGE))
                        .resize(250, 250)
                        .centerCrop()
                        .transform(new CircleTransform())
                        .into(profileImage);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }
}
