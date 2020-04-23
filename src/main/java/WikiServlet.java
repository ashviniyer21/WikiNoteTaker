import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;

import javax.print.Doc;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;

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
        String s = request.getParameter("title");
//        try {
//            Filterer.getPDF(s);
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        }
//        downloadPDF(request, response);
        PrintWriter out = response.getWriter();
//        out.println("<html>");
//        out.println("<head>");
//        out.println("<title>Title of the document</title>");
//        out.println("</head>");
//        out.println("<body>");
//        out.println("<h1>PDF Example</h1>");
//        out.println("<p>Open a PDF file <a href=\"PDF on " + s + ".pdf\">example</a>.</p>");
//        out.println("</body>");
//        out.println("</html>");
        String s1 = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "<!--    <link rel = \"stylesheet\" type = \"text/css\" href = \"css/slides.css\" />-->\n" +
                "    <style>\n" +
                "        body{\n" +
                "            font-family: Arial, Helvetica, sans-serif;\n" +
                "            background-color: #121212;\n" +
                "            color: white;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        h1{\n" +
                "            text-transform: uppercase;\n" +
                "            font-size: 48px;\n" +
                "            line-height: 0;\n" +
                "            line-height: 1.5;\n" +
                "        }\n" +
                "\n" +
                "        #container{\n" +
                "\n" +
                "            margin: 1em auto;\n" +
                "            width: 75%;\n" +
                "            min-width: 450px;\n" +
                "            border: 15px solid red;\n" +
                "            background-color: tomato;\n" +
                "            border-radius: 10px;\n" +
                "            display: flex;\n" +
                "            flex-direction: column;\n" +
                "        }\n" +
                "\n" +
                "        .slide{\n" +
                "            text-align: center;\n" +
                "            margin: 2em auto;\n" +
                "            width: 90%;\n" +
                "            border: 10px solid black;\n" +
                "            border-radius: 5px;\n" +
                "            background-color: #191919 ;\n" +
                "            /*\n" +
                "            display: flex;\n" +
                "            flex-direction: column;\n" +
                "            */\n" +
                "            line-height: 1.5;\n" +
                "            color: white;\n" +
                "            overflow: hidden;\n" +
                "        }\n" +
                "\n" +
                "        .slideHeading{\n" +
                "\n" +
                "            font-size: 24px;\n" +
                "            border: 2px solid white;\n" +
                "\n" +
                "        }\n" +
                "\n" +
                "        .content{\n" +
                "            font-size: 18px;\n" +
                "            text-align: left;\n" +
                "            border: 2px solid white;\n" +
                "        }\n" +
                "    </style>\n" +
                "    <title> Notes </title>\n" +
                "\n" +
                "</head>\n" +
                "<body>\n" +
                "<h1 id = \"title\"> </h1>\n" +
                "<div id = \"container\">\n" +
                "\n" +
                "</div>\n" +
                "<script>\n" +
                "    const container = document.querySelector(\"#container\");\n" +
                "    const title = document.querySelector(\"#title\");\n" +
                "\n" +
                "    let titleString = decodeURIComponent(window.location.search);\n" +
                "    titleString = titleString.substring(titleString.indexOf(\"=\") + 1);\n" +
                "\n" +
                "    title.textContent = titleString;\n" +
                "\n" +
                "    let slides = [];\n" +
                "\n" +
                "\n" +
                "    createSampleSlides();\n" +
                "\n" +
                "    function slide(heading, body){\n" +
                "        this.heading = heading;\n" +
                "        this.body = body; // paragraph text\n" +
                "    }\n" +
                "\n" +
                "    function createSlides(){\n" +
                "\n" +
                "        for(let i=0; i<slides.length; i++){\n" +
                "\n" +
                "            const slide = document.createElement(\"div\");\n" +
                "            slide.classList = \"slide\";\n" +
                "            slide.value = i;\n" +
                "\n" +
                "            let slideTitle = slides[i].heading;\n" +
                "            let slideBody = slides[i].body;\n" +
                "            sentences = slideBody.split(\". \");\n" +
                "\n" +
                "            const sildeHeading = document.createElement(\"div\");\n" +
                "            sildeHeading.textContent = slideTitle;\n" +
                "            sildeHeading.classList = \"slideHeading\";\n" +
                "\n" +
                "            const content = document.createElement(\"div\");\n" +
                "            content.classList = \"content\";\n" +
                "\n" +
                "            const list = document.createElement(\"ul\");  //create list for bullet points\n" +
                "\n" +
                "            for(let j=0; j<sentences.length; j++){\n" +
                "                const bullet = document.createElement(\"li\");\n" +
                "                bullet.classList = \"bullet\";\n" +
                "                bullet.textContent = sentences[j];\n" +
                "                list.appendChild(bullet);\n" +
                "            }\n" +
                "\n" +
                "            content.appendChild(list);\n" +
                "\n" +
                "            slide.appendChild(sildeHeading);\n" +
                "            slide.appendChild(content);\n" +
                "            container.appendChild(slide);\n" +
                "\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    function createSampleSlides(){";
