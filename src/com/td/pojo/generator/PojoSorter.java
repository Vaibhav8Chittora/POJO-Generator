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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.collections4.ComparatorUtils;

/**
 *
 * @author vc186009
 */
public class PojoSorter {

    private Properties dataTypeProp = new Properties();
    private Map<String, String> fieldsNamesDataTypeMap;

    public PojoSorter(Map<String, String> fieldsNamesDataTypeMap) {
        this.fieldsNamesDataTypeMap = fieldsNamesDataTypeMap;
        try (InputStream in = getClass().getResourceAsStream("/resources/DataType.properties")) {
            dataTypeProp.load(in);
        } catch (IOException ex) {
            Logger.getLogger(PojoCreator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Collection<Object> orderBy(List<Object> pojoList, List<String> orderByList, int topRecord) {
        Collection<Comparator<Object>> comparatorList = new ArrayList<>();
        for (Iterator<String> iterator = orderByList.iterator(); iterator.hasNext();) {
            String fieldName = iterator.next();

            String dataType = (String) dataTypeProp.get(fieldsNamesDataTypeMap.get(fieldName));
            Comparator comparator = new Comparator() {

                @Override
                public int compare(Object o1, Object o2) {
                    Method getM = null;
                    try {
                        switch (dataType) {
                            case "String":
                                getM = o1.getClass().getMethod("get" + fieldName);
                                String s1 = (String) getM.invoke(o1);
                                String s2 = (String) getM.invoke(o2);
                                return s1.compareTo(s2);
                            case "Long":
                                getM = o1.getClass().getMethod("get" + fieldName);
                                Long l1 = (Long) getM.invoke(o1);
                                Long l2 = (Long) getM.invoke(o2);
                                return l1.compareTo(l2);
                            case "Double":
                                getM = o1.getClass().getMethod("get" + fieldName);
                                Double d1 = (Double) getM.invoke(o1);
                                Double d2 = (Double) getM.invoke(o2);
                                return d1.compareTo(d2);
                            case "Date":
                                getM = o1.getClass().getMethod("get" + fieldName);
                                Date da1 = (Date) getM.invoke(o1);
                                Date da2 = (Date) getM.invoke(o2);
                                return da1.compareTo(da2);
                        }
                    } catch (SecurityException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                    return 0;
                }
            };
            comparatorList.add(comparator);
        }

        Collections.sort(pojoList, ComparatorUtils.chainedComparator(comparatorList));

        if (topRecord > 0) {
            pojoList = pojoList.subList(0, topRecord);
        }

        return pojoList;
    }
}
