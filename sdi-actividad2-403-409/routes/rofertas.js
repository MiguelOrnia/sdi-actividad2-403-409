module.exports = function(app, swig, gestorBD) {

    app.get("/sales/add", function (req, res) {
        var respuesta = swig.renderFile('views/sales/add.html', {});
        res.send(respuesta);
    });

    app.post("/sales/add", function (req, res) {
        if(req.session.usuario!=null){
        var sale = {
            title: req.body.title,
            details: req.body.details,
            price: req.body.price
        }
        // Conectarse
        gestorBD.addSale(sale, function (id) {
            if (id == null) {
                res.send("Error al insertar canción");
            } else {
                res.redirect("/home");
            }
        });
        }else{
            res.redirect("/identificarse");
        }
    });

    app.get("/sales/list", function (req, res) {

    });

    app.get("/sales/search", function (req, res) {

    });

    app.get("/sales/purchased", function (req, res) {

    });

}