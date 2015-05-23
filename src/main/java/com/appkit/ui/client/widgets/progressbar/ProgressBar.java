package com.appkit.ui.client.widgets.progressbar;


import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.SimplePanel;

public class ProgressBar extends Composite implements HasValueChangeHandlers<Double>, HasValue<Double> {

    private SimplePanel panel;
    private Element progressIndicator;
    private double value = 0.0;
    private double maxValue = 100.0;
    private double minValue = 0.0;

    private ProgressBarAppearance appearance;
    static ProgressBarAppearance DEFAULT_APPEARANCE = GWT.create(DefaultProgressBarAppearance.class);

    public ProgressBar(ProgressBarAppearance appearance) {
        this.appearance = appearance;
        render();
    }

    public ProgressBar() {
        this(DEFAULT_APPEARANCE);
    }


    private void render() {
        panel = new SimplePanel();
        initWidget(panel);

        panel.setStyleName(appearance.css().progressBarClass());
        panel.getElement().setAttribute("role", "progressbar");
        panel.getElement().setAttribute("aria-minvalue", "" + minValue);
        panel.getElement().setAttribute("aria-maxvalue", "" + maxValue);

        progressIndicator = DOM.createDiv();
        progressIndicator.addClassName(appearance.css().progressIndicatorClass());

        panel.getElement().appendChild(progressIndicator);

    }

    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Double> handler) {
        return addHandler(handler, ValueChangeEvent.getType());
    }

    @Override
    public Double getValue() {
        return value;
    }

    @Override
    public void setValue(Double value) {
        setValue(value, false);
    }

    public void setValue(double value) {
        setValue(value, false);
    }

    @Override
    public void setValue(Double value, boolean fireEvents) {

        this.value = Math.min(maxValue, Math.max(minValue, value.doubleValue()));
        panel.getElement().setAttribute("aria-valuenow", "" + this.value);

        final double p = (this.value - minValue) / (maxValue - minValue);

        Scheduler.ScheduledCommand command = new Scheduler.ScheduledCommand() {
            @Override
            public void execute() {
                double w = p * (panel.getOffsetWidth());
                progressIndicator.getStyle().setWidth(w, Style.Unit.PX);
            }
        };

        Scheduler.get().scheduleDeferred(command);

        if (p == 1.0) {
            progressIndicator.addClassName(appearance.css().progressIndicatorFinishClass());
        } else {
            progressIndicator.removeClassName(appearance.css().progressIndicatorFinishClass());

        }

        if (fireEvents) {
            ValueChangeEvent.fire(this, getValue());
        }

    }

    public void setStriped(boolean striped) {
        if (striped)
            progressIndicator.addClassName(appearance.css().progressIndicatorStripedClass());
        else
            progressIndicator.removeClassName(appearance.css().progressIndicatorStripedClass());

    }

    public boolean isStriped() {
        return progressIndicator.hasClassName(appearance.css().progressIndicatorStripedClass());
    }
}
