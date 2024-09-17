package ru.neoflex.vacationpaycalculator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.neoflex.vacationpaycalculator.exception.IncorrectDateFormatException;
import ru.neoflex.vacationpaycalculator.service.VacationPayCalculatorService;
import java.util.Date;

@RestController
@RequestMapping("/calculate")
public class VacationPayCalculatorController {

    @Autowired
    private VacationPayCalculatorService vacationPayCalculatorService;


    @GetMapping
    public ResponseEntity getAmountVacationPay(@RequestParam Double amountVacationPay,
                                               @RequestParam(required = false) Integer numbersOfVacationDays,
                                               @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date startDay,
                                               @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDay) {

        try {
            if (startDay == null & endDay == null) {
                return ResponseEntity.ok(vacationPayCalculatorService.calculation(amountVacationPay, numbersOfVacationDays));
            } else {
                return ResponseEntity.ok(vacationPayCalculatorService.calculation(amountVacationPay, startDay, endDay));
            }
        } catch (IncorrectDateFormatException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Fatal error");
        }
    }
}
