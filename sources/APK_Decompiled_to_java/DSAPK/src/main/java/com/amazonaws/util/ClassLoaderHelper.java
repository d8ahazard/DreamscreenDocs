package com.amazonaws.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public enum ClassLoaderHelper {
    ;

    public static URL getResource(String resource, Class<?>... classes) {
        return getResource(resource, false, classes);
    }

    public static URL getResource(String resource, boolean classesFirst, Class<?>... classes) {
        URL url;
        if (classesFirst) {
            url = getResourceViaClasses(resource, classes);
            if (url == null) {
                url = getResourceViaContext(resource);
            }
        } else {
            url = getResourceViaContext(resource);
            if (url == null) {
                url = getResourceViaClasses(resource, classes);
            }
        }
        return url == null ? ClassLoaderHelper.class.getResource(resource) : url;
    }

    private static URL getResourceViaClasses(String resource, Class<?>[] classes) {
        if (classes != null) {
            for (Class<?> c : classes) {
                URL url = c.getResource(resource);
                if (url != null) {
                    return url;
                }
            }
        }
        return null;
    }

    private static URL getResourceViaContext(String resource) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if (loader == null) {
            return null;
        }
        return loader.getResource(resource);
    }

    private static Class<?> loadClassViaClasses(String fqcn, Class<?>[] classes) {
        if (classes != null) {
            int length = classes.length;
            int i = 0;
            while (i < length) {
                ClassLoader loader = classes[i].getClassLoader();
                if (loader != null) {
                    try {
                        return loader.loadClass(fqcn);
                    } catch (ClassNotFoundException e) {
                    }
                } else {
                    i++;
                }
            }
        }
        return null;
    }

    private static Class<?> loadClassViaContext(String fqcn) {
        Class<?> cls = null;
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if (loader == null) {
            return cls;
        }
        try {
            return loader.loadClass(fqcn);
        } catch (ClassNotFoundException e) {
            return cls;
        }
    }

    public static Class<?> loadClass(String fqcn, Class<?>... classes) throws ClassNotFoundException {
        return loadClass(fqcn, true, classes);
    }

    public static Class<?> loadClass(String fqcn, boolean classesFirst, Class<?>... classes) throws ClassNotFoundException {
        Class<?> target;
        if (classesFirst) {
            target = loadClassViaClasses(fqcn, classes);
            if (target == null) {
                target = loadClassViaContext(fqcn);
            }
        } else {
            target = loadClassViaContext(fqcn);
            if (target == null) {
                target = loadClassViaClasses(fqcn, classes);
            }
        }
        return target == null ? Class.forName(fqcn) : target;
    }

    public static InputStream getResourceAsStream(String resource, Class<?>... classes) {
        return getResourceAsStream(resource, false, classes);
    }

    public static InputStream getResourceAsStream(String resource, boolean classesFirst, Class<?>... classes) {
        InputStream inputStream = null;
        URL url = getResource(resource, classesFirst, classes);
        if (url == null) {
            return inputStream;
        }
        try {
            return url.openStream();
        } catch (IOException e) {
            return inputStream;
        }
    }
}
