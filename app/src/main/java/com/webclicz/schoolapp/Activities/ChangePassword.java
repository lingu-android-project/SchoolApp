package com.webclicz.schoolapp.Activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.webclicz.schoolapp.MainActivity;
import com.webclicz.schoolapp.R;
import com.webclicz.schoolapp.Utilities.Constants;
import com.webclicz.schoolapp.Utilities.HttpRequest;
import com.webclicz.schoolapp.Utilities.UserSessions;
import com.webclicz.schoolapp.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChangePassword extends AppCompatActivity {
    ImageView backButton;
    EditText old_password, new_password, etype_password;
    Button changePasswordBtn;
    UserSessions session;
    HttpRequest hr;
    Constants constants;

    JSONObject currentLoggedin = new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        session = new UserSessions(getApplicationContext());
        constants = new Constants();
        hr = new HttpRequest();

        backButton = (ImageView) findViewById(R.id.backButton);
        old_password = (EditText) findViewById(R.id.old_password);
        new_password = (EditText) findViewById(R.id.new_password);
        etype_password = (EditText) findViewById(R.id.etype_password);
        changePasswordBtn = (Button) findViewById(R.id.changePasswordBtn);
        
        String userType = session.getUserType();
        String userId = null;
        if(userType.equalsIgnoreCase(constants.PARENT)){
            currentLoggedin = session.getSessionParentDetails();
            try {
                userId = currentLoggedin.getString(constants.PARENT_ID);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else if(userType.equalsIgnoreCase(constants.STAFF)){
            currentLoggedin = session.getSessionStaffDetails();
            try {
                userId = currentLoggedin.getString(constants.STAFF_ID);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else if(userType.equalsIgnoreCase(constants.STUDENT)){
            currentLoggedin = session.getSessionStaffDetails();
            try {
                userId = currentLoggedin.getString(constants.STAFF_ID);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        final String finalUserId = userId;
        changePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String oldPassword = old_password.getText().toString();
                String newPassword = new_password.getText().toString();
                String reTypePassword = etype_password.getText().toString();

                if (!oldPassword.equalsIgnoreCase("") && !newPassword.equalsIgnoreCase("") && !reTypePassword.equalsIgnoreCase("")){
                    if (newPassword.equalsIgnoreCase(reTypePassword)){
                        Map<String, String> input = new HashMap<String, String>();
                        try {
                            input.put(constants.SCHOOL_ID, currentLoggedin.getString(constants.SCHOOL_ID));
                            input.put(constants.USER_TYPE, currentLoggedin.getString(constants.USER_TYPE));
                            input.put("UserID", finalUserId);
                            input.put(constants.CURRENT_PASSWORD, oldPassword);
                            input.put(constants.NEW_PASSWORD, newPassword);
                            final JSONObject data = new JSONObject(input);
                            Log.e("data", String.valueOf(data));
                            HttpRequest hr = new HttpRequest();
                            hr.doPost(ChangePassword.this, constants.API_CHANGE_PASSWORD, data, new VolleyCallback() {
                                @Override
                                public void onSuccess(JSONObject result) {
                                    JSONArray dataArr = new JSONArray();
                                    JSONObject dataObj = new JSONObject();
                                    try {
                                        Log.e("result", String.valueOf(result));

                                        String code = result.getString("code");
                                        if (code.equalsIgnoreCase("200")) {
                                            dataArr = result.getJSONArray("data");
                                            Toast toast = Toast.makeText(getApplicationContext(),
                                                    dataArr.getJSONObject(0).getString("Message"),
                                                    Toast.LENGTH_SHORT);
                                            toast.show();
                                            onBackPressed();
                                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                                        }  else{
                                            Toast toast = Toast.makeText(getApplicationContext(),
                                                    "Old password is not matched!",
                                                    Toast.LENGTH_SHORT);
                                            toast.show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "New passwords not matching!",
                                Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Please enter all fields!",
                            Toast.LENGTH_SHORT);
                    toast.show();
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
    }
}
