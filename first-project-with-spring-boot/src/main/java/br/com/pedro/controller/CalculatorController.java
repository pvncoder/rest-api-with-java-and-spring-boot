/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.pedro.controller;

import br.com.pedro.service.CalculatorService;
import br.com.pedro.util.ArithmeticUtil;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Pedro Vitor Nunes Arruda
 */
@RestController
public class CalculatorController {
    
    // GET
    @RequestMapping(value = "/sum/{numberOne}/{numberTwo}", method = RequestMethod.GET)
    public Double sum(@PathVariable(value = "numberOne") String numberOne, @PathVariable(value = "numberTwo") String numberTwo) {
        CalculatorService.validateIfStringsAreNumbers(numberOne, numberTwo);
        return ArithmeticUtil.sum(numberOne, numberTwo);
    }
    
    @RequestMapping(value = "/subtract/{numberOne}/{numberTwo}", method = RequestMethod.GET)
    public Double subtract(@PathVariable(value = "numberOne") String numberOne, @PathVariable(value = "numberTwo") String numberTwo) {
        CalculatorService.validateIfStringsAreNumbers(numberOne, numberTwo);
        return ArithmeticUtil.subtract(numberOne, numberTwo);
    }
    
    @RequestMapping(value = "/multiply/{numberOne}/{numberTwo}", method = RequestMethod.GET)
    public Double multiply(@PathVariable(value = "numberOne") String numberOne, @PathVariable(value = "numberTwo") String numberTwo) {
        CalculatorService.validateIfStringsAreNumbers(numberOne, numberTwo);
        return ArithmeticUtil.multiply(numberOne, numberTwo);
    }
    
    @RequestMapping(value = "/divide/{numberOne}/{numberTwo}", method = RequestMethod.GET)
    public Double divide(@PathVariable(value = "numberOne") String numberOne, @PathVariable(value = "numberTwo") String numberTwo) {
        CalculatorService.validateIfStringsAreNumbers(numberOne, numberTwo);
        return ArithmeticUtil.divide(numberOne, numberTwo);
    }
    
    @RequestMapping(value = "/average/{numberOne}/{numberTwo}", method = RequestMethod.GET)
    public Double average(@PathVariable(value = "numberOne") String numberOne, @PathVariable(value = "numberTwo") String numberTwo) {
        CalculatorService.validateIfStringsAreNumbers(numberOne, numberTwo);
        return ArithmeticUtil.average(numberOne, numberTwo);
    }
    
    @RequestMapping(value = "/square-root/{number}", method = RequestMethod.GET)
    public Double squareRoot(@PathVariable(value = "number") String number) {
        CalculatorService.validateIfStringsAreNumbers(number);
        return ArithmeticUtil.squareRoot(number);
    }
}
