import com.pdfcrowd.Pdfcrowd;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Servlet class used when generating the PDF
 */
@WebServlet(name = "PDFServlet", urlPatterns = {"/pdf"})
public class PDFServlet extends HttpServlet {

    /**
     * Generates the html code to display a PDF of the slides
     * @param request is a part of the function in HttpServlet, has no purpose for {@link PDFServlet}
     * @param response is used to get the output stream to write the html code to
     * @throws IOException If the request or the response is not valid
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        String s = WikiServlet.getLastHTMLCode();
        getPDF(s);
        downloadPDF(response);
    }

    /**
     * Outputs a PDF to be displayed in the web browser
     * @param response is used to get the output stream for the code to display the PDF
     */
    private void downloadPDF(HttpServletResponse response) {
        response.setContentType("application/pdf");
        try {
            File f = new File("notes.pdf");
            FileInputStream fis = new FileInputStream(f);
            DataOutputStream os = new DataOutputStream(response.getOutputStream());
            response.setHeader("Content-Length",String.valueOf(f.length()));
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = fis.read(buffer)) >= 0) {
                os.write(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Converts HTML code to a PDF called notes.pdf
     * @param code the HTML code as a String
     * @throws IOException if the HTML code is not found
     */
    private void getPDF(String code) throws IOException {
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
}