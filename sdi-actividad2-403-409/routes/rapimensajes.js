module.exports = function (app, gestorBD) {

    app.post("/api/identificarse/", function (req, res) {
        let seguro = app.get("crypto").createHmac('sha256', app.get('clave'))
            .update(req.body.password).digest('hex');
        let criterio = {
            email: req.body.email,
            password: seguro
        }

        gestorBD.obtenerUsuarios(criterio, function (usuarios) {
            if (usuarios == null || usuarios.length === 0) {
                res.status(401);
                res.json({
                    autenticado: false
                });
            } else {
                var token = app.get('jwt').sign(
                    {
                        usuario: usuarios[0],
                        tiempo: Date.now() / 1000
                    }, "secreto");
                res.status(200);
                res.json({
                    autenticado: true,
                    token: token
                });
            }
        });
    });

    app.get("/api/sales/", function (req, res) {
        var token = req.headers['token'] || req.body.token || req.query.token;
        app.get('jwt').verify(token, 'secreto', function (err, infoToken) {
            if (err) {
                app.get("logger").error('API: Token inválido');
                res.status(403);
                res.json({
                    acceso: false,
                    error: 'Token inválido o caducado'
                });
            } else {
                var usuario = infoToken.usuario;
                criterio = {
                    owner: {$ne: res.usuario},
                    state: {$ne: 'No disponible'}
                }
                gestorBD.obtenerOfertas(criterio, function (ofertas) {
                    if (ofertas == null || ofertas.length === 0) {
                        app.get("logger").error('API: Usuario no autorizado');
                        res.status(204);
                        res.json({
                            err: "Sin resultados"
                        });
                    } else {
                        app.get("logger").info('API: Se han mostrado las ofertas disponibles');
                        res.status(200);
                        res.send(ofertas);
                    }
                })
            }
        })
    });

    app.get("/api/message/leido/:id", function (req, res) {
        let criterio = {
            "_id": gestorBD.mongo.ObjectID(req.params.id)
        }
        gestorBD.marcarLeido(criterio, function (mensajes) {
            if (mensajes == null) {
                res.status(500);
                app.get("logger").error('API: Se ha producido un error al leer mensajes');

                res.json({
                    error: "Se ha producido un error"
                });
            } else {
                res.status(200);
                app.get("logger").info('API: Se ha leído mensaje');
                res.send(mensajes);
            }
        })
    });


    app.get('/api/sales/conversation/:id', function (req, res) {
        var token = req.headers['token'] || req.body.token || req.query.token;
        var saleID = req.params.id;

        app.get('jwt').verify(token, 'secreto', function (err, infoToken) {
            if (err) {
                res.status(403); // Forbidden
                res.json({
                    acceso: false,
                    error: 'Token invalido o caducado'
                });
            } else {
                var usuario = infoToken.usuario;
                var criterioOferta = {_id: gestorBD.mongo.ObjectID(saleID)};

                gestorBD.obtenerOfertas(criterioOferta, function (ofertas) {
                    if (ofertas == null || ofertas[0] == null) {
                        res.status(404);
                        res.json({
                            err: 'Ha ocurrido un error con la base de datos'
                        });
                    } else {
                        var criterioConversation = {sale: ofertas[0], user: usuario};
                        gestorBD.findConversation(criterioConversation, function (conversations) {
                            if (conversations == null) {
                                res.status(404);
                                res.json({
                                    err: 'Ha ocurrido un error con la base de datos'
                                });
                            } else {
                                if (conversations.length == 0) {
                                    gestorBD.createConversation(ofertas[0], usuario, function (result) {
                                        if (result == null) {
                                            res.status(404);
                                            res.json({
                                                err: 'Ha ocurrido un error con la base de datos'
                                            });
                                        } else {
                                            //SUCCESS RETURN CONVERSATION
                                            res.status(200);
                                            res.json({conversation: result});
                                        }
                                    });
                                } else {
                                    //SUCCESS RETURN CONVERSATION
                                    res.status(200);
                                    res.json({conversation: conversations[0]});
                                }

                            }
                        });
                    }
                });
            }
        });
    });

    app.post('/api/sales/message/', function (req, res) {
        let criterio = {"_id": gestorBD.mongo.ObjectID(req.body.id)};
        var token = req.headers['token'] || req.body.token || req.query.token;
        app.get('jwt').verify(token, 'secreto', function (err, infoToken) {
            if (err) {
                app.get("logger").error('API: Token inválido');
                res.status(403);
                res.json({
                    acceso: false,
                    error: 'Token inválido o caducado'
                });
            } else {
                gestorBD.findConversation(criterio, function (conversations) {
                    if (conversations == null || conversations.length === 0) {
                        res.status(403);
                        res.json({
                            err: "Sin resultados"
                        });
                    } else {
                        var message = {sender: infoToken.usuario, message: req.body.message, date: new Date()};
                        gestorBD.insertMessage(conversations[0], message, function (result) {
                            if(result==null){
                                res.status(403);
                                res.json({
                                    err: "Sin resultados"
                                });
                            }else{
                                //SUCCESS
                                let hola = 1;
                            }
                        })
                    }
                });
            }
        });
    });
};