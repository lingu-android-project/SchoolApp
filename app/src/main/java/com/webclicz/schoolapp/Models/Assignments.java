package com.webclicz.schoolapp.Models;

/**
 * Created by Linesh on 10/22/2017.
 */

public class Assignments {
    public String assignmentId;
    public String assignmentName;
    public String assignmentDate;
    public String submissionDate;

    public Assignments(String ASSIGNMENT_ID, String ASSIGNMENT_DATE, String ASSIGNMENT_NAME, String SUBMISSION_DATE) {

        setAssignmentId(ASSIGNMENT_ID);
        setAssignmentDate(ASSIGNMENT_DATE);
        setAssignmentName(ASSIGNMENT_NAME);
        setSubmissionDate(SUBMISSION_DATE);

    }

    public String getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public String getAssignmentDate() {
        return assignmentDate;
    }

    public void setAssignmentDate(String assignmentDate) {
        this.assignmentDate = assignmentDate;
    }

    public String getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(String submissionDate) {
        this.submissionDate = submissionDate;
    }

}
