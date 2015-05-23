package com.appkit.ui.client.layout.splitlayout;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.ui.Widget;

public class SplitLayoutPanel extends SplitLayoutPanelBase {


    @UiConstructor
    public SplitLayoutPanel() {
        super(11);

    }

    @Override
    public void addEast(Widget widget, double size) {
        super.addEast(widget, size);

        final Widget east = widget;

        Scheduler.ScheduledCommand command = new Scheduler.ScheduledCommand() {
            @Override
            public void execute() {
                Widget splitter = getAssociatedSplitter(east);

                if (splitter != null) {
                    splitter.getElement().addClassName(getAppearance().css().splitLayoutEastPanelClass());

                }
            }
        };

        Scheduler.get().scheduleDeferred(command);
    }

    @Override
    public void addWest(Widget widget, double size) {
        super.addWest(widget, size);

        final Widget west = widget;

        Scheduler.ScheduledCommand command = new Scheduler.ScheduledCommand() {
            @Override
            public void execute() {
                Widget splitter = getAssociatedSplitter(west);
                if (splitter != null) {
                    splitter.getElement().addClassName(getAppearance().css().splitLayoutWestPanelClass());

                }

            }
        };

        Scheduler.get().scheduleDeferred(command);
    }

    @Override
    public void addNorth(Widget widget, double size) {

        super.addNorth(widget, size);

        final Widget north = widget;

        Scheduler.ScheduledCommand command = new Scheduler.ScheduledCommand() {
            @Override
            public void execute() {
                Widget splitter = getAssociatedSplitter(north);
                if (splitter != null) {
                    splitter.getElement().addClassName(getAppearance().css().splitLayoutNorthPanelClass());
                }
            }
        };

        Scheduler.get().scheduleDeferred(command);
    }

    @Override
    public void addSouth(Widget widget, double size) {

        super.addSouth(widget, size);

        final Widget south = widget;

        Scheduler.ScheduledCommand command = new Scheduler.ScheduledCommand() {
            @Override
            public void execute() {
                Widget splitter = getAssociatedSplitter(south);
                if (splitter != null) {
                    splitter.getElement().addClassName(getAppearance().css().splitLayoutSouthPanelClass());
                }

            }
        };

        Scheduler.get().scheduleDeferred(command);
    }

}
