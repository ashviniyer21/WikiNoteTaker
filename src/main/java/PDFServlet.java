import com.itextpdf.text.DocumentException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet(name = "PDFServlet", urlPatterns = {"/wiki"})
public class PDFServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String s = WikiServlet.getLastString();
        try {
            Filterer.getPDF(s);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        downloadPDF(request, response);
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