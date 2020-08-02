package com.myproject.pkl.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "identified_object")
public class IdentifiedObject implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "jenis")
    private String jenis;
    @ColumnInfo(name = "tanggal")
    private String tanggal;
    @ColumnInfo(name = "jam")
    private String jam;
    @ColumnInfo(name = "photo")
    private byte[] photo;

    public IdentifiedObject(int id, String jenis, String tanggal, String jam, byte[] photo) {
        this.id = id;
        this.jenis = jenis;
        this.tanggal = tanggal;
        this.jam = jam;
        this.photo = photo;
    }
    @Ignore
    public IdentifiedObject(String jenis, String tanggal, String jam, byte[] photo) {
        this.jenis = jenis;
        this.tanggal = tanggal;
        this.jam = jam;
        this.photo = photo;
    }

    protected IdentifiedObject(Parcel in) {
        jenis = in.readString();
        tanggal = in.readString();
        jam = in.readString();
        photo = in.createByteArray();
    }

    public static final Creator<IdentifiedObject> CREATOR = new Creator<IdentifiedObject>() {
        @Override
        public IdentifiedObject createFromParcel(Parcel in) {
            return new IdentifiedObject(in);
        }

        @Override
        public IdentifiedObject[] newArray(int size) {
            return new IdentifiedObject[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getJenis() {
        return jenis;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getJam() {
        return jam;
    }

    public byte[] getPhoto() {
        return photo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(jenis);
        dest.writeString(tanggal);
        dest.writeString(jam);
        dest.writeByteArray(photo);
    }
}
