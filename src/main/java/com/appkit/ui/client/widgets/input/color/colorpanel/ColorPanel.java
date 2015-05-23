package com.appkit.ui.client.widgets.input.color.colorpanel;

import com.appkit.ui.client.widgets.input.slider.Slider;
import com.appkit.ui.client.widgets.input.text.TextField;
import com.appkit.ui.client.widgets.windowpanel.WindowPanel;
import com.appkit.ui.shared.Color;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.HTMLPanel;

import java.util.ArrayList;

public class ColorPanel {

    interface ColorPanelUiBinder extends UiBinder<WindowPanel, ColorPanel> {
    }

    private static ColorPanelUiBinder uiBinder = GWT.create(ColorPanelUiBinder.class);

    private Color value;
    private ColorChangeHandler colorChangeHandler;

    @UiField
    WindowPanel window;

    @UiField
    ColorPanelToolbarButton wheelToolbarBtn, sliderToolbarBtn;

    @UiField
    DeckPanel mainPanel;

    @UiField
    HTMLPanel colorPreview;

    @UiField
    Slider opacitySlider, brightnessSlider, redSlider, greenSlider, blueSlider, hueSlider,
            saturationSlider, briSlider;

    @UiField
    TextField redField, greenField, blueField, hueField, saturationField, briField, hexField;

    @UiField
    ColorWheel colorWheel;

    @UiHandler("wheelToolbarBtn")
    protected void wheelToolbarBtnClick(ClickEvent event) {
        event.preventDefault();
        sliderToolbarBtn.setDown(false);
        wheelToolbarBtn.setDown(true);
        mainPanel.showWidget(0);
    }

    @UiHandler("sliderToolbarBtn")
    protected void sliderToolbarBtnClick(ClickEvent event) {

        event.preventDefault();
        wheelToolbarBtn.setDown(false);
        sliderToolbarBtn.setDown(true);
        mainPanel.showWidget(1);
    }

    @UiHandler("colorWheel")
    protected void colorWheelValueChange(ValueChangeEvent<ArrayList<Integer>> event) {
        updateColorFromWheel();
    }

    @UiHandler("opacitySlider")
    protected void opacitySliderChange(ValueChangeEvent<Double> event) {
        Color color = getColor();
        color.setAlpha(opacitySlider.getValue().intValue());

        setColor(color, false, false);
    }


    @UiHandler("brightnessSlider")
    protected void brightnessSliderChange(ValueChangeEvent<Double> event) {
        updateColorFromWheel();
    }

    @UiHandler({"redSlider", "greenSlider", "blueSlider"})
    protected void rgbSliderChange(ValueChangeEvent<Double> event) {

        Color newColor = new Color(
                redSlider.getValue().intValue(),
                greenSlider.getValue().intValue(),
                blueSlider.getValue().intValue(),
                opacitySlider.getValue().intValue()

        );

        updateHSBSliders(newColor);

        updateLabels();
        updateHex(newColor);

        setColor(newColor, true, false);
    }

    @UiHandler({"hueSlider", "saturationSlider", "briSlider"})
    protected void hsbSliderChange(ValueChangeEvent<Double> event) {

        Color newColor = Color.colorWithHSBA(
                hueSlider.getValue().intValue(),
                saturationSlider.getValue().intValue(),
                briSlider.getValue().intValue(),
                opacitySlider.getValue().intValue()

        );

        updateRGBSliders(newColor);

        updateLabels();
        updateHex(newColor);

        setColor(newColor);
    }

    @UiHandler({"redField", "greenField", "blueField"})
    protected void rgbFieldChange(ValueChangeEvent<String> event) {

        Color newColor;

        try {
            newColor = new Color(
                    Integer.parseInt(redField.getValue()),
                    Integer.parseInt(greenField.getValue()),
                    Integer.parseInt(blueField.getValue()),
                    opacitySlider.getValue().intValue()

            );
        } catch (NumberFormatException e) {
            return;
        }

        if (newColor != null) {
            updateRGBSliders(newColor);
            updateHSBSliders(newColor);

            updateLabels();
            updateHex(newColor);

            setColor(newColor, true, false);
        }
    }

