package com.webclicz.schoolapp.Models;

/**
 * Created by Linesh on 11/6/2017.
 */

public class StudentList {
    public String StudentID;
    public String StudentName;
    public String PhotoFilePath;
    public String Division;
    public String AdmissionNumber;

    public String getStudentID() {
        return StudentID;
    }

    public void setStudentID(String studentID) {
        StudentID = studentID;
    }

    public String getStudentName() {
        return StudentName;
    }

    public void setStudentName(String studentName) {
        StudentName = studentName;
    }

    public String getPhotoFilePath() {
        return PhotoFilePath;
    }

    public void setPhotoFilePath(String photoFilePath) {
        PhotoFilePath = photoFilePath;
    }

    public String getDivision() {
        return Division;
    }

    public void setDivision(String division) {
        Division = division;
    }

    public String getAdmissionNumber() {
        return AdmissionNumber;
    }

    public void setAdmissionNumber(String admissionNumber) {
        AdmissionNumber = admissionNumber;
    }

}
