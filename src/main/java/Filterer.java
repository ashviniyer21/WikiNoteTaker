
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Image;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.fit.pdfdom.PDFDomTree;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.FileOutputStream;
import java.io.*;

public class Filterer {
    private static void main(String... args) throws IOException, DocumentException {
        String subject = "Circle";
        URL url = new URL("https://en.wikipedia.org/w/index.php?action=raw&title=" + subject.replace(" ", "_"));
        StringBuilder text = new StringBuilder();
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("notes.pdf"));
        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 13, BaseColor.BLACK);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()))) {
            String line;
            while (null != (line = br.readLine())) {
                line = line.trim();
//                if (!line.startsWith("|")
//                        && !line.startsWith("{")
//                        && !line.startsWith("}")
//                        && !line.startsWith("<center>")
//                        && !line.startsWith("---")) {
                    text.append(line).append("\n");
                    Paragraph chunk = new Paragraph(filter(line) + "\n", font);
                    if(line.length() > 0){
                        document.add(chunk);
                    }
//                }
            }
        }
        String imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/0/06/CIRCLE_LINES.svg/440px-CIRCLE_LINES.svg.png";
        Image image = Image.getInstance(new URL(imageUrl));
        document.add(image);
        String t = filter(text.toString());

        document.close();




    }

    public static void getPDF(String subject) throws IOException, DocumentException {
        URL url = new URL("https://en.wikipedia.org/w/index.php?action=raw&title=" + subject.replace(" ", "_"));
        StringBuilder text = new StringBuilder();
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("notes.pdf"));
        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 13, BaseColor.BLACK);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()))) {
            String line;
            while (null != (line = br.readLine())) {
                line = line.trim();
                text.append(line).append("\n");
                Paragraph chunk = new Paragraph(filter(line) + "\n", font);
                if(line.length() > 0){
                    document.add(chunk);
                }
            }
        }
        String imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/0/06/CIRCLE_LINES.svg/440px-CIRCLE_LINES.svg.png";
        Image image = Image.getInstance(new URL(imageUrl));
        document.add(image);

        document.close();
    }

    private static String filter(String s){
        StringBuilder newS = new StringBuilder();
        int count = 0;
        int oCount = 0;
        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) == '<'){
                count++;
            } else if(s.charAt(i) == '{'){
                oCount++;
            }
            if(s.charAt(i) != '[' && s.charAt(i) != ']' && s.charAt(i) != '\'' && s.charAt(i) != '|' && count == 0 && oCount == 0){
                newS.append(s.charAt(i));
            }
            if(s.charAt(i) == '>'){
                count--;
            } else if(s.charAt(i) == '}'){
                oCount--;
            }
        }
        return newS.toString();
    }

    private static void generateHTMLFromPDF(String filename) throws IOException, ParserConfigurationException {
        PDDocument pdf = PDDocument.load(new File(filename));
        Writer output = new PrintWriter("src/main/webapp/pdf.html", "utf-8");
        new PDFDomTree().writeText(pdf, output);

        output.close();
    }
}
