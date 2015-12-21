package kr.co.opensns.ksbiz.socialbot.agent;

import java.util.Arrays;


public class ArrayBean {
 
    private Integer[] intArray;
 
    public Integer[] getIntArray() {
        return intArray;
    }
 
    public void setIntArray(Integer[] intArray) {
        this.intArray = intArray;
    }      
     
    public String toString() {
        return Arrays.asList(intArray).toString();
    }
}
