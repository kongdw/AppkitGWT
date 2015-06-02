package com.appkit.ui.client.layouts.tabpanel;


import com.appkit.ui.client.widgets.button.Button;

public class TabBarScrollButton extends Button {


    public TabBarScrollButton() {

        setHeight("31px");
        setVisible(false);
        setCanFocus(false);

        getElement().addClassName("appkit-tabpanel-scrollbutton");
    }
}
