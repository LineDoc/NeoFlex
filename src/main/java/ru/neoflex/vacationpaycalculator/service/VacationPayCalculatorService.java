package ru.neoflex.vacationpaycalculator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.neoflex.vacationpaycalculator.exception.IncorrectDateFormatException;

import java.text.DecimalFormat;
import java.util.*;

@Service
public class VacationPayCalculatorService {

    @Autowired
    CalendarService calendarService;

    private final DecimalFormat decimalFormat = new DecimalFormat("##.##");
    private final double AVERAGE_NUMBER_DAYS_IN_MONTH = 29.3;
    private Double amountVacationPay;

    public String calculation(double averageSalary, int days) {
        amountVacationPay = 0.87 * (averageSalary / AVERAGE_NUMBER_DAYS_IN_MONTH)
                * days;
        return "Сумма отпускных с учётом НДФЛ 13%: " + decimalFormat.format(amountVacationPay) + " руб";
    }

    public String calculation(double averageSalary, Date startDay, Date stopDay) throws IncorrectDateFormatException {
        amountVacationPay = 0.87 * (averageSalary / AVERAGE_NUMBER_DAYS_IN_MONTH)
                * calendarService.payVacationDays(startDay,stopDay);

        calendarService.getPayDaysList().clear();

        return "Сумма отпускных с учётом НДФЛ 13%: " + decimalFormat.format(amountVacationPay) + " руб.";

    }

}
