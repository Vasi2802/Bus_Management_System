const homeLink = document.getElementById("home");
const bookLink = document.getElementById("bookbus");
//const contactLink = document.getElementById("contact");

const homeContent = document.getElementById("homeContent");
const bookContent = document.getElementById("bookbusContent");
//const contactContent = document.getElementById("contact-content");

homeLink.addEventListener("click", () => {
	console.log("click homelink");
  showContent(homeContent);
});

bookLink.addEventListener("click", () => {
	console.log("click bus link");
  showContent(bookContent);
});

/*contactLink.addEventListener("click", () => {
  showContent(contactContent);
});*/

function showContent(content) {
  homeContent.style.display = "none";
  bookContent.style.display = "none";
 // contactContent.style.display = "none";
  content.style.display = "block";
}
