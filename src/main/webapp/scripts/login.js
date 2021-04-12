$(document).ready(function () {
    $('#login').click(function () {
        event.preventDefault();
        if (validate()) {
            const req = $.ajax({
                type: "POST",
                url: "http://localhost:8080/todo/auth",
                data: {email: $('#email').val(), password: $('#password').val()},
            })
            req.done(function (data) {
                if (data !== '') {
                    $('#wrongUser').html(data)
                } else {
                    window.location.href = "http://localhost:8080/todo/index.html"
                }
            })
        }
    })
    $('#reg').click(function () {
        event.preventDefault();
        window.location.href = "http://localhost:8080/todo/reg.html"
    })
});

function validate() {
    let result = true;
    if ($('#name').val() === '' || $('#password').val() === '') {
        alert("Необходимо ввести почту и пароль")
        result = false;
    }
    return result;
}

