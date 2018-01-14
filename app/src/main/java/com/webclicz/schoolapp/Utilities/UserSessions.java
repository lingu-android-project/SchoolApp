package com.webclicz.schoolapp.Utilities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

//import com.google.gson.Gson;
import com.webclicz.schoolapp.Activities.Assignment;
import com.webclicz.schoolapp.Activities.LoginActivity;
import com.webclicz.schoolapp.Utilities.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Linesh on 10/21/2017.
 */

public class UserSessions extends JSONObject {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    Constants constants = new Constants();
    //Gson gson = new Gson();

    int PRIVATE_MODE = 0;
    private static final String PREFER_NAME = "SCHOOLAPP";
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";
    public final String KEY_USER_TYPE = constants.USER_TYPE;
    public final String KEY_SCHOOL_ID = constants.SCHOOL_ID;
    public final String KEY_PARENT_ID = constants.PARENT_ID;
    public final String KEY_PARENT_NAME = constants.PARENT_NAME;
    public final String KEY_PROFILE_IMAGE = constants.PROFILE_IMAGE;
    public final String KEY_CURRENT_STUDENT = constants.CURRENT_STUDENT;
    public final String KEY_STUDENT_ID = constants.STUDENT_ID;
    public final String KEY_STUDENT_NAME = constants.STUDENT_NAME;
    public final String KEY_STUDENT_IMAGE = constants.PROFILE_IMAGE;
    public final String KEY_STUDENT_CLASS = constants.CLASS;
    public final String KEY_STUDENT_DIVISION = constants.DIVISION;

