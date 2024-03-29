/*
 * Copyright 2012 Daniel Kurka
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.appkit.ui.client.events.recognizer.pinch;

import com.google.gwt.user.client.ui.UIObject;

/**
 * Brigde from widget / UIObject to Offsetprovider
 *
 * @author Daniel Kurka
 */
public class UIObjectToOffsetProvider implements OffsetProvider {

    private final UIObject uiObject;

    public UIObjectToOffsetProvider(UIObject uiObject) {
        this.uiObject = uiObject;
    }

    @Override
    public int getLeft() {
        return uiObject.getAbsoluteLeft();
    }

    @Override
    public int getTop() {
        return uiObject.getAbsoluteTop();
    }
}
