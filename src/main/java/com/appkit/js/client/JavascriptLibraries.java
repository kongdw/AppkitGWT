package com.appkit.js.client;


import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;


public class JavascriptLibraries {
    public static JavascriptLibraries INSTANCE = GWT.create(JavascriptLibraries.class);

    private boolean jquery = false;
    private boolean maskedInput = false;

    public interface Resources extends ClientBundle {

        Resources INSTANCE = GWT.create(Resources.class);

        @Source("jquery.min.js")
        TextResource jquery();

        @Source("maskedinput.min.js")
        TextResource maskedInput();
    }

    public void useJquery() {

        if (!jquery) {
            ScriptInjector.fromString(Resources.INSTANCE.jquery().getText())
                    .setWindow(ScriptInjector.TOP_WINDOW)
                    .inject();

            jquery = true;
        }
    }

    public void useMaskedInput() {

        if (!maskedInput) {
            useJquery();

            ScriptInjector.fromString(Resources.INSTANCE.maskedInput().getText())
                    .setWindow(ScriptInjector.TOP_WINDOW)
                    .inject();

            maskedInput = true;
        }

    }

}


