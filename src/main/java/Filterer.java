import java.io.IOException;
import java.net.URL;
import java.io.*;
import java.util.HashMap;
import com.pdfcrowd.Pdfcrowd;

public class Filterer {

    public static void getPDF(String code) throws IOException {
        try {
            Pdfcrowd.HtmlToPdfClient client =
                    new Pdfcrowd.HtmlToPdfClient("ashviniyer21", "da6365e8ef9caf3d31ec3cd7b3c3dcb1");
            client.convertStringToFile(code, "Notes.pdf");
        } catch(Pdfcrowd.Error why) {
            System.err.println("Pdfcrowd Error: " + why);
            throw why;
        } catch(IOException why) {
            System.err.println("IO Error: " + why);
            throw why;
        }
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
            if(tempS.equals("Image") || tempS.equals("Retrieved")){
                bad = true;
            }
            if(s.charAt(i) == '>'){
                count--;
            } else if(s.charAt(i) == '}'){
                oCount--;
            }
        }
        return newS;
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
            while (null != (line = br.readLine()) && count < 8) {
                line = (line.trim());
                if(line.length() > 0 && line.charAt(0) == '='){
                    if(count > 1){
                        if(isGoodToPut(header, filter((text.toString())))){
                            values.put(header.replaceAll("=", ""), filter((text.toString())).replaceAll("Image", "").replaceAll("Retrieved", ""));
                        } else {
                            count--;
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