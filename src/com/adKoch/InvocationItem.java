package com.adKoch;

import java.lang.reflect.Method;

public class InvocationItem {

    private Method method;
    private String className;

    public InvocationItem(Method method, String className) {
        this.method = method;
        this.className = className;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
