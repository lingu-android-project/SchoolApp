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

import com.webclicz.schoolapp.Adapters.FeeAdapters;
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

public class Fee extends AppCompatActivity {
    ImageView backButton;
    JSONObject currentStudent = new JSONObject();
    UserSessions session;
    HttpRequest hr;
    Constants constants;
    JSONArray assignData;
    private List<Fees> feeList = new ArrayList<>();;
    private FeeAdapters feeAdapter;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee);
        backButton = (ImageView) findViewById(R.id.backButton);

        session = new UserSessions(getApplicationContext());
        constants = new Constants();
        hr = new HttpRequest();

        recyclerView = (RecyclerView) findViewById(R.id.fee);
        feeAdapter = new FeeAdapters(feeList);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(feeAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                try {
                    String feeId = assignData.getJSONObject(position).getString(constants.FEE_ID);
                    Intent i = new Intent(Fee.this, FeeDetails.class);
                    i.putExtra(constants.FEE_ID, feeId);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                Log.e("position", String.valueOf(feeId));
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

        getFee();
    }

    private void getFee() {
        Map<String, String> input = new HashMap<String, String>();
        JSONObject data = null;
        try {
            input.put(constants.SCHOOL_ID, currentStudent.getString(constants.SCHOOL_ID));
            input.put(constants.STUDENT_ID, currentStudent.getString(constants.STUDENT_ID));
            data = new JSONObject(input);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("data", String.valueOf(data));
        hr.doPost(Fee.this, constants.API_FEE, data, new VolleyCallback() {
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
                                Fees feeModels = new Fees(res.getString(constants.PAYMENT_PERIOD),
                                        res.getString(constants.FEE_TYPE),
                                        res.getString(constants.ACEDAMIC_YEAR),
                                        res.getString(constants.PAYABLE_BY),
                                        res.getString(constants.AMOUNT),
                                        res.getString(constants.AMOUNT_PAID),
                                        res.getString(constants.ADMISSION_NUMBER),
                                        res.getString(constants.CLASS_DIVISION),
                                        res.getString(constants.BALANCE)
                                );
                                feeList.add(feeModels);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        feeAdapter.notifyDataSetChanged();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
