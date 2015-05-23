package com.appkit.geometry.shared;


public class Rectangle {


    private Point origin;
    private Size size;

    public Rectangle() {
        origin = new Point();
        size = new Size();
    }


    public Rectangle(double x, double y, double w, double h) {
        origin = new Point(x, y);
        size = new Size(w, h);
    }

    public Rectangle(Point pt, Size sz) {
        origin = pt;
        size = sz;
    }

    public void setOrigin(Point o) {
        this.origin = o;
    }

    public Point getOrigin() {
        return origin;
    }

    public void setSize(Size s) {
        this.size = s;
    }

    public Size getSize() {
        return size;
    }


    public double getLeft() {
        return origin.x;
    }

    public double getRight() {
        return origin.x + size.width;
    }

    public double getTop() {
        return origin.y;
    }

    public double getBottom() {
        return origin.y + size.height;
    }

    public double getWidth() {
        return size.width;
    }

    public double getHeight() {
        return size.height;
    }


}
