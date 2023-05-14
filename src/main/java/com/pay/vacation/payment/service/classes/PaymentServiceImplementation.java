package com.pay.vacation.payment.service.classes;

import com.pay.vacation.payment.exceptions.WrongDateException;
import com.pay.vacation.payment.repository.interfaces.DaoStorageInterface;
import com.pay.vacation.payment.service.interfaces.PaymentServiceInterface;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PaymentServiceImplementation implements PaymentServiceInterface {
    private final DaoStorageInterface daoStorage;

    public PaymentServiceImplementation(DaoStorageInterface daoStorage) {
        this.daoStorage = daoStorage;
    }

    @Override
    public Double calculatePaymentWithoutDates(double anniversaryPayment, int vacationDays) {
        return daoStorage.calculatePaymentWithoutDates(anniversaryPayment, vacationDays);
    }

    @Override
    public Double calculatePaymentWithDates(double anniversaryPayment, int vacationDays,
                                    LocalDate firstDate, LocalDate lastDate) {
        if ((firstDate.isAfter(LocalDate.now()) || firstDate.isEqual(LocalDate.now()))
                    && (lastDate.isAfter(firstDate))) {
            return daoStorage.calculatePaymentWithSpecificDates(anniversaryPayment, vacationDays, firstDate, lastDate);
        } else {
            throw new WrongDateException("Передана неподходящая дата.");
        }
    }
}