    public UserSessions(Context applicationContext) {
        this._context = applicationContext;
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public UserSessions() {

    }


    public void createUserLoginSession(JSONObject dataObj){
        try {
            String userType = dataObj.getString(KEY_USER_TYPE);
            if(userType.equalsIgnoreCase(constants.PARENT)){
                String schoolId = dataObj.getString(KEY_SCHOOL_ID);
                String parentId = dataObj.getString(KEY_PARENT_ID);
                String parentName = dataObj.getString(KEY_PARENT_NAME);
                String profileImage = dataObj.getString(KEY_PROFILE_IMAGE);
                String CellPhone = dataObj.getString("CellPhone");
                String Email = dataObj.getString("Email");
                String Address = dataObj.getString("Address");

                editor.putBoolean(IS_USER_LOGIN, true);
                editor.putString(KEY_USER_TYPE, userType);
                editor.putString(KEY_SCHOOL_ID, schoolId);
                editor.putString(KEY_PARENT_ID, parentId);
                editor.putString(KEY_PARENT_NAME, parentName);
                editor.putString(KEY_PROFILE_IMAGE, profileImage);
                editor.putString("CellPhone", CellPhone);
                editor.putString("Email", Email);
                editor.putString("Address", Address);
                editor.commit();
            }else if (userType.equalsIgnoreCase(constants.STUDENT)) {
                String schoolId = dataObj.getString(KEY_SCHOOL_ID);
                String studentId = dataObj.getString(KEY_STUDENT_ID);
                String studentName = dataObj.getString(KEY_STUDENT_NAME);
                String studentImage = dataObj.getString(KEY_STUDENT_IMAGE);
                String studentClass = dataObj.getString(KEY_STUDENT_CLASS);
                String studentDivision = dataObj.getString(KEY_STUDENT_DIVISION);

                editor.putBoolean(IS_USER_LOGIN, true);
                editor.putString(KEY_USER_TYPE, userType);
                editor.putString(KEY_SCHOOL_ID, schoolId);
                editor.putString(KEY_STUDENT_ID, studentId);
                editor.putString(KEY_STUDENT_NAME, studentName);
                editor.putString(KEY_STUDENT_IMAGE, studentImage);
                editor.putString(KEY_STUDENT_CLASS, studentClass);
                editor.putString(KEY_STUDENT_DIVISION, studentDivision);
                editor.commit();
            }else if (userType.equalsIgnoreCase(constants.STAFF)) {
                String schoolId = dataObj.getString(KEY_SCHOOL_ID);
                String StaffID = dataObj.getString(constants.STAFF_ID);
                String StaffName = dataObj.getString(constants.STAFF_NAME);
                String empId = dataObj.getString(constants.EMP_ID);
                String designation = dataObj.getString(constants.DESIGNATION);
                String address = dataObj.getString(constants.ADDRESS);
                String cellPhone = dataObj.getString(constants.CELL_PHONE);
                String email = dataObj.getString(constants.EMAIL);
                String profileImage = dataObj.getString(KEY_PROFILE_IMAGE);

                editor.putBoolean(IS_USER_LOGIN, true);
                editor.putString(KEY_USER_TYPE, userType);
                editor.putString(KEY_SCHOOL_ID, schoolId);
                editor.putString(KEY_PROFILE_IMAGE, profileImage);
                editor.putString(constants.STAFF_NAME, StaffName);
                editor.putString(constants.STAFF_ID, StaffID);
                editor.putString(constants.EMP_ID, empId);
                editor.putString(constants.DESIGNATION, designation);
                editor.putString(constants.ADDRESS, address);
                editor.putString(constants.CELL_PHONE, cellPhone);
                editor.putString(constants.EMAIL, email);
                editor.commit();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getSessionParentDetails(){
        Map<String, String> input = new HashMap<String, String>();
        input.put(KEY_USER_TYPE, pref.getString(KEY_USER_TYPE, null));
        input.put(KEY_SCHOOL_ID, pref.getString(KEY_SCHOOL_ID, null));
        input.put(KEY_PARENT_ID, pref.getString(KEY_PARENT_ID, null));
        input.put(KEY_PARENT_NAME, pref.getString(KEY_PARENT_NAME, null));
        input.put(KEY_PROFILE_IMAGE, pref.getString(KEY_PROFILE_IMAGE, null));
        input.put("CellPhone", pref.getString("CellPhone", null));
        input.put("Email", pref.getString("Email", null));
        input.put("Address", pref.getString("Address", null));
        JSONObject data = new JSONObject(input);
        return data;
    }
// 8951046267
    public JSONObject getSessionStudentDetails(){
        Map<String, String> input = new HashMap<String, String>();
        input.put(KEY_USER_TYPE, pref.getString(KEY_USER_TYPE, null));
        input.put(KEY_SCHOOL_ID, pref.getString(KEY_SCHOOL_ID, null));
        input.put(KEY_STUDENT_ID, pref.getString(KEY_STUDENT_ID, null));
        input.put(KEY_STUDENT_NAME, pref.getString(KEY_STUDENT_NAME, null));
        input.put(KEY_PROFILE_IMAGE, pref.getString(KEY_STUDENT_IMAGE, null));
        input.put(KEY_STUDENT_CLASS, pref.getString(KEY_STUDENT_CLASS, null));
        input.put(KEY_STUDENT_DIVISION, pref.getString(KEY_STUDENT_DIVISION, null));

        JSONObject data = new JSONObject(input);
        return data;
    }

    public JSONObject getSessionStaffDetails(){
        Map<String, String> input = new HashMap<String, String>();
        input.put(KEY_USER_TYPE, pref.getString(KEY_USER_TYPE, null));
        input.put(KEY_SCHOOL_ID, pref.getString(KEY_SCHOOL_ID, null));
        input.put(KEY_PROFILE_IMAGE, pref.getString(KEY_PROFILE_IMAGE, null));
        input.put(constants.STAFF_NAME, pref.getString(constants.STAFF_NAME, null));
        input.put(constants.STAFF_ID, pref.getString(constants.STAFF_ID, null));
        input.put(constants.EMP_ID, pref.getString(constants.EMP_ID, null));
        input.put(constants.DESIGNATION, pref.getString(constants.DESIGNATION, null));
        input.put(constants.CELL_PHONE, pref.getString(constants.CELL_PHONE, null));
        input.put(constants.ADDRESS, pref.getString(constants.ADDRESS, null));
        input.put(constants.EMAIL, pref.getString(constants.EMAIL, null));

        JSONObject data = new JSONObject(input);
        return data;
    }

    public void setCurrentStudentSession(JSONObject student){
        //String json = gson.toJson(student);
        String json = String.valueOf(student);
        editor.putString(KEY_CURRENT_STUDENT, json);
        editor.commit();
    }

    public JSONObject getCurrentStudentSession(Context con){
        //UserSessions studentCurrObj = gson.fromJson(pref.getString(KEY_CURRENT_STUDENT, null),getClass());
        try {
            JSONObject tempJson = new JSONObject(pref.getString(KEY_CURRENT_STUDENT, null));
            return tempJson;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //UserSessions studentCurrObj = new UserSessions();

        return null;
    }

    public void logout(){
        editor.clear();
        editor.commit();
    }

    public String getSchoolId(){
        return pref.getString(KEY_SCHOOL_ID, null);
    }
    public String getUserType(){
        return pref.getString(KEY_USER_TYPE, null);
    }
    public boolean isUserLoggedIn(){
        return pref.getBoolean(IS_USER_LOGIN, false);
    }
}
