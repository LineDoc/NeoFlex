package ru.neoflex.vacationpaycalculator.service;

import org.springframework.stereotype.Service;
import ru.neoflex.vacationpaycalculator.exception.IncorrectDateFormatException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CalendarService {


    private static final List<String> stringHolidaysList = Arrays.asList("01-01-2024", "02-01-2024","03-01-2024","04-01-2024","05-01-2024",
            "06-01-2024","07-01-2024","08-01-2024","23-02-2024", "08-03-2024", "01-05-2024", "09-05-2024",
            "12-06-2024", "04-11-2024");

    private List<Date> dateHolidaysList = new ArrayList<>();

    private Calendar calendar = new GregorianCalendar();
    private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

    private List<Date> payDaysList = new ArrayList<>();


    private void holidaysToDate() {
        for (String day : stringHolidaysList) {
            try {
                dateHolidaysList.add(format.parse(day));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Integer payVacationDays(Date startDay, Date endDay) throws IncorrectDateFormatException {

        if(startDay == null) {
            throw new IncorrectDateFormatException("Не указана дата выхода в отпуск!!");
        } else if (endDay == null) {
            throw new IncorrectDateFormatException("Не указана дата выхода из отпуска!");
        }

        if(!(startDay.before(endDay))) {
            Date date = new Date();
            date = startDay;
            startDay = endDay;
            endDay = date;
        }

        calendar.setTime(startDay);

        while (calendar.getTime().before(endDay)) {
            int day = calendar.get(Calendar.DAY_OF_WEEK);
            if (!(day == Calendar.SATURDAY || day == Calendar.SUNDAY)) {
                payDaysList.add(calendar.getTime());
            }
            calendar.add(Calendar.DATE, 1);
        }

        holidaysToDate();

        payDaysList.removeAll(dateHolidaysList);

        return payDaysList.size();

    }
    public List<Date> getPayDaysList() {
        return payDaysList;
    }
}
