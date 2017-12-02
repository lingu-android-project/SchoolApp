package com.webclicz.schoolapp.Models;

/**
 * Created by Linesh on 10/22/2017.
 */

public class Examinations {
    public String AcademicYearID;
    public String Class;
    public String ExamName;
    public String SubjectName;
    public String ExamDate;
    public String StartTime;
    public String EndTime;
    public String Supervisor;
    public String Syllabus;

    public String getAcademicYearID() {
        return AcademicYearID;
    }

    public void setAcademicYearID(String academicYearID) {
        AcademicYearID = academicYearID;
    }

    public void setClass(String aClass) {
        Class = aClass;
    }

    public String getExamName() {
        return ExamName;
    }

    public void setExamName(String examName) {
        ExamName = examName;
    }

    public String getSubjectName() {
        return SubjectName;
    }

    public void setSubjectName(String subjectName) {
        SubjectName = subjectName;
    }

    public String getExamDate() {
        return ExamDate;
    }

    public void setExamDate(String examDate) {
        ExamDate = examDate;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public String getSupervisor() {
        return Supervisor;
    }

    public void setSupervisor(String supervisor) {
        Supervisor = supervisor;
    }

    public String getSyllabus() {
        return Syllabus;
    }

    public void setSyllabus(String syllabus) {
        Syllabus = syllabus;
    }

    public Examinations(String ACEDAMIC_YEAR_ID, String CLASS, String EXAM_NAME, String SUBJECT_NAME,
                        String EXAM_DATE, String START_TIME, String END_TIME, String SUPERVISOR, String SYLLABUS) {
        setAcademicYearID(ACEDAMIC_YEAR_ID);
        setClass(CLASS);
        setExamName(EXAM_NAME);
        setExamDate(EXAM_DATE);
        setSubjectName(SUBJECT_NAME);
        setStartTime(START_TIME);
        setEndTime(END_TIME);
        setSupervisor(SUPERVISOR);
        setSyllabus(SYLLABUS);
    }

}
