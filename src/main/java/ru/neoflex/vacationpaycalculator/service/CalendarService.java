package ru.neoflex.vacationpaycalculator.service;

import org.springframework.stereotype.Service;
import ru.neoflex.vacationpaycalculator.date.PublicHolidays;
import ru.neoflex.vacationpaycalculator.exception.IncorrectDateFormatException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.MonthDay;
import java.util.*;

@Service
public class CalendarService {

    private final Set<LocalDate> payDaysSet = new HashSet<>();

    public int payVacationDaysCalculate(LocalDate startDay, LocalDate endDay) throws IncorrectDateFormatException {

        //Проверка принятых дат
        //Если не указана какая-либо из дат -> исключение
        if(startDay == null) {
            throw new IncorrectDateFormatException("Не указана дата выхода в отпуск!");
        } else if (endDay == null) {
            throw new IncorrectDateFormatException("Не указана дата выхода из отпуска!");
        }
        //Если дата начала отпуска > даты окончания -> исключение
        if(!(startDay.isBefore(endDay))) {
            throw new IncorrectDateFormatException("Дата начала отпуска позже больше даты окончания!");
        }

        //Рабочие дни с учётом праздников при рабочем графике 5/2
        LocalDate startLocalDate = startDay;
        while (!startLocalDate.isAfter(endDay)) {
            if (!(startLocalDate.getDayOfWeek().equals(DayOfWeek.SATURDAY) ||
                    startLocalDate.getDayOfWeek().equals(DayOfWeek.SUNDAY)) &&
                    !PublicHolidays.set.contains(MonthDay.from(startLocalDate))) {

                payDaysSet.add(startLocalDate);
            }
            startLocalDate = startLocalDate.plusDays(1);
        }
        return payDaysSet.size();
    }
}
