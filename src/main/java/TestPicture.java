import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;

public class TestPicture {
    public static void main(String[] args) throws IOException, DocumentException {
        ArrayList<String> validURL = new ArrayList<>();
        ArrayList<String> pictureName = new ArrayList<>();
        Document pictureDoc = new Document();
        PdfWriter.getInstance(pictureDoc, new FileOutputStream("CirclePictures.pdf"));
        pictureDoc.open();
        for(String testURL : insertPictures(validURL)){
            Image image = Image.getInstance(new URL(testURL));
            pictureDoc.add(image);
        }
        pictureDoc.close();

    }

     private static ArrayList<String> insertPictures(ArrayList<String> url) throws IOException {
         for(int i = 0; i<10; i++) {
             for(int j = 0; j<10; j++) {
                 String imageURL = "https://upload.wikimedia.org/wikipedia/commons/thumb/" + i + "/" + i + j + "/CIRCLE_LINES.svg/440px-CIRCLE_LINES.svg.png";
                 if (isValidURL(imageURL)) {
                     url.add(imageURL);
                 }
             }
             for(char k = 'A'; k <= 'Z';k++){
                 String imageURL = "https://upload.wikimedia.org/wikipedia/commons/thumb/" + i + "/" + i + String.valueOf(k) + "/CIRCLE_LINES.svg/440px-CIRCLE_LINES.svg.png";
             }
         }
         return url;

     }
    private static boolean isValidURL(String url) throws IOException {
        try {
            BufferedImage image = ImageIO.read(new URL(url));
            if (image != null) {
                return true;
            } else {
                return false;
            }
        } catch(Exception e){
            return false;
        }

    }
/*    private static ArrayList<String> nameOfPicture (ArrayList<String> name){

    }*/
}



