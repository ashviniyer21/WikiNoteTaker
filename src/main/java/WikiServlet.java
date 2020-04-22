import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;

import javax.print.Doc;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet(name = "WikiServlet", urlPatterns = {"/pdf"})
public class WikiServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        String s = request.getParameter("name");
        try {
            Filterer.main();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Title of the document</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>PDF Example</h1>");
        out.println("<p>Open a PDF file <a href=\"notes.pdf\">example</a>.</p>");
        out.println("</body>");
        out.println("</html>");
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        String s = request.getParameter("name");
        try {
            Filterer.getPDF(s);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        downloadPDF(request, response);
        PrintWriter out = response.getWriter();
//        out.println(Filterer.getHTMLStuff(s));
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Title of the document</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>PDF Example</h1>");
        out.println("<p>Open a PDF file <a href=\"PDF on " + s + ".pdf\">example</a>.</p>");
        out.println("</body>");
        out.println("</html>");
        out.flush();
    }

    public void downloadPDF(HttpServletRequest request, HttpServletResponse response)
            throws IOException{
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
}
