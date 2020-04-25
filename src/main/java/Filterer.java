
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Image;

import java.io.IOException;
import java.net.URL;
import java.io.FileOutputStream;
import java.io.*;
import java.util.HashMap;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.jsoup.Jsoup;
import org.w3c.tidy.Tidy;

public class Filterer {
    private static final String API_KEY = "869c9de4e1ea4246968439ef10d55c36";
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
    private static String initialFilter(String s){
//        if(s!= null){
//            org.jsoup.nodes.Document document = Jsoup.parse(s);
//            document.select("img").remove();
//            s = document.toString();
//        }
        return s;
    }
    private static String filter(String s){
        String newS = "";
        String tempS = "";
        int count = 0;
        int oCount = 0;
        boolean skip = false;
        boolean bad = false;
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
            if (c == '.' && c2 == ' ') {
                tempS = "";
                bad = false;
            }
            if(c == '.' && c2 != ' ' && !Character.isDigit(c2)){
                skip = true;
            }  else if(c == '.'){
                skip = false;
            }
            if((Character.isLetterOrDigit(c) || c == '.' || c == ' ' || c == '-' || c == '=' || c == '(' || c == ')') && !skip && count == 0 && oCount == 0 && !bad){
                newS += (c);
                tempS += (c);
            }
            if(tempS.equals("Image")){
                bad = true;
                newS.replaceAll("Image", "");
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
        HashMap<String, String> values = new HashMap<>();
        URL url;
        try {
            url = new URL("https://en.wikipedia.org/w/index.php?action=raw&title=" + subject.replace(" ", "_"));
        } catch (Exception e){
            return values;
        }
        int count = 0;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()))) {
            String line;
            StringBuilder text = new StringBuilder();
            String header = "";
            boolean hasCategory = false;
            while (null != (line = br.readLine()) && count < 8) {
                line = (line.trim());
                if(line.length() > 0 && line.charAt(0) == '='){
                    hasCategory = true;
                    if(count > 1){
                        if(count == 1){
//                            String stuff = filter(text.toString());
//                            values.put(stuff.substring(0, 17), stuff.substring(17));
                        } else {
                            if(isGoodToPut(header, filter(initialFilter(text.toString())))){
                                values.put(header.replaceAll("=", ""), filter(initialFilter(text.toString())).replaceAll("Image", "").replaceAll("Retrieved", ""));
                            } else {
                                count--;
                            }
                        }
                        header = line;
                        text = new StringBuilder();
                    }
                    count++;
                } else {
                    text.append(line).append("\n");
                }
            }
        }
        return values;
    }
    public static void getPDF2(String htmlCode) throws Exception {
//        Document document = new Document();
//        PdfWriter.getInstance(document, new FileOutputStream("notes.pdf"));
//        document.open();
//        document.close();
        Tidy tidy = new Tidy();
        tidy.setXHTML(true);
        tidy.setQuiet(true);
        tidy.setShowWarnings(false);
        PdfRendererBuilder builder = new PdfRendererBuilder();
        builder.useFastMode();
        builder.withUri(tidy.parseDOM(new ByteArrayInputStream(htmlCode.getBytes()), new FileOutputStream("document.xml")).toString());
        builder.toStream(new FileOutputStream("notes.pdf"));
        builder.run();
    }

    private static boolean isGoodToPut(String header, String body){
        if(header.replaceAll("=", "").equals("")){
            return false;
        }
        int perCount = 0;
        for(int i = 0; i < body.length()-1; i++){
            if(body.charAt(i) == '.' && body.charAt(i+1) == ' '){
                perCount++;
            }
        }
        return perCount > 2;
    }
}
