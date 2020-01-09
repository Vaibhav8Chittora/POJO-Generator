/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.td.pojo.generator;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vc186009
 */
public class PojoUtility {

    public static List<String[]> toStringArray(List<Object> pojoList) {
        List<String[]> pojoListArr = new ArrayList<>();
        for (Object pojo : pojoList) {
            String pojoStr = pojo.toString().replaceAll("\\[|\\]", "");
            String[] pojoStrArr = pojoStr.split(",");
            pojoListArr.add(pojoStrArr);
        }
        return pojoListArr;
    }

}
