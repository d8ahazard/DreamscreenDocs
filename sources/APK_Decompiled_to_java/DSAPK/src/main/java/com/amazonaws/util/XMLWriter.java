package com.amazonaws.util;

import com.amazonaws.AmazonClientException;
import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.util.Stack;

public class XMLWriter {
    private static final String PROLOG = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    private Stack<String> elementStack;
    private boolean rootElement;
    private final Writer writer;
    private final String xmlns;

    public XMLWriter(Writer w) {
        this(w, null);
    }

    public XMLWriter(Writer w, String xmlns2) {
        this.elementStack = new Stack<>();
        this.rootElement = true;
        this.writer = w;
        this.xmlns = xmlns2;
        append(PROLOG);
    }

    public XMLWriter startElement(String element) {
        append("<" + element);
        if (this.rootElement && this.xmlns != null) {
            append(" xmlns=\"" + this.xmlns + "\"");
            this.rootElement = false;
        }
        append(">");
        this.elementStack.push(element);
        return this;
    }

    public XMLWriter endElement() {
        append("</" + ((String) this.elementStack.pop()) + ">");
        return this;
    }

    public XMLWriter value(String s) {
        append(escapeXMLEntities(s));
        return this;
    }

    public XMLWriter value(Date date) {
        append(escapeXMLEntities(StringUtils.fromDate(date)));
        return this;
    }

    public XMLWriter value(Object obj) {
        append(escapeXMLEntities(obj.toString()));
        return this;
    }

    private void append(String s) {
        try {
            this.writer.append(s);
        } catch (IOException e) {
            throw new AmazonClientException("Unable to write XML document", e);
        }
    }

    private String escapeXMLEntities(String s) {
        if (s.contains("&")) {
            s = s.replace("&quot;", "\"").replace("&apos;", "'").replace("&lt;", "<").replace("&gt;", ">").replace("&amp;", "&");
        }
        return s.replace("&", "&amp;").replace("\"", "&quot;").replace("'", "&apos;").replace("<", "&lt;").replace(">", "&gt;");
    }
}
