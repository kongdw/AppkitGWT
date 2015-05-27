package datepicker.client;


import com.appkit.ui.client.widgets.input.date.CalendarUtil;
import com.appkit.ui.client.widgets.input.date.DatePicker;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.RootPanel;

import java.util.Date;

public class Application implements EntryPoint {

    @Override
    public void onModuleLoad() {

        DatePicker datePicker = new DatePicker();

        Date minDate = new Date();
        CalendarUtil.addDaysToDate(minDate, -60);

        datePicker.setMinimumDate(minDate);


        datePicker.setSize("150px", "28px");
        datePicker.getElement().getStyle().setMargin(30.0, Style.Unit.PX);
        RootPanel.get().add(datePicker);

    }
}
