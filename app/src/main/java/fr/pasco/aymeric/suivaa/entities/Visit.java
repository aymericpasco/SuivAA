package fr.pasco.aymeric.suivaa.entities;


import java.text.SimpleDateFormat;
import java.util.Date;

public class Visit {

    int id;
    String visit_date;
    /*byte appointment;
    SimpleDateFormat arriving_time;
    SimpleDateFormat start_time_interview;
    SimpleDateFormat departure_time;
    Doctor doctor;*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVisit_date() {
        return visit_date;
    }

    public void setVisit_date(String visit_date) {
        this.visit_date = visit_date;
    }
}
