package com.appkit.ui.client.widgets.input.text;

public class TextField extends AbstractTextField {

    public TextField(TextControlAppearance appearance) {
        super(appearance);
    }

    public TextField() {
        super();
    }


    public void setSecureField(boolean secureField) {
        if (secureField) {
            input.setAttribute("type", "password");
        } else {
            input.setAttribute("type", "text");
        }
    }

    public boolean isSecureField() {
        return (input.getAttribute("type") == "password");
    }
}
