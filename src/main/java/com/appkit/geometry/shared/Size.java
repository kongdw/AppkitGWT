package com.appkit.geometry.shared;

/**
 * Created by cbruno on 1/17/15.
 */
public class Size {
    double width;
    double height;

    public Size() {
        width = 0;
        height = 0;
    }

    public Size(double w, double h) {
        this.width = w;
        this.height = h;
    }

    public void setWidth(double x) {
        this.width = x;
    }

    public double getWidth() {
        return width;
    }

    public void setHeight(double y) {
        this.height = y;
    }

    public double getHeight() {
        return height;
    }

}
