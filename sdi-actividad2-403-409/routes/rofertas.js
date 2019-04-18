module.exports = function(app, swig, gestorBD) {

    app.get("/sales/add", function (req, res) {
        var respuesta = swig.renderFile('views/sales/add.html', {});
        res.send(respuesta);
    });

    app.post("/sales/add", function (req, res) {

    });

    app.get("/sales/list", function (req, res) {

    });

    app.get("/sales/search", function (req, res) {

    });

    app.get("/sales/purchased", function (req, res) {

    });

}