package com.vdoers.dynamiccontrolslibrary.mControls;

import java.util.HashMap;

public class DataObject extends HashMap<String, Object> /* extends  Properties*/ {


    public Object setProperty(String key, Object value) {
        return put(key, value);
    }

    public Object removeProperty(String key) {
        return remove(key);
    }


}
