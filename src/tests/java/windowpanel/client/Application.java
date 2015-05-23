package windowpanel.client;


import com.appkit.ui.client.widgets.button.Button;
import com.appkit.ui.client.widgets.windowpanel.WindowPanel;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style;

public class Application implements EntryPoint {

    @Override
    public void onModuleLoad() {

        WindowPanel windowPanel = new WindowPanel();

        windowPanel.setFrameSize(600, 400);
        windowPanel.setResizable(true);
        windowPanel.setMaximizable(true);
        windowPanel.setMinimizable(true);

        Button m = new Button("Button");
        m.setButtonStyle(Button.ButtonStyle.PRIMARY);
        m.getElement().getStyle().setPosition(Style.Position.ABSOLUTE);
        m.getElement().getStyle().setRight(15.0, Style.Unit.PX);
        m.getElement().getStyle().setBottom(15.0, Style.Unit.PX);
        m.setSize("100px", "28px");

        windowPanel.add(m);


        windowPanel.setVisible(true);


    }
}
