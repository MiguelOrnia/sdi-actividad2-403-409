module.exports = function (app, swig, gestorBD) {
    app.get("/registrarse", function (req, res) {
        var respuesta = swig.renderFile('views/signup.html', {});
        res.send(respuesta);
    });

    app.post('/registrarse', function (req, res) {
        if (req.body.email === null || req.body.email === "") {
            res.redirect("/registrarse?mensaje=El email no puede estar vacío");
        }

        if (req.body.name === null || req.body.name === "") {
            res.redirect("/registrarse?mensaje=El nombre no puede estar vacío");
        }

        if (req.body.surname === null || req.body.surname === "") {
            res.redirect("/registrarse?mensaje=El apellido no puede estar vacío");
        }

        if (req.body.password === null || req.body.password === "") {
            res.redirect("/registrarse?mensaje=La contraseña no puede estar vacía");
        }

        if (req.body.repassword.length >= 0 && req.body.repassword.length < 8) {
            res.redirect("/registrarse?mensaje=Repita la contraseña");
        }

        if (req.body.repassword != req.body.password) {
            res.redirect("/registrarse?mensaje=Las contraseñas no coinciden");
        } else {
            var seguro = app.get("crypto").createHmac('sha256', app.get('clave'))
                .update(req.body.password).digest('hex');

            var usuario = {
                email: req.body.email,
                password: seguro,
                name: req.body.name,
                surname: req.body.surname,
                money: 100,
                rol: "rol_estandar",
                active: true
            }

            var criterio = {
                email: usuario.email
            }

            gestorBD.obtenerUsuarios(criterio, function (usuarios) {
                if (usuarios != null && usuarios.length != 0) {
                    res.redirect("/registrarse?mensaje=El correo ya está registrado. Inténtelo de nuevo con un " +
                        "correo diferente");
                } else {
                    gestorBD.insertarUsuario(usuario, function (id) {
                        if (id == null) {
                            res.redirect("/registrarse?mensaje=Error al registrar usuario");
                        } else {
                            res.redirect("/identificarse?mensaje=Nuevo usuario registrado");
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
        var seguro = app.get("crypto").createHmac('sha256', app.get('clave'))
            .update(req.body.password).digest('hex');
        var criterio = {
            email: req.body.username,
            password: seguro
        }
        gestorBD.obtenerUsuarios(criterio, function (usuarios) {
            if (usuarios == null || usuarios.length == 0) {
                req.session.usuario = null;
                res.redirect("/identificarse" +
                    "?mensaje=Email o password incorrecto");
            } else {
                req.session.usuario = usuarios[0];
                res.redirect("/home");
            }
        });
    });

    app.get("/home", function (req, res) {
        var user = req.session.usuario;
        if (user != null) {
            if (user.rol == "rol_estandar") {
                res.redirect("/homeStandard");
            } else {
                res.redirect("/homeAdmin");
            }
        } else {
            res.redirect("/identificarse");
        }
    });

    app.get('/desconectarse', function (req, res) {
        var respuesta = swig.renderFile('views/login.html', {});
        req.session.usuario = null;
        res.send(respuesta);
    })

    app.get("/homeStandard", function (req, res) {
        if (req.session.usuario === null) {
            res.redirect("/identificarse");
        }
        if (req.session.usuario.rol != "rol_estandar") {
            res.redirect(("/homeAdmin?mensaje=No puede acceder a esta zona de la web"));
        } else {
            var respuesta = swig.renderFile('views/homeStandard.html', {
                user: req.session.usuario
            });
            res.send(respuesta);
        }
    });

    app.get("/homeAdmin", function (req, res) {
        if (req.session.usuario === null) {
            res.redirect("/identificarse");
        }
        if (req.session.usuario.rol != "rol_admin") {
            res.redirect(("/homeStandard?mensaje=No puede acceder a esta zona de la web"))
        } else {
            var respuesta = swig.renderFile('views/homeAdmin.html', {
                user: req.session.usuario
            });
            res.send(respuesta);
        }
    });

    app.get("/user/list", function (req, res) {
        if (req.session.usuario === null || req.session.usuario.rol != "rol_admin") {
            res.redirect("/homeStandard?mensaje=No puede acceder a esta zona de la web");
        } else {
            gestorBD.obtenerUsuarios({}, function (usuarios) {
                let respuesta = swig.renderFile('views/user/list.html', {
                    user: req.session.usuario,
                    usersList: usuarios
                });
                res.send(respuesta);
            });
        }
    });

    app.post("/user/delete", function (req, res) {
        var criterio;
        if (typeof (req.body.email) === "object") {
            criterio = {email: {$in: req.body.email}};
        }

        if (typeof (req.body.email) === "string") {
            criterio = {email: req.body.email};
        }
        var criterioEliminar = {active: false};
        if (criterio !== undefined) {
            gestorBD.eliminarUsuarios(criterio, criterioEliminar, function (usuarios) {
                if (usuarios === null || usuarios.length === 0) {
                    res.redirect("/user/list" +
                        "?mensaje=Los usuarios no pudieron ser eliminados");
                } else {
                    res.redirect("/user/list" +
                        "?mensaje=Los usuarios se eliminaron correctamente");
                }
            });
        } else {
            res.redirect("/user/list");
        }
    });
}