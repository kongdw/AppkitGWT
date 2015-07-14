package com.appkit.ui.client.layouts.form;

import com.appkit.ui.client.events.action.ActionEvent;
import com.appkit.ui.client.events.action.ActionHandler;
import com.appkit.ui.client.events.action.HasActionHandlers;
import com.appkit.ui.client.layouts.flow.FlowLayoutPanel;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiConstructor;

public class FormLayoutPanel extends FlowLayoutPanel implements HasActionHandlers {

    @UiConstructor
    public FormLayoutPanel() {

        super();

        addDomHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                    ActionEvent.fire(FormLayoutPanel.this);
                }
            }
        }, KeyUpEvent.getType());


    }


    @Override
    public HandlerRegistration addActionHandler(ActionHandler handler) {
        return addHandler(handler, ActionEvent.getType());
    }
}
