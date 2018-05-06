/*
package com.sabel.JRechnung;



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Section;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class TestApachePOI {
        public static void main(String[] args){
            String filePath = "C:\\Users\\Mirko\\Projekte\\sample.docx";
            POIFSFileSystem fs = null;
            try {
                fs = new POIFSFileSystem(new FileInputStream(filePath));
                XWPFDocument doc = new XWPFDocument(fs);
                doc = replaceText(doc, "$VAR", "MyValue1");
                saveWord(filePath, doc);
            }
            catch(FileNotFoundException e){
                e.printStackTrace();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }

        private static HWPFDocument replaceText(HWPFDocument doc, String findText, String replaceText){
            Range r1 = doc.getRange();

            for (int i = 0; i < r1.numSections(); ++i ) {
                Section s = r1.getSection(i);
                for (int x = 0; x < s.numParagraphs(); x++) {
                    Paragraph p = s.getParagraph(x);
                    for (int z = 0; z < p.numCharacterRuns(); z++) {
                        CharacterRun run = p.getCharacterRun(z);
                        String text = run.text();
                        if(text.contains(findText)) {
                            run.replaceText(findText, replaceText);
                        }
                    }
                }
            }
            return doc;
        }

        private static void saveWord(String filePath, HWPFDocument doc) throws FileNotFoundException, IOException{
            FileOutputStream out = null;
            try{
                out = new FileOutputStream(filePath);
                doc.write(out);
            }
            finally{
                out.close();
            }
        }
    }

*/