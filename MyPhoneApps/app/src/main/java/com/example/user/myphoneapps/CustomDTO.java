package com.example.user.myphoneapps;

import android.graphics.drawable.Drawable;

public class CustomDTO {
    private Drawable resId;
    private String name;
    private String pkg;
    private String size;
    private String property;

    public Drawable getResId() {
        return resId;
    }

    public void setResId(Drawable resId) {
        this.resId = resId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPkg() {
        return pkg;
    }

    public void setPkg(String pkg) {
        this.pkg = pkg;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }
}
