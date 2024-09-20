package ru.neoflex.vacationpaycalculator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.neoflex.vacationpaycalculator.exception.IncorrectDateFormatException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Service
public final class VacationPayCalculatorService {

    CalendarService calendarService;

    @Autowired
    public VacationPayCalculatorService(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    private final double AVERAGE_NUMBER_DAYS_IN_MONTH = 29.3;
    private final double NDFL_TAX = 0.13;

    //Расчёт отпускных с учётом НДФЛ 13%
    //если указаны только среднемесячная зарплата и кол-во дней отпуска
    public String calculation(BigDecimal averageSalary, Integer vacationDays) {
BigDecimal amountVacationPay = averageSalary.divide(BigDecimal.valueOf(AVERAGE_NUMBER_DAYS_IN_MONTH), 2, RoundingMode.HALF_UP)
        .multiply(BigDecimal.valueOf(vacationDays)).multiply(BigDecimal.valueOf(1 - NDFL_TAX));

        return "Сумма отпускных с учётом НДФЛ 13%: " + amountVacationPay.setScale(2,RoundingMode.HALF_UP)+ " руб.";
    }

    //если указаны среднемесячная зарплата и даты начала и окончания отпуска;
    //с учётом выходных и праздников
    public String calculation(BigDecimal averageSalary, LocalDate startDay, LocalDate endDay) throws IncorrectDateFormatException {
        BigDecimal amountVacationPay = averageSalary
                .divide(BigDecimal.valueOf(AVERAGE_NUMBER_DAYS_IN_MONTH), 2, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(calendarService.payVacationDaysCalculate(startDay,endDay)))
                .multiply(BigDecimal.valueOf(1 - NDFL_TAX));

        return "Сумма отпускных с учётом НДФЛ 13%: " + amountVacationPay.setScale(2, RoundingMode.HALF_UP) + " руб.";
    }


}
