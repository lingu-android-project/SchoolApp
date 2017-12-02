package com.webclicz.schoolapp.Models;

import android.util.Log;

/**
 * Created by Linesh on 10/26/2017.
 */

public class FeeDetailsModel {
    public String StudentName;
    public String AdmissionNumber;
    public String ClassDivision;
    public String AcademicYear;
    public String FeeType;
    public String PaymentPeriod;
    public String FeePaidDate;
    public String Amount;

    public FeeDetailsModel(String string, String string1, String string2, String string3, String string4, String string5, String string6, String string7) {
        setStudentName(string);
        setAdmissionNumber(string1);
        setClassDivision(string2);
        setAcademicYear(string3);
        setFeeType(string4);
        setPaymentPeriod(string5);
        setFeePaidDate(string6);
        setAmount(string7);
    }

    public String getStudentName() {
        return StudentName ;
    }

    public void setStudentName(String studentName) {
        Log.e("studentName", studentName);
        StudentName = studentName;
    }

    public String getAdmissionNumber() {
        return AdmissionNumber;
    }

    public void setAdmissionNumber(String admissionNumber) {
        AdmissionNumber = admissionNumber;
    }

    public String getClassDivision() {
        return ClassDivision;
    }

    public void setClassDivision(String classDivision) {
        ClassDivision = classDivision;
    }

    public String getAcademicYear() {
        return AcademicYear;
    }

    public void setAcademicYear(String academicYear) {
        AcademicYear = academicYear;
    }

    public String getFeeType() {
        return FeeType;
    }

    public void setFeeType(String feeType) {
        FeeType = feeType;
    }

    public String getPaymentPeriod() {
        return PaymentPeriod;
    }

    public void setPaymentPeriod(String paymentPeriod) {
        PaymentPeriod = paymentPeriod;
    }

    public String getFeePaidDate() {
        return FeePaidDate;
    }

    public void setFeePaidDate(String feePaidDate) {
        FeePaidDate = feePaidDate;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }
}
