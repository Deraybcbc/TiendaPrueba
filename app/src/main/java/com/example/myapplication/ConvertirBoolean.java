package com.example.myapplication;

import android.util.JsonReader;
import android.util.JsonWriter;

import com.google.gson.TypeAdapter;

import java.io.IOException;

public class ConvertirBoolean extends TypeAdapter<Boolean> {

    @Override
    public void write(com.google.gson.stream.JsonWriter out, Boolean value) throws IOException {
        out.value(value);

    }

    @Override
    public Boolean read(com.google.gson.stream.JsonReader in) throws IOException {
        return in.nextInt() != 0;
    }
}
