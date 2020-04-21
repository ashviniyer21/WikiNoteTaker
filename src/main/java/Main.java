import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.*;
import java.net.URL;

public class Main {
    public static void main(String... args) throws IOException, DocumentException {
        String subject = "Circle";
        URL url = new URL("https://en.wikipedia.org/w/index.php?action=raw&title=" + subject.replace(" ", "_"));
        StringBuilder text = new StringBuilder();
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("PDF on " + subject));
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
        String t = filter(text.toString());
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
}
