package test.client;


import com.appkit.ui.client.widgets.input.date.DatePicker;
import com.appkit.ui.client.widgets.windowpanel.WindowPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;


public class CustomDialogController {

    interface CustomDialogUiBinder extends UiBinder<WindowPanel, CustomDialogController> {
    }

    private static CustomDialogUiBinder uiBinder = GWT.create(CustomDialogUiBinder.class);

    public CustomDialogController() {

        uiBinder.createAndBindUi(this);

    }

    @UiField
    WindowPanel windowPanel;

    @UiField
    DatePicker datePicker;

    public void showWindow() {
        windowPanel.center();
        windowPanel.setVisible(true);
    }

}
