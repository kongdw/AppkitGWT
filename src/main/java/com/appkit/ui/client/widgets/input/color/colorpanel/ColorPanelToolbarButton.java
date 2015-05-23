package com.appkit.ui.client.widgets.input.color.colorpanel;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.ToggleButton;

public class ColorPanelToolbarButton extends ToggleButton {

    private static DefaultColorPanelAppearance colorAppearance = GWT.create(DefaultColorPanelAppearance.class);

    @UiConstructor
    public ColorPanelToolbarButton() {

        setTabIndex(0);

        addStyleName(colorAppearance.css().colorPanelToolbarButton());

        addMouseDownHandler(new MouseDownHandler() {
            @Override
            public void onMouseDown(MouseDownEvent event) {
                setFocus(false);
                if (event.getNativeButton() == Event.BUTTON_LEFT)
                    addStyleName(colorAppearance.css().activeStateClass());
            }
        });

        addMouseUpHandler(new MouseUpHandler() {
            @Override
            public void onMouseUp(MouseUpEvent event) {
                removeStyleName(colorAppearance.css().activeStateClass());
            }
        });

    }
}
