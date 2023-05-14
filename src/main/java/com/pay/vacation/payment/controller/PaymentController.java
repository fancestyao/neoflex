package com.pay.vacation.payment.controller;

import com.pay.vacation.payment.service.interfaces.PaymentServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@Slf4j
public class PaymentController {
    private final PaymentServiceInterface paymentService;

    @Autowired
    public PaymentController(PaymentServiceInterface paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/calculate")
    public ResponseEntity<Double> CalculateVacationPayment(@RequestParam(name = "avg") Double averageAnniversarySalary,
                                                           @RequestParam(name = "days") int daysOfVacation,
                                                           @RequestParam(required = false, name = "fdate")
                                                           @DateTimeFormat(pattern = "dd.MM.yyyy")
                                                               LocalDate firstDateOfVacation,
                                                           @RequestParam(required = false, name = "ldate")
                                                               @DateTimeFormat(pattern = "dd.MM.yyyy")
                                                               LocalDate lastDateOfVacation) {
        if (firstDateOfVacation != null && lastDateOfVacation != null) {
            log.info("Пришел запрос на получение отпускных для {} дней при средней ЗП {} и определенными датами.",
                    daysOfVacation, averageAnniversarySalary);
            return ResponseEntity.ok(paymentService.calculatePaymentWithDates(averageAnniversarySalary, daysOfVacation,
                    firstDateOfVacation, lastDateOfVacation));
        }
        log.info("Пришел запрос на получение отпускных для {} дней при средней ЗП {} и без дат.",
                daysOfVacation, averageAnniversarySalary);
        return ResponseEntity.ok(paymentService.calculatePaymentWithoutDates(averageAnniversarySalary, daysOfVacation));
    }
}
