package ru.neoflex.vacationpaycalculator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.neoflex.vacationpaycalculator.exception.IncorrectDateFormatException;

import java.text.DecimalFormat;
import java.time.LocalDate;

@Service
public final class VacationPayCalculatorService {

    @Autowired
    CalendarService calendarService;

    private final double AVERAGE_NUMBER_DAYS_IN_MONTH = 29.3;
    private final double NDFL_TAX = 0.13;
    private double amountVacationPay;
    private final DecimalFormat decimalFormat = new DecimalFormat("#.##");

    //Расчёт отпускных с учётом НДФЛ 13%
    //если указаны только среднемесячная зарплата и кол-во дней отпуска
    public String calculation(double averageSalary, int vacationDays) {
        amountVacationPay = (1 - NDFL_TAX) * (averageSalary / AVERAGE_NUMBER_DAYS_IN_MONTH)
                * vacationDays;
        return "Сумма отпускных с учётом НДФЛ 13%: " + decimalFormat.format(amountVacationPay) + " руб";
    }

    //если указаны среднемесячная зарплата и даты начала и окончания отпуска;
    //с учётом выходных и праздников
    public String calculation(double averageSalary, LocalDate startDay, LocalDate endDay) throws IncorrectDateFormatException {
        amountVacationPay = (1 - NDFL_TAX) * (averageSalary / AVERAGE_NUMBER_DAYS_IN_MONTH)
                * calendarService.payVacationDays(startDay,endDay);
        return "Сумма отпускных с учётом НДФЛ 13%: " + decimalFormat.format(amountVacationPay) + " руб.";
    }

}
