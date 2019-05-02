module.exports = function (app, swig, gestorBD) {

    app.get("/sales/add", function (req, res) {
        var respuesta = swig.renderFile('views/sales/add.html', {
            user: req.session.usuario
        });
        res.send(respuesta);
    });

    app.post("/sales/add", function (req, res) {
        if (req.session.usuario !== undefined) {
            var sale = {
                title: req.body.title,
                details: req.body.details,
                price: req.body.price,
                seller: req.session.usuario,
                buyer: null,
            }
            // Conectarse
            gestorBD.addSale(sale, function (id) {
                if (id == null) {
                    res.send("Error al insertar canción");
                } else {
                    res.redirect("/home");
                }
            });
        } else {
            res.redirect("/identificarse");
        }
    });

    app.get("/sales/list", function (req, res) {
        var criterio = {seller: req.session.usuario};
        gestorBD.obtenerOfertas(criterio, function (sales) {
            if (sales == null) {
                res.send("Error al listar ");
            } else {
                var respuesta = swig.renderFile('views/sales/list.html',
                    {
                        salesList: sales,
                        user: req.session.usuario
                    });
                res.send(respuesta);
            }
        });
    });

    app.get('/sales/delete/:id', function (req, res) {
        var criterio = {"_id": gestorBD.mongo.ObjectID(req.params.id)};
        gestorBD.eliminarOferta(criterio, function (ofertas) {
            if (ofertas == null) {
                res.send("Could not delete song");
            } else {
                res.redirect("/sales/list");
            }
        });
    })


    app.get("/sales/search", function (req, res) {
        var criterio = { buyer: null};

        if (req.query.searchText != null) {
            criterio = {"title": {$regex: ".*" + req.query.searchText + ".*", $options: 'i'}, "isSold" : false};
        }

        var pg = parseInt(req.query.pg); // Es String !!!
        if (req.query.pg == null) { // Puede no venir el param
            pg = 1;
        }

        gestorBD.obtenerOfertasPg(criterio, pg, function (ofertas, total) {
            if (ofertas == null) {
                res.send("Error al listar ");
            } else {
                var ultimaPg = total / 5;
                if (total % 5 > 0) { // Sobran decimales
                    ultimaPg = ultimaPg + 1;
                }
                var paginas = []; // paginas mostrar
                for (var i = pg - 2; i <= pg + 2; i++) {
                    if (i > 0 && i <= ultimaPg) {
                        paginas.push(i);
                    }
                }
                var respuesta = swig.renderFile('views/sales/search.html',
                    {
                        sales: ofertas,
                        paginas: paginas,
                        actual: pg,
                        user: req.session.usuario
                    });
                res.send(respuesta);
            }
        });
    });

    app.get("/sales/purchased", function (req, res) {

    });

    app.get("/sales/buy/:id", function (req, res) {
        var id = req.params.id;
        var criterio = {"_id": gestorBD.mongo.ObjectID(id)};
        var buyer = req.session.usuario;

        gestorBD.comprarOferta(criterio, buyer, function(result){
            if(result==null){
                res.send("Error al comprar oferta: "+id);
            }else{
                res.redirect("/sales/search");
            }
        });
    });

}