module.exports = function (app, swig, gestorBD) {
    app.get("/registrarse", function (req, res) {
        if (req.session.usuario === undefined) {
            var respuesta = swig.renderFile('views/signup.html', {});
            res.send(respuesta);
        } else {
            res.redirect("/home");
        }
    });

    app.post('/registrarse', function (req, res) {
        if (req.body.email === null || req.body.email === "") {
            res.redirect("/registrarse?mensaje=El email no puede estar vacío");
            return;
        }

        if (req.body.name === null || req.body.name === "") {
            res.redirect("/registrarse?mensaje=El nombre no puede estar vacío");
            return;
        }

        if (req.body.surname === null || req.body.surname === "") {
            res.redirect("/registrarse?mensaje=El apellido no puede estar vacío");
            return;
        }

        if (req.body.password === null || req.body.password === "") {
            res.redirect("/registrarse?mensaje=La contraseña no puede estar vacía");
            return;
        }

        if (req.body.repassword === null || req.body.repassword === "") {
            res.redirect("/registrarse?mensaje=Repita la contraseña");
            return;
        }

        if (req.body.repassword !== req.body.password) {
            res.redirect("/registrarse?mensaje=Las contraseñas no coinciden");
            return;
        } else {
            var seguro = app.get("crypto").createHmac('sha256', app.get('clave'))
                .update(req.body.password).digest('hex');

            var criterio = {
                email: req.body.email
            }

            gestorBD.obtenerUsuarios(criterio, function (usuarios) {
                if (usuarios !== null && usuarios.length !== 0) {
                    res.redirect("/registrarse?mensaje=El correo ya está registrado. Inténtelo de nuevo con un " +
                        "correo diferente");
                } else {
                    var usuario = {
                        email: req.body.email,
                        password: seguro,
                        name: req.body.name,
                        surname: req.body.surname,
                        money: 100,
                        rol: "rol_estandar"
                    }
                    gestorBD.insertarUsuario(usuario, function (id) {
                        if (id == null) {
                            app.get("logger").error('Error registro de usuario');
                            res.redirect("/registrarse?mensaje=Error al registrar usuario");
                        } else {
                            app.get("logger").info('Usuario se ha registrado');
                            req.session.usuario = usuario;
                            res.redirect("/home");
                        }
                    })
                }
            });
        }
    });

    app.get("/identificarse", function (req, res) {
        var respuesta = swig.renderFile('views/login.html', {});
        res.send(respuesta);
    });

    app.post("/identificarse", function (req, res) {
        if (req.body.email === "" || req.body.email === null) {
            res.redirect("/identificarse?mensaje=El email no puede estar vacío");
            return;
        }
        if (req.body.password === "" || req.body.password === null) {
            res.redirect("/identificarse?mensaje=La contraseña no puede estar vacía");
            return;
        }
        var seguro = app.get("crypto").createHmac('sha256', app.get('clave'))
            .update(req.body.password).digest('hex');
        var criterio = {
            email: req.body.username,
            password: seguro
        }
        gestorBD.obtenerUsuarios(criterio, function (usuarios) {
            if (usuarios === undefined || usuarios.length === 0) {
                req.session.usuario = null;
                app.get("logger").error('Fallo en autenticación');
                res.redirect("/identificarse?mensaje=Email o password incorrecto");
            } else {
                req.session.usuario = usuarios[0];
                res.redirect("/home");
            }
        });
    });

    app.get("/home", function (req, res) {
        var user = req.session.usuario;
        if (user != null) {
            if (user.rol == "rol_admin") {
                res.redirect("/homeAdmin");
                app.get("logger").info('Usuario se ha identificado como admin');
            } else {
                res.redirect("/homeStandard");
                app.get("logger").info('Usuario estándar se ha identificado');
            }
        } else {
            res.redirect("/identificarse");
        }
    });

    app.get('/desconectarse', function (req, res) {
        var respuesta = swig.renderFile('views/login.html', {});
        req.session.usuario = undefined;
        app.get("logger").info('Usuario se ha desconectado');
        res.send(respuesta);
    })

    app.get("/homeStandard", function (req, res) {
        if (req.session.usuario === null) {
            res.redirect("/identificarse");
        }
        if (req.session.usuario.rol != "rol_estandar") {
            res.redirect(("/homeAdmin?mensaje=No puede acceder a esta zona de la web"));
        } else {
            var criterio = {seller: req.session.usuario};
            gestorBD.obtenerOfertas(criterio, function (sales) {
                if (sales == null) {
                    res.send("Error al listar ");
                } else {
                    var respuesta = swig.renderFile('views/homeStandard.html',
                        {
                            salesList: sales,
                            user: req.session.usuario
                        });
                    res.send(respuesta);
                }
            });
        }
    });

    app.get("/homeAdmin", function (req, res) {
        if (req.session.usuario === null) {
            res.redirect("/identificarse");
        }
        if (req.session.usuario.rol !== "rol_admin") {
            res.redirect(("/homeStandard?mensaje=No puede acceder a esta zona de la web"))
        } else {
            var respuesta = swig.renderFile('views/homeAdmin.html', {
                user: req.session.usuario
            });
            res.send(respuesta);
        }
    });

    app.get("/user/list", function (req, res) {
        let userLogged = req.session.usuario;
        if (userLogged.rol === 'rol_estandar') {
            res.redirect("/home?mensaje=No puede acceder a esta zona de la web");
        } else {
            let criterio = {
                email: {
                    $ne: userLogged.email
                }
            };
            gestorBD.obtenerUsuarios(criterio, function (users) {
                if (users == null) {
                    res.redirect("/home?mensaje=Error al listar los usuarios");
                    app.get("logger").error('Error al listar los usuarios');
                } else {
                    let respuesta = swig.renderFile('views/user/list.html',
                        {
                            usersList: users,
                            user: req.session.usuario
                        });
                    res.send(respuesta);
                    app.get("logger").info('Administrador se ha dirigido a la vista de usuarios');
                }
            });
        }
    });

    app.post("/user/delete", function (req, res) {
        var criterio, criterioOfertas, criterioConversaciones;

        if (typeof (req.body.email) === "object") {
            criterio = {email: {$in: req.body.email}};
        }

        if (typeof (req.body.email) === "string") {
            criterio = {email: req.body.email};
        }

        gestorBD.obtenerUsuarios(criterio, function (users) {
            if (users == null) {
                res.redirect("/user/list" +
                    "?mensaje=Los usuarios no pudieron ser eliminados");
                app.get("logger").error('Error al borrar el usuario');
            } else {
                criterioOfertas = { seller : {$in: users} };
                criterioConversaciones = { user : {$in: users} };
            }
        });

        if (criterio !== undefined) {
            gestorBD.eliminarUsuarios(criterio, function (usuarios) {
                if (usuarios === null || usuarios.length === 0) {
                    app.get("logger").error('Error al borrar el usuario');
                    res.redirect("/user/list" +
                        "?mensaje=Los usuarios no pudieron ser eliminados");
                } else {
                    gestorBD.eliminarOfertas(criterioOfertas, function (sales) {
                        if (sales === null || sales.length === 0) {
                            app.get("logger").error('Error al borrar las ofertas del usuario');
                            res.redirect("/user/list" +
                                "?mensaje=Las ofertas no pudieron ser eliminadas");
                        } else {
                            gestorBD.eliminarConversaciones(criterioConversaciones, function (conversaciones) {
                                if (conversaciones === null || conversaciones.length === 0) {
                                    app.get("logger").error('Error al borrar las conversaciones del usuario');
                                    res.redirect("/user/list" +
                                        "?mensaje=Las ofertas no pudieron ser eliminadas");
                                } else {
                                    app.get("logger").info('Usuarios borrados correctamente');
                                    res.redirect("/user/list" +
                                        "?mensaje=Los usuarios se eliminaron correctamente");
                                }
                        }
                    });
                }
            });
        } else {
            res.redirect("/user/list");
        }
    });
}