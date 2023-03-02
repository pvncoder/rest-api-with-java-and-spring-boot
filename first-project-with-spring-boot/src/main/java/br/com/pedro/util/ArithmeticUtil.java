/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.pedro.util;

/**
 *
 * @author Pedro Vitor Nunes Arruda
 */
public class ArithmeticUtil {
    
    // General
    public static Double sum(String number1, String number2) {
        return sum(Double.valueOf(number1), Double.valueOf(number2));
    }

    public static Double sum(Double number1, Double number2) {
        return number1 + number2;
    }
    
    public static Double subtract(String number1, String number2) {
        return subtract(Double.valueOf(number1), Double.valueOf(number2));
    }
    
    public static Double subtract(Double number1, Double number2) {
        return number1 - number2;
    }
    
    public static Double multiply(String number1, String number2) {
        return multiply(Double.valueOf(number1), Double.valueOf(number2));
    }
    
    public static Double multiply(Double number1, Double number2) {
        return number1 * number2;
    }
    
    public static Double divide(String number1, String number2) {
        return divide(Double.valueOf(number1), Double.valueOf(number2));
    }
    
    public static Double divide(Double number1, Double number2) {
        return number1 / number2;
    }
    
    public static Double average(String number1, String number2) {
        return average(Double.valueOf(number1), Double.valueOf(number2));
    }
    
    public static Double average(Double number1, Double number2) {
        return (number1 + number2) / 2;
    }
    
    public static Double squareRoot(String number) {
        return squareRoot(Double.valueOf(number));
    }
    
    public static Double squareRoot(Double number) {
        return Math.sqrt(number);
    }
}
