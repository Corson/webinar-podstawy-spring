package pl.training.shop.payments;

import org.javamoney.moneta.FastMoney;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FakePaymentServiceTest {

    private static final String PAYMENT_ID = "1";
    private static final FastMoney MONEY = LocalMoney.of(1_000);
    private static final PaymentRequest PAYMENT_REQUEST = PaymentRequest.builder()
            .money(MONEY)
            .build();

    private final PaymentIdGenerator paymentIdGenerator = mock(PaymentIdGenerator.class);
    private final FakePaymentService paymentService = new FakePaymentService(paymentIdGenerator);

    private Payment payment;

    @BeforeEach
    void setUp() {
        when(paymentIdGenerator.getNext()).thenReturn(PAYMENT_ID);
        payment = paymentService.process(PAYMENT_REQUEST);
    }

    @DisplayName("Should assign generated id to created payment")
    @Test
    void shouldAssignGeneratedIdToCreatedPayment() {
        assertEquals(PAYMENT_ID, payment.getId());
    }

    @DisplayName("Should assign money from payment request to created payment")
    @Test
    void shouldAssignMoneyFromPaymentRequestToCreatedPayment() {
        assertEquals(MONEY, payment.getMoney());
    }

    @DisplayName("Should assign timestamp to created payment")
    @Test
    void shouldAssignTimestampToCreatedPayment() {
        assertNotNull(payment.getTimestamp());
    }

    @DisplayName("Should assign STARTED status to created payment")
    @Test
    void shouldAssignStartedStatusToCreatedPayment() {
        assertEquals(PaymentStatus.STARTED, payment.getStatus());
    }

}