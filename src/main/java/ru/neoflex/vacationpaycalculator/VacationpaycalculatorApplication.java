package ru.neoflex.vacationpaycalculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import ru.neoflex.vacationpaycalculator.service.VacationPayCalculatorService;

import java.text.SimpleDateFormat;
import java.util.Date;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class VacationpaycalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(VacationpaycalculatorApplication.class, args);


	}

}
