const container = document.querySelector("#container");
const title = document.querySelector("#title");

let titleString = decodeURIComponent(window.location.search);
titleString = titleString.substring(titleString.indexOf("=") + 1);

title.textContent = titleString;

let slides = [];


createSampleSlides();

function slide(heading, body){
    this.heading = heading;
    this.body = body; // paragraph text
}

function createSlides(){

    for(let i=0; i<slides.length; i++){

        const slide = document.createElement("div");
        slide.classList = "slide";
        slide.value = i;

        let slideTitle = slides[i].heading;
        let slideBody = slides[i].body;
        sentences = slideBody.split(". ");

        const sildeHeading = document.createElement("div");
        sildeHeading.textContent = slideTitle;
        sildeHeading.classList = "slideHeading";

        const content = document.createElement("div");
        content.classList = "content";

        const list = document.createElement("ul");  //create list for bullet points

        for(let j=0; j<sentences.length; j++){
            const bullet = document.createElement("li");
            bullet.classList = "bullet";
            bullet.textContent = sentences[j];
            list.appendChild(bullet);
        }

        content.appendChild(list);

        slide.appendChild(sildeHeading);
        slide.appendChild(content);
        container.appendChild(slide);

    }
}

function createSampleSlides(){
    slides.push(new slide("Definition", "A circle is a shape consisting of all points in a plane that are a given distance from a given point, the centre; equivalently it is the curve traced out by a point that moves in a plane so that its distance from a given point is constant. The distance between any point of the circle and the centre is called the radius. This article is about circles in Euclidean geometry, and, in particular, the Euclidean plane, except where otherwise noted. Specifically, a circle is a simple closed curve that divides the plane into two regions: an interior and an exterior. In everyday use, the term circle may be used interchangeably to refer to either the boundary of the figure, or to the whole figure including its interior; in strict technical usage, the circle is only the boundary and the whole figure is called a disc. A circle may also be defined as a special kind of ellipse in which the two foci are coincident and the eccentricity is 0, or the two-dimensional shape enclosing the most area per unit perimeter squared, using calculus of variations."));
    slides.push(new slide("Euclid's definition", "A circle is a plane figure bounded by one curved line, and such that all straight lines drawn from a certain point within it to the bounding line, are equal. The bounding line is called its circumference and the point, its centre." ));

    createSlides();

}