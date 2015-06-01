package alert.client;


import com.appkit.ui.client.widgets.alert.Alert;
import com.appkit.ui.client.widgets.alert.AlertCallback;
import com.appkit.ui.client.widgets.button.Button;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.RootPanel;

public class Application implements EntryPoint {

    @Override
    public void onModuleLoad() {

        Button b = new Button("Show Alert");
        b.setButtonStyle(Button.ButtonStyle.PRIMARY);

        b.setPixelSize(100, 28);
        b.getElement().getStyle().setMargin(25.0, Style.Unit.PX);

        b.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                Alert.success("Test", "Test this alert.", new AlertCallback() {
                    @Override
                    public void onConfirm() {
                        GWT.log("confirmed");
                    }

                    @Override
                    public void onCancel() {
                        GWT.log("calling on cancel");
                    }
                });
            }
        });

        Alert.show("Test", "Test this alert.", new AlertCallback() {
            @Override
            public void onConfirm() {
                GWT.log("confirmed");
            }

            @Override
            public void onCancel() {
                GWT.log("calling on cancel");
            }
        });

        Alert.confirmYES_NO("Test", "Test this alert.", new AlertCallback() {
            @Override
            public void onConfirm() {
                GWT.log("confirmed");
            }

            @Override
            public void onCancel() {
                GWT.log("calling on cancel");
            }
        });


        RootPanel.get().add(b);

    }
}
