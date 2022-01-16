package com.zpi.transfergenerator.payment;

import iso.std.iso._20022.tech.xsd.pain_001_001.Document;
import iso.std.iso._20022.tech.xsd.pain_001_001.ObjectFactory;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.StringWriter;

@Component
public class XmlPaymentFactory {
    public String generateXmlString(Document document) {
        try {
            final var jc = JAXBContext.newInstance(Document.class);
            final var objectFactory = new ObjectFactory();
            final var doc = objectFactory.createDocument(document);
            final var marshaller = jc.createMarshaller();
            final var stringWriter = new StringWriter();
            marshaller.marshal(doc, stringWriter);
            return stringWriter.toString();
        } catch (JAXBException e) {
            throw new XmlGenerationException("Failed to generate xml file", e);
        }
    }
}
