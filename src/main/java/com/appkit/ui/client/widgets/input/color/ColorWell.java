package com.appkit.ui.client.widgets.input.color;


import com.appkit.ui.client.widgets.button.ImageButton;
import com.appkit.ui.client.widgets.input.color.colorpanel.ColorPanel;
import com.appkit.ui.shared.Color;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;

public class ColorWell extends Composite implements HasValue<Color> {

    private Color value;
    private ImageButton button;

    public ColorWellAppearance DEFAULT_APPEARANCE = GWT.create(DefaultColorWellAppearance.class);

    public ColorWell() {

        button = new ImageButton();
        button.getElement().addClassName("appkit-colorwell");
        button.getElement().getStyle().setPadding(5.0, Style.Unit.PX);

        setValue(Color.whiteColor());

        button.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                ColorPanel colorPanel = ColorPanel.get();

                colorPanel.setColorChangeHandler(new ColorPanel.ColorChangeHandler() {
                    @Override
                    public void onColorChange(Color newColor) {
                        setValue(newColor, true);
                    }
                });

                colorPanel.setColor(getValue());
                colorPanel.setVisible(true);
            }
        });

        initWidget(button);
    }


    @Override
    public Color getValue() {
        return value;
    }

    @Override
    public void setValue(Color color) {

        this.value = color;
        button.getElement().getFirstChildElement().getStyle().setBackgroundColor(value.toString());
    }

    @Override
    public void setValue(Color value, boolean fireEvents) {

        setValue(value);

        if (fireEvents) {
            ValueChangeEvent.fire(this, getValue());
        }

    }

    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Color> handler) {
        return addHandler(handler, ValueChangeEvent.getType());
    }
}
