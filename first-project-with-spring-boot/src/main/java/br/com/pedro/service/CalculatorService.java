/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.pedro.service;

import static java.util.Objects.nonNull;
import org.apache.commons.lang3.math.NumberUtils;

/**
 *
 * @author Pedro Vitor Nunes Arruda
 */
public class CalculatorService {

    // General
    public static void validateIfStringsAreNumbers(String... strings) {
        if (nonNull(strings)) {
            for (String str : strings) {
                if (!NumberUtils.isCreatable(str)) {
                    throw new UnsupportedOperationException("Please set a numeric number");
                }
            }
        }
    }
}
