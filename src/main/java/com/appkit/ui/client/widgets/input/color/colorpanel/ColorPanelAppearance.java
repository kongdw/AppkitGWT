package com.appkit.ui.client.widgets.input.color.colorpanel;


import com.google.gwt.resources.client.CssResource;

public interface ColorPanelAppearance {

    public interface ColorPanelCss extends CssResource {

        @ClassName("appkit-ColorPanel")
        String colorPanel();

        @ClassName("appkit-ColorPanel-Toolbar")
        String colorPanelToolbar();

        @ClassName("appkit-ColorPanel-Toolbar-Btn")
        String colorPanelToolbarButton();

        @ClassName("appkit-state-active")
        String activeStateClass();

        @ClassName("appkit-ColorPanel-Toolbar-WheelBtn")
        String colorPanelToolbarWheelButton();

        @ClassName("appkit-ColorPanel-Toolbar-SliderBtn")
        String colorPanelToolbarSliderButton();

        @ClassName("appkit-ColorPanel-ColorPreview")
        String colorPreview();

        @ClassName("appkit-ColorPanel-OpacitySlider")
        String opacitySlider();

        @ClassName("appkit-ColorPanel-BrightnessSlider")
        String brightnessSlider();

        @ClassName("appkit-ColorPanel-ColorSlider")
        String colorSlider();

        @ClassName("appkit-ColorPanel-ColorTextField")
        String colorTextField();

        @ClassName("appkit-ColorPanel-HexTextField")
        String hexTextField();

        @ClassName("appkit-ColorPanel-ValuePanel")
        String valuePanel();

        @ClassName("appkit-ColorPanel-WheelPanel")
        String wheelPanel();


        @ClassName("appkit-ColorPanel-SliderPanel")
        String sliderPanel();


    }

    ColorPanelCss css();
}
