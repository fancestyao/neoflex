package com.pay.vacation.payment.service.interfaces;

import java.time.LocalDate;

public interface PaymentServiceInterface {
    Double calculatePaymentWithDates(double anniversaryPayment, int vacationDays,
                                     LocalDate firstDate, LocalDate lastDate);
    Double calculatePaymentWithoutDates(double anniversaryPayment, int vacationDays);
}
