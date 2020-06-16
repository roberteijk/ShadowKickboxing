$(".contact-link").click(function (event) {
    event.preventDefault();
    let protocol = ":otliam";
    let local = "gnixobkcikwodahs"
    let domain = "moc.kooltuo@"
    window.location = reverse(protocol) + reverse(local) + reverse(domain);
});

$("#simple-mode").click(function () {
    $(".main-content-simple").css("display", "block");
    $(".main-content-expert").css("display", "none");
});

$("#expert-mode").click(function () {
    $(".main-content-simple").css("display", "none");
    $(".main-content-expert").css("display", "block");
});

function reverse(str){
    return str.split("").reverse().join("");
}