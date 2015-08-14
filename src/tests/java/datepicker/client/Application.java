package datepicker.client;


import com.appkit.ui.client.widgets.input.date.CalendarUtil;
import com.appkit.ui.client.widgets.input.date.DatePicker;
import com.appkit.ui.client.widgets.input.text.SearchField;
import com.appkit.ui.client.widgets.windowpanel.WindowPanel;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style;

import java.util.Date;

public class Application implements EntryPoint {

    @Override
    public void onModuleLoad() {

        WindowPanel panel = new WindowPanel();
        panel.setFrameSize(300, 300);


        DatePicker datePicker = new DatePicker();

        Date minDate = new Date();
        CalendarUtil.addDaysToDate(minDate, -60);

        datePicker.setMinimumDate(minDate);

        Date maxDate = new Date();
        CalendarUtil.addDaysToDate(maxDate, 100);

        datePicker.setMaximumDate(maxDate);

        //datePicker.setDateFormat("yyyy/MM/dd");


        datePicker.setSize("150px", "28px");
        datePicker.getElement().getStyle().setMargin(30.0, Style.Unit.PX);


        panel.add(datePicker);

        SearchField textField = new SearchField();
        panel.add(textField);


        panel.open();

    }
}
