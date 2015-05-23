package com.appkit.ui.client.widgets.input.slider;


import com.appkit.ui.client.widgets.Control;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Touch;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class Slider extends Control implements HasValue<Double>, RequiresResize,
        MouseDownHandler, TouchStartHandler {

    private SliderHandle handle;

    private boolean _leftMouseDown;

    private double value = Double.MIN_VALUE;
    private double step = 1.0;
    private double minValue = 0;
    private double maxValue = 100.0;

    private boolean continuous = false;
    private boolean vertical = false;

    private Element sliderRange = null;
    private Element sliderValueTip = null;

    private SliderAppearance appearance;

    static SliderAppearance DEFAULT_APPEARANCE = GWT.create(DefaultSliderAppearance.class);

    public Slider(SliderAppearance appearance) {
        super(DOM.createDiv());
        this.appearance = appearance;
        render();

    }

    public Slider() {
        this(DEFAULT_APPEARANCE);
    }

    private void render() {

        getElement().setClassName(appearance.css().sliderClass());
        getElement().addClassName("appkit-slider");

        getElement().setAttribute("role", "slider");


        setMinValue(0);
        setMaxValue(100);

        setTabIndex(-2);

        addDomHandler(this, MouseDownEvent.getType());
        addDomHandler(this, TouchStartEvent.getType());

        vertical = false;

        handle = new SliderHandle();

        getElement().appendChild(handle.getElement());

        getElement().addClassName(appearance.css().sliderHorizontalClass());

        sliderRange = DOM.createDiv();
        sliderRange.setClassName(appearance.css().sliderRangeClass());
        sliderRange.addClassName("appkit-slider-range");

        getElement().appendChild(sliderRange);

        setValue(getMinValue());

        positionKnob();
    }

    public SliderAppearance getAppearance() {
        return appearance;
    }

    private double valueOfKnobPosition(double pos) {

        double p;

        if (isVertical()) {
            double h = getElement().getOffsetHeight() - 20.0;
            p = Math.max(0.0, Math.min(1.0, (h - (pos - 10)) / h));
        } else {
            p = Math.max(0.0, Math.min(1.0, (pos - 10) / (getElement().getOffsetWidth() - 20.0)));
        }


        double dv = p * (getMaxValue() - getMinValue()) + getMinValue();

        dv = this.stepToNearestAllowedValue(dv);

        return dv;
    }

    private void calcValue(Touch event) {

        double offsetx = event.getRelativeX(getElement()),
                offsety = event.getRelativeY(getElement());

        if (isVertical()) {
            double dv = valueOfKnobPosition(offsety);
            setValue(dv);
        } else {
            double dv = valueOfKnobPosition(offsetx);
            setValue(dv);
        }
    }

    private void calcValue(Event event) {

        double hofx = handle != null ? handle.getHandleOffsetX() : 0,
                hofy = handle != null ? handle.getHandleOffsetY() : 0,
                offsetx = event.getClientX() - getElement().getAbsoluteLeft() - hofx,
                offsety = event.getClientY() - getElement().getAbsoluteTop() - hofy;

        if (isVertical()) {
            double dv = valueOfKnobPosition(offsety);
            setValue(dv);
        } else {
            double dv = valueOfKnobPosition(offsetx);
            setValue(dv);
        }
    }


    public void positionKnob() {

        double p = 1 - (getMaxValue() - getValue()) / (getMaxValue() - getMinValue());

        if (handle != null) {
            if (isVertical()) {


                if (getElement().getOffsetHeight() == 0) { // need to get height

                    ScheduledCommand command = new ScheduledCommand() {
                        @Override
                        public void execute() {
                            positionKnob();
                        }
                    };

                    Scheduler.get().scheduleDeferred(command);

                }

                double yshift = Math.round((1 - p) * (getElement().getOffsetHeight() - 20.0));

                handle.setPosition(yshift + 10.0);

                double range = getElement().getOffsetHeight() - (yshift + 10.0);

                sliderRange.getStyle().setHeight(range, Style.Unit.PX);

            } else {


                if (getElement().getOffsetWidth() == 0) {
                    ScheduledCommand command = new ScheduledCommand() {
                        @Override
                        public void execute() {
                            positionKnob();
                        }
                    };

                    Scheduler.get().scheduleDeferred(command);
                }

                double xshift = Math.round(p * (getElement().getOffsetWidth() - 20.0));
                handle.setPosition(xshift + 10.0);

                sliderRange.getStyle().setWidth(xshift + 10.0, Style.Unit.PX);

            }
        }


    }


    private double stepToNearestAllowedValue(double dv) {
        double m = Math.round(dv / getStep());
        return Math.min(getMaxValue(), Math.max(getMinValue(), m * getStep()));
    }


    public void setValue(double v) {

        double dv = stepToNearestAllowedValue(v);
        if (value != dv) {

            value = dv;
            getElement().setAttribute("aria-valuenow", "" + value);
            positionKnob();
        }


        getElement().setAttribute("aria-valuenow", "" + value);

    }

    public Double getValue() {
        return new Double(value);
    }

    @Override
    public void setValue(Double value) {
        setValue(value.doubleValue());
    }

    @Override
    public void setValue(Double value, boolean fireEvents) {

        double oldValue = getValue();

        setValue(value);

        if (fireEvents && oldValue != getValue()) {
            ValueChangeEvent.fire(this, getValue());
        }
    }


    public void setMinValue(double mv) {
        minValue = mv;
        setValue(getValue());

        getElement().setAttribute("aria-valuemin", "" + minValue);
    }

    public double getMinValue() {
        return minValue;
    }

    public void setMaxValue(double mv) {
        maxValue = mv;
        setValue(getValue());
        getElement().setAttribute("aria-valuemax", "" + maxValue);
    }

    public double getMaxValue() {
        return maxValue;
    }

    public void setVertical(boolean v) {

        if (vertical != v) {

            vertical = v;

            handle.getElement().removeFromParent();
            handle = null;

            handle = new SliderHandle();

            getElement().appendChild(handle.getElement());

            getElement().removeClassName(appearance.css().sliderVerticalClass());
            getElement().removeClassName(appearance.css().sliderHorizontalClass());

            if (isVertical()) {
                getElement().addClassName(appearance.css().sliderVerticalClass());
            } else {
                getElement().addClassName(appearance.css().sliderHorizontalClass());
            }

            setValue(getMinValue());

            positionKnob();
        }
    }

    public boolean isVertical() {
        return vertical;
    }

    public void setStep(double s) {
        step = s;
    }

    public double getStep() {
        return step;
    }

    public void setContinuous(boolean continuous1) {
        continuous = continuous1;
    }

    public boolean isContinuous() {
        return continuous;
    }

    public void setShowSliderValueTip(boolean showSliderValueTip) {

        if (showSliderValueTip) {

            if (sliderValueTip == null) {
                sliderValueTip = DOM.createDiv();
                sliderValueTip.setClassName(appearance.css().sliderValueTipClass());

                RootPanel.getBodyElement().appendChild(sliderValueTip);
            }


        } else {
            if (sliderValueTip != null) {
                sliderValueTip.removeFromParent();
                sliderValueTip = null;
            }
        }
    }

    public boolean isShowingSliderValueTip() {
        return sliderValueTip != null;
    }


    @Override
    public void setEnabled(boolean enabled) {

        super.setEnabled(enabled);

        if (!this.isEnabled()) {
            getElement().addClassName(appearance.css().disabledClass());
            handle.getElement().setTabIndex(-2);

        } else {
            getElement().removeClassName(appearance.css().disabledClass());
            handle.getElement().setTabIndex(0);
        }
    }

    private void positionAndShowValueTip() {

        if (sliderValueTip != null && handle != null) {

            double v = getValue();
            sliderValueTip.setInnerHTML("" + NumberFormat.getDecimalFormat().format(v));
            sliderValueTip.getStyle().setDisplay(Style.Display.BLOCK);

            if (isVertical()) {
                sliderValueTip.getStyle().setTop(
                        handle.getElement().getAbsoluteTop() + (handle.getElement().getOffsetHeight() - sliderValueTip.getOffsetHeight()) / 2.0,
                        Style.Unit.PX
                );

                sliderValueTip.getStyle().setLeft(
                        handle.getElement().getAbsoluteLeft() + handle.getElement().getOffsetWidth() + 3,
                        Style.Unit.PX
                );
            } else {

                sliderValueTip.getStyle().setTop(
                        handle.getElement().getAbsoluteTop() - sliderValueTip.getOffsetHeight() - 3,
                        Style.Unit.PX
                );

                sliderValueTip.getStyle().setLeft(
                        handle.getElement().getAbsoluteLeft() + (handle.getElement().getOffsetWidth() - sliderValueTip.getOffsetWidth()) / 2.0,
                        Style.Unit.PX
                );

            }
        }

    }


    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Double> handler) {
        return addHandler(handler, ValueChangeEvent.getType());
    }


    @Override
    public void onMouseDown(MouseDownEvent event) {

        event.preventDefault();

        if (event.getNativeButton() == Event.BUTTON_LEFT) {

            if (!isEnabled())
                return;


            if (!_leftMouseDown) {

                calcValue(Event.getCurrentEvent());

                ValueChangeEvent.fire(Slider.this, getValue());

                _leftMouseDown = true;

                handle.makeActive(true);
                DOM.setCapture(handle.getElement());

                handle.getElement().addClassName(appearance.css().activeClass());

                if (isShowingSliderValueTip()) {
                    positionAndShowValueTip();
                }
            }

        }

    }

    @Override
    public void onTouchStart(TouchStartEvent event) {

        event.preventDefault();
        event.stopPropagation();

        if (!isEnabled())
            return;

        if (event.getTouches().length() > 0) {

            calcValue(event.getTouches().get(0));

            ValueChangeEvent.fire(this, getValue());

            _leftMouseDown = true;

            handle.makeActive(true);
            DOM.setCapture(handle.getElement());

            handle.getElement().addClassName(appearance.css().activeClass());

            if (isShowingSliderValueTip()) {
                positionAndShowValueTip();
            }
        }
    }

    private class SliderHandle extends Widget {

        private double startValue = 0;
        private double position = 0;
        private double handleOffsetX = 0,
                handleOffsetY = 0;

        private Timer valueTipTimer = null;

        public SliderHandle() {

            setElement(DOM.createDiv());

            getElement().setClassName(appearance.css().sliderHandleClass());

            getElement().addClassName("appkit-slider-handle");

            getElement().setTabIndex(0);

            setPosition(0);

            DOM.sinkEvents(getElement(), Event.ONKEYUP | Event.ONKEYDOWN | Event.ONMOUSEDOWN
                    | Event.ONTOUCHSTART | Event.ONMOUSEUP | Event.ONTOUCHEND | Event.ONMOUSEMOVE |
                    Event.ONTOUCHMOVE | Event.ONFOCUS | Event.ONBLUR);

            DOM.setEventListener(getElement(), new EventListener() {
                @Override
                public void onBrowserEvent(Event event) {

                    if (valueTipTimer != null) {
                        valueTipTimer.cancel();
                        valueTipTimer = null;
                    }
                    if (event.getTypeInt() == Event.ONMOUSEDOWN || event.getTypeInt() == Event.ONTOUCHSTART) {

                        event.preventDefault();

                        if (!isEnabled())
                            return;

                        if (event.getButton() == Event.BUTTON_LEFT ||
                                event.getTypeInt() == Event.ONTOUCHSTART) {


                            startValue = getValue();

                            handleOffsetX = event.getClientX() - getOffsetWidth() / 2.0 - getAbsoluteLeft();
                            handleOffsetY = event.getClientY() - getOffsetHeight() / 2.0 - getAbsoluteTop();

                            _leftMouseDown = true;
                            makeActive(true);

                            DOM.setCapture(getElement());

                            getElement().addClassName(appearance.css().activeClass());

                            if (isShowingSliderValueTip()) {
                                positionAndShowValueTip();
                            }

                        }
                    } else if (event.getTypeInt() == Event.ONMOUSEMOVE) {

                        if (!isEnabled())
                            return;

                        if (_leftMouseDown && handle != null) {
                            double oldValue = getValue();

                            calcValue(event);

                            if (isShowingSliderValueTip()) {
                                positionAndShowValueTip();
                            }

                            if (isContinuous() && oldValue != getValue()) {
                                ValueChangeEvent.fire(Slider.this, getValue());
                            }
                        }
                    } else if (event.getTypeInt() == Event.ONTOUCHMOVE) {

                        if (!isEnabled())
                            return;

                        if (_leftMouseDown && handle != null) {
                            double oldValue = getValue();
                            if (event.getTouches().length() > 0) {
                                calcValue(event.getTouches().get(0));

                                if (isShowingSliderValueTip()) {
                                    positionAndShowValueTip();
                                }

                                if (isContinuous() && oldValue != getValue()) {
                                    ValueChangeEvent.fire(Slider.this, getValue());
                                }
                            }
                        }

                    } else if (event.getTypeInt() == Event.ONMOUSEUP || event.getTypeInt() == Event.ONTOUCHEND) {
                        DOM.releaseCapture(getElement());

                        handleOffsetX = 0;
                        handleOffsetY = 0;

                        if (_leftMouseDown && !isContinuous() &&
                                startValue != getValue() && isEnabled()) {
                            ValueChangeEvent.fire(Slider.this, getValue());

                        }

                        getElement().removeClassName(appearance.css().activeClass());

                        if (sliderValueTip != null) {
                            sliderValueTip.getStyle().setDisplay(Style.Display.NONE);
                        }

                        _leftMouseDown = false;
                    } else if (event.getTypeInt() == Event.ONFOCUS) {
                        makeActive(true);
                    } else if (event.getTypeInt() == Event.ONKEYDOWN) {

                        if (!isEnabled())
                            return;

                        if (isVertical()) {
                            if (event.getKeyCode() == KeyCodes.KEY_UP) {

                                double dv = getValue() + getStep();
                                dv = stepToNearestAllowedValue(dv);
                                setValue(dv);
                                ValueChangeEvent.fire(Slider.this, getValue());

                                if (isShowingSliderValueTip()) {
                                    positionAndShowValueTip();
                                }

                            } else if (event.getKeyCode() == KeyCodes.KEY_DOWN) {

                                double dv = getValue() - getStep();
                                dv = stepToNearestAllowedValue(dv);
                                setValue(dv);
                                ValueChangeEvent.fire(Slider.this, getValue());

                                if (isShowingSliderValueTip()) {
                                    positionAndShowValueTip();
                                }

                            }

                        } else {

                            if (event.getKeyCode() == KeyCodes.KEY_RIGHT) {

                                double dv = getValue() + getStep();
                                dv = stepToNearestAllowedValue(dv);
                                setValue(dv);
                                ValueChangeEvent.fire(Slider.this, getValue());

                                if (isShowingSliderValueTip()) {
                                    positionAndShowValueTip();
                                }

                            } else if (event.getKeyCode() == KeyCodes.KEY_LEFT) {

                                double dv = getValue() - getStep();
                                dv = stepToNearestAllowedValue(dv);
                                setValue(dv);
                                ValueChangeEvent.fire(Slider.this, getValue());

                                if (isShowingSliderValueTip()) {
                                    positionAndShowValueTip();
                                }

                            }

                        }
                    } else if (event.getTypeInt() == Event.ONKEYUP) {
                        if (sliderValueTip != null) {
                            valueTipTimer = new Timer() {
                                @Override
                                public void run() {
                                    sliderValueTip.getStyle().setDisplay(Style.Display.NONE);
                                }
                            };

                            valueTipTimer.schedule(1000);
                        }
                    } else if (event.getTypeInt() == Event.ONBLUR) {
                        if (sliderValueTip != null) {
                            sliderValueTip.getStyle().setDisplay(Style.Display.NONE);

                        }
                    }
                }
            });

        }

        public void makeActive(boolean flag) {

            if (flag) {
                if (handle != null) {
                    handle.makeActive(false);
                }

                handle = this;

                getElement().getStyle().setZIndex(3);

                setValue(valueOfKnobPosition(getPosition()));

            } else {

                getElement().getStyle().setZIndex(2);
            }
        }

        public double getHandleOffsetX() {
            return handleOffsetX;
        }

        public double getHandleOffsetY() {
            return handleOffsetY;
        }

        public void setPosition(double pos) {

            position = pos;

            if (isVertical()) {
                getElement().getStyle().setTop(position, Style.Unit.PX);
            } else {
                getElement().getStyle().setLeft(position, Style.Unit.PX);
            }
        }

        public double getPosition() {
            return position;
        }

    }


    @Override
    public void setTitle(String title) {
        getElement().setTitle(title);
    }

    @Override
    public String getTitle() {
        return getElement().getTitle();
    }

    @Override
    public void onResize() {
        positionKnob();
    }

}
