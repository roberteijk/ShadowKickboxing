$(".contact-link").click(function (event) {
    event.preventDefault();
    let protocol = ":otliam";
    let local = "gnixobkcikwodahs"
    let domain = "moc.kooltuo@"
    window.location = reverse(protocol) + reverse(local) + reverse(domain);
});

function reverse(str){
    return str.split("").reverse().join("");
}