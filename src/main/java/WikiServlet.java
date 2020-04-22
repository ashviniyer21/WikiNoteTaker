import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;

import javax.print.Doc;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "WikiServlet", urlPatterns = {"/pdf"})
public class WikiServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        String s = request.getParameter("name");
        try {
            Filterer.getPDF(s);
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
        PrintWriter out = response.getWriter();
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
}
