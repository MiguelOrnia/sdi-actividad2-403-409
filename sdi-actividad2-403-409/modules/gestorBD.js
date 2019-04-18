module.exports = {
    mongo: null,
    app: null,
    init: function (app, mongo) {
        this.mongo = mongo;
        this.app = app;
    },
    // Gestion de usuarios
    obtenerUsuarios : function(criterio, funcionCallback){
        this.mongo.MongoClient.connect(this.app.get('db'), function(err, db) {
            if (err) {
                funcionCallback(null);
            } else {
                var collection = db.collection('usuarios');
                collection.find(criterio).toArray(function(err, usuarios) {
                    if (err) {
                        funcionCallback(null);
                    } else {
                        funcionCallback(usuarios);
                    }
                    db.close();
                });
            }
        });
    },
    insertarUsuario : function(usuario, funcionCallback) {
        this.mongo.MongoClient.connect(this.app.get('db'), function(err, db) {
            if (err) {
                funcionCallback(null);
            } else {
                var collection = db.collection('usuarios');
                collection.insert(usuario, function(err, result) {
                    if (err) {
                        funcionCallback(null);
                    } else {
                        funcionCallback(result.ops[0]._id);
                    }
                    db.close();
                });
            }
        });
    },
    //Gestion de ofertas
    addSale: function(sale, callbackFunction){
        this.mongo.MongoClient.connect(this.app.get('db'), function(err, db) {
            if (err) {
                callbackFunction(null);
            } else {
                var collection = db.collection('sales');
                collection.insert(sale, function(err, result) {
                    if (err) {
                        callbackFunction(null);
                    } else {
                        callbackFunction(result.ops[0]._id);
                    }
                    db.close();
                });
            }
        });
    }
};