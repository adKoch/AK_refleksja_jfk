package com.adKoch;

import com.adKoch.callable.IComparable;
import com.adKoch.callable.ILetterCounter;
import com.adKoch.callable.IMean;
import com.adKoch.callable.IRandomGenerator;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Invoker {

    private List<InvocationItem> invocationItems;

    public Invoker(File folder){
        invocationItems = new ArrayList<>();
        loadInvocationItems(folder);
    }

    public LinkedList<String> getMethodNames(){
        LinkedList<String> methodNameList = new LinkedList<>();
        for(InvocationItem item:invocationItems) {
            methodNameList.add(item.getClassName()+"."+item.getMethod().getName());
        }
        return  methodNameList;
    }

    public String invoke(int index, String arg1, String arg2){
        return invocationItems.get(index).invoke(arg1,arg2);
    }

    public int getParamCountForItem(int index){
        if(index<0 || null==invocationItems || null==invocationItems.get(index)) return 0;
        return invocationItems.get(index).getParameterCount();
    }

    private void loadInvocationItems(File folder) {
        for (File f : folder.listFiles()) {
            try {
                if (f.getName().endsWith(".jar")) loadFromJar(f);
                else if(f.getName().endsWith(".class")) loadFromClass(f);
            } catch (Exception e) {
            }
        }

    }

    private void loadFromJar(File file) throws Exception {

        JarFile jarFile = null;
        try {

            jarFile = new JarFile(file);
            Enumeration<JarEntry> entries = jarFile.entries();

            URL[] urls = {new URL("jar:file:" + file + "!/")};
            URLClassLoader cl = URLClassLoader.newInstance(urls);

            while (entries.hasMoreElements()) {
                JarEntry je = entries.nextElement();
                if (je.isDirectory() || !je.getName().endsWith(".class")) {
                    continue;
                }

                String className = je.getName().substring(0, je.getName().length() - 6);
                className = className.replace('/', '.');
                try {
                    Class<?> c = cl.loadClass(className);

                    assignClass(c);

                } catch (ClassNotFoundException exp) {
                    continue;
                }

            }
        } catch (IOException exp) {
        } finally {
            if (null != jarFile)
                jarFile.close();
        }
    }

    private void loadFromClass(File file) throws Exception{
        String path = file.getParent();
        String className = file.getName();
        className = className.substring(0,className.indexOf("."));

        file = new File(path);

        URL url = file.toURI().toURL();
        URL[] urls = new URL[]{url};

        ClassLoader cl = new URLClassLoader(urls);
        Class<?> c = cl.loadClass(className);

        assignClass(c);
    }

    private void assignClass(Class c) throws Exception{

        if (IComparable.class.isAssignableFrom(c)){
            IComparable comparable = (IComparable) c.getDeclaredConstructor().newInstance();
            if(null == comparable) throw new Exception();
            for(Method m:IComparable.class.getMethods()) {
                invocationItems.add(new InvocationItem(c,m,c.toString().substring(6)));
            }
        } else if (ILetterCounter.class.isAssignableFrom(c)){
            ILetterCounter letterCounter = (ILetterCounter) c.getDeclaredConstructor().newInstance();
            if(null == letterCounter) throw new Exception();
            for(Method m:ILetterCounter.class.getMethods()) {
                invocationItems.add(new InvocationItem(c,m,c.toString().substring(6)));
            }
        } else if (IMean.class.isAssignableFrom(c)){
            IMean mean = (IMean) c.getDeclaredConstructor().newInstance();
            if(null == mean) throw new Exception();
            for(Method m:IMean.class.getMethods()) {
                invocationItems.add(new InvocationItem(c,m,c.toString().substring(6)));
            }
        } else if (IRandomGenerator.class.isAssignableFrom(c)){
            IRandomGenerator randomGenerator = (IRandomGenerator) c.getDeclaredConstructor().newInstance();
            if(null == randomGenerator) throw new Exception();
            for(Method m:IRandomGenerator.class.getMethods()) {
                invocationItems.add(new InvocationItem(c,m,c.toString().substring(6)));
            }
        }

    }

}
