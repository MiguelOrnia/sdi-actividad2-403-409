module.exports = {
    mongo: null,
    app: null,
    init: function (app, mongo) {
        this.mongo = mongo;
        this.app = app;
    },
    // Gestion de usuarios
    obtenerUsuarios: function (criterio, funcionCallback) {
        this.mongo.MongoClient.connect(this.app.get('db'), function (err, db) {
            if (err) {
                funcionCallback(null);
            } else {
                var collection = db.collection('usuarios');
                collection.find(criterio).toArray(function (err, usuarios) {
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
    insertarUsuario: function (usuario, funcionCallback) {
        this.mongo.MongoClient.connect(this.app.get('db'), function (err, db) {
            if (err) {
                funcionCallback(null);
            } else {
                var collection = db.collection('usuarios');
                collection.insert(usuario, function (err, result) {
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
    eliminarUsuarios: function (criterio, nuevoCriterio, callbackFunction) {
        this.mongo.MongoClient.connect(this.app.get('db'), function (err, db) {
            if (err) {
                callbackFunction(null);
            } else {
                var collection = db.collection('usuarios');
                collection.updateMany(criterio, {
                    $set: nuevoCriterio
                }, function (err, result) {
                    if (err) {
                        callbackFunction(null);
                    } else {
                        callbackFunction(result);
                    }
                    db.close();
                });
            }
        });
    },
    //Gestion de ofertas
    addSale: function (sale, callbackFunction) {
        this.mongo.MongoClient.connect(this.app.get('db'), function (err, db) {
            if (err) {
                callbackFunction(null);
            } else {
                var collection = db.collection('sales');
                collection.insert(sale, function (err, result) {
                    if (err) {
                        callbackFunction(null);
                    } else {
                        callbackFunction(result.ops[0]._id);
                    }
                    db.close();
                });
            }
        });
    },
    obtenerOfertas: function (criterio, funcionCallback) {
        this.mongo.MongoClient.connect(this.app.get('db'), function (err, db) {
            if (err) {
                funcionCallback(null);
            } else {
                var collection = db.collection('sales');
                collection.find(criterio).toArray(function (err, ofertas) {
                    if (err) {
                        funcionCallback(null);
                    } else {
                        funcionCallback(ofertas);
                    }
                    db.close();
                });
            }
        });
    },
    eliminarOferta: function (criterio, funcionCallback) {
        this.mongo.MongoClient.connect(this.app.get('db'), function (err, db) {
            if (err) {
                funcionCallback(null);
            } else {
                var collection = db.collection('sales');
                collection.remove(criterio, function (err, result) {
                    if (err) {
                        funcionCallback(null);
                    } else {
                        funcionCallback(result);
                    }
                    db.close();
                });
            }
        });
    },
    obtenerOfertasPg: function (criterio, pg, funcionCallback) {
        this.mongo.MongoClient.connect(this.app.get('db'), function (err, db) {
            if (err) {
                funcionCallback(null);
            } else {
                var collection = db.collection('sales');
                collection.count(function (err, count) {
                    collection.find(criterio).skip((pg - 1) * 5).limit(5)
                        .toArray(function (err, sales) {
                            if (err) {
                                funcionCallback(null);
                            } else {
                                funcionCallback(sales, count);
                            }
                            db.close();
                        });
                });
            }
        });
    },
    comprarOferta: function (criterio, buyer, funcionCallback) {
        var buyerId = this.mongo.ObjectID(buyer._id);
        this.mongo.MongoClient.connect(this.app.get('db'), function (err, db) {
            if (err) {
                funcionCallback(null);
            } else {
                var collection = db.collection('sales');

                collection.find(criterio).limit(1).toArray(function (err, sales) {
                    if (err) {
                        db.close();
                        funcionCallback(null);
                    } else {
                        if (sales[0].seller._id.toString() != buyer._id.toString() && buyer.money >= sales[0].price) {
                            var usersCollection = db.collection('usuarios');
                            var criterioUser = {_id: buyerId};
                            var updatedMoney = {money: buyer.money - sales[0].price};

                            usersCollection.updateOne(criterioUser, {$set: updatedMoney}, function (err, result) {
                                if (err) {
                                    db.close();
                                    funcionCallback(null);
                                } else {
                                    buyer.money = updatedMoney.money;
                                    // Si el comprador no es el vendedor marcamos la oferta como comprada
                                    var compra = {buyer: buyer};
                                    collection.updateOne(criterio, {$set: compra}, function (err, result) {
                                        if (err) {
                                            db.close();
                                            funcionCallback(null);
                                        } else {
                                            db.close();
                                            funcionCallback(result);
                                        }
                                    });
                                }
                            });
                        } else {
                            db.close();
                            funcionCallback(null);
                        }
                    }

                });
            }
        });
    },
    // Gestion de mensajes (API)
    insertarMensaje: function (message, funcionCallback) {
        this.mongo.MongoClient.connect(this.app.get('db'), function (err, db) {
            if (err) {
                funcionCallback(null);
            } else {
                let collection = db.collection('mensajes');
                collection.insert(message, function (err, result) {
                    if (err) {
                        funcionCallback(null);
                    } else {
                        funcionCallback(result.ops[0]);
                    }
                    db.close();
                });
            }
        });
    },
    obtenerMensajes: function (criterio, funcionCallback) {
        this.mongo.MongoClient.connect(this.app.get('db'), function (err, db) {
            if (err) {
                funcionCallback(null);
            } else {
                var collection = db.collection('mensajes');
                collection.find(criterio).toArray(function (err, mensajes) {
                    if (err) {
                        funcionCallback(null);
                    } else {
                        funcionCallback(mensajes);
                    }
                    db.close();
                });
            }
        });
    },
    marcarLeido: function (criterio, funcionCallback) {
        this.mongo.MongoClient.connect(this.app.get('db'), function (err, db) {
            if (err) {
                funcionCallback(null);
            } else {
                var collection = db.collection('mensajes');
                collection.update(criterio, {$set: {read: true}}, function (err, resultado) {
                    if (err) {
                        funcionCallback(null);
                    } else {
                        funcionCallback(resultado);
                    }
                });
            }
            db.close();
        });
    },
    findConversation: function (criterio, funcionCallback) {
        this.mongo.MongoClient.connect(this.app.get('db'), function (err, db) {
            if (err) {
                funcionCallback(null);
            } else {
                var collection = db.collection('conversations');
                collection.find(criterio).toArray(function (err, conversations) {
                    if (err) {
                        funcionCallback(null);
                    } else {
                        funcionCallback(conversations);
                    }
                    db.close();
                });
            }
        });
    },
    createConversation: function (sale, usuario, funcionCallback) {
        var conversation = {sale: sale, user: usuario, messages: []};
        this.mongo.MongoClient.connect(this.app.get('db'), function (err, db) {
            if (err) {
                funcionCallback(null);
            } else {
                let collection = db.collection('conversations');
                collection.insert(conversation, function (err, result) {
                    if (err) {
                        funcionCallback(null);
                    } else {
                        funcionCallback(result.ops[0]);
                    }
                    db.close();
                });
            }
        });
    },
    insertMessage: function (conversation, message, funcionCallback) {
        var criterio = {_id: conversation._id};
        var m = conversation.messages;
        m.push(message);
        var updatedMessages = {messages: m};
        this.mongo.MongoClient.connect(this.app.get('db'), function (err, db) {
            if (err) {
                funcionCallback(null);
            } else {
                let collection = db.collection('conversations');
                collection.updateOne(criterio, {$set: updatedMessages},function (err, result) {
                    if (err) {
                        funcionCallback(null);
                    } else {
                        funcionCallback(result);
                    }
                    db.close();
                });
            }
        });
    }
};