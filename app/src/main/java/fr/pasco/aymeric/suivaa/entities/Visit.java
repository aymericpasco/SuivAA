package fr.pasco.aymeric.suivaa.entities;


import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.Locale;

public class Visit {

    /*@SerializedName("id")
    private int id;*/

    @SerializedName("visit_date")
    private String visit_date;

    @SerializedName("appointment")
    private int appointment;

    @SerializedName("arriving_time")
    private String arriving_time;

    @SerializedName("start_time_interview")
    @Nullable
    private String start_time_interview;

    @SerializedName("departure_time")
    @Nullable
    private String departure_time;

    @SerializedName("doctor_id")
    private int doctor_id;

    @SerializedName("doctor")
    private Doctor doctor;

    public Visit(String visit_date, int appointment, String arriving_time,
                 @Nullable String start_time_interview, @Nullable String departure_time,
                 int doctor_id) {
        //this.id = id;
        this.visit_date = visit_date;
        this.appointment = appointment;
        this.arriving_time = arriving_time;
        this.start_time_interview = start_time_interview;
        this.departure_time = departure_time;
        this.doctor_id = doctor_id;
    }

    //public int getId() { return id; }

    public String getVisitDate() { return visit_date; }

    public int getAppointment() { return appointment; }

    public String getArrivingTime() { return arriving_time; }

    @Nullable
    public String getStartTimeInterview() { return start_time_interview; }

    @Nullable
    public String getDepartureTime() { return departure_time; }

    public Doctor getDoctor() { return doctor; }

    public int getDoctorId() { return doctor_id; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Visit that = (Visit) o;

        return  visit_date != null ? visit_date.equals(that.visit_date) : that.visit_date == null;
    }

    @Override
    public int hashCode() {
        return visit_date != null ? visit_date.hashCode() : 0;
    }
}
