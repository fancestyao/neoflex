package com.pay.vacation.payment.repository.interfaces;

import java.time.LocalDate;

public interface DaoStorageInterface {
    Double calculatePaymentWithoutDates(double anniversaryPayment, int vacationDays);
    Double calculatePaymentWithSpecificDates(double anniversaryPayment, int vacationDays,
                                             LocalDate firstDate, LocalDate lastDate);
}
