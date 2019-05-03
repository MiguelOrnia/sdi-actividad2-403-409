module.exports = function (app, swig, gestorBD) {

    app.get("/sales/add", function (req, res) {
        var respuesta = swig.renderFile('views/sales/add.html', {
            user: req.session.usuario
        });
        app.get("logger").info('Usuario se ha dirigido a la vista de añadir oferta');
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
                fav: req.body.fav,
            }
            // Conectarse
            gestorBD.addSale(sale, function (id) {
                if (id == null) {
                    res.send("Error al insertar oferta");
                    res.redirect("/sales/add?mensaje=Error al insertar oferta&tipoMensaje=alert-danger");
                } else {
                    app.get("logger").info('Se ha añadido la oferta');
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
                res.send("No se puede eliminar la oferta");
            } else {
                res.redirect("/sales/list");
            }
        });
    })


    app.get("/sales/search", function (req, res) {
        var criterio = {};

        if (req.query.searchText != null) {
            criterio = {"title": {$regex: ".*" + req.query.searchText + ".*", $options: 'i'}};
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

                var success = null;
                var error = null;

                if( req.query.success != null ) {
                    success = req.query.success;
                }
                if( req.query.error != null ) {
                    error = req.query.error;
                }

                var respuesta = swig.renderFile('views/sales/search.html',
                    {
                        sales: ofertas,
                        paginas: paginas,
                        actual: pg,
                        user: req.session.usuario,
                        success: success,
                        error: error
                    });
                res.send(respuesta);
            }
        });
    });

    app.get("/sales/purchased", function (req, res) {
        var buyer = req.session.usuario;
        var criterio = {buyer: buyer};

        gestorBD.obtenerOfertas(criterio, function (sales) {
            if (sales == null) {
                res.send("Error al listar ");
            } else {
                var respuesta = swig.renderFile('views/sales/purchased.html',
                    {
                        salesList: sales,
                        user: req.session.usuario
                    });
                res.send(respuesta);
            }
        });
    });

    app.get("/sales/buy/:id", function (req, res) {
        var id = req.params.id;
        var criterio = {"_id": gestorBD.mongo.ObjectID(id)};
        var buyer = req.session.usuario;

        gestorBD.comprarOferta(criterio, buyer, function(result){
            if(result==null){
                res.redirect("/sales/search/?error=Ha+ocurrido+un+error+procesando+la+oferta");
            }else{
                res.redirect("/sales/search/?success=La+oferta+se+ha+realizado+correctamente");
            }
        });
    });

}