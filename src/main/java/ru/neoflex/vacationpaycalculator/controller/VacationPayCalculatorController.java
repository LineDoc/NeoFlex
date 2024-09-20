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

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("/calculate")
public class VacationPayCalculatorController {

    private final VacationPayCalculatorService vacationPayCalculatorService;

    @Autowired
    public VacationPayCalculatorController(VacationPayCalculatorService vacationPayCalculatorService) {
        this.vacationPayCalculatorService = vacationPayCalculatorService;
    }

    @GetMapping
    public ResponseEntity<?> getAmountVacationPay(@RequestParam BigDecimal averageSalary,
                                               @RequestParam(required = false) Integer numbersOfVacationDays,
                                               @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDay,
                                               @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDay) {

        try {
            if (startDay == null & endDay == null) {
                return ResponseEntity.ok(vacationPayCalculatorService.calculation(averageSalary, numbersOfVacationDays));
            } else {
                return ResponseEntity.ok(vacationPayCalculatorService.calculation(averageSalary, startDay, endDay));
            }
        } catch (IncorrectDateFormatException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("UNKNOWN ERROR");
        }
    }
}
