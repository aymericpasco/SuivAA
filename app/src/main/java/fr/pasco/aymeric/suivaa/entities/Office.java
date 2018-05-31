package fr.pasco.aymeric.suivaa.entities;

import android.location.Location;

import com.google.gson.annotations.SerializedName;

public class Office {

    @SerializedName("id")
    private int id;

    @SerializedName("street_number")
    private int street_number;

    @SerializedName("street_name")
    private String street_name;

    @SerializedName("city")
    private String city;

    @SerializedName("zip_code")
    private int zip_code;

    @SerializedName("latitude")
    private double latitude;

    @SerializedName("longitude")
    private double longitude;

    public Office(int id, int street_number, String street_name, String city, int zip_code,
                  double latitude, double longitude)
    {
        this.id = id;
        this.street_number = street_number;
        this.street_name = street_name;
        this.city = city;
        this.zip_code = zip_code;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() { return id; }

    public int getStreetNumber() { return street_number; }

    public String getStreetName() { return street_name; }

    public String getCity() { return city; }

    public int getZipCode() { return zip_code; }

    public double getLatitude() { return latitude; }

    public double getLongitude() { return longitude; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Office that = (Office) o;

        return  city != null ? city.equals(that.city) : that.city == null;
    }

    @Override
    public int hashCode() {
        return city != null ? city.hashCode() : 0;
    }
}
