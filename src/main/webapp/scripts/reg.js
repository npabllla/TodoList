$(document).ready(function () {
    $('#goReg').click(function () {
        event.preventDefault();
        if (validate()) {
            const req = $.ajax({
                type: "POST",
                url: "http://localhost:8080/todo/reg",
                data: {name: $('#name').val(), email: $('#email').val(), password: $('#password').val()},
            })
            req.done(function (data) {
                if (data !== '') {
                    $('#alreadyExist').html(data)
                } else {
                    window.location.href = "http://localhost:8080/todo/index.html"
                }
            })
        }
    })
});

function validate() {
    let elements = [$('#name'), $('#email'), $('#password')]
    for (let i = 0; i < elements.length; i++) {
        if (elements[i].val() === "") {
            alert(elements[i].attr("placeholder"));
            return false;
        }
    }
    return true;
}
