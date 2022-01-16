package com.zpi.transfergenerator.controller;

import com.zpi.transfergenerator.model.PaymentGeneratorRequest;
import com.zpi.transfergenerator.payment.PaymentGenerator;
import com.zpi.transfergenerator.payment.XmlPaymentFactory;
import com.zpi.transfergenerator.repository.BasketRepository;
import iso.std.iso._20022.tech.xsd.pain_001_001.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("generatePayment")
public class PaymentGeneratorController {
    private final PaymentGenerator paymentGenerator;
    private final BasketRepository basketRepository;
    private final XmlPaymentFactory xmlPaymentFactory;

    public PaymentGeneratorController(PaymentGenerator paymentGenerator, BasketRepository basketRepository, XmlPaymentFactory xmlPaymentFactory) {
        this.paymentGenerator = paymentGenerator;
        this.basketRepository = basketRepository;
        this.xmlPaymentFactory = xmlPaymentFactory;
    }

    @PostMapping(produces = "application/xml")
    public ResponseEntity<String> generatePayment(@RequestBody PaymentGeneratorRequest request) {
        final var basket = basketRepository.findById(request.getId());
        final var outputDocument = new Document();
        return basket.map(value -> {
            outputDocument.setCstmrCdtTrfInitn(paymentGenerator.generatePayment(value));
            return new ResponseEntity<>(xmlPaymentFactory.generateXmlString(outputDocument), HttpStatus.CREATED);

        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
