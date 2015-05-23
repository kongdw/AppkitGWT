package com.appkit.ui.client.widgets.progressbar;


import com.google.gwt.resources.client.CssResource;

public interface ProgressBarAppearance {


    public interface ProgressBarCss extends CssResource {

        @ClassName("appkit-ProgressBar")
        String progressBarClass();

        @ClassName("appkit-ProgressIndicator")
        String progressIndicatorClass();

        @ClassName("appkit-ProgressIndicator-Striped")
        String progressIndicatorStripedClass();

        @ClassName("appkit-ProgressIndicator-Finish")
        String progressIndicatorFinishClass();


    }

    ProgressBarCss css();


}
