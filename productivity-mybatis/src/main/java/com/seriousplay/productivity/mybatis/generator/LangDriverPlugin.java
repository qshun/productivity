package com.seriousplay.productivity.mybatis.generator;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author changqingshun
 */
public class LangDriverPlugin extends PluginAdapter {
    static List<String> elements = Arrays.asList("sql", "select","insert","update","delete");
    private String lang = "xml";

    /**
     * @param element
     */
    void addLangAttribute(XmlElement element) {
        element.addAttribute(new Attribute("lang", getProperties().getProperty("lang", lang)));
    }

    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        List<XmlElement> addLangElements = new ArrayList<>(document.getRootElement().getElements().size());
        document.getRootElement().getElements().forEach(one -> {
            XmlElement element = (XmlElement) one;
            if (elements.contains(((XmlElement) one).getName())) {
                if ("sql".equals(element.getName())) {
                    Iterator<Attribute> attributes = element.getAttributes().iterator();
                    while (attributes.hasNext()) {
                        Attribute attr = attributes.next();
                        String attrname = attr.getName();
                        if ("id".equals(attrname)) {
                            if (attr.getValue().contains("Example_Where_Clause")) {
                                addLangElements.add(element);
                            }
                        } else {
                            continue;
                        }
                    }
                }else {
                    addLangElements.add(element);
                }

            }else {

            }
        });
        if (addLangElements.size() > 0) {
            addLangElements.forEach(this::addLangAttribute);
        }
        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }
}
