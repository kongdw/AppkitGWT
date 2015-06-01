package com.appkit.ui.client.widgets.alert;

import com.appkit.ui.client.widgets.button.Button;
import com.appkit.ui.client.widgets.windowpanel.WindowPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;

public class Alert extends WindowPanel {

    public enum IconStyle {ALERT, ERROR, OK, QUESTION}

    private Button confirmBtn;
    private Button cancelBtn;
    private HTML content;
    private HTML icon;

    private AlertCallback callback;

    private AlertAppearance appearance;
    static AlertAppearance DEFAULT_APPERARANCE = GWT.create(DefaultAlertAppearance.class);

    public Alert(AlertAppearance appearance) {
        this.appearance = appearance;
        render();
    }

    public Alert() {
        this(DEFAULT_APPERARANCE);
    }

    private void render() {
        setVisible(false);
        setModal(true);
        setFrameSize(400, 170);
        getElement().addClassName(appearance.css().alertClass());

        FlowPanel buttonPane = new FlowPanel();
        buttonPane.setStyleName(appearance.css().alertButtonPanelClass());

        FlowPanel p = new FlowPanel();
        p.getElement().getStyle().setFloat(Style.Float.RIGHT);


        cancelBtn = new Button("Cancel");
        cancelBtn.addStyleName(appearance.css().alertCancelButtonClass());
        cancelBtn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                close(false);
                callback.onCancel();
            }
        });

        p.add(cancelBtn);

        confirmBtn = new Button("OK");
        confirmBtn.setButtonStyle(Button.ButtonStyle.PRIMARY);
        confirmBtn.addStyleName(appearance.css().alertConfirmButtonClass());
        confirmBtn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                close(false);
                callback.onConfirm();
            }
        });

        p.add(confirmBtn);

        buttonPane.add(p);

        add(buttonPane);

        addCloseHandler(new CloseHandler<WindowPanel>() {
            @Override
            public void onClose(CloseEvent<WindowPanel> event) {
                callback.onCancel();
            }
        });

        content = new HTML();
        content.setStyleName(appearance.css().alertContentClass());

        add(content);

        icon = new HTML();
        icon.setStyleName(appearance.css().alertIconClass());

        add(icon);
    }

    public void setCallback(AlertCallback callback) {
        this.callback = callback;
    }

    public AlertCallback getCallback() {
        return callback;
    }

    public void setContent(String content) {
        this.content.setHTML(content);
    }

    public String getContent() {
        return this.content.getText();
    }

    public void setIconStyle(IconStyle style) {

        icon.removeStyleName(appearance.css().alertIconStyleClass());
        icon.removeStyleName(appearance.css().questionIconStyleClass());
        icon.removeStyleName(appearance.css().okIconStyleClass());
        icon.removeStyleName(appearance.css().errorIconStyleClass());

        switch (style) {
            case ALERT: {
                icon.addStyleName(appearance.css().alertIconStyleClass());
            }
            break;
            case OK: {
                icon.addStyleName(appearance.css().okIconStyleClass());
            }
            break;
            case QUESTION: {
                icon.addStyleName(appearance.css().questionIconStyleClass());
            }
            break;
            case ERROR: {
                icon.addStyleName(appearance.css().errorIconStyleClass());
            }
            break;
        }
    }

    public void setHasCancelButton(boolean hasCancel) {
        cancelBtn.setVisible(hasCancel);
    }

    public boolean hasCancelButton() {
        return cancelBtn.isVisible();
    }

    public void setConfirmText(String confirmText) {
        confirmBtn.setText(confirmText);
    }

    public String getConfirmText() {
        return confirmBtn.getText();
    }

    public void setCancelText(String cancelText) {
        cancelBtn.setText(cancelText);
    }

    public String getCancelText() {
        return cancelBtn.getText();
    }

    public static Alert show(String title, String content, AlertCallback callback) {

        Alert alert = new Alert();
        alert.center();
        alert.setHasCancelButton(false);
        alert.setTitle(title);
        alert.setContent(content);
        alert.setIconStyle(IconStyle.ALERT);

        alert.setCallback(callback);
        alert.open();

        return alert;

    }

    public static Alert confirm(String title, String content, AlertCallback callback) {

        Alert alert = new Alert();
        alert.center();
        alert.setTitle(title);
        alert.setContent(content);
        alert.setIconStyle(IconStyle.QUESTION);

        alert.setCallback(callback);
        alert.open();

        return alert;

    }

    public static Alert error(String title, String content, AlertCallback callback) {

        Alert alert = new Alert();
        alert.center();
        alert.setHasCancelButton(false);
        alert.setTitle(title);
        alert.setContent(content);
        alert.setIconStyle(IconStyle.ERROR);

        alert.setCallback(callback);
        alert.open();

        return alert;

    }

    public static Alert confirmYES_NO(String title, String content, AlertCallback callback) {

        Alert alert = new Alert();
        alert.center();

        alert.setConfirmText("YES");
        alert.setCancelText("NO");
        alert.setTitle(title);
        alert.setContent(content);
        alert.setIconStyle(IconStyle.QUESTION);

        alert.setCallback(callback);
        alert.open();

        return alert;

    }

    public static Alert success(String title, String content, AlertCallback callback) {

        Alert alert = new Alert();
        alert.center();

        alert.setHasCancelButton(false);
        alert.setTitle(title);
        alert.setContent(content);
        alert.setIconStyle(IconStyle.OK);

        alert.setCallback(callback);
        alert.open();

        return alert;

    }


}
