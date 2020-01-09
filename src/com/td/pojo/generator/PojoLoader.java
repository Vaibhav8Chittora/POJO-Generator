/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.td.pojo.generator;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
public class PojoLoader {

    private Properties dataTypeProp = new Properties();
    private Map<String, String> fieldsNamesDataTypeMap;

    public PojoLoader(Map<String, String> fieldsNamesDataTypeMap) {
        this.fieldsNamesDataTypeMap = fieldsNamesDataTypeMap;
        try (InputStream in = getClass().getResourceAsStream("/resources/DataType.properties")) {
            dataTypeProp.load(in);
        } catch (IOException ex) {
            Logger.getLogger(PojoCreator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param clazz - Table Name for which dynamic class must be created.
     * @param feildsValues - Array of fields values
     * @param fieldsNames - Fields Names in order of fields values. Starting from 0 i.e first column and so on.
     * @param fieldDateFormatMap - Format of date per field, if applicable. Key as field position (Starting from 0 i.e first column and so on.)
     * @return <code>List</code> List of loaded objects;data set
     */
    public List<Object> loadPojo(Class clazz, List<String[]> feildsValues, List<String> fieldsNames, Map<Integer, String> fieldDateFormatMap) {
        List<Object> pojoList = new ArrayList<>();
        for (Iterator<String[]> iterator = feildsValues.iterator(); iterator.hasNext();) {
            try {
                String[] feildsValuesArr = iterator.next();
                Object iClass = clazz.newInstance();
                for (int i = 0; i < fieldsNames.size(); i++) {
                    String fieldName = fieldsNames.get(i);
                    String dataType = (String) dataTypeProp.get(fieldsNamesDataTypeMap.get(fieldName));
                    Method setM = null;

                    switch (dataType) {
                        case "String":
                            setM = iClass.getClass().getMethod("set" + WordUtils.capitalize(fieldName), String.class);
                            if (feildsValuesArr[i].isEmpty()) {
                                setM.invoke((Object) iClass, (Object[]) new String[]{null});
                            } else {
                                setM.invoke((Object) iClass, feildsValuesArr[i]);
                            }
                            break;
                        case "Long":
                            setM = iClass.getClass().getMethod("set" + WordUtils.capitalize(fieldName), Long.class);
                            if (feildsValuesArr[i].trim().isEmpty()) {
                                setM.invoke((Object) iClass, (Object[]) new Long[]{null});
                            } else {
                                setM.invoke((Object) iClass, Long.parseLong(feildsValuesArr[i]));
                            }

                            break;
                        case "Date":
                            setM = iClass.getClass().getMethod("set" + WordUtils.capitalize(fieldName), java.util.Date.class);
                            if (feildsValuesArr[i].trim().isEmpty()) {
                                setM.invoke((Object) iClass, (Object[]) new Date[]{null});
                            } else {
                                SimpleDateFormat formatter = new SimpleDateFormat(fieldDateFormatMap.get(i));
                                Date convertedDate = formatter.parse(feildsValuesArr[i]);
                                setM.invoke((Object) iClass, convertedDate);
                            }
                            break;
                        case "Double":
                            setM = iClass.getClass().getMethod("set" + WordUtils.capitalize(fieldName), Double.class);
                            if (feildsValuesArr[i].trim().isEmpty()) {
                                setM.invoke((Object) iClass, (Object[]) new Double[]{null});
                            } else {
                                setM.invoke((Object) iClass, Double.parseDouble(feildsValuesArr[i]));
                            }
                            break;
                    }
                }
                pojoList.add(iClass);
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                    SecurityException | IllegalArgumentException | InvocationTargetException | ParseException ex) {
                Logger.getLogger(PojoCreator.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

        }
        return pojoList;
    }

}
