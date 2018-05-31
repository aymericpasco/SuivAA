package fr.pasco.aymeric.suivaa.entities;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class Doctor {

    @SerializedName("id")
    private int id;

    @SerializedName("firstname")
    private String firstname;

    @SerializedName("lastname")
    private String lastname;

    @SerializedName("fullname")
    private String fullname;

    @SerializedName("phone")
    private String phone;

    @SerializedName("specialty")
    @Nullable
    private String specialty;

    @SerializedName("office")
    private Office office;

    public Doctor(int id, String firstname, String lastname, String fullname, String phone,
                  @Nullable String specialty, Office office)
    {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.fullname = fullname;
        this.phone = phone;
        this.specialty = specialty;
        this.office = office;
    }

    public int getId() { return id; }

    public String getFirstname() { return firstname; }

    public String getLastname() { return lastname; }

    public String getFullname() { return fullname; }

    public String getPhone() { return phone; }

    @Nullable
    public String getSpecialty() { return specialty; }

    public Office getOffice() { return office; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Doctor that = (Doctor) o;

        return  lastname != null ? lastname.equals(that.lastname) : that.lastname == null;
    }

    @Override
    public int hashCode() {
        return lastname != null ? lastname.hashCode() : 0;
    }

    @Override
    public String toString(){
        return getFullname();
    }
}
