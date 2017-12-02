package com.webclicz.schoolapp.Models;

/**
 * Created by Linesh on 10/25/2017.
 */

public class Fees {
    public String PaymentPeriod;
    public String FeeType;
    public String AcademicYear;
    public String PayableBy;
    public String Amount;
    public String AmountPaid;
    public String AdmissionNumber;
    public String ClassDivision;
    public String Balance;
    public String FeeDetailID;

    public Fees(String PAYMENT_PERIOD, String FEE_TYPE, String ACEDAMIC_YEAR, String PAYABLE_BY, String AMOUNT, String AMOUNT_PAID,
                String ADMISSION_NUMBER, String STUDENT_CLASS_DIVISION, String BALANCE) {
        setPaymentPeriod(PAYMENT_PERIOD);
        setFeeType(FEE_TYPE);
        setAcademicYear(ACEDAMIC_YEAR);
        setPayableBy(PAYABLE_BY);
        setAmount(AMOUNT);
        setAmountPaid(AMOUNT_PAID);
        setAdmissionNumber(ADMISSION_NUMBER);
        setClassDivision(STUDENT_CLASS_DIVISION);
        setBalance(BALANCE);
    }

    public String getPaymentPeriod() {
        return PaymentPeriod;
    }

    public void setPaymentPeriod(String paymentPeriod) {
        PaymentPeriod = paymentPeriod;
    }

    public String getFeeType() {
        return FeeType;
    }

    public void setFeeType(String feeType) {
        FeeType = feeType;
    }

    public String getAcademicYear() {
        return AcademicYear;
    }

    public void setAcademicYear(String academicYear) {
        AcademicYear = academicYear;
    }

    public String getPayableBy() {
        return PayableBy;
    }

    public void setPayableBy(String payableBy) {
        PayableBy = payableBy;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getAmountPaid() {
        return AmountPaid;
    }

    public void setAmountPaid(String amountPaid) {
        AmountPaid = amountPaid;
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

    public String getBalance() {
        return Balance;
    }

    public void setBalance(String balance) {
        Balance = balance;
    }

    public String getFeeDetailID() {
        return FeeDetailID;
    }

    public void setFeeDetailID(String feeDetailID) {
        FeeDetailID = feeDetailID;
    }


}
