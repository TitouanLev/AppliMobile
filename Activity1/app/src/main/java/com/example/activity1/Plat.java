package com.example.activity1;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Plat implements Parcelable {

    private int id;
    private String title;
    private int time;
    private int persons;
    private String image;
    private List<String> images;

    protected Plat(int id, String title, int time, int persons, String image, List<String> images) {
        this.id = id;
        this.title = title;
        this.time = time;
        this.persons = persons;
        this.image = image;
        this.images = images;
    }

    protected Plat(Parcel in) {
        id = in.readInt();
        title = in.readString();
        time = in.readInt();
        persons = in.readInt();
        image = in.readString();
        images = in.createStringArrayList();
    }

    public static final Creator<Plat> CREATOR = new Creator<Plat>() {
        @Override
        public Plat createFromParcel(Parcel in) {
            return new Plat(in);
        }

        @Override
        public Plat[] newArray(int size) {
            return new Plat[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeInt(time);
        dest.writeInt(persons);
        dest.writeString(image);
        dest.writeStringList(images);
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getTime() {
        return time;
    }

    public int getPersons() {
        return persons;
    }

    public String getImage() {
        return image;
    }

    public List<String> getImages() {
        return images;
    }

    @Override
    public String toString() {
        return "Plat{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", time=" + time +
                ", persons=" + persons +
                ", image='" + image + '\'' +
                ", images=" + images +
                '}';
    }
}
