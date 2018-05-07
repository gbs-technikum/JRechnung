package com.sabel.JRechnung.model;

import com.sabel.JRechnung.Publisher;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.events.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;

public class Configuration {

    public static final String CONFIG_FILE_NAME = "config.xml";
    private String wordFileExportPath;
    private String wordTemplate;

    public Configuration() {
        this.wordFileExportPath = null;
        this.wordTemplate = null;
    }

    public void loadConfigFile() throws FileNotFoundException {
        try {
            File configFile = new File(Publisher.getModel().getJarContainingFolder(com.sabel.JRechnung.Main.class) + "//" + CONFIG_FILE_NAME);

            parseConfigXML(configFile);
        } catch (Exception e) {
            throw new FileNotFoundException(CONFIG_FILE_NAME);
        }
    }

    private void parseConfigXML(File configFile) throws FileNotFoundException {
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader eventReader =
                    factory.createXMLEventReader(new FileReader(configFile.getAbsoluteFile()));

            while(eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                switch(event.getEventType()) {

                    case XMLStreamConstants.START_ELEMENT:
                        StartElement startElement = event.asStartElement();
                        String qName = startElement.getName().getLocalPart();

                        if (qName.equalsIgnoreCase("export_path")) {
                            Iterator<Attribute> attributes = startElement.getAttributes();
                            this.wordFileExportPath = attributes.next().getValue();
                        }else if (qName.equalsIgnoreCase("word_template")) {
                            Iterator<Attribute> attributes = startElement.getAttributes();
                            this.wordTemplate = attributes.next().getValue();
                        }
                        break;

                    case XMLStreamConstants.END_ELEMENT:
                        EndElement endElement = event.asEndElement();

                        if(endElement.getName().getLocalPart().equalsIgnoreCase("config")) {

                        }
                        break;
                }
            }
        } catch (Exception e) {
            throw new FileNotFoundException(CONFIG_FILE_NAME);
        }
    }

    public String getWordFileExportPath() {
        return wordFileExportPath;
    }

    public String getWordTemplate() {
        return wordTemplate;
    }
}
