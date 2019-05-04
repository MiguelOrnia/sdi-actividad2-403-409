package test;

import java.text.ParseException;
import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * Clase de pruebas para la inserción de datos en MongoDB
 */

public class JavaMongoDbInsertData {

	private MongoClient mongoClient; // Cliente Java MongoDB
	private MongoDatabase mongodb; // Objeto base de datos

	/**
	 * Método para establecer conexión con la bd de MongoDB
	 */
	
	public void connectDatabase() {
		try {
			setMongoClient(new MongoClient(new MongoClientURI(
					"mongodb://admin:sdi@tiendamusica-shard-00-00-ri6vu.mongodb.net:27017,tiendamusica-shard-00-01-ri6vu.mongodb.net:27017,tiendamusica-shard-00-02-ri6vu.mongodb.net:27017/test?ssl=true&replicaSet=tiendamusica-shard-0&authSource=admin&retryWrites=true")));
			setMongodb(getMongoClient().getDatabase("test"));
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}

	/**
	 * Método para insertar ofertas y usuarios en la bd de MongoDB
	 * Se usará después de cada test para reiniciar la misma
	 */
	
	public void insertOneDataTest() throws ParseException {
		try {
			// Usuarios
			MongoCollection<Document> col = getMongodb().getCollection("usuarios");
			Document user1 = new Document().append("_id", 1)
					.append("name", "Miguel")
					.append("surname", "Ornia Gomez")
					.append("email", "miguel@email.com")
					.append("password", "742d7b38a9ffd280abe7d115d76268478d2b8c736ff72945c8b4e0bc982a46ba")
					.append("active",true)
					.append("money", 100)
					.append("rol", "rol_estandar");
			Document user2 = new Document()
					.append("name", "Alfredo").append("_id", 2)
					.append("surname", "Perez Fernandez")
					.append("email", "alfredo@email.com")
					.append("password", "742d7b38a9ffd280abe7d115d76268478d2b8c736ff72945c8b4e0bc982a46ba")
					.append("active",true)
					.append("money", 100)
					.append("rol", "rol_estandar");
			Document user3 = new Document().append("_id", 3)
					.append("name", "Paco")
					.append("surname", "Salvador Vega")
					.append("email", "paco@email.com")
					.append("password", "742d7b38a9ffd280abe7d115d76268478d2b8c736ff72945c8b4e0bc982a46ba")
					.append("active",true)
					.append("money", 100)
					.append("rol", "rol_estandar");
			Document user4 = new Document().append("_id", 4)
					.append("name", "Maria")
					.append("surname", "Perez Hernandez")
					.append("email", "maria@hotmail.es")
					.append("password", "742d7b38a9ffd280abe7d115d76268478d2b8c736ff72945c8b4e0bc982a46ba")
					.append("active",true)
					.append("money", 100)
					.append("rol", "rol_estandar");
			Document user5 = new Document().append("_id", 5)
					.append("name", "Alvaro")
					.append("surname", "Sanchez Vega")
					.append("email", "alvaro@email.com")
					.append("password", "742d7b38a9ffd280abe7d115d76268478d2b8c736ff72945c8b4e0bc982a46ba")
					.append("active",true)
					.append("money", 100)
					.append("rol", "rol_estandar");
			Document admin = new Document().append("name", "admin")
					.append("surname", "")
					.append("email", "admin@email.com")
					.append("password", "ebd5359e500475700c6cc3dd4af89cfd0569aa31724a1bf10ed1e3019dcfdb11")
					.append("active",true)
					.append("rol", "rol_admin");
			
			col.insertOne(user1);
			col.insertOne(user2);
			col.insertOne(user3);
			col.insertOne(user4);
			col.insertOne(user5);
			col.insertOne(admin);

			// Ofertas
			col = getMongodb().getCollection("sales");
			col.insertOne(new Document()
					.append("title", "Coche")
					.append("details", "Vendo BMW")
					.append("price", 100)
					.append("buyer", user3)
					.append("seller", user1));
			col.insertOne(new Document()
					.append("title", "Consola")
					.append("details", "Vendo Play Station 5")
					.append("price", 500)
					.append("seller", user2));
			col.insertOne(new Document()
					.append("title", "Raton")
					.append("details", "Vendo raton de ordenador")
					.append("price", 50)
					.append("seller", user3));
			col.insertOne(new Document()
					.append("title", "Joya")
					.append("details", "Vendo pendiente de diamantes")
					.append("price", 25000)
					.append("seller", user3));
			
			col.insertOne(new Document()
					.append("title", "Botella")
					.append("details", "Vendo botella")
					.append("price", 25000)
					.append("seller", user1));
			col.insertOne(new Document()
					.append("title", "Casa")
					.append("details", "Vendo casa")
					.append("price", 25000)
					.append("buyer", user3)
					.append("seller", user1));
			
			col.insertOne(new Document()
					.append("title", "Boligrafo")
					.append("details", "Vendo boligrafo azul")
					.append("price", 25000)
					.append("seller", user1));
			col.insertOne(new Document()
					.append("title", "Ordenador")
					.append("details", "Vendo ordenador")
					.append("price", 25000)
					.append("seller", user1));
			col.insertOne(new Document()
					.append("title", "Vendo Play 3")
					.append("details", "En perfecto estado")
					.append("price", 80)
					.append("seller", user4));
			col.insertOne(new Document()
					.append("title", "Gafas")
					.append("details", "Vendo gafas")
					.append("price", 2500)
					.append("buyer", user1)
					.append("seller", user4));
			
			col.insertOne(new Document()
					.append("title", "Caña de Pescar")
					.append("details", "Vendo caña de pescar 9000")
					.append("price", 50)
					.append("seller", user4));
			col.insertOne(new Document()
					.append("title", "Vendo Ford Fiesta")
					.append("details", "En perfecto estado")
					.append("price", 4560.2)
					.append("seller", user1));
			col.insertOne(new Document()
					.append("title", "Vendo iPhone 4")
					.append("details", "Con arañazos")
					.append("price", 120)
					.append("seller", user1));
			col.insertOne(new Document()
					.append("title", "Calculadora")
					.append("details", "Vendo calculadora")
					.append("price", 25000)
					.append("buyer", user4)
					.append("seller", user1));
			
			col.insertOne(new Document()
					.append("title", "Vendo Cosa Random 1")
					.append("details", "En perfecto estado")
					.append("price", 4560.2)
					.append("seller", user2));
			col.insertOne(new Document()
					.append("title", "Vendo Cosa Random 2")
					.append("details", "Con arañazos")
					.append("price", 100)
					.append("seller", user2));
			col.insertOne(new Document()
					.append("title", "Vendo Cosa Random 3")
					.append("details", "En perfecto estado")
					.append("price", 80)
					.append("seller", user2));
			col.insertOne(new Document()
					.append("title", "Vendo Cosa Random 4")
					.append("details", "En perfecto estado")
					.append("price", 4560.2)
					.append("seller", user3));
			col.insertOne(new Document()
					.append("title", "Vendo Cosa Random 5")
					.append("details", "Con arañazos")
					.append("price", 120)
					.append("seller", user3));
			col.insertOne(new Document()
					.append("title", "Vendo Cosa Random 6")
					.append("details", "En perfecto estado")
					.append("price", 80)
					.append("seller", user3));
			col.insertOne(new Document()
					.append("title", "Vendo Cosa Random 7")
					.append("details", "En perfecto estado")
					.append("price", 4560.2)
					.append("seller", user4));
			col.insertOne(new Document()
					.append("title", "Vendo Cosa Random 8")
					.append("details", "Con arañazos")
					.append("price", 120)
					.append("seller", user4));
			col.insertOne(new Document()
					.append("title", "Vendo Cosa Random 9")
					.append("details", "En perfecto estado")
					.append("price", 80)
					.append("seller", user4));
			col.insertOne(new Document()
					.append("title", "Vendo Cosa Random 10")
					.append("details", "En perfecto estado")
					.append("price", 4560.2)
					.append("seller", user5));
			col.insertOne(new Document()
					.append("title", "Vendo Cosa Random 11")
					.append("details", "Con arañazos")
					.append("price", 120)
					.append("seller", user4));
			col.insertOne(new Document()
					.append("title", "Vendo Cosa Random 12")
					.append("details", "En perfecto estado")
					.append("price", 80)
					.append("seller", user4));
			
			
		} catch (Exception ex) {
			System.out.print(ex.toString());
		}

	}

	public void removeDataTest() {
		getMongodb().getCollection("sales").drop();
		getMongodb().getCollection("usuarios").drop();
	}

	public MongoClient getMongoClient() {
		return mongoClient;
	}

	public void setMongoClient(MongoClient mongoClient) {
		this.mongoClient = mongoClient;
	}

	public MongoDatabase getMongodb() {
		return mongodb;
	}

	public void setMongodb(MongoDatabase mongodb) {
		this.mongodb = mongodb;
	}

	/**
	 * Añadiendo datos a la base test de MongoDB con Java: ejemplos
	 * insertOne e insertMany .
	 * 
	 * @param args
	 * @throws ParseException
	 */
	
	public void dataInsertion() throws ParseException {
		JavaMongoDbInsertData javaMongodbInsertData = new JavaMongoDbInsertData();
		System.out.println("Conectando a la base");
		javaMongodbInsertData.connectDatabase();
		System.out.println("Eliminando la base");
		javaMongodbInsertData.removeDataTest();
		System.out.println("Insertando en la base");
		javaMongodbInsertData.insertOneDataTest();
	}
}