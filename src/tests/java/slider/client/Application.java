package slider.client;


import com.appkit.ui.client.widgets.input.slider.Slider;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.RootPanel;

public class Application implements EntryPoint {

    @Override
    public void onModuleLoad() {

        Slider slider = new Slider();
        slider.setShowSliderValueTip(true);

        slider.setSize("140px", "5px");
        slider.getElement().getStyle().setMargin(30.0, Style.Unit.PX);

        RootPanel.get().add(slider);

        Slider vSlider = new Slider();
        vSlider.setShowSliderValueTip(true);
        vSlider.setVertical(true);

        vSlider.setSize("5px", "140px");
        vSlider.getElement().getStyle().setMargin(30.0, Style.Unit.PX);

        RootPanel.get().add(vSlider);


    }
}
