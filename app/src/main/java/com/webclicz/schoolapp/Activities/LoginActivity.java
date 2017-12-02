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

public class LoginActivity extends AppCompatActivity {
    Button loginButton;
    EditText userName, password;
    String req_url;
    UserSessions session;
    ImageView logoImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new UserSessions(getApplicationContext());

        final Constants constants = new Constants();
        req_url = constants.API_LOGIN;

        loginButton = (Button) findViewById(R.id.loginButton);
        userName = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);

        if(session.isUserLoggedIn()){
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            LoginActivity.this.finish();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String getUserName = userName.getText().toString();
                String getPassword = password.getText().toString();

                if(getUserName.equalsIgnoreCase("")){
                    Snackbar.make(view, "Please enter valid Username", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else if(getPassword.equalsIgnoreCase("")){
                    Snackbar.make(view, "Please enter valid Password", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else{
                    Map<String, String> input = new HashMap<String, String>();
                    input.put(constants.EMAIL_ID, getUserName);
                    input.put(constants.PASSWORD, getPassword);
                    final JSONObject data = new JSONObject(input);

                    HttpRequest hr = new HttpRequest();
                    hr.doPost(LoginActivity.this, req_url, data, new VolleyCallback() {
                        @Override
                        public void onSuccess(JSONObject result) {
                            JSONArray dataArr = new JSONArray();
                            JSONObject dataObj = new JSONObject();
                            try {
                                Log.e("result", String.valueOf(result));
                                dataArr = result.getJSONArray("data");
                                String code = result.getString("code");
                                if (code.equalsIgnoreCase("200")) {
                                    dataObj = dataArr.getJSONObject(0);
                                    session.createUserLoginSession(dataObj);

                                    if(session.isUserLoggedIn()){
                                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(i);
                                        LoginActivity.this.finish();
                                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                    }else{
                                        Snackbar.make(view, "Invalid Username or Password!", Snackbar.LENGTH_LONG)
                                                .setAction("Action", null).show();
                                    }
                                }  else{
                                    Snackbar.make(view, "Invalid Username or Password!", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                }
            }
        });

        final JSONObject data = new JSONObject();

        HttpRequest hr = new HttpRequest();
        hr.doPost(LoginActivity.this, constants.API_LOGO, data, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                JSONArray dataArr = new JSONArray();
                JSONObject dataObj = new JSONObject();
                logoImage = (ImageView) findViewById(R.id.logo);
                try {
                    Log.e("result", String.valueOf(result));
                    dataArr = result.getJSONArray("data");
                    String code = result.getString("code");
                    if (code.equalsIgnoreCase("200")) {
                        dataObj = dataArr.getJSONObject(0);
                        String logo = dataObj.getString("CompanyLogo");
                        Picasso.with(LoginActivity.this)
                                .load(logo)
                                .into(logoImage);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
