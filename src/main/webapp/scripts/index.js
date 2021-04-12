$(document).ready(function () {
    loadItems()
    loadCategories()
})

function loadItems() {
    const req = $.ajax({
        type: "GET",
        url: "http://localhost:8080/todo/load",
        dataType: "json"
    })
    req.done(function (data) {
        let items = ""
        let user = ""
        for (let i = 0; i < data.length; i++) {
            let categories = ""
            for (let j = 0; j < data[i]["categories"].length; j++) {
                categories += data[i]["categories"][j]["name"] + " ";
            }
            if (data[i]["status"] !== "Done") {
                items += "<tr>"
                    + "<td>" + data[i]["description"] + "</td>"
                    + "<td>" + categories + "</td>"
                    + "<td>" + data[i]["created"] + "</td>"
                    + "<td>" + data[i]["user"]["name"] + "</td>"
                    + "<td>" + "Не выполнено  "
                    + "<input type='checkbox' id='setStatus' value=" + data[i]["id"] + ">"
                    + "</td>"
                    + "</tr>"
            }
            user = "Текущий пользователь: " + data[i]["user"]["name"]
        }
        $('#showAll').on('click', function () {
            $('#items').html(items);
            $('input:checked:not(:disabled)').each(function () {
                for (let i = 0; i < data.length; i++) {
                    let categories = ""
                    for (let j = 0; j < data[i]["categories"].length; j++) {
                        categories += data[i]["categories"][j]["name"] + " ";
                    }
                    if (data[i]["status"] === "Done") {
                        $('#items').append("<tr style='background: #7dec4c'>"
                            + "<td>" + data[i]["description"] + "</td>"
                            + "<td>" + categories + "</td>"
                            + "<td>" + data[i]["created"] + "</td>"
                            + "<td>" + data[i]["user"]["name"] + "</td>"
                            + "<td>" + "Выполнено  "
                            + "<input disabled type='checkbox' id='setStatus' checked value=" + data[i]["id"] + ">"
                            + "</td>"
                            + "</tr>");
                    }
                }
            });
        });
        $('#items').html(items)
        $('#user').html(user)
    })
}

function loadCategories() {
    const req = $.ajax({
        type: "GET",
        url: "http://localhost:8080/todo/categories",
        dataType: "json"
    })
    req.done(function (data) {
        let categories = ""
        for (let i = 0; i < data.length; i++) {
            categories += "<option value=" + data[i]["id"] + ">"
                + data[i]["name"]
                + "</option>>"
        }
        $('#cIds').html(categories)
    })
}

function validate() {
    let result = true
    if ($("#desc").val() === '' || $("#cIds").val() == "") {
        alert("Необходимо ввести описание задачи и выбрать категории")
        result = false
    }
    return result
}

function addTask() {
    if (validate()) {
        $.ajax({
            method: 'POST',
            url: 'http://localhost:8080/todo/add',
            data: {description: $("#desc").val(), categoryIds: $('#cIds').val().join(",")},
            dataType: 'json'
        });
        location.reload()
    }
}

function getSelectedValues() {
    let selectedValues = []
    $('input[id=setStatus]:checked:not(:disabled)').each(function (i, e) {
        selectedValues.push($(e).attr('value'))
    })
    return selectedValues;
}

function markAsDone() {
    let selectedValues = getSelectedValues()
    if (selectedValues.length > 0) {
        for (let i = 0; i < selectedValues.length; i++) {
            $.ajax({
                method: 'POST',
                url: 'http://localhost:8080/todo/update',
                data: {id: selectedValues[i]},
                dataType: 'json'
            });
        }
        location.reload()
    } else {
        alert("Необходмио выбрать задания")
    }
}

