package com.appkit.ui.client.widgets.input.text;


import com.appkit.js.client.JavascriptLibraries;
import com.appkit.ui.client.widgets.Control;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.editor.client.IsEditor;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.editor.ui.client.adapters.HasTextEditor;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.ui.HasText;


public abstract class AbstractTextControl extends Control implements HasText,
        IsEditor<LeafValueEditor<String>> {

    protected InputElement input;
    private LeafValueEditor<String> editor;

    private TextControlAppearance appearance;
    static TextControlAppearance DEFAULT_APPEARANCE = GWT.create(DefaultTextControlAppearance.class);

    static {
        JavascriptLibraries.INSTANCE.useMaskedInput();
    }

    public AbstractTextControl(TextControlAppearance appearance) {
        super(DOM.createDiv());
        this.appearance = appearance;
        render();
    }

    public AbstractTextControl() {
        this(DEFAULT_APPEARANCE);
    }

    protected void render() {

        getElement().setClassName(appearance.css().textControlClass());
        getElement().setAttribute("role", "textbox");

        input = InputElement.as(DOM.createInputText());
        input.setClassName(appearance.css().textControlInputClass());

        addNativeDOMHandler(input);

        getElement().appendChild(input);

        DOM.sinkEvents(input, Event.ONKEYDOWN | Event.ONKEYUP |
                Event.ONPASTE | Event.ONFOCUS | Event.ONBLUR);
        DOM.setEventListener(input, new EventListener() {
            @Override
            public void onBrowserEvent(Event event) {
                if (event.getTypeInt() == Event.ONFOCUS) {
                    input.addClassName(appearance.css().activeClass());
                    FocusEvent.fireNativeEvent(event, AbstractTextControl.this);
                } else if (event.getTypeInt() == Event.ONBLUR) {
                    input.removeClassName(appearance.css().activeClass());
                    BlurEvent.fireNativeEvent(event, AbstractTextControl.this);
                } else if (event.getTypeInt() == Event.ONPASTE) {

                    final Object oldValue = input.getValue();

                    Scheduler.ScheduledCommand command = new Scheduler.ScheduledCommand() {
                        @Override
                        public void execute() {
                            if (!oldValue.equals(input.getValue())) {
                                fireValueChanged();
                            }
                        }
                    };

                    Scheduler.get().scheduleDeferred(command);
                }

            }
        });

    }

    protected abstract void fireValueChanged();

    public TextControlAppearance getAppearance() {
        return appearance;
    }

    public void setMaxLength(int maxLength) {
        input.setMaxLength(maxLength);
    }

    public int getMaxLength() {
        return input.getMaxLength();
    }

    public void setName(String name) {
        input.setName(name);
    }

    public String getName() {
        return input.getName();
    }

    public void setReadOnly(boolean readOnly) {

        input.setReadOnly(readOnly);
        getElement().setAttribute("aria-readonly", readOnly ? "true" : "false");
    }

    public boolean isReadOnly() {
        return input.isReadOnly();
    }

    @Override
    public void setFocus(boolean focus) {
        if (focus) {
            input.focus();
        } else {
            input.blur();
        }
    }

    public void setText(String text) {
        input.setValue(text);
    }

    public String getText() {
        return input.getValue();
    }

    public void setPlaceholder(String placeholder) {
        input.setAttribute("placeholder", placeholder);
    }

    public String getPlaceholder() {
        return input.getAttribute("placeholder");
    }

    public int getCursorPos() {
        return getSelectionStart(input);
    }

    public void setCursorPos(int pos) {
        setSelectionRange(input, pos, 0);
    }

    public String getSelectedText() {
        int start = getCursorPos();
        if (start < 0) {
            return "";
        }
        int length = getSelectionLength(input);
        return getText().substring(start, start + length);
    }

    public void setSelectionRange(int start, int end) {
        setSelectionRange(input, start, end);
    }

    public void setMask(String mask) {
        if (mask != null) {
            setMask(input, mask);
        } else {
            removeMask(input);
        }
    }

    private native int getSelectionLength(InputElement elem) /*-{
        // Guard needed for FireFox.
        try {
            return elem.selectionEnd - elem.selectionStart;
        } catch (e) {
            return 0;
        }
    }-*/;

    private native int getSelectionStart(InputElement input) /*-{
        try {
            return input.selectionStart;
        } catch (e) {
            return 0;
        }
    }-*/;

    private native int getSelectionEnd(InputElement input) /*-{
        return input.selectionEnd;

    }-*/;

    private native int setSelectionRange(InputElement input, int start, int end) /*-{
        input.setSelectionRange(start, end);
    }-*/;

    private native void setMask(InputElement elem, String mask)/*-{
        $wnd.jQuery(elem).mask(mask);
    }-*/;

    private native void removeMask(InputElement elem)/*-{
        $wnd.jQuery(elem).unmask();
    }-*/;


    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);

        if (!this.isEnabled()) {
            getElement().addClassName(appearance.css().disabledClass());
        } else {
            getElement().removeClassName(appearance.css().disabledClass());
        }

        input.setDisabled(!enabled);
    }

    @Override
    public void setTitle(String title) {
        getElement().setTitle(title);
    }

    @Override
    public String getTitle() {
        return getElement().getTitle();
    }

    @Override
    public void setTabIndex(int tabIndex) { /** do nothing **/}

    private native void addNativeDOMHandler(Element elementID)
    /*-{
        var temp = this;  // hack to hold on to 'this' reference
        elementID.oncut = function (e) {
            temp.@com.appkit.ui.client.widgets.input.text.AbstractTextControl::handleChange()();
        };

        elementID.oninput = function (e) {
            temp.@com.appkit.ui.client.widgets.input.text.AbstractTextControl::handleChange()();
        };

    }-*/;

    private void handleChange() {

        Scheduler.ScheduledCommand command = new Scheduler.ScheduledCommand() {
            @Override
            public void execute() {
                fireValueChanged();
            }
        };

        Scheduler.get().scheduleDeferred(command);
    }

    @Override
    public LeafValueEditor<String> asEditor() {
        if (editor == null) {
            editor = HasTextEditor.of(this);
        }
        return editor;
    }
}