    @UiHandler({"hueField", "saturationField", "briField"})
    protected void hsbFieldChange(ValueChangeEvent<String> event) {

        Color newColor;

        try {
            newColor = Color.colorWithHSBA(
                    Integer.parseInt(hueField.getValue()),
                    Integer.parseInt(saturationField.getValue()),
                    Integer.parseInt(briField.getValue()),
                    opacitySlider.getValue().intValue()

            );
        } catch (NumberFormatException e) {
            return;
        }

        if (newColor != null) {

            updateRGBSliders(newColor);
            updateHSBSliders(newColor);
            updateLabels();
            updateHex(newColor);

            setColor(newColor);
        }
    }

    @UiHandler("hexField")
    protected void hexFieldChange(ValueChangeEvent<String> event) {
        String hex = hexField.getValue();

        if (hex.length() == 6 && !hex.startsWith("#")) {
            hex = "#" + hex;
        }

        if (hex.length() == 7) {

            Color newColor = Color.colorFromHex(hex);
            if (newColor != null) {

                updateRGBSliders(newColor);
                updateHSBSliders(newColor);
                updateLabels();
                setColor(newColor);
            }

        }
    }


    private static ColorPanel sharedColorPanel = GWT.create(ColorPanel.class);

    public ColorPanel() {

        uiBinder.createAndBindUi(this);

        wheelToolbarBtn.setDown(true);
        opacitySlider.setValue(100);
        brightnessSlider.setValue(100);

        colorWheel.setWheelBrightness(100);

        updateColorFromWheel();

        mainPanel.showWidget(0);

    }

    // PUBLIC methods

    public static ColorPanel get() {
        return sharedColorPanel;
    }

    public void setVisible(boolean visible) {
        window.setVisible(visible);
    }

    public void setPosition(int left, int top) {
        window.setFramePosition(left, top);
    }

    public void setColor(Color color) {
        setColor(color, true, true);
    }

    public Color getColor() {
        return value;
    }

    public void setColorChangeHandler(ColorChangeHandler handler) {
        colorChangeHandler = handler;
    }

    public interface ColorChangeHandler {
        public void onColorChange(Color newColor);
    }

    //END PUBLIC METHODS

    private void updateColorFromWheel() {

        setColor(Color.colorWithHSBA(
                        colorWheel.getAngle(),
                        colorWheel.getDistance(),
                        brightnessSlider.getValue().intValue(),
                        opacitySlider.getValue().intValue()
                ),
                false, true);

    }

    private void setColor(Color color, boolean updateColorWheel, boolean updateSliders) {

        value = color;

        colorPreview.getElement().getStyle().setBackgroundColor(value.toString());

        int[] hsb = value.getHSBComponents();

        if (hsb[2] > 10) {
            brightnessSlider.getElement().getStyle().setBackgroundColor(
                    Color.colorWithHSBA(hsb[0], hsb[1], 100, 100).toHexString()
            );
        }


        brightnessSlider.setValue(hsb[2]);

        colorWheel.setWheelBrightness(hsb[2]);

        opacitySlider.setValue(value.getAlpha());

        if (updateColorWheel) {
            colorWheel.setPositionToColor(value);
        }

        if (updateSliders) {
            updateSliderPanel();
        }

        if (colorChangeHandler != null) {
            colorChangeHandler.onColorChange(value);
        }
    }

    private void updateHSBSliders(Color color) {

        int[] hsb = color.getHSBComponents();

        hueSlider.setValue(hsb[0]);
        saturationSlider.setValue(hsb[1]);
        briSlider.setValue(hsb[2]);

    }

    private void updateRGBSliders(Color color) {

        redSlider.setValue(color.getRed());
        greenSlider.setValue(color.getGreen());
        blueSlider.setValue(color.getBlue());

    }

    private void updateHex(Color color) {
        hexField.setValue(color.toHexString());
    }

    private void updateLabels() {

        hueField.setValue(hueSlider.getValue().toString());
        saturationField.setValue(saturationSlider.getValue().toString());
        briField.setValue(briSlider.getValue().toString());

        redField.setValue(redSlider.getValue().toString());
        greenField.setValue(greenSlider.getValue().toString());
        blueField.setValue(blueSlider.getValue().toString());

    }

    private void updateSliderPanel() {

        updateHSBSliders(getColor());
        updateRGBSliders(getColor());
        updateHex(getColor());
        updateLabels();

    }

}
