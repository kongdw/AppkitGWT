package test.client;

import com.appkit.ui.client.layout.flow.FlowLayoutPanel;
import com.appkit.ui.client.widgets.progressbar.ProgressBar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.ResizeComposite;


public class MainWindow extends ResizeComposite {
    interface MainWindowUiBinder extends UiBinder<FlowLayoutPanel, MainWindow> {
    }

    private static MainWindowUiBinder ourUiBinder = GWT.create(MainWindowUiBinder.class);

    public MainWindow() {
        initWidget(ourUiBinder.createAndBindUi(this));

        Timer t = new Timer() {
            @Override
            public void run() {
                double value = progressBar.getValue();
                value += 1.0;
                progressBar.setValue(value);
            }
        };

        t.scheduleRepeating(50);
    }

    @UiField
    ProgressBar progressBar;


}