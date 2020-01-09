/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.td.pojo.generator;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.TransformerUtils;
import org.apache.commons.lang.WordUtils;

/**
 *
 * @author vc186009
 */
public class PojoExtractor {

    private Properties dataTypeProp = new Properties();
    private Map<String, String> fieldsNamesDataTypeMap;

    public PojoExtractor(Map<String, String> fieldsNamesDataTypeMap) {
        this.fieldsNamesDataTypeMap = fieldsNamesDataTypeMap;
        try (InputStream in = getClass().getResourceAsStream("/resources/DataType.properties")) {
            dataTypeProp.load(in);
        } catch (IOException ex) {
            Logger.getLogger(PojoCreator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Collection<? extends Object> getCollectionOfFields(List<Object> pojoList, String fieldName) {
        String dataType = (String) dataTypeProp.get(fieldsNamesDataTypeMap.get(fieldName));

        switch (dataType) {
            case "String":
                Collection<String> stringFieldData = CollectionUtils.collect(pojoList, TransformerUtils.invokerTransformer("get" + WordUtils.capitalize(fieldName)));
                return stringFieldData;
            case "Long":
                Collection<Long> longFieldData = CollectionUtils.collect(pojoList, TransformerUtils.invokerTransformer("get" + WordUtils.capitalize(fieldName)));
                return longFieldData;
            case "Double":
                Collection<Double> doubleFieldData = CollectionUtils.collect(pojoList, TransformerUtils.invokerTransformer("get" + WordUtils.capitalize(fieldName)));
                return doubleFieldData;
            case "Date":
                Collection<Date> dateFieldData = CollectionUtils.collect(pojoList, TransformerUtils.invokerTransformer("get" + WordUtils.capitalize(fieldName)));
                return dateFieldData;
        }
        return null;
    }
}
