package com.appkit.ui.client.widgets.input.radio;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class RadioGroup {

    protected static final Map<String, RadioGroup> RADIO_GROUPS = new HashMap<String, RadioGroup>();

    private Set<RadioButton> buttons = new HashSet<RadioButton>();
    private String groupName = null;

    public static RadioGroup get(String groupName) {
        return RADIO_GROUPS.get(groupName);
    }

    public RadioGroup(String name) {
        setName(name);
    }

    public void setName(String gn) {
        groupName = gn;
    }

    public String getName() {
        return groupName;
    }

    public RadioButton getSelected() {
        for (RadioButton rb : buttons) {
            if (rb.getValue() == RadioButton.State.ON) {
                return rb;
            }
        }

        return null;
    }

    public void unselectAll() {

        for (RadioButton rb : buttons) {
            rb.setValue(RadioButton.State.OFF);
        }
    }

    public void add(RadioButton rb) {

        if (rb != null) {
            rb.setRadioGroup(this);
            buttons.add(rb);
        }
    }

    public boolean contains(RadioButton rb) {
        return buttons.contains(rb);
    }

    public void clear() {
        buttons.clear();
    }


    public boolean remove(RadioButton w) {
        if (w != null) {
            return buttons.remove(w);
        }

        return false;
    }
}
