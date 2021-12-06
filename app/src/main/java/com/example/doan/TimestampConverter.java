package com.example.doan;

import android.annotation.SuppressLint;

import androidx.room.TypeConverter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimestampConverter {
    @SuppressLint("SimpleDateFormat")
    static DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    @TypeConverter
    public static String toString(Date value) {
        if (value != null) {
            return df.format(value);
        } else {
            return null;
        }
    }

    @TypeConverter
    public static Date toTimestamp(String value) {
        if (value != null && !value.equals("")) {
            try {
                return df.parse(value);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            return null;
        }
    }
}
