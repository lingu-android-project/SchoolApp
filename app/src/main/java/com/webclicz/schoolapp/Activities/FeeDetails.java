package com.webclicz.schoolapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.webclicz.schoolapp.Adapters.FeeAdapters;
import com.webclicz.schoolapp.Adapters.FeeDetailsAdapters;
import com.webclicz.schoolapp.Models.FeeDetailsModel;
import com.webclicz.schoolapp.Models.Fees;
import com.webclicz.schoolapp.R;
import com.webclicz.schoolapp.Utilities.Constants;
import com.webclicz.schoolapp.Utilities.HttpRequest;
import com.webclicz.schoolapp.Utilities.RecyclerTouchListener;
import com.webclicz.schoolapp.Utilities.UserSessions;
import com.webclicz.schoolapp.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeeDetails extends AppCompatActivity {
    ImageView backButton;
    JSONObject currentStudent = new JSONObject();
    UserSessions session;
    HttpRequest hr;
    Constants constants;
    JSONArray assignData;
    private RecyclerView recyclerView;
    private List<FeeDetailsModel> feeList = new ArrayList<>();
    private FeeDetailsAdapters feeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee_details);

        backButton = (ImageView) findViewById(R.id.backButton);

        session = new UserSessions(getApplicationContext());
        constants = new Constants();
        hr = new HttpRequest();

        String stringData= getIntent().getExtras().getString(constants.FEE_ID);

        recyclerView = (RecyclerView) findViewById(R.id.feeDetails);
        feeAdapter = new FeeDetailsAdapters(feeList);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(feeAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                try {
                    JSONObject currObj = assignData.getJSONObject(position);
                    Log.e("position", String.valueOf(currObj));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        currentStudent = session.getCurrentStudentSession(getApplicationContext());

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        getDetails(stringData);
    }

    private void getDetails(String stringData) {
        Map<String, String> input = new HashMap<String, String>();
        JSONObject data = null;
        try {
            input.put(constants.SCHOOL_ID, currentStudent.getString(constants.SCHOOL_ID));
            input.put(constants.STUDENT_ID, currentStudent.getString(constants.STUDENT_ID));
            input.put(constants.INT_FEE_ID, stringData);
            data = new JSONObject(input);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("data", String.valueOf(data));
        hr.doPost(FeeDetails.this, constants.API_FEE_DETAILS, data, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                Log.e("result", String.valueOf(result));
                try {
                    String code = result.getString("code");
                    if(code.equalsIgnoreCase("200")){
                        assignData = result.getJSONArray("data");

                        for(int i = 0; i < assignData.length(); i++){
                            JSONObject res = null;
                            try {
                                res = assignData.getJSONObject(i);
                                Log.e("res", String.valueOf(res));
                                Log.e("res", String.valueOf(res.getString(constants.STUDENT_NAME)));
                                Log.e("res", String.valueOf(res.getString(constants.ADMISSION_NUMBER)));
                                FeeDetailsModel feeModels = new FeeDetailsModel(res.getString(constants.STUDENT_NAME),
                                        res.getString(constants.ADMISSION_NUMBER),
                                        res.getString(constants.CLASS_DIVISION),
                                        res.getString(constants.ACEDAMIC_YEAR),
                                        res.getString(constants.FEE_TYPE),
                                        res.getString(constants.PAYMENT_PERIOD),
                                        res.getString(constants.FEE_PAID_TYPE),
                                        res.getString(constants.AMOUNT)
                                );

                                feeList.add(feeModels);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        feeAdapter.notifyDataSetChanged();
                    }else{
                        Toast.makeText(getApplicationContext(), "No data found!", Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
