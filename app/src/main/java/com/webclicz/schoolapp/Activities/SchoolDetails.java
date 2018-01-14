package com.webclicz.schoolapp.Activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.webclicz.schoolapp.MainActivity;
import com.webclicz.schoolapp.R;
import com.webclicz.schoolapp.Utilities.CircleTransform;
import com.webclicz.schoolapp.Utilities.Constants;
import com.webclicz.schoolapp.Utilities.HttpRequest;
import com.webclicz.schoolapp.Utilities.UserSessions;
import com.webclicz.schoolapp.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SchoolDetails extends AppCompatActivity {
    ImageView backButton, profileImage;
    TextView schoolName, branchName, schoolEmail, schoolPhoneNumber, schoolWebsite, schoolAddress;
    UserSessions session;
    HttpRequest hr;
    Constants constants;
    String schoolId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_details);
        session = new UserSessions(getApplicationContext());
        constants = new Constants();
        hr = new HttpRequest();
        schoolId = session.getSchoolId();
        schoolName = (TextView) findViewById(R.id.schoolName);
        branchName = (TextView) findViewById(R.id.branchName);
        schoolEmail = (TextView) findViewById(R.id.schoolEmail);
        schoolPhoneNumber = (TextView) findViewById(R.id.schoolPhoneNumber);
        schoolWebsite = (TextView) findViewById(R.id.schoolWebsite);
        schoolAddress = (TextView) findViewById(R.id.schoolAddress);

        backButton = (ImageView) findViewById(R.id.backButton);
        profileImage = (ImageView) findViewById(R.id.profileImage);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        getSchoolDetails();
    }

    private void getSchoolDetails() {
        Map<String, String> input = new HashMap<String, String>();
        JSONObject data = null;
        input.put(constants.SCHOOL_ID, schoolId);
        data = new JSONObject(input);
        final ProgressDialog pd = new ProgressDialog(SchoolDetails.this);

        hr.doPost(SchoolDetails.this, constants.API_SCHOOL_DETAILS, data, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                pd.dismiss();
                try {
                    JSONArray SchoolDetails = result.getJSONArray("data");
                    JSONObject schoolObj = SchoolDetails.getJSONObject(0);
                    schoolName.setText(schoolObj.getString("SchoolName"));
                    branchName.setText(schoolObj.getString("BranchName"));
                    schoolEmail.setText(schoolObj.getString("Email"));
                    schoolPhoneNumber.setText(schoolObj.getString("Phone"));
                    schoolWebsite.setText(schoolObj.getString("WebSite"));
                    schoolAddress.setText(schoolObj.getString("Address"));
                    Picasso.with(SchoolDetails.this)
                            .load(schoolObj.getString("PhotoFilePath"))
                            .resize(250, 250)
                            .centerCrop()
                            .transform(new CircleTransform())
                            .into(profileImage);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
