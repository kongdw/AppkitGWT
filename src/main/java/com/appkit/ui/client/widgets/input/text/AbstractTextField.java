package com.appkit.ui.client.widgets.input.text;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasValue;

public abstract class AbstractTextField extends AbstractTextControl
        implements HasValue<String> {

    public AbstractTextField(TextControlAppearance appearance) {
        super(appearance);
    }

    public AbstractTextField() {
        super();
    }


    @Override
    protected void render() {

        super.render();
        getElement().addClassName("appkit-textfield");

    }

    @Override
    public void setValue(String value) {

        input.setValue(value);
    }

    @Override
    public void setValue(String value, boolean fireEvents) {

        final String oldValue = getValue();

        setValue(value);

        if (fireEvents) {

            final AbstractTextField that = this;
            Scheduler.ScheduledCommand command = new Scheduler.ScheduledCommand() {
                @Override
                public void execute() {
                    if (!oldValue.equals(getValue())) {
                        ValueChangeEvent.fire(that, getValue());
                    }
                }
            };

            Scheduler.get().scheduleDeferred(command);

        }
    }

    public String getValue() {
        return input.getValue();
    }

    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {
        return addHandler(handler, ValueChangeEvent.getType());
    }

    @Override
    protected void fireValueChanged() {
        ValueChangeEvent.fire(this, getValue());
    }


}
