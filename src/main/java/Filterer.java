import java.io.IOException;
import java.net.URL;
import java.io.*;
import java.util.HashMap;
import com.pdfcrowd.Pdfcrowd;

/**
 * Class used to get data for the slides
 */
public class Filterer {

    /**
     * Filters out the content from the read from a line of html to be used for the slides
     * @param s the line that needs to be filters
     * @return the filtered line
     */
    private static String filter(String s){
        String newS = "";
        String tempS = "";
        int count = 0;
        int oCount = 0;
        boolean skip = false;
        boolean bad = false;
        boolean hasLet = true;
        for(int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            char c2 = ' ';
            char c3 = ' ';
            if(i < s.length() - 1){
                c2 = s.charAt(i+1);
            }
            if(i < s.length() - 2){
                c3 = s.charAt(i+2);
            }
            if(s.charAt(i) == '<'){
                count++;

            } else if(s.charAt(i) == '{'){
                oCount++;
            }

            if (c == '.' && c2 == ' ') {
                tempS = "";
                bad = false;
                hasLet = false;
            }
            if(c == '.' && c2 != ' ' && !Character.isDigit(c2)){
                skip = true;
            }  else if(c == '.'){
                skip = false;
            }
            if(tempS.equals("Image") || tempS.equals("Retrieved") || tempS.equals("File")){
                bad = true;
            }
            if((Character.isLetterOrDigit(c) || c == '.' || c == ' ' || c == '-' || c == '=' || c == '(' || c == ')') && !skip && count == 0 && oCount == 0 && !bad){
                boolean run = true;
                if(!hasLet && Character.isDigit(c)){
                    bad = true;
                    run = false;
                } else if(Character.isLetter(c)){
                    hasLet = true;
                } else if(c == '(' && !hasLet){
                    run = false;
                }
                if(run){
                    newS += (c);
                    tempS += (c);
                }
            }
            if(s.charAt(i) == '>'){
                count--;
            } else if(s.charAt(i) == '}'){
                oCount--;
            }
        }
        return newS;
    }

    /**
     * Returns all of the headers and content for each slide that has to be created
     * @param subject the subject of topic that has been searched
     * @return a HashMap <Header, Content> to be used for creating the slides
     * @throws IOException if the topic is not found as a wikipedia page
     */
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
                            values.put(header.replaceAll("=", ""), filter((text.toString().replaceAll("Retrieved on ", ""))).replaceAll("Image", "").replaceAll("File", ""));
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

    /**
     * Function that determines if the header and body match specifications to be added to the slides
     * @param header the header string to be added
     * @param body the body string to be added
     * @return true if it should be added to the slides; otherwise false
     */
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