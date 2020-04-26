const container = document.querySelector("#container");
const title = document.querySelector("#title");
const downloadButton = document.querySelector("#download");

let titleString = decodeURIComponent(window.location.search);
titleString = titleString.substring(titleString.indexOf("=") + 1);
title.textContent = "WIKI Notes";

downloadButton.addEventListener("click", function () {



});

let slides = [];

createTitleSlide();
createSampleSlides();

function Slide(heading, body, image, rendered) {
    this.heading = heading;
    this.body = body; // paragraph text
    if(image != undefined) this.image = image;
    this.rendered = false;
}


function updateSlides() {

    for (let i = 0; i < slides.length; i++) {

        if (!slides[i].rendered) {

            if (i != 0) {

                const slide = document.createElement("div");
                slide.classList = "slide";
                slide.value = i;

                let slideTitle = slides[i].heading;
                let slideBody = slides[i].body;

                // parse slide body text
                slideBody.replace("*", ".");
                slideBody.replace('"', "'");
                sentences = slideBody.split(". ");

                //create slide heading
                const sildeHeading = document.createElement("div");
                sildeHeading.textContent = slideTitle;
                sildeHeading.classList = "slideHeading";

                // create slide content
                const content = document.createElement("div");
                content.classList = "content";
                const list = document.createElement("ul");

                // make bulletpoints
                for (let j = 0; j < sentences.length; j++) {
                    const bullet = document.createElement("li");
                    bullet.classList = "bullet";
                    bullet.textContent = sentences[j];
                    if (bullet.textContent != "") list.appendChild(bullet);
                }

                content.appendChild(list);

                if(slides[i].image != undefined && slides[i].image != false){

                    console.log(slides[i].image);

                    const iconDiv = document.createElement("div");
                    iconDiv.classList = "iconDiv";

                    const image = document.createElement("img");
                    image.src = slides[i].image;
                    image.classList = "icon";

                    iconDiv.appendChild(image);
                    content.appendChild(iconDiv);
                    
                }



                const settings = document.createElement("div");
                settings.classList = "settings";

                const deleteButton = document.createElement("button");
                deleteButton.classList = "deleteButton";
                deleteButton.textContent = "X";
                deleteButton.addEventListener("click", function () {

                    // confirm slide deletion
                    let c = confirm("Delete " + sildeHeading.textContent + " slide? ");

                    if (c) {
                        container.removeChild(slide);
                        slides.splice(i, 1);
                    }

                });



                const addButton = document.createElement("button");
                addButton.classList = "addButton";
                addButton.textContent = "Add Slide";

                addButton.addEventListener("click", function () {

                    let count = container.childNodes.length;

                    while (count > 2) {
                        container.removeChild(container.lastChild);
                        count--;
                    }

                    for (let j = 0; j < slides.length; j++) slides[j].rendered = false;
                    slides.splice(i + 1, 0, new Slide(" ", " ", false));
                    updateSlides();

                });


                const editButton = document.createElement("button");
                editButton.classList = "editButton";
                editButton.textContent = "Edit";

                editButton.addEventListener("click", function () {
                    if (editButton.textContent == "Edit") {
                        slide.style.borderColor = "#2ecc71";
                        content.contentEditable = "true";
                        sildeHeading.contentEditable = "true";
                        editButton.textContent = "Save";
                    }
                    else if (editButton.textContent == "Save") {
                        slide.style.borderColor = "black"
                        content.contentEditable = "false";
                        sildeHeading.contentEditable = "false";
                        editButton.textContent = "Edit";

                        slides[i].heading = sildeHeading.textContent;
                        console.log(slides[i].body);
                        slides[i].body = "";

                        for (let j = 0; j < list.childNodes.length; j++) slides[i].body += list.childNodes[j].textContent + ". ";
                    }

                });

                settings.appendChild(deleteButton);
                settings.appendChild(addButton);
                settings.appendChild(editButton);

                slide.appendChild(sildeHeading);
                slide.appendChild(content);
                slide.appendChild(settings);

                container.appendChild(slide);

            }

            if (i == 0) {
                const addButton = document.createElement("button");
                addButton.classList = "addButton";
                addButton.textContent = "Add Slide";

                addButton.addEventListener("click", function () {

                    let count = container.childNodes.length;

                    while (count > 2) {
                        container.removeChild(container.lastChild);
                        count--;
                    }

                    for (let j = 0; j < slides.length; j++) slides[j].rendered = false;
                    slides.splice(i + 1, 0, new Slide(" ", " ", false));
                    updateSlides();

                });


                container.appendChild(addButton);
            }

            slides[i].rendered = true;



        }
    }


}


function createTitleSlide() {

    const introSlide = document.createElement("div");
    introSlide.classList = "introSlide";
    container.appendChild(introSlide);

    const introSlideTitle = document.createElement("div");
    introSlideTitle.classList = "introSlideTitle";
    introSlideTitle.textContent = titleString;
    introSlide.appendChild(introSlideTitle);

    slides.push(introSlide);
    container.appendChild(introSlide);

}







function createSampleSlides() {

    slides.push(new Slide("Definition", "A circle is a shape consisting of all points in a plane that are a given distance from a given point, the centre; equivalently it is the curve traced out by a point that moves in a plane so that its distance from a given point is constant. The distance between any point of the circle and the centre is called the radius. This article is about circles in Euclidean geometry, and, in particular, the Euclidean plane, except where otherwise noted. Specifically, a circle is a simple closed curve that divides the plane into two regions: an interior and an exterior. In everyday use, the term circle may be used interchangeably to refer to either the boundary of the figure, or to the whole figure including its interior; in strict technical usage, the circle is only the boundary and the whole figure is called a disc. A circle may also be defined as a special kind of ellipse in which the two foci are coincident and the eccentricity is 0, or the two-dimensional shape enclosing the most area per unit perimeter squared, using calculus of variations.", "icons/circle_test.png"));
    slides.push(new Slide("Euclid's definition", "A circle is a plane figure bounded by one curved line, and such that all straight lines drawn from a certain point within it to the bounding line, are equal. The bounding line is called its circumference and the point, its centre."));

    updateSlides();

}

