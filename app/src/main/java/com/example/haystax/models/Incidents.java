package com.example.haystax.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Incidents implements Parcelable
{
    private String title;
    private String Date;
    private String id;
    private String summary;
    private String encodedString;

    public Incidents(String title, String date, String id, String summary,String encodedString) {
        this.title = title;
        Date = date;
        this.id = id;
        this.summary = summary;
        this.encodedString = encodedString;
    }

    public Incidents() {
    }


    protected Incidents(Parcel in) {
        title = in.readString();
        Date = in.readString();
        id = in.readString();
        summary = in.readString();
        encodedString = in.readString();
    }

    public static final Creator<Incidents> CREATOR = new Creator<Incidents>() {
        @Override
        public Incidents createFromParcel(Parcel in) {
            return new Incidents(in);
        }

        @Override
        public Incidents[] newArray(int size) {
            return new Incidents[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getEncodedString() {
        return encodedString;
    }

    public void setEncodedString(String encodedString) {
        this.encodedString = encodedString;
    }

    @Override
    public String toString() {
        return "Incidents{" +
                "title='" + title + '\'' +
                ", Date='" + Date + '\'' +
                ", id='" + id + '\'' +
                ", summary='" + summary + '\'' +
                ", encodedString='" + encodedString + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(Date);
        dest.writeString(id);
        dest.writeString(summary);
    }

}
