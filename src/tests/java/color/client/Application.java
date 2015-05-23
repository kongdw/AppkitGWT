package color.client;


import com.appkit.ui.client.widgets.input.color.ColorWell;
import com.appkit.ui.client.widgets.input.color.colorpanel.ColorPanel;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.RootPanel;

public class Application implements EntryPoint {

    @Override
    public void onModuleLoad() {

        ColorWell colorWell = new ColorWell();

        ColorPanel.get().setPosition(200, 100);

        colorWell.setPixelSize(100, 32);
        colorWell.getElement().getStyle().setMargin(30.0, Style.Unit.PX);

        RootPanel.get().add(colorWell);

    }
}
