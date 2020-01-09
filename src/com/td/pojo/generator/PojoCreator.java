/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.td.pojo.generator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.WordUtils;

/**
 *
 * @author vc186009
 */
public class PojoCreator {

    private Properties dataTypeProp = new Properties();
    private Map<String, String> fieldsNamesDataTypeMap;
    private List<String> headers;

    public PojoCreator(Map<String, String> fieldsNamesDataTypeMap, List<String> headers) {
        this.fieldsNamesDataTypeMap = fieldsNamesDataTypeMap;
        this.headers = headers;
        try (InputStream in = getClass().getResourceAsStream("/resources/DataType.properties")) {
            dataTypeProp.load(in);
        } catch (IOException ex) {
            Logger.getLogger(PojoCreator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Class createClass(String className) throws ClassNotFoundException {
        Class clazz = null;
        String classSource = "";
        try {

            classSource = System.getProperty("user.home") + File.separator + "classes" + File.separator + className + ".java";
            if (!(new File(System.getProperty("user.home") + File.separator + "classes").exists())) {
                new File(System.getProperty("user.home") + File.separator + "classes").mkdir();
            }
            File classSourceFile = new File(classSource);
            if (classSourceFile.exists()) {
                classSourceFile.delete();
            }

            try (FileWriter classWriter = new FileWriter(classSource, true)) {
                classWriter.write("import java.util.Date;\n");
                classWriter.write("import java.util.List;\n");
                classWriter.write("import java.util.ArrayList;\n");
                classWriter.write("\n");
                classWriter.write("public class " + className + "{\n");
                classWriter.write(" public void doit() {\n");
                classWriter.write(" System.out.println(\"" + System.currentTimeMillis() + "\");\n");
                classWriter.write(" }\n");
                for (Map.Entry<String, String> entrySet : fieldsNamesDataTypeMap.entrySet()) {
                    String fieldName = entrySet.getKey();
                    String value = entrySet.getValue();

                    classWriter.write(" private " + dataTypeProp.getProperty(value) + " " + fieldName + ";\n");
                    classWriter.write(" public " + dataTypeProp.getProperty(value) + " get" + WordUtils.capitalize(fieldName) + "() {\n");
                    classWriter.write(" return " + fieldName + ";\n");
                    classWriter.write(" }\n");
                    classWriter.write(" public void set" + WordUtils.capitalize(fieldName) + "(" + dataTypeProp.getProperty(value) + " " + fieldName + ") {\n");
                    classWriter.write(" this." + fieldName + " = " + fieldName + ";\n");
                    classWriter.write(" }\n");
                }
                classWriter.write("@Override \n");
                classWriter.write("public String toString() {\n");
//            aWriter.write("StringBuffer sb = new StringBuffer();\n");
                classWriter.write("List<String> pojoString = new ArrayList<>();\n");
//            aWriter.write("sb.append(\"[\");\n");
                for (String header : headers) {
                    classWriter.write("pojoString.add(this." + header + "!= null ? this." + header + ".toString().trim():\"\");\n");
                }
                classWriter.write("return pojoString.toString();\n");
                classWriter.write(" }\n");

                classWriter.write(" }\n");
                classWriter.flush();
            }
        } catch (IOException ex) {
            Logger.getLogger(PojoCreator.class.getName()).log(Level.SEVERE, null, ex);
        }

        boolean isCompile = compileIt(classSource);

        if (isCompile) {
            File file = new File(System.getProperty("user.home") + File.separator + "classes");
            try {
                // Convert File to a URL
                URL url = file.toPath().toUri().toURL();          // file:/c:/myclasses/
                URL[] urls = new URL[]{url};

                // Create a new class loader with the directory
                ClassLoader cl = new URLClassLoader(urls);

                // Load in the class; MyClass.class should be located in
                // the directory file:/c:/myclasses/com/mycompany
                clazz = cl.loadClass(className);
            } catch (MalformedURLException e) {
            }
//            return Class.forName(className);
        }
        return clazz;
    }

    private boolean compileIt(String classSource) {
        String[] source = {classSource};
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        com.sun.tools.javac.Main.compile(source);
        // if using JDK >= 1.3 then use
        //   public static int com.sun.tools.javac.Main.compile(source);    
        return (baos.toString().indexOf("error") == -1);
    }

    public void runClass(Class clazz) {

    }
}
