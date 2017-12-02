package com.webclicz.schoolapp.Models;

/**
 * Created by Linesh on 10/25/2017.
 */

public class EventsModel {
    public String NoticeBoardSubject;
    public String Description;
    public String EventStartDate;
    public String EventEndDate;
    public String ApplicableTo;

    public EventsModel(String NoticeBoardSubject, String Description, String EventStartDate, String EventEndDate, String ApplicableTo){
        setNoticeBoardSubject(NoticeBoardSubject);
        setDescription(Description);
        setEventEndDate(EventEndDate);
        setEventStartDate(EventStartDate);
        setApplicableTo(ApplicableTo);
    }
    public String getNoticeBoardSubject() {
        return NoticeBoardSubject;
    }

    public void setNoticeBoardSubject(String noticeBoardSubject) {
        NoticeBoardSubject = noticeBoardSubject;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getEventStartDate() {
        return EventStartDate;
    }

    public void setEventStartDate(String eventStartDate) {
        EventStartDate = eventStartDate;
    }

    public String getEventEndDate() {
        return EventEndDate;
    }

    public void setEventEndDate(String eventEndDate) {
        EventEndDate = eventEndDate;
    }

    public String getApplicableTo() {
        return ApplicableTo;
    }

    public void setApplicableTo(String applicableTo) {
        ApplicableTo = applicableTo;
    }
}
