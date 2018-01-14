package com.webclicz.schoolapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.webclicz.schoolapp.Adapters.FaqAdapters;
import com.webclicz.schoolapp.Adapters.FeeAdapters;
import com.webclicz.schoolapp.Models.FAQ;
import com.webclicz.schoolapp.Models.Fees;
import com.webclicz.schoolapp.R;
import com.webclicz.schoolapp.Utilities.CircleTransform;
import com.webclicz.schoolapp.Utilities.Constants;
import com.webclicz.schoolapp.Utilities.HttpRequest;
import com.webclicz.schoolapp.Utilities.UserSessions;
import com.webclicz.schoolapp.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class faq extends AppCompatActivity {
    UserSessions session;
    HttpRequest hr;
    Constants constants;
    ImageView backButton;
    String schoolId, userType;

    private List<FAQ> faqList = new ArrayList<>();;
    private FaqAdapters faqAdapter;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        backButton = (ImageView) findViewById(R.id.backButton);
        session = new UserSessions(getApplicationContext());
        constants = new Constants();
        hr = new HttpRequest();
        schoolId = session.getSchoolId();
        userType = session.getUserType();

        recyclerView = (RecyclerView) findViewById(R.id.faq);
        faqAdapter = new FaqAdapters(faqList);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(faqAdapter);

        getFAQDetails();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }

    private void getFAQDetails() {
        Map<String, String> input = new HashMap<String, String>();
        JSONObject data = null;
        input.put(constants.SCHOOL_ID, schoolId);
        input.put(constants.USER_TYPE, userType);
        data = new JSONObject(input);
        hr.doPost(faq.this, constants.API_FAQ, data, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    String code = result.getString("code");
                    if(code.equalsIgnoreCase("200")) {
                        JSONArray faqDetails = result.getJSONArray("data");

                        for(int i = 0; i < faqDetails.length(); i++){
                            JSONObject res = null;
                            try {
                                res = faqDetails.getJSONObject(i);
                                Log.e("res", String.valueOf(res));
                                FAQ feeModels = new FAQ(res.getString("FAQID"),
                                        res.getString("FAQ"),
                                        res.getString("FAQDetail")
                                );
                                faqList.add(feeModels);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        faqAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