//                String s2 = "        slides.push(new slide(\"Definition\", \"A circle is a shape consisting of all points in a plane that are a given distance from a given point, the centre; equivalently it is the curve traced out by a point that moves in a plane so that its distance from a given point is constant. The distance between any point of the circle and the centre is called the radius. This article is about circles in Euclidean geometry, and, in particular, the Euclidean plane, except where otherwise noted. Specifically, a circle is a simple closed curve that divides the plane into two regions: an interior and an exterior. In everyday use, the term circle may be used interchangeably to refer to either the boundary of the figure, or to the whole figure including its interior; in strict technical usage, the circle is only the boundary and the whole figure is called a disc. A circle may also be defined as a special kind of ellipse in which the two foci are coincident and the eccentricity is 0, or the two-dimensional shape enclosing the most area per unit perimeter squared, using calculus of variations.\"));\n" +
//                "        slides.push(new slide(\"Euclid's definition\", \"A circle is a plane figure bounded by one curved line, and such that all straight lines drawn from a certain point within it to the bounding line, are equal. The bounding line is called its circumference and the point, its centre.\" ));";
                String s3 = "\n" +
                "        createSlides();\n" +
                "\n" +
                "    }\n" +
                "</script>\n" +
                "</body>\n" +
                "</html>";
//        out.println(1);
        out.println(s1);
        HashMap<String, String> slideContent = Filterer.getValues(s);
//        HashMap<String, String> slideContent = new HashMap<>();
//        slideContent.put("hi", "test");
        System.out.println(slideContent.size());
        boolean first = true;
        for(String value: slideContent.keySet()){

//            System.out.println("slides.push(new slide(\"" + oFilter(value) + "\", \"" + oFilter(slideContent.get(value)) + "\" ));");
//            if(first){
                out.println("slides.push(new slide(\"" + oFilter(value) + "\", \"" + oFilter(slideContent.get(value)) + "\" ));");
//                first = false;
//            }
        }
