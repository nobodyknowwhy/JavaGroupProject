var slides = document.querySelectorAll('#slider img');
var slideIndex = 0;

function showSlide(n) {
    slides[slideIndex].classList.remove('active');
    slideIndex = (n + slides.length) % slides.length;
    slides[slideIndex].classList.add('active');
    showCaption(); 
}

function showCaption() {
    var activeImg = document.querySelector('#slider img.active');
    var caption = activeImg.getAttribute('data-caption');
    var captionDiv = document.querySelector('#slider .slider-caption');
    captionDiv.innerHTML = caption;
}

function prevSlide() {
    if (slideIndex === 0) {
        showSlide(slides.length - 1);
    } else {
        showSlide(slideIndex - 1);
    }
}

function nextSlide() {
    showSlide(slideIndex + 1);
}

function autoSlide() {
    nextSlide();
    showCaption(); 
}


autoSlide()
setInterval(autoSlide, 5000);


