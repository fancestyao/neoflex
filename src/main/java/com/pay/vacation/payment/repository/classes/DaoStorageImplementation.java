package com.pay.vacation.payment.repository.classes;

import com.pay.vacation.payment.exceptions.NegativeNumberException;
import com.pay.vacation.payment.repository.interfaces.DaoStorageInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Repository
@Slf4j
public class DaoStorageImplementation implements DaoStorageInterface {
    private final static int AVG_WORKING_DAYS = 20;
    private final static int NUMBER_OF_MONTHS = 12;

    @Override
    public Double calculatePaymentWithoutDates(double anniversaryPayment, int vacationDays) {
        paramsValidation(anniversaryPayment, vacationDays);
        return countDailyAvgSalary(anniversaryPayment) * vacationDays;
    }

    @Override
    public Double calculatePaymentWithSpecificDates(double anniversaryPayment, int vacationDays,
                                                    LocalDate firstDate, LocalDate lastDate) {
        int workDays = 0;
        LocalDate currentDate = firstDate;
        while (currentDate.isBefore(lastDate) || currentDate.equals(lastDate)) {
            DayOfWeek dayOfWeek = currentDate.getDayOfWeek();
            if (dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY) {
                workDays++;
            }
            currentDate = currentDate.plusDays(1);
        }
        return calculatePaymentWithoutDates(anniversaryPayment, vacationDays + workDays) ;
    }

    private Double countDailyAvgSalary(double anniversaryPayment) {
        return countMonthlyAvgSalary(anniversaryPayment) / AVG_WORKING_DAYS;
    }

    private Double countMonthlyAvgSalary(double anniversaryPayment) {
        return anniversaryPayment / NUMBER_OF_MONTHS;
    }

    private void paramsValidation(double anniversaryPayment, int vacationDays) {
        if (anniversaryPayment < 0) {
            log.warn("Ошибка валидации средней ЗП. Передано отрицательное значение.");
            throw new NegativeNumberException("Передано отрицательное значение ЗП.");
        } else if (vacationDays < 0) {
            log.warn("Ошибка валидации дней отпуска. Передано отрицательное значение.");
            throw new NegativeNumberException("Передано отрицательное значение количества отпускных дней.");
        }
    }
}
