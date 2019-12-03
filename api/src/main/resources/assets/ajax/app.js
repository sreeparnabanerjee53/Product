var reloadData = function () {
    console.log("loaded");
    // var a = document.getElementById("IDInput").value;
    $.ajax({
        type: 'GET',
        url: "http://localhost:9000/api/products",
        success: function (data) {
            console.log(data);
            var html = '<table><thead><tr class="table100-head"><th class="column1">Name</th><th class="column2">Price</th></tr></thead><tbody>';

            for (var key in data) {
                html += '<tr>'
                html += '<td class="column1">' + data[key].name  + '</td>' + '<td class="column1">' + data[key].current_price + '</td>';
                html += '</tr>'
            }
            html += '</tbody></table>';
            document.getElementById('message').innerHTML = html;
        },
        error: function (data) {
            console.log(data.responseJSON);
        },
        async: false
    });
}