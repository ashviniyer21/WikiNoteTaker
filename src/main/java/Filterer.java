
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Image;
import org.apache.pdfbox.pdmodel.PDDocument;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.FileOutputStream;
import java.io.*;
import java.util.HashMap;

public class Filterer {

    public static void main(String... args) throws IOException, DocumentException {
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
        System.out.println(getHTMLStuff(subject));



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

    public static String getHTMLStuff(String subject) throws IOException {
        URL url = new URL("https://en.wikipedia.org/w/index.php?action=raw&title=" + subject.replace(" ", "_"));
        StringBuilder text = new StringBuilder();
        Font font = FontFactory.getFont(FontFactory.COURIER, 13, BaseColor.BLACK);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()))) {
            String line;
            while (null != (line = br.readLine())) {
                line = line.trim();
                text.append((line)).append("\n");
            }
        }
        return text.toString();
    }

    private static String filter(String s){
        StringBuilder newS = new StringBuilder();
        int count = 0;
        int oCount = 0;
        boolean skip = false;
        for(int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            char c2 = ' ';
            if(i < s.length() - 1){
                c2 = s.charAt(i+1);
            }
            if(s.charAt(i) == '<'){
                count++;

            } else if(s.charAt(i) == '{'){
                oCount++;
            }
//            if(c == '<' || c == '{' || c =='[' ){
//                c = '(';
//            } else if(c == '>' || c == '}' || c == ']'){
//                c = ')';
//            }
            if(c == '.' && c2 != ' ' && !Character.isDigit(c2)){
                skip = true;
            }  else if(c == '.'){
                skip = false;
            }
            if((Character.isLetterOrDigit(c) || c == '.' || c == ' ' || c == '-' || c == '=' || c == ':' || c == '(' || c == ')') && !skip){
                newS.append(c);
            } else if(c == '|'){
//                newS.append(" or ");
            }
            if(s.charAt(i) == '>'){
                count--;
            } else if(s.charAt(i) == '}'){
                oCount--;
            }
        }
        return newS.toString();
    }

    public static HashMap<String, String> getValues(String subject) throws IOException {
        URL url = new URL("https://en.wikipedia.org/w/index.php?action=raw&title=" + subject.replace(" ", "_"));
        HashMap<String, String> values = new HashMap<>();
        int count = 0;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()))) {
            String line;
            StringBuilder text = new StringBuilder();
            String header = "";
            while (null != (line = br.readLine()) && count < 6) {
                line = (line.trim());
                if(line.length() > 0 && line.charAt(0) == '=' && !text.toString().equals("")){
                    if(count > 0){
                        if(count == 1){
                            String stuff = filter(text.toString());
                            values.put(stuff.substring(0, 17), stuff.substring(17));
                        } else {
                            values.put(header.replaceAll("=", ""), filter(text.toString()));
                        }
                        header = line;
                        text = new StringBuilder();
                    }
                    count++;
                } else {
                    text.append(line);
                }
            }
        }
        return values;
    }
}
