package ru.exdata.moex.utils;

import lombok.SneakyThrows;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class XmlParser {

    private volatile DocumentBuilder factory;

    public DocumentBuilder newInstance() {
        if (factory == null) {
            factory = createInstance();
            return factory;
        }
        return factory;
    }

    @SneakyThrows
    private DocumentBuilder createInstance() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(false);
        factory.setValidating(false);
        factory.setFeature("http://xml.org/sax/features/namespaces", false);
        factory.setFeature("http://xml.org/sax/features/validation", false);
        factory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
        factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        return factory.newDocumentBuilder();
    }

}
