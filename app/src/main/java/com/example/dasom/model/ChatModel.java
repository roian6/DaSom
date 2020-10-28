package com.example.dasom.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.dasom.screen.chat.ChatBody;

public class ChatModel implements Parcelable {

    private String date;
    private String time;
    private String text;
    private String location;
    private Boolean isMine;

    private Uri imageUri;

    public ChatModel() {
    }

    public ChatModel(String date, String time, String text) {
        this.date = date;
        this.time = time;
        this.text = text;
        isMine = true;
    }

    public ChatModel(String date, String time, String text, String location, Boolean isMine) {
        this.date = date;
        this.time = time;
        this.text = text;
        this.location = location;
        this.isMine = isMine;
    }

    public ChatModel(ChatBody body) {
        text = body.getText();
        isMine = false;
    }

    public ChatModel(String text, Uri imageUri, Boolean isMine){
        this.text = text;
        this.imageUri = imageUri;
        this.isMine = isMine;
    }

    protected ChatModel(Parcel in) {
        date = in.readString();
        time = in.readString();
        text = in.readString();
        location = in.readString();
        byte tmpIsMine = in.readByte();
        isMine = tmpIsMine == 0 ? null : tmpIsMine == 1;
        imageUri = in.readParcelable(Uri.class.getClassLoader());
    }

    public static final Creator<ChatModel> CREATOR = new Creator<ChatModel>() {
        @Override
        public ChatModel createFromParcel(Parcel in) {
            return new ChatModel(in);
        }

        @Override
        public ChatModel[] newArray(int size) {
            return new ChatModel[size];
        }
    };

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getMine() {
        return isMine;
    }

    public void setMine(Boolean mine) {
        isMine = mine;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(time);
        dest.writeString(text);
        dest.writeString(location);
        dest.writeByte((byte) (isMine == null ? 0 : isMine ? 1 : 2));
        dest.writeParcelable(imageUri, flags);
    }
}
