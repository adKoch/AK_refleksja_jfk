package com.adKoch;

import java.lang.reflect.Method;

public class InvocationItem {

    private Method method;
    private String className;
    private Class ofClass;

    public InvocationItem(Class c, Method method, String className) {
        this.ofClass=c;
        this.method = method;
        this.className = className;
    }

    public int getParameterCount(){
        return method.getParameterCount();
    }

    public String invoke(String arg1, String arg2){
        String decimalPattern = "([0-9]*)(\\.)?([0-9]*)";
        String integerPattern = "([0-9]*)";



        String result = null;
        Object[] types = method.getParameterTypes();
        Object[] params = new Object[2];
        try{
            if(types.length==0){
                result = method.invoke(ofClass.getDeclaredConstructor().newInstance()).toString();
            }
            if (types.length>0){
                if(types[0]==int.class && arg1.matches(integerPattern)){
                    try {
                        int i = Integer.parseInt(arg1);
                        params[0] =  i;
                    }catch (Exception e){

                    }
                } else if(types[0]==double.class && arg1.matches(decimalPattern)){
                    try {
                        double d = Double.parseDouble(arg1);
                        params[0] = d;
                    }catch (Exception e){

                    }
                } else if(types[0]==char.class){
                    params[0] = arg1.charAt(0);
                } else if(types[0]==String.class){
                    params[0] = arg1;
                } else {
                    return "Type mismatch";
                }

                if(types[0]!=double.class && types[0]!=int.class){
                    if(types[0]!=params[0].getClass()) return "Type mismatch ";

                } else if(types[0]==double.class && params[0].getClass() != Double.class){
                    return "Type mismatch ";
                } else if(types[0]==int.class && params[0].getClass() != Integer.class){
                    return "Type mismatch ";
                }

                if(types.length==1) result = method.invoke(ofClass.getDeclaredConstructor().newInstance(),params[0]).toString();
            }
            if (types.length>1){
                if(types[1]==int.class && arg2.matches(integerPattern)){
                    params[1] = (int) Integer.parseInt(arg2);
                } else if(types[1]==double.class && arg2.matches(decimalPattern)){
                    params[1] = (double) Double.parseDouble(arg2);
                } else if(types[1]==char.class){
                    params[1] = arg2.charAt(0);
                } else if(types[1]==String.class){
                    params[1] = arg2;
                } else {
                    return "Type mismatch";
                }

                if(types[1]!=double.class && types[1]!=int.class){
                    if(types[1]!=params[1].getClass()) return "Type mismatch ";

                } else if(types[1]==double.class && params[1].getClass() != Double.class){
                    return "Type mismatch ";
                } else if(types[1]==int.class && params[1].getClass() != Integer.class){
                    return "Type mismatch ";
                }

                if(types.length==2) result = method.invoke(ofClass.getDeclaredConstructor().newInstance(),params).toString();
            }
            if(null==result) result="";
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
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
