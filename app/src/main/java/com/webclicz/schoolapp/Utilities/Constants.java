package com.webclicz.schoolapp.Utilities;

/**
 * Created by Linesh on 9/27/2017.
 */

public class Constants {
    //API REQUEST URLs
    public String BASE_URL = "http://ems.webclicz.com/api/";

    //device
    public String API_LOGIN = BASE_URL + "login.php";
    public String API_STU_BY_PAR = BASE_URL + "getParentStudentDetail.php";
    public String API_STU_DETAILS_BY_PAR = BASE_URL + "getStudentDetail.php";
    public String API_ASSIGNMENT = BASE_URL + "getStudentAssignmentCalendar.php";
    public String API_FEE = BASE_URL + "getStudentFee.php";
    public String API_FEE_DETAILS = BASE_URL + "getStudentFeePayment.php";
    public String API_EXAM_DETAILS = BASE_URL + "getStudentExaminationCalendar.php";
    public String API_ATTENDANCE = BASE_URL + "getStudentAttendanceCalendar.php";
    public String API_EVENTS = BASE_URL + "getStudentEventCalendar.php";
    public String API_LOGO = BASE_URL + "getCompanyLogo.php";
    public String API_CHANGE_PASSWORD = BASE_URL + "changePassword.php";
    public String API_TIME_TABLE = BASE_URL + "getStudentTimetable.php";
    public String API_STAFF_TIME_TABLE = BASE_URL + "getStaffTimetable.php";
    public String AVTAR_IMAGE = "http://www.newsshare.in/wp-content/uploads/2017/04/Miniclip-8-Ball-Pool-Avatar-11.png";

    //API REQUEST KEYWORDS
    public String EMAIL_ID = "EmailID";
    public String PASSWORD = "Password";
    public String CURRENT_STUDENT = "CurrentStudent";

    // USER TYPES
    public String PARENT = "PARENT";
    public String STUDENT = "STUDENT";
    public String STAFF = "STAFF";

    //API RESPONSE KEYWORD
    public String USER_TYPE = "UserType";
    public String SCHOOL_ID = "SchoolID";
    public String PARENT_ID = "ParentID";
    public String PARENT_NAME = "ParentName";
    public String PROFILE_IMAGE = "PhotoFilePath";

    public String STUDENT_ID = "StudentID";
    public String STUDENT_NAME = "StudentName";
    public String CLASS = "Class";
    public String DIVISION = "Division";
    public String ADMISSION_NUMBER = "AdmissionNumber";
    public String STUDENT_CLASS_DIVISION = "StudentClassDivision";

    public String ASSIGNMENT_ID = "AssignmentID";
    public String ASSIGNMENT_DATE = "AssignmentDate";
    public String ASSIGNMENT_NAME = "Assignment";
    public String SUBMISSION_DATE = "SubmissionDate";
    public String ASSIGNMENT = "Assignment";
    public String MONTH = "Month";
    public String YEAR = "Year";

    public String PAYMENT_PERIOD = "PaymentPeriod";
    public String FEE_TYPE = "FeeType";
    public String ACEDAMIC_YEAR = "AcademicYear";
    public String PAYABLE_BY = "PayableBy";
    public String AMOUNT = "Amount";
    public String AMOUNT_PAID = "AmountPaid";
    public String BALANCE = "Balance";
    public String FEE_ID = "FeeDetailID";
    public String CLASS_DIVISION = "ClassDivision";
    public String FEE_PAID_TYPE = "FeePaidDate";
    public String INT_FEE_ID = "intFeeDetailID";

    public String ACEDAMIC_YEAR_ID = "AcademicYearID";
    public String EXAM_NAME = "ExamName";
    public String SUBJECT_NAME = "SubjectName";
    public String EXAM_DATE = "ExamDate";
    public String START_TIME = "StartTime";
    public String END_TIME = "EndTime";
    public String SUPERVISOR = "Supervisor";
    public String SYLLABUS = "Syllabus";

    public String ACC_YEAR_TOTAL_DAYS = "AcademicYearTotalDays";
    public String ACC_YEAR_ABSENT_DAYS = "AcademicYearAbsentDays";
    public String MONTH_TOTAL_DAYS = "MonthTotalDays";
    public String MONTH_ABSENT_DAYS = "MonthAbsentDays";

    public String EVENT_SUBJECT = "NoticeBoardSubject";
    public String EVENT_DESCRIPTION = "Description";
    public String EVENT_START_DATE = "EventStartDate";
    public String EVENT_END_DATE = "EventEndDate";
    public String EVENT_APPLICABLE_TO = "ApplicableTo";

    public String CURRENT_PASSWORD = "CurrentPassword";
    public String NEW_PASSWORD = "NewPassword";

    public String STAFF_ID = "StaffID";
    public String STAFF_NAME = "StaffName";
}
