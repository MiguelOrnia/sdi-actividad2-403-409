module.exports = function (app, gestorBD) {

    app.post("/api/identificarse/", function (req, res) {
        var seguro = app.get("crypto").createHmac('sha256', app.get('clave'))
            .update(req.body.password).digest('hex');
        var criterio = {
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
                        usuario: criterio.email,
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
                app.get("logger").error('Token inválido');
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
                        app.get("logger").error('Usuario no autorizado');
                        res.status(204);
                        res.json({
                            err: "Sin resultados"
                        });
                    } else {
                        app.get("logger").info('Se han mostrado las ofertas disponibles');
                        res.status(200);
                        res.send(ofertas);
                    }
                })
            }
        })
    });

    app.post('/api/sales/message/:id', function (req, res) {
        let criterio = {"_id": gestorBD.mongo.ObjectID(req.params.id)};
        gestorBD.obtenerOfertas(criterio, function (ofertas) {
            if (ofertas == null || ofertas.length === 0) {
                res.status(403);
                res.json({
                    err: "Sin resultados"
                });
            } else {
                let usuario = res.usuario;
                let oferta = ofertas[0];
                let message = {
                    sender: usuario,
                    receiver: req.body.receiver,
                    offer: gestorBD.mongo.ObjectID(req.params.id),
                    message: req.body.message,
                    date: new Date(),
                    read: false
                };
                gestorBD.insertarMensaje(message, function (mensaje) {
                    if (mensaje == null) {
                        res.status(500);
                        app.get("logger").info('Se ha producido un error al enviar el mensaje');
                        res.json({
                            err: "Error del servidor"
                        });
                    } else {
                        res.status(200);
                        app.get("logger").info('El mensaje se ha enviado correctamente');
                        res.json(mensaje);
                    }
                })
            }
        });
    });

    app.get("/api/sales/conversation/:id", function (req, res) {
        let criterio = {"_id": gestorBD.mongo.ObjectID(req.params.id)};
        gestorBD.obtenerOfertas(criterio, function (ofertas) {
            if (ofertas == null) {
                res.status(500);
                app.get("logger").info('Se ha producido un error al obtener ofertas');
                res.json({
                    error: "Se ha producido un error"
                });
            } else if (ofertas.length === 0) {
                res.status(400);
                app.get("logger").info('Oferta no encontrada');
                res.json({
                    error: "Oferta no encontrada"
                });
            } else {
                let offer = ofertas[0];
                let owner = offer.owner;
                let user = res.usuario;
                let criterio = {
                    $or: [
                        {
                            $and: [
                                {
                                    sender: user
                                },
                                {
                                    receiver: owner
                                },
                                {
                                    offer: offer
                                }
                            ]
                        },
                        {
                            $and: [
                                {
                                    sender: owner
                                },
                                {
                                    receiver: user
                                },

                                {
                                    offer: offer
                                }
                            ]
                        }
                    ]
                };
                gestorBD.obtenerMensajes(criterio, function (mensajes) {
                    if (mensajes == null) {
                        res.status(500);
                        app.get("logger").info('Se ha producido un error al obtener mensajes');
                        res.json({
                            error: "Ha habido un error"
                        });
                    } else {
                        res.status(200);
                        res.send(JSON.stringify(mensajes));
                    }
                });
            }
        });
    });
};