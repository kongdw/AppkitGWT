package stepper.client;


import com.appkit.ui.client.widgets.input.stepper.Stepper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.RootPanel;

public class Application implements EntryPoint {

    @Override
    public void onModuleLoad() {

        Stepper stepper = new Stepper();
        stepper.setSize("100px", "0px");

        stepper.setMinValue(10);
        stepper.setMaxValue(50);
        stepper.setStep(5);


        stepper.getElement().getStyle().setMargin(30.0, Style.Unit.PX);


        RootPanel.get().add(stepper);

    }
}
