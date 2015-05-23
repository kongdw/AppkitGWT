/*
 * Copyright 2010 Daniel Kurka
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
package com.appkit.collection.client;

import com.appkit.collection.shared.LightArrayBoolean;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * An implementation of {@link LightArrayBoolean} that uses native js arrays
 *
 * @author Daniel Kurka
 */
public class JsLightArrayBoolean implements LightArrayBoolean {

    private JavaScriptObject array;

    /**
     * Construct a {@link JsLightArrayBoolean}
     */
    public JsLightArrayBoolean() {
        this(JavaScriptObject.createArray());
    }

    /**
     * Construct a {@link JsLightArrayBoolean} with a given js array
     *
     * @param array the js array to use
     */
    public JsLightArrayBoolean(JavaScriptObject array) {
        this.array = array;
    }

    @Override
    public native void push(boolean value)/*-{
        this.@com.appkit.collection.client.JsLightArrayBoolean::array[this.@com.appkit.collection.client.JsLightArrayBoolean::array.length] = value;
    }-*/;

    @Override
    public native boolean shift() /*-{
        return this.@com.appkit.collection.client.JsLightArrayBoolean::array
            .shift();
    }-*/;

    @Override
    public native boolean get(int index) /*-{
        return this.@com.appkit.collection.client.JsLightArrayBoolean::array[index];
    }-*/;

    @Override
    public native void set(int index, boolean value) /*-{
        this.@com.appkit.collection.client.JsLightArrayBoolean::array[index] = value;
    }-*/;

    @Override
    public native int length()/*-{
        return this.@com.appkit.collection.client.JsLightArrayBoolean::array.length;
    }-*/;

    @Override
    public native void unshift(boolean value)/*-{
        this.@com.appkit.collection.client.JsLightArrayBoolean::array
            .unshift(value);
    }-*/;

    /**
     * get the underlying js array
     *
     * @return the js array
     */
    public JavaScriptObject getArray() {
        return array;
    }

}
