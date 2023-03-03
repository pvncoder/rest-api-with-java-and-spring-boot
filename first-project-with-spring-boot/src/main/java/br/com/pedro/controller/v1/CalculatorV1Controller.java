/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.pedro.controller.v1;

import br.com.pedro.service.CalculatorService;
import br.com.pedro.util.ArithmeticUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Pedro Vitor Nunes Arruda
 */
@RequestMapping("/api/v1/calculator")
@RestController
public class CalculatorV1Controller {
    
    // GET
    @GetMapping(value = "/sum/{numberOne}/{numberTwo}")
    public Double sum(@PathVariable(value = "numberOne") String numberOne, @PathVariable(value = "numberTwo") String numberTwo) {
        CalculatorService.validateIfStringsAreNumbers(numberOne, numberTwo);
        return ArithmeticUtil.sum(numberOne, numberTwo);
    }
    
    @GetMapping(value = "/subtract/{numberOne}/{numberTwo}")
    public Double subtract(@PathVariable(value = "numberOne") String numberOne, @PathVariable(value = "numberTwo") String numberTwo) {
        CalculatorService.validateIfStringsAreNumbers(numberOne, numberTwo);
        return ArithmeticUtil.subtract(numberOne, numberTwo);
    }
    
    @GetMapping(value = "/multiply/{numberOne}/{numberTwo}")
    public Double multiply(@PathVariable(value = "numberOne") String numberOne, @PathVariable(value = "numberTwo") String numberTwo) {
        CalculatorService.validateIfStringsAreNumbers(numberOne, numberTwo);
        return ArithmeticUtil.multiply(numberOne, numberTwo);
    }
    
    @GetMapping(value = "/divide/{numberOne}/{numberTwo}")
    public Double divide(@PathVariable(value = "numberOne") String numberOne, @PathVariable(value = "numberTwo") String numberTwo) {
        CalculatorService.validateIfStringsAreNumbers(numberOne, numberTwo);
        return ArithmeticUtil.divide(numberOne, numberTwo);
    }
    
    @GetMapping(value = "/average/{numberOne}/{numberTwo}")
    public Double average(@PathVariable(value = "numberOne") String numberOne, @PathVariable(value = "numberTwo") String numberTwo) {
        CalculatorService.validateIfStringsAreNumbers(numberOne, numberTwo);
        return ArithmeticUtil.average(numberOne, numberTwo);
    }
    
    @GetMapping(value = "/square-root/{number}")
    public Double squareRoot(@PathVariable(value = "number") String number) {
        CalculatorService.validateIfStringsAreNumbers(number);
        return ArithmeticUtil.squareRoot(number);
    }
}
