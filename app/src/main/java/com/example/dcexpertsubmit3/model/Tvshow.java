package com.example.dcexpertsubmit3.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Dibuat oleh petersam
 */
@Entity
public class Tvshow implements Parcelable {
    @PrimaryKey(autoGenerate = false)       private int id;
    @ColumnInfo(name = "name")              private String name;
    @ColumnInfo(name = "first_air_date")    private String first_air_date;
    @ColumnInfo(name = "overview")          private String overview;
    @ColumnInfo(name = "poster_path")       private String poster_path;
    @ColumnInfo(name = "original_language") private String original_language;
    @ColumnInfo(name = "vote_average")      private double vote_average;
    @ColumnInfo(name = "popularity")        private double popularity;

    public Tvshow() {
    }

    protected Tvshow(Parcel in) {
        id = in.readInt();
        name = in.readString();
        first_air_date = in.readString();
        overview = in.readString();
        poster_path = in.readString();
        original_language = in.readString();
        vote_average = in.readDouble();
        popularity = in.readDouble();
    }

    public static final Creator<Tvshow> CREATOR = new Creator<Tvshow>() {
        @Override
        public Tvshow createFromParcel(Parcel in) {
            return new Tvshow(in);
        }

        @Override
        public Tvshow[] newArray(int size) {
            return new Tvshow[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(first_air_date);
        parcel.writeString(overview);
        parcel.writeString(poster_path);
        parcel.writeString(original_language);
        parcel.writeDouble(vote_average);
        parcel.writeDouble(popularity);
    }
}
