package button.client;

import com.appkit.ui.client.widgets.button.Button;
import com.appkit.ui.client.widgets.button.ImageButton;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import java.util.Iterator;


public class Application implements EntryPoint {


    @Override
    public void onModuleLoad() {

        VerticalPanel panel = new VerticalPanel();

        Button defaultBtn = new Button("Default");
        panel.add(defaultBtn);

        Button primaryBtn = new Button("Primary");
        primaryBtn.setButtonStyle(Button.ButtonStyle.PRIMARY);
        panel.add(primaryBtn);

        Button successBtn = new Button("Success");
        successBtn.setButtonStyle(Button.ButtonStyle.SUCCESS);
        panel.add(successBtn);

        Button warningBtn = new Button("Warning");
        warningBtn.setButtonStyle(Button.ButtonStyle.WARNING);
        panel.add(warningBtn);


        Button dangerBtn = new Button("Danger");
        dangerBtn.setButtonStyle(Button.ButtonStyle.DANGER);
        panel.add(dangerBtn);

        Button infoBtn = new Button("Info");
        infoBtn.setButtonStyle(Button.ButtonStyle.INFO);
        panel.add(infoBtn);

        Button inverseBtn = new Button("Inverse");
        inverseBtn.setButtonStyle(Button.ButtonStyle.INVERSE);
        panel.add(inverseBtn);

        Button linkBtn = new Button("Link");
        linkBtn.setButtonStyle(Button.ButtonStyle.LINK);
        panel.add(linkBtn);

        Button icon = new Button("Icon");
        icon.setIcon(new Image(Resources.INSTANCE.trashImage()));
        panel.add(icon);

        ImageButton imageBtn = new ImageButton();
        imageBtn.setIcon(new Image(Resources.INSTANCE.colorImage()));
        imageBtn.getIcon().setSize("22px", "22px");
        imageBtn.setSize("32px", "32px");
        panel.add(imageBtn);

        panel.setWidth("150px");
        panel.getElement().getStyle().setMargin(30.0, Style.Unit.PX);

        Iterator<Widget> it = panel.iterator();

        while (it.hasNext()) {
            Widget w = it.next();
            w.getElement().getStyle().setMarginBottom(10.0, Style.Unit.PX);
        }


        RootPanel.get().add(panel);

    }
}
