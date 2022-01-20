package com.zpi.transfergenerator.payment;

import com.zpi.transfergenerator.entity.Basket;
import iso.std.iso._20022.tech.xsd.pain_001_001.*;
import org.springframework.stereotype.Component;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.GregorianCalendar;

@Component
public class PaymentGenerator {
    private final AccountProvider accountProvider;
    private final RandomNumericStringProvider randomNumericStringProvider;

    public PaymentGenerator(AccountProvider accountProvider, RandomNumericStringProvider randomNumericStringProvider) {
        this.accountProvider = accountProvider;
        this.randomNumericStringProvider = randomNumericStringProvider;
    }


    public CustomerCreditTransferInitiationV03 generatePayment(Basket basket) {
        final var cstmrCdtTrfInitn = new CustomerCreditTransferInitiationV03();
        final var groupHeader = new GroupHeader32();
        final var gregorianCal = new GregorianCalendar();
        gregorianCal.setTime(basket.getTerm());

        XMLGregorianCalendar date;
        try {
            date = DatatypeFactory.newInstance()
                    .newXMLGregorianCalendar(gregorianCal);
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException("Failed to create payment", e);
        }
        groupHeader.setCreDtTm(date);
        groupHeader.setMsgId(basket.getName());

        final var ptyId = new PartyIdentification32();
        ptyId.setNm(basket.getName());
        groupHeader.setInitgPty(ptyId);
        groupHeader.setNbOfTxs(Integer.toString(basket.getTransfers().size()));

        final var account = accountProvider.getAccount();

        final var paymentInstructionInformation = new PaymentInstructionInformation3();
        paymentInstructionInformation.setPmtInfId(randomNumericStringProvider.getRandomString(5));
        paymentInstructionInformation.setPmtMtd(PaymentMethod3Code.TRF);
        paymentInstructionInformation.setReqdExctnDt(date);
        final var dbtr = new PartyIdentification32();
        dbtr.setNm(account.getName());
        paymentInstructionInformation.setDbtr(dbtr);
        final var dbtrAcct = new CashAccount16();
        final var accIdentifier = new AccountIdentification4Choice();
        accIdentifier.setIBAN(account.getIban());
        dbtrAcct.setId(accIdentifier);
        paymentInstructionInformation.setDbtrAcct(dbtrAcct);
        final var dbtrAgt = new BranchAndFinancialInstitutionIdentification4();
        final var finInstId = new FinancialInstitutionIdentification7();
        final var clrSysMmbId = new ClearingSystemMemberIdentification2();
        final var clrSysId = new ClearingSystemIdentification2Choice();
        clrSysId.setCd("PLKNR");
        clrSysMmbId.setClrSysId(clrSysId);
        clrSysMmbId.setMmbId(account.getIban().substring(4, 12));
        finInstId.setClrSysMmbId(clrSysMmbId);
        dbtrAgt.setFinInstnId(finInstId);
        paymentInstructionInformation.setDbtrAgt(dbtrAgt);

        final var transfers = new ArrayList<CreditTransferTransactionInformation10>();
        for (var transfer : basket.getTransfers()) {
            final var cdtTrfTxInf = new CreditTransferTransactionInformation10();
            final var pmtId = new PaymentIdentification1();
            pmtId.setEndToEndId(randomNumericStringProvider.getRandomString(10));
            pmtId.setInstrId(randomNumericStringProvider.getRandomString(5));
            cdtTrfTxInf.setPmtId(pmtId);

            final var amt = new AmountType3Choice();

            final var instdAmt = new ActiveOrHistoricCurrencyAndAmount();
            instdAmt.setCcy("PLN");
            instdAmt.setValue(BigDecimal.valueOf(transfer.getAmount()));
            amt.setInstdAmt(instdAmt);
            cdtTrfTxInf.setAmt(amt);

            final var cdtr = new PartyIdentification32();
            cdtr.setNm(transfer.getName());
            final var pstlAdr = new PostalAddress6();
            pstlAdr.getAdrLine().add(transfer.getStreet());
            pstlAdr.getAdrLine().add(transfer.getZipcode() + " " + transfer.getCity());
            cdtTrfTxInf.setCdtr(cdtr);

            final var cdtrAcct = new CashAccount16();
            final var id = new AccountIdentification4Choice();
            final var othr = new GenericAccountIdentification1();
            othr.setId(transfer.getIban().substring(2));
            id.setOthr(othr);
            cdtrAcct.setId(id);

            cdtTrfTxInf.setCdtrAcct(cdtrAcct);
            final var purp = new Purpose2Choice();
            purp.setPrtry("PLKR");

            cdtTrfTxInf.setPurp(purp);
            final var rmtInf = new RemittanceInformation5();
            rmtInf.getUstrd().add(transfer.getDescription());

            cdtTrfTxInf.setRmtInf(rmtInf);
            transfers.add(cdtTrfTxInf);
        }
        paymentInstructionInformation.getCdtTrfTxInf().addAll(transfers);
        cstmrCdtTrfInitn.setGrpHdr(groupHeader);
        cstmrCdtTrfInitn.getPmtInf().add(paymentInstructionInformation);

        return cstmrCdtTrfInitn;
    }
}
