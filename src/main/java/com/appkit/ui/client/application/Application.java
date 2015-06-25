package com.appkit.ui.client.application;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;


public class Application implements EntryPoint {

    private static ApplicationCss APPLICATION_CSS = GWT.create(ApplicationCss.class);

    @Override
    public void onModuleLoad() {

        Scheduler.ScheduledCommand command = new Scheduler.ScheduledCommand() {
            @Override
            public void execute() {

                Element spinner = DOM.getElementById("loading-spinner");

                if (spinner != null) {
                    spinner.removeFromParent();
                }
            }
        };

        Scheduler.get().scheduleDeferred(command);
    }


}
