/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.td.pojo.calc;

import com.td.pojo.generator.PojoCreator;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.collections4.CollectionUtils;

/**
 *
 * @author vc186009
 */
public class DVTFunctions {

    private List<? extends Object> fieldDataList;

    private Properties dataTypeProp = new Properties();

    public DVTFunctions(List<? extends Object> fieldDataList) {
        this.fieldDataList = fieldDataList;
        try (InputStream in = getClass().getResourceAsStream("/resources/DataType.properties")) {
            dataTypeProp.load(in);
        } catch (IOException ex) {
            Logger.getLogger(PojoCreator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @return distinct count of character values (char, varchar, etc.)
     */
    public Long distinctCountChar() {
        List<String> nullList = (List<String>) CollectionUtils.select((List<String>) fieldDataList, (String s) -> s == null);
        List<String> minusList = (List<String>) CollectionUtils.subtract((List<String>) fieldDataList, nullList);
        Long distinctCount = (minusList).stream().distinct().count();
        //Add 1 for nulls
        return distinctCount;
    }

    /**
     *
     * @return distinct count of numeric values (long, int, etc.)
     */
    public Long distinctCountNumeric() {
        List<Long> nullList = (List<Long>) CollectionUtils.select((List<Long>) fieldDataList, (Long l) -> l == null);
        List<Long> minusList = (List<Long>) CollectionUtils.subtract((List<Long>) fieldDataList, nullList);
        Long distinctCount = ((List<Long>) minusList).stream().mapToLong(v -> v).distinct().count();
        //Add 1 for nulls
        return distinctCount;
    }

    /**
     *
     * @return distinct count of floating values (decimal, number, etc.)
     */
    public Long distinctCountFloating() {
        List<Double> nullList = (List<Double>) CollectionUtils.select((List<Double>) fieldDataList, (Double d) -> d == null);
        List<Double> minusList = (List<Double>) CollectionUtils.subtract((List<Double>) fieldDataList, nullList);

        Long distinctCount = (minusList).stream().mapToDouble(v -> v).distinct().count();
        //Add 1 for nulls
        return distinctCount;
    }

    public Long distinctCountDate() {

        List<Date> nullList = (List<Date>) CollectionUtils.select((List<Date>) fieldDataList, (Date da) -> da == null);
        List<Date> minusList = (List<Date>) CollectionUtils.subtract((List<Date>) fieldDataList, nullList);

        Long distinctCount = (minusList).stream().distinct().count();
        //Add 1 for nulls        
        return distinctCount;
    }

    /**
     *
     * @return <code>java.lang.Long</code>
     */
    public Long minOfNumeric() {
        List<Long> nullList = (List<Long>) CollectionUtils.select((List<Long>) fieldDataList, (Long l) -> l == null);
        List<Long> minusList = (List<Long>) CollectionUtils.subtract((List<Long>) fieldDataList, nullList);
        Long min = null;
        if (!minusList.isEmpty()) {
            min = Collections.min((List<Long>) minusList);
        }
        return min;
    }

    /**
     *
     * @return <code>java.lang.Long</code>
     */
    public Long maxOfNumeric() {
        List<Long> nullList = (List<Long>) CollectionUtils.select((List<Long>) fieldDataList, (Long l) -> l == null);
        List<Long> minusList = (List<Long>) CollectionUtils.subtract((List<Long>) fieldDataList, nullList);
        Long max = null;
        if (!minusList.isEmpty()) {
            max = Collections.max((List<Long>) minusList);
        }
        return max;
    }

    /**
     *
     * @return <code>java.lang.Double</code>
     */
    public Double minOfFloating() {
        List<Double> nullList = (List<Double>) CollectionUtils.select((List<Double>) fieldDataList, (Double d) -> d == null);
        List<Double> minusList = (List<Double>) CollectionUtils.subtract((List<Double>) fieldDataList, nullList);
        Double min = null;
        if (!minusList.isEmpty()) {
            min = Collections.min((List<Double>) minusList);
        }

        return min;

    }

    /**
     *
     * @return <code>java.lang.Double</code>
     */
    public Double maxOfFloating() {
        List<Double> nullList = (List<Double>) CollectionUtils.select((List<Double>) fieldDataList, (Double d) -> d == null);
        List<Double> minusList = (List<Double>) CollectionUtils.subtract((List<Double>) fieldDataList, nullList);
        Double max = null;
        if (!minusList.isEmpty()) {
            max = Collections.max((List<Double>) minusList);
        }

        return max;
    }

    /**
     *
     * @return Floating value upto 3 decimal
     */
    public Double meanOfFloating() {
        List<Double> nullList = (List<Double>) CollectionUtils.select((List<Double>) fieldDataList, (Double d) -> d == null);
        List<Double> minusList = (List<Double>) CollectionUtils.subtract((List<Double>) fieldDataList, nullList);

        Double sum = (minusList).stream().mapToDouble(v -> v).sum();
        Long count = (minusList).stream().mapToDouble(v -> v).count() + nullList.size();
        Double mean = sum * 1.0 / count;
        DecimalFormat decimalFormat = new DecimalFormat(".000");
        return Double.parseDouble(decimalFormat.format(mean));
    }

    /**
     *
     * @return Floating value upto 3 decimal
     */
    public Double meanOfNumeric() {
        List<Long> nullList = (List<Long>) CollectionUtils.select((List<Long>) fieldDataList, (Long l) -> l == null);
        List<Long> minusList = (List<Long>) CollectionUtils.subtract((List<Long>) fieldDataList, nullList);

        Long sum = (minusList).stream().mapToLong(v -> v).sum();
        Long count = (minusList).stream().mapToLong(v -> v).count() + nullList.size();
        Double mean = sum * 1.0 / count;
        DecimalFormat decimalFormat = new DecimalFormat(".000");
        return Double.parseDouble(decimalFormat.format(mean));
    }

    public Long sumOfNumeric() {
        List<Long> nullList = (List<Long>) CollectionUtils.select((List<Long>) fieldDataList, (Long l) -> l == null);
        List<Long> minusList = (List<Long>) CollectionUtils.subtract((List<Long>) fieldDataList, nullList);

        Long sum = (minusList).stream().mapToLong(v -> v).sum();
        return sum;
    }

    public Double sumOfFloating() {
        List<Double> nullList = (List<Double>) CollectionUtils.select((List<Double>) fieldDataList, (Double d) -> d == null);
        List<Double> minusList = (List<Double>) CollectionUtils.subtract((List<Double>) fieldDataList, nullList);

        Double sum = (minusList).stream().mapToDouble(v -> v).sum();

        DecimalFormat decimalFormat = new DecimalFormat(".00000000000");
        return Double.parseDouble(decimalFormat.format(sum));
    }

    public Integer sumOfNULLChar() {
        int noOfNull = CollectionUtils.select((List<String>) fieldDataList, (String s) -> s == null).size();
        return noOfNull;
    }

    public Integer sumOfNULLNumeric() {
        int noOfNull = CollectionUtils.select((List<Long>) fieldDataList, (Long l) -> l == null).size();
        return noOfNull;
    }

    public Integer sumOfNULLFloating() {
        int noOfNull = CollectionUtils.select((List<Double>) fieldDataList, (Double d) -> d == null).size();
        return noOfNull;
    }

    public Integer sumOfNULLDate() {
        int noOfNull = CollectionUtils.select((List<Date>) fieldDataList, (Date da) -> da == null).size();
        return noOfNull;
    }

    public Integer minCharLength() {
        List<String> nullList = (List<String>) CollectionUtils.select((List<String>) fieldDataList, (String s) -> s == null);
        List<String> minusList = (List<String>) CollectionUtils.subtract((List<String>) fieldDataList, nullList);
        Integer minStringLen = null;
        if (!minusList.isEmpty()) {
            minStringLen = Collections.min(minusList, Comparator.comparing(s -> s.length())).length();
        }
        return minStringLen;
    }

    public Integer maxCharLength() {
        List<String> nullList = (List<String>) CollectionUtils.select((List<String>) fieldDataList, (String s) -> s == null);
        List<String> minusList = (List<String>) CollectionUtils.subtract((List<String>) fieldDataList, nullList);

        Integer maxStringLen = null;
        if (!minusList.isEmpty()) {
            maxStringLen = Collections.max(minusList, Comparator.comparing(s -> s.length())).length();
        }
        return maxStringLen;
    }

    public Long sumCharLength() {
        List<String> nullList = (List<String>) CollectionUtils.select((List<String>) fieldDataList, (String s) -> s == null);
        List<String> minusList = (List<String>) CollectionUtils.subtract((List<String>) fieldDataList, nullList);
        Long sumCharLength = 0l;
        sumCharLength = minusList.stream().map((eachChar) -> {
            return (long) eachChar.length();
        }).reduce(sumCharLength, Long::sum);
        return sumCharLength;
    }

    public Date maxDate() {
        List<Date> nullList = (List<Date>) CollectionUtils.select((List<Date>) fieldDataList, (Date da) -> da == null);
        List<Date> minusList = (List<Date>) CollectionUtils.subtract((List<Date>) fieldDataList, nullList);
        Date maxDate = null;
        if (!minusList.isEmpty()) {
            maxDate = Collections.max(minusList);
        }
        return maxDate;
    }

    public Date minDate() {
        List<Date> nullList = (List<Date>) CollectionUtils.select((List<Date>) fieldDataList, (Date da) -> da == null);
        List<Date> minusList = (List<Date>) CollectionUtils.subtract((List<Date>) fieldDataList, nullList);

        Date minDate = null;
        if (!minusList.isEmpty()) {
            minDate = Collections.min(minusList);
        }
        return minDate;
    }

}
