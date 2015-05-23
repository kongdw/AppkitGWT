package com.appkit.ui.client.application;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.RootPanel;


public class Application implements EntryPoint {

    private static ApplicationCss APPLICATION_CSS = GWT.create(ApplicationCss.class);

    @Override
    public void onModuleLoad() {
        Element spinner = DOM.getElementById("loading-spinner");

        if (spinner != null) {
            spinner.removeFromParent();
        }

        RootPanel.getBodyElement().setAttribute("role", "application");
    }


}
