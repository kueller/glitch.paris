function init() {
    Array.from(document.getElementsByClassName("photo-item")).forEach(element => {
        element.addEventListener("click", event_photoOpen);
        element.disabled = true;
    });

    const overlay = document.getElementById("photo-overlay");
    const close = document.getElementById("photo-box-close");

    overlay.style.removeProperty("opacity");
    overlay.addEventListener("click", event_photoClose);
    close.addEventListener("click", event_photoClose);
}


function setupPhotoBox(imgElement) {
    const content = document.getElementsByClassName("photo-box-content")[0];
    Array.from(content.children).forEach(img => { img.remove(); });

    const filename = imgElement.currentSrc.split("/").at(-1).replace("prev_", "");
    const fullURL = "./static/night/original/" + filename;

    const link = document.createElement("a");
    link.href = fullURL;
    link.target = "_blank";

    const fullImg = document.createElement("img");
    fullImg.src = fullURL;
    fullImg.title = imgElement.title;
    fullImg.alt = imgElement.alt;

    link.appendChild(fullImg);
    content.appendChild(fullImg);
}


function event_photoOpen(event) {
    event.preventDefault();

    setupPhotoBox(event.target);

    const overlay = document.getElementById("photo-overlay");
    overlay.classList.add("active");
}


function event_photoClose(event) {
    const overlay = document.getElementById("photo-overlay");

    if (event.target.id == "photo-overlay" || event.target.id == "photo-box-close") {
        overlay.classList.remove("active");
    }
}


window.onload = () => { init(); };
