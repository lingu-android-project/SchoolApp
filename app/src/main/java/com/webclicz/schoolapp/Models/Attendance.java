package com.webclicz.schoolapp.Models;

/**
 * Created by Linesh on 10/22/2017.
 */

public class Attendance {
    public String IsAbsent;
    public String AttendanceSession;
    public String luDivisionID;
    public String AttendanceDate;
    public String StudentAttendanceID;
    public String StudentName;
    public String StudentAttendanceDetailID;
    public String luClassID;
    public String StudentID;
    public Boolean selected = false;

    public Boolean getSelected() {
        if( this.getIsAbsent().equalsIgnoreCase("0")){
            selected = false;
        }else {
            selected = true;
        }
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }



    public Attendance(String studentName, String attendanceSession, String isAbsent, String name) {
        setStudentName(studentName);
        setAttendanceSession(attendanceSession);
        setIsAbsent(isAbsent);
        setStudentID(name);
    }

    public String getIsAbsent() {
        return IsAbsent;
    }

    public void setIsAbsent(String isAbsent) {
        IsAbsent = isAbsent;
    }

    public String getAttendanceSession() {
        return AttendanceSession;
    }

    public void setAttendanceSession(String attendanceSession) {
        AttendanceSession = attendanceSession;
    }

    public String getLuDivisionID() {
        return luDivisionID;
    }

    public void setLuDivisionID(String luDivisionID) {
        this.luDivisionID = luDivisionID;
    }

    public String getAttendanceDate() {
        return AttendanceDate;
    }

    public void setAttendanceDate(String attendanceDate) {
        AttendanceDate = attendanceDate;
    }

    public String getStudentAttendanceID() {
        return StudentAttendanceID;
    }

    public void setStudentAttendanceID(String studentAttendanceID) {
        StudentAttendanceID = studentAttendanceID;
    }

    public String getStudentName() {
        return StudentName;
    }

    public void setStudentName(String studentName) {
        StudentName = studentName;
    }

    public String getStudentAttendanceDetailID() {
        return StudentAttendanceDetailID;
    }

    public void setStudentAttendanceDetailID(String studentAttendanceDetailID) {
        StudentAttendanceDetailID = studentAttendanceDetailID;
    }

    public String getLuClassID() {
        return luClassID;
    }

    public void setLuClassID(String luClassID) {
        this.luClassID = luClassID;
    }

    public String getStudentID() {
        return StudentID;
    }

    public void setStudentID(String studentID) {
        StudentID = studentID;
    }
}
