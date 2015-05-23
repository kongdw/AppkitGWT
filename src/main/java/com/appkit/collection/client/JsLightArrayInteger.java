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

import com.appkit.collection.shared.LightArrayInt;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * An implementation of {@link LightArrayInt} that uses native js arrays
 *
 * @author Daniel Kurka
 */
public class JsLightArrayInteger implements LightArrayInt {

    private JavaScriptObject array;

    /**
     * Construct a {@link JsLightArrayInteger}
     */
    public JsLightArrayInteger() {
        this(JavaScriptObject.createArray());
    }

    /**
     * Construct a {@link JsLightArrayInteger} with a given js array
     *
     * @param array the array to use
     */
    public JsLightArrayInteger(JavaScriptObject array) {
        this.array = array;
    }

    @Override
    public native void push(int value)/*-{
        this.@com.appkit.collection.client.JsLightArrayInteger::array[this.@com.appkit.collection.client.JsLightArrayInteger::array.length] = value;
    }-*/;

    @Override
    public native int shift() /*-{
        return this.@com.appkit.collection.client.JsLightArrayInteger::array
            .shift();
    }-*/;

    @Override
    public native int get(int index) /*-{
        return this.@com.appkit.collection.client.JsLightArrayInteger::array[index];
    }-*/;

    @Override
    public native void set(int index, int value) /*-{
        this.@com.appkit.collection.client.JsLightArrayInteger::array[index] = value;
    }-*/;

    @Override
    public native int length()/*-{
        return this.@com.appkit.collection.client.JsLightArrayInteger::array.length;
    }-*/;

    @Override
    public native void unshift(int value)/*-{
        this.@com.appkit.collection.client.JsLightArrayInteger::array
            .unshift(value);
    }-*/;

    /**
     * get the underlying js array
     *
     * @return the underlying js array
     */
    public JavaScriptObject getArray() {
        return array;
    }

}
