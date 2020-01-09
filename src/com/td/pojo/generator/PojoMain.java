/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.td.pojo.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vc186009
 */
public class PojoMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            String className = "Employee";
            String[] h = new String[]{"ID", "Salary", "Name", "Dob", "HireDate", "Age"};
            String[] s1 = new String[]{"1", "4000.55512", "Roh Kha", "08-OCT-85", "02/01/2014", "30.5"};
            String[] s2 = new String[]{"2", "14500.263828", "Vai Chi", "08-OCT-90", "02/01/2015", "35.5"};
            String[] s3 = new String[]{"3", "6000.354354", "Dur Pat", "18-SEP-92", "02/18/2016", "25.5"};
            String[] s4 = new String[]{"", "5000.354354", "Dur Pat", "11-JAN-95", "02/01/2016", "25.5"};
            String[] s5 = new String[]{"", "1500.263828", "Ruc Chi", "08-MAY-89", "02/01/2009", "30.5"};
            String[] s6 = new String[]{"6", "500.263828", "Sho Kap", "01-JUL-12", "02/01/2010", "45.5"};
            List<String[]> fieldsValues = new ArrayList<>();

            fieldsValues.add(s1);
            fieldsValues.add(s2);
            fieldsValues.add(s3);
            fieldsValues.add(s4);
            fieldsValues.add(s5);
            fieldsValues.add(s6);

            Map<String, String> fieldsNamesDataTypeMap = new HashMap<>();

            fieldsNamesDataTypeMap.put("Age", "NUMBER");
            fieldsNamesDataTypeMap.put("Dob", "DATE");
            fieldsNamesDataTypeMap.put("ID", "INTEGER");
            fieldsNamesDataTypeMap.put("HireDate", "DATE");
            fieldsNamesDataTypeMap.put("Name", "VARCHAR");
            fieldsNamesDataTypeMap.put("Salary", "DECIMAL");

            Map<Integer, String> fieldsDateFormatMap = new HashMap<>();

            fieldsDateFormatMap.put(3, "dd-MMM-yy");
            fieldsDateFormatMap.put(4, "MM/dd/yyyy");

            List<String> headers = Arrays.asList(h);

            PojoCreator creator = new PojoCreator(fieldsNamesDataTypeMap, headers);
            Class clazz = creator.createClass(className);

            PojoLoader loader = new PojoLoader(fieldsNamesDataTypeMap);
            List<Object> pojoList = loader.loadPojo(clazz, fieldsValues, headers, fieldsDateFormatMap);

//            List<String> orderBy = new ArrayList<>();
//            orderBy.add("Name");
//            orderBy.add("Salary");
//            orderBy.add("ID");
//            orderBy.add("HireDate");
//
//            PojoExtractor extractor = new PojoExtractor(fieldsNamesDataTypeMap);
//
//            for (String fieldName : headers) {
//                Collection<? extends Object> fieldDataSet = (Collection<? extends Object>) extractor.getCollectionOfFields(pojoList, fieldName);
//                DVTFunctions df = new DVTFunctions((List<? extends Object>) fieldDataSet);
//                if ("SMALLINT".equalsIgnoreCase(fieldsNamesDataTypeMap.get(fieldName)) || "INT".equalsIgnoreCase(fieldsNamesDataTypeMap.get(fieldName))
//                        || "INTEGER".equalsIgnoreCase(fieldsNamesDataTypeMap.get(fieldName))) {
//                    System.out.println(fieldName + "-DISTICNT COUNT-->" + df.distinctCountNumeric());
//                    System.out.println(fieldName + "-MIN-->" + df.minOfNumeric());
//                    System.out.println(fieldName + "-MAX-->" + df.maxOfNumeric());
//                    System.out.println(fieldName + "-MEAN-->" + df.meanOfNumeric());
//                    System.out.println(fieldName + "-SUM-->" + df.sumOfNumeric());
//                    System.out.println(fieldName + "-NO-NULL-->" + df.sumOfNULLNumeric());
//                } else if ("NUMBER".equalsIgnoreCase(fieldsNamesDataTypeMap.get(fieldName)) || "DECIMAL".equalsIgnoreCase(fieldsNamesDataTypeMap.get(fieldName))) {
//                    System.out.println(fieldName + "-DISTICNT COUNT-->" + df.distinctCountFloating());
//                    System.out.println(fieldName + "-MIN-->" + df.minOfFloating());
//                    System.out.println(fieldName + "-MAX-->" + df.maxOfFloating());
//                    System.out.println(fieldName + "-MEAN-->" + df.meanOfFloating());
//                    System.out.println(fieldName + "-SUM-->" + df.sumOfFloating());
//                    System.out.println(fieldName + "-NO-NULL-->" + df.sumOfNULLFloating());
//                } else if ("CAHR".equalsIgnoreCase(fieldsNamesDataTypeMap.get(fieldName)) || "VARCHAR".equalsIgnoreCase(fieldsNamesDataTypeMap.get(fieldName))
//                        || "VARCHAR2".equalsIgnoreCase(fieldsNamesDataTypeMap.get(fieldName))) {
//                    System.out.println(fieldName + "-DISTICNT-COUNT-->" + df.distinctCountChar());
//                    System.out.println(fieldName + "-MIN-LENGTH->" + df.minCharLength());
//                    System.out.println(fieldName + "-MAX-LENGTH->" + df.maxCharLength());
//                    System.out.println(fieldName + "-SUM-->" + df.sumCharLength());
//                    System.out.println(fieldName + "-NO-NULL->" + df.sumOfNULLChar());
//                } else if ("DATE".equalsIgnoreCase(fieldsNamesDataTypeMap.get(fieldName))) {
//                    System.out.println(fieldName + "-DISTICNT COUNT-->" + df.distinctCountDate());
//                    System.out.println(fieldName + "-MAX-->" + df.maxDate());
//                    System.out.println(fieldName + "-MIN-->" + df.minDate());
//                    System.out.println(fieldName + "-NO-NULL->" + df.sumOfNULLDate());
//                }
//            }
            PojoSorter sorter = new PojoSorter(fieldsNamesDataTypeMap);
//            pojoList = (List<Object>) sorter.orderBy(pojoList, orderBy, 0);

//            int topRecord = 5;
//            pojoList = (List<Object>) sorter.orderBy(pojoList, orderBy, topRecord);
            List<String[]> pojoListArr = PojoUtility.toStringArray(pojoList);

            for (Iterator<String[]> iterator = pojoListArr.iterator(); iterator.hasNext();) {
                String[] next = iterator.next();
                for (int i = 0; i < headers.size(); i++) {
                    System.out.println(headers.get(i) + "~" + next[i]);

                }
            }

        } catch (SecurityException | ClassNotFoundException ex) {
            Logger.getLogger(PojoMain.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
