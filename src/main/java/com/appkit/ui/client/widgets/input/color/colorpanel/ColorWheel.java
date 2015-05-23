package com.appkit.ui.client.widgets.input.color.colorpanel;


import com.appkit.ui.client.events.touch.TouchEvents;
import com.appkit.ui.client.widgets.touch.TouchFocusWidget;
import com.appkit.ui.shared.Color;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;

import java.util.ArrayList;

public class ColorWheel extends TouchFocusWidget implements MouseDownHandler,
        MouseMoveHandler, MouseUpHandler,
        TouchStartHandler, TouchMoveHandler, TouchEndHandler,
        HasValueChangeHandlers<ArrayList<Integer>> {

    private static ColorWheelAppearance APPEARANCE = GWT.create(DefaultColorWheelAppearance.class);

    private int angle = -1; //0-360
    private int distance = -1; //0-100

    private boolean mouseDown = false;

    Element blackWheel;
    Element selectBox;


    public ColorWheel() {
        super(DOM.createDiv());
        render();
    }

    private void render() {

        getElement().addClassName(APPEARANCE.css().wheel());

        blackWheel = DOM.createDiv();
        blackWheel.addClassName(APPEARANCE.css().blackWheel());

        getElement().appendChild(blackWheel);

        selectBox = DOM.createDiv();
        selectBox.addClassName(APPEARANCE.css().selectBox());

        getElement().appendChild(selectBox);

        setPositionToColor(Color.whiteColor());

        addMouseDownHandler(this);
        addMouseMoveHandler(this);
        addMouseUpHandler(this);
        addTouchStartHandler(this);
        addTouchMoveHandler(this);
        addTouchEndHandler(this);


    }

    private void reposition() {

        Event event = Event.getCurrentEvent();

        double midX = 105.0;
        double midY = 105.0;

        if (event != null) {

            int[] location = getPointerLocation();

            double d = Math.round(Math.min(Math.sqrt(
                    (location[0] - midX) * (location[0] - midX) + (location[1] - midY) * (location[1] - midY)
            ), 105));

            double angleRad = Math.atan2(location[1] - midY, location[0] - midX);

            setAngleAndDistance(angleRad, d / 105.0);

            ValueChangeEvent.fire(this, getValues());

        }
    }

    private int[] getPointerLocation() {

        Event event = Event.getCurrentEvent();

        int x = 0;
        int y = 0;


        if (TouchEvents.isTouchEvent(event)) {
            if (event.getTouches().length() > 0) {
                x = event.getTouches().get(0).getClientX();
                y = event.getTouches().get(0).getClientY();
            }
        } else {
            x = event.getClientX();
            y = event.getClientY();
        }


        return new int[]{x - getAbsoluteLeft(), y - getAbsoluteTop()};
    }


    public void setWheelBrightness(int brightness) {

        blackWheel.getStyle().setOpacity((100 - brightness) / 100.0);
    }

    public void setPositionToColor(Color color) {

        int[] hsb = color.getHSBComponents();

        double angleRad = convertDegreesToRadians(hsb[0]);
        int dist = hsb[1];

        setAngleAndDistance(angleRad, dist / 100.0);

    }

    public int getAngle() {
        return angle;
    }

    public int getDistance() {
        return distance;
    }

    public ArrayList<Integer> getValues() {
        ArrayList<Integer> values = new ArrayList<Integer>();
        values.add(new Integer(angle));
        values.add(new Integer(distance));

        return values;
    }


    public void setAngleAndDistance(double angleRad, double distance) {

        double midX = 105.0;
        double midY = 105.0;

        angle = (int) convertRadiansToDegrees(angleRad);
        this.distance = (int) (distance * 100);

        selectBox.getStyle().setLeft(Math.cos(angleRad) * distance * 105 + midX - 3.0, Style.Unit.PX);
        selectBox.getStyle().setTop(Math.sin(angleRad) * distance * 105 + midY - 3.0, Style.Unit.PX);

    }

    private double convertDegreesToRadians(int degrees) {
        return -((degrees - 360.0) / 180.0) * Math.PI;
    }

    private double convertRadiansToDegrees(double radians) {
        return ((-radians / Math.PI) * 180.0 + 360.0) % 360.0;
    }

    @Override
    public void onMouseDown(MouseDownEvent event) {

        int[] location = getPointerLocation();

        int r = 105;
        if (Math.sqrt((location[0] - r) * (location[0] - r) + (location[1] - r) * (location[1] - r)) < r) {
            mouseDown = true;
            event.preventDefault();
            DOM.setCapture(getElement());
            reposition();
        }


    }

    @Override
    public void onMouseMove(MouseMoveEvent event) {

        if (mouseDown) {
            reposition();
        }

    }

    @Override
    public void onMouseUp(MouseUpEvent event) {
        DOM.releaseCapture(getElement());
        mouseDown = false;
    }


    @Override
    public void onTouchEnd(TouchEndEvent event) {
        DOM.releaseCapture(getElement());
        mouseDown = false;
    }

    @Override
    public void onTouchMove(TouchMoveEvent event) {

        if (mouseDown) {
            reposition();
        }

    }

    @Override
    public void onTouchStart(TouchStartEvent event) {


        int[] location = getPointerLocation();

        int r = 105;
        if (Math.sqrt((location[0] - r) * (location[0] - r) + (location[1] - r) * (location[1] - r)) < r) {

            mouseDown = true;
            event.preventDefault();


            DOM.setCapture(getElement());

            reposition();
        }

    }


    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<ArrayList<Integer>> handler) {
        return addHandler(handler, ValueChangeEvent.getType());
    }


}
