package com.pay.vacation.payment;

import com.pay.vacation.payment.controller.PaymentController;
import com.pay.vacation.payment.exceptions.NegativeNumberException;
import com.pay.vacation.payment.exceptions.WrongDateException;
import com.pay.vacation.payment.repository.interfaces.DaoStorageInterface;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class PaymentApplicationTests {
	private final PaymentController paymentController;
	private final DaoStorageInterface storageInterface;

	@Autowired
	PaymentApplicationTests(PaymentController paymentController,
							DaoStorageInterface storageInterface) {
		this.paymentController = paymentController;
		this.storageInterface = storageInterface;
	}

	@Test
	public void shouldBeEqualIfQueryWithSpecificDates() {
		assertEquals(ResponseEntity.ok(1500.0), paymentController.
												CalculateVacationPayment(60000.0, 5,
												LocalDate.now(), LocalDate.now().plusDays(1)));
	}

	@Test
	public void shouldBeEqualIfQueryWithoutSpecificDates() {
		assertEquals(1250.0, storageInterface.calculatePaymentWithoutDates(60000.0, 5));
	}

	@Test
	public void shouldThrowWrongDateExceptionEqualIfGivenWrongFirstDate() {
		final WrongDateException exception = assertThrows(WrongDateException.class, () -> paymentController.CalculateVacationPayment(60000.0, 5,
				LocalDate.of(2023, 5, 13), LocalDate.now().plusDays(1)));
		assertEquals("Передана неподходящая дата.", exception.getMessage());
	}

	@Test
	public void shouldThrowWrongDateExceptionEqualIfGivenWrongLastDate() {
		final WrongDateException exception = assertThrows(WrongDateException.class, () -> paymentController.CalculateVacationPayment(60000.0, 5,
				LocalDate.now(), LocalDate.now()));
		assertEquals("Передана неподходящая дата.", exception.getMessage());
	}

	@Test
	public void shouldThrowValidationExceptionIfGivenNegativeSalaryParam() {
		final NegativeNumberException exception = assertThrows(NegativeNumberException.class, () -> paymentController.CalculateVacationPayment(-60000.0, 5,
				LocalDate.now(), LocalDate.now().plusDays(1)));
		assertEquals("Передано отрицательное значение ЗП.", exception.getMessage());
	}

	@Test
	public void shouldThrowValidationExceptionIfGivenNegativeVacationDaysParam() {
		final NegativeNumberException exception = assertThrows(NegativeNumberException.class, () -> paymentController.CalculateVacationPayment(60000.0, -5,
				LocalDate.now(), LocalDate.now().plusDays(1)));
		assertEquals("Передано отрицательное значение количества отпускных дней.", exception.getMessage());
	}
}