package datepicker.client;


import com.appkit.ui.client.widgets.input.date.DatePicker;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.RootPanel;

public class Application implements EntryPoint {

    @Override
    public void onModuleLoad() {

        DatePicker datePicker = new DatePicker();

        datePicker.setSize("150px", "28px");
        datePicker.getElement().getStyle().setMargin(30.0, Style.Unit.PX);
        RootPanel.get().add(datePicker);

    }
}
