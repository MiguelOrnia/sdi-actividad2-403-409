//Modulos
var express = require('express');
var app = express();

var rest = require('request');
app.set('rest',rest);

app.use(function(req, res, next) {
    res.header("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Credentials", "true");
    res.header("Access-Control-Allow-Methods", "POST, GET, DELETE, UPDATE, PUT");
    res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, token");
    // Debemos especificar todas las headers que se aceptan. Content-Type , token
    next();
});

var jwt = require('jsonwebtoken');
app.set('jwt', jwt);


var expressSession = require('express-session');
app.use(expressSession({
    secret: 'abcdefg',
    resave: true,
    saveUninitialized: true
}));

var crypto = require('crypto');

var fileUpload = require('express-fileupload');
app.use(fileUpload());

var swig = require('swig-templates');
var bodyParser = require('body-parser');
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));


// routerUsuarioSession
var routerUsuarioSession = express.Router();
routerUsuarioSession.use(function (req, res, next) {
    console.log("routerUsuarioSession");
    if (req.session.usuario) {
        // dejamos correr la petición
        next();
    } else {
        res.redirect("/identificarse");
    }
});
//Aplicar routerUsuarioSession
//app.use("rutaParaAutorizar", routerUsuarioSession);
app.use("/sales/*", routerUsuarioSession);


app.use(express.static('public'));
var mongo = require('mongodb');

// Variables
app.set('port', 8081);
app.set('db', 'mongodb://admin:sdi@tiendamusica-shard-00-00-ri6vu.mongodb.net:27017,tiendamusica-shard-00-01-ri6vu.mongodb.net:27017,tiendamusica-shard-00-02-ri6vu.mongodb.net:27017/test?ssl=true&replicaSet=tiendamusica-shard-0&authSource=admin&retryWrites=true');
app.set('clave', 'abcdefg');
app.set('crypto', crypto);

var gestorBD = require("./modules/gestorBD.js");
gestorBD.init(app, mongo);

//Rutas/controladores por lógica
require("./routes/rusuarios")(app, swig, gestorBD);
require("./routes/rofertas")(app, swig, gestorBD);

app.get('/', function (req, res) {
    res.redirect('/home');
});

app.use(function (err, req, res, next) {
    console.log("Error producido: " + err); //we log the error in our db
    if (!res.headersSent) {
        res.status(400);
        res.send("Recurso no disponible");
    }
});


// lanzar el servidor
app.listen(app.get('port'), function () {
    console.log("Servidor activo");
});