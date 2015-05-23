package com.appkit.ui.client.widgets.input.stepper;

import com.appkit.ui.client.widgets.button.Button;
import com.appkit.ui.client.widgets.button.ImageButton;
import com.appkit.ui.client.widgets.input.text.TextField;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Image;


public class Stepper extends Composite implements HasValue<Double> {

    private AbsolutePanel panel;
    private TextField textField;
    private ImageButton stepUp;
    private ImageButton stepDown;

    private double step = 1;
    private double minValue = 0;
    private double maxValue = 100;
    private double value = Double.MIN_VALUE;

    private boolean continuous = false;

    private StepperAppearance appearance;

    static StepperAppearance DEFAULT_APPEARANCE = GWT.create(DefaultStepperAppearance.class);

    public Stepper(StepperAppearance appearance) {
        this.appearance = appearance;
        render();
    }

    public Stepper() {
        this(DEFAULT_APPEARANCE);
    }


    private void render() {

        panel = new AbsolutePanel();

        initWidget(panel);

        getElement().setClassName(appearance.css().stepperClass());
        getElement().addClassName("appkit-stepper");
        getElement().setAttribute("role", "spinbutton");

        stepUp = new ImageButton(new Image(appearance.chevronUpImage()));
        stepUp.getIcon().setSize("14px", "14px");
        stepUp.getElement().addClassName(appearance.css().stepperUpButtonClass());

        stepUp.setCanFocus(false);


        stepUp.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (isEnabled()) {
                    stepUp();
                    ValueChangeEvent.fire(Stepper.this, new Double(getValue()));
                }
            }
        });

        stepUp.setContinuous(true);

        panel.add(stepUp);


        stepDown = new ImageButton(new Image(appearance.chevronDownImage()));
        stepDown.getIcon().setPixelSize(14, 14);
        stepDown.getElement().addClassName(appearance.css().stepperDownButtonClass());
        stepDown.setCanFocus(false);

        stepDown.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (isEnabled()) {
                    stepDown();
                    ValueChangeEvent.fire(Stepper.this, new Double(getValue()));
                }
            }
        });

        stepDown.setContinuous(true);

        panel.add(stepDown);

        textField = new TextField();
        textField.getElement().addClassName(appearance.css().stepperTextFieldClass());

        textField.addKeyDownHandler(new KeyDownHandler() {
            @Override
            public void onKeyDown(KeyDownEvent event) {
                if (event.getNativeKeyCode() == KeyCodes.KEY_UP) {
                    event.preventDefault();
                    stepUp();
                    ValueChangeEvent.fire(Stepper.this, new Double(getValue()));
                } else if (event.getNativeKeyCode() == KeyCodes.KEY_DOWN) {
                    event.preventDefault();
                    stepDown();
                    ValueChangeEvent.fire(Stepper.this, new Double(getValue()));
                } else if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                    event.preventDefault();

                    double v = Double.parseDouble(textField.getValue());
                    if (v != getValue()) {
                        setValue(v);
                        ValueChangeEvent.fire(Stepper.this, new Double(getValue()));
                    }
                }
            }
        });

        textField.addBlurHandler(new BlurHandler() {
            @Override
            public void onBlur(BlurEvent event) {

                double v = Double.parseDouble(textField.getValue());
                if (v != getValue()) {
                    setValue(v);
                    ValueChangeEvent.fire(Stepper.this, new Double(getValue()));
                }


            }
        });

        panel.add(textField);

        setButtonStyle(Button.ButtonStyle.INFO);

        setMinValue(0);
        setMaxValue(100);

        setValue(getMinValue());

    }

    public StepperAppearance getAppearance() {
        return appearance;
    }

    public void setButtonStyle(Button.ButtonStyle style) {
        stepUp.setButtonStyle(style);
        stepDown.setButtonStyle(style);
    }

    public void setEnabled(boolean enabled) {

        stepDown.setEnabled(enabled);
        stepUp.setEnabled(enabled);
        textField.setEnabled(enabled);
        getElement().setAttribute("aria-disabled", enabled ? "false" : "true");


    }

    public boolean isEnabled() {
        return textField.isEnabled();
    }

    public void setValue(double v) {

        value = Math.max(getMinValue(), Math.min(getMaxValue(), v));
        textField.setValue("" + value);

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

    public void setStep(double s) {
        step = s;
    }

    public double getStep() {
        return step;
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

    public void setContinuous(boolean continuous1) {
        continuous = continuous1;
    }

    public boolean isContinuous() {
        return continuous;
    }

    public void setReadOnly(boolean readOnly) {
        textField.setReadOnly(readOnly);
    }

    public boolean isReadOnly() {
        return textField.isReadOnly();
    }

    public void stepUp() {
        double currentValue = getValue();
        if (currentValue < getMaxValue()) {
            setValue(currentValue + getStep());
        }
    }

    public void stepDown() {
        double currentValue = getValue();
        if (currentValue > getMinValue()) {
            setValue(currentValue - getStep());
        }
    }

    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Double> handler) {
        return addHandler(handler, ValueChangeEvent.getType());
    }
}