//        out.println("slides.push(new slide(\"\", \"name       = Circleimage      = Circle-withsegments.svgcaption    = A circle (black), which is measured by its circumference (C), diameter (D) in cyan, and radius (R) in red; its centre (O) is in magenta.}A circle is a shape consisting of all point (geometry)points in a plane (mathematics)plane that are a given distance from a given point, the Centre (geometry)centre; equivalently it is the curve traced out by a point that moves in a plane so that its distance from a given point is Constant (mathematics)constant. The distance between any point of the circle and the centre is called the radius. This article is about circles in Euclidean geometry, and, in particular, the Euclidean plane, except where otherwise noted.Specifically, a circle is a Simple polygonsimple closed curve that divides the plane into two Region (mathematics)regions: an interior (topology)interior and an exterior. In everyday use, the term \"circle\" may be used interchangeably to refer to either the boundary of the figure, or to the whole figure including its interior; in strict technical usage, the circle is only the boundary and the whole figure is called a Disk (mathematics)disc.A circle may also be defined as a special kind of ellipse in which the two Focus (geometry)foci are coincident and the eccentricity (mathematics)eccentricity is 0, or the two-dimensional shape enclosing the most area per unit perimeter squared, using calculus of variations.\" ));");
//        out.println("slides.push(new slide(\"hi\", \"test\" ))");
//        out.println("slides.push(new slide(\"==Analytic results==\", \"\" ));");
//        out.println("slides.push(new slide(\"==Terminology==\", \"* Annulus (mathematics)Annulus: a ring-shaped object, the region bounded by two concentric circles.* Arc (geometry)Arc: any Connected spaceconnected part of a circle. Specifying two end points of an arc and a center allows for two arcs that together make up a full circle.* Centre (geometry)Centre: the point equidistant from all points on the circle.* Chord (geometry)Chord: a line segment whose endpoints lie on the circle, thus dividing a circle into two segments.* Circumference: the length of one circuit along the circle, or the distance around the circle.* Diameter: a line segment whose endpoints lie on the circle and that passes through the centre; or the length of such a line segment. This is the largest distance between any two points on the circle. It is a special case of a chord, namely the longest chord for a given circle, and its length is twice the length of a radius.* Disk (mathematics)Disc: the region of the plane bounded by a circle.* Lens (geometry)Lens: the region common to (the intersection of) two overlapping discs.* Passant: a coplanar straight line that has no point in common with the circle.* Radius: a line segment joining the centre of a circle with any single point on the circle itself; or the length of such a segment, which is half (the length of) a diameter.* Circular sectorSector: a region bounded by two radii of equal length with a common center and either of the two possible arcs, determined by this center and the endpoints of the radii.* Circular segmentSegment: a region bounded by a chord and one of the arcs connecting the chords endpoints. The length of the chord imposes a lower boundary on the diameter of possible arcs. Sometimes the term segment is used only for regions not containing the center of the circle to which their arc belongs to.* Secant lineSecant: an extended chord, a coplanar straight line, intersecting a circle in two points.* Semicircle: one of the two possible arcs determined by the endpoints of a diameter, taking its midpoint as center. In non-technical common usage it may mean the interior of the  two dimensional region bounded by a diameter and one of its arcs, that is technically called a half-Disk (mathematics)disc. A half-disc is a special case of a Circular segmentsegment, namely the largest one.* Tangent: a coplanar straight line that has one single point in common with a circle (\"touches the circle at this point\").All of the specified regions may be considered as open, that is, not containing their boundaries, or as closed, including their respective boundaries.-Image:CIRCLE LINES.svgrightthumbChord, secant, tangent, radius, and diameterImage:Circle slices.svgrightthumbArc, sector, and segment}\" ));");
//        out.println("slides.push(new slide(\"==Topological definition==\", \"In the field of topology, a circle isnt limited to the geometric concept, but to all of its homeomorphisms. Two topological circles are equivalent if one can be transformed into the other via a deformation of Real coordinate spaceR3 upon itself (known as an ambient isotopy).\" ));");
//        out.println("slides.push(new slide(\"==History==\", \"Image:God the Geometer.jpgthumbright200pxThe compass (drafting)compass in this 13th-century manuscript is a symbol of Gods act of Creation mythCreation. Notice also the circular shape of the Halo (religious iconography)halo.The word circle derives from the Greek languageGreek κίρκος/κύκλος (kirkos/kuklos), itself a metathesis (linguistics)metathesis of the Homeric Greek κρίκος (krikos), meaning \"hoop\" or \"ring\".http://www.perseus.tufts.edu/hopper/text?doc=Perseus%3Atext%3A1999.04.0057%3Aentry%3Dkri%2Fkos krikos , Henry George Liddell, Robert Scott, A Greek-English Lexicon, on Perseus The origins of the words circus and wikt:circuitcircuit are closely related.Image:IlkhanateSilkCircular.jpgleftthumb200pxCircular piece of silk with Mongol images Image:Shatir500.jpgrightthumb200pxCircles in an old Arabic astronomical drawing.The circle has been known since before the beginning of recorded history. Natural circles would have been observed, such as the Moon, Sun, and a short plant stalk blowing in the wind on sand, which forms a circle shape in the sand. The circle is the basis for the wheel, which, with related inventions such as gears, makes much of modern machinery possible. In mathematics, the study of the circle has helped inspire the development of geometry, astronomy and calculus.Early science, particularly geometry and astrology and astronomy, was connected to the divine for most History of science in the Middle Agesmedieval scholars, and many believed that there was something intrinsically \"divine\" or \"perfect\" that could be found in circles.Arthur Koestler, The Sleepwalkers (Koestler book)The Sleepwalkers: A History of Mans Changing Vision of the Universe (1959)Proclus, https://books.google.com/books?id=E1HYAAAAMAAJ The Six Books of Proclus, the Platonic Successor, on the Theology of Plato  Tr. Thomas Taylor (1816) Vol. 2, Ch. 2, \"Of Plato\"Some highlights in the history of the circle are:* 1700 BCE – The Rhind papyrus gives a method to find the area of a circular field. The result corresponds to  (3.16049...) as an approximate value of Pi.http://www-history.mcs.st-andrews.ac.uk/history/Chronology/30000BC_500BC.html#1700BC Chronology for 30000 BC to 500 BC . History.mcs.st-andrews.ac.uk. Retrieved on 2012-05-03.Image:Toghrol Tower looking up.jpgleftthumb200pxTughrul Tower from inside* 300 BCE – Book 3 of Euclids ElementsEuclids Elements deals with the properties of circles.* In Platos Seventh Letter there is a detailed definition and explanation of the circle. Plato explains the perfect circle, and how it is different from any drawing, words, definition or explanation.* 1880 CE – Ferdinand von LindemannLindemann proves that  is transcendental numbertranscendental, effectively settling the millennia-old problem of squaring the circle.http://www-history.mcs.st-andrews.ac.uk/history/HistTopics/Squaring_the_circle.html Squaring the circle . History.mcs.st-andrews.ac.uk. Retrieved on 2012-05-03.\" ));");
//        String t = "a".repeat(100000);
//        out.println("slides.push(new slide(\"hi\", \"" + t + "\" ))");
        out.println(s3);
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
    private static String oFilter(String s){
//        for(int i = 0; i < s.length(); i++){
//            if(!Character.isLetter(s.charAt(i)) && !Character.isDigit(s.charAt(i))){
//                s.replace(Character.toString(s.charAt(i)), "");
//                i--;
//            }
//        }
        return s;
    }
}
