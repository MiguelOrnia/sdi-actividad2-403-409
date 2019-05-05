package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import test.pageobjects.PO_AddSale;
import test.pageobjects.PO_BoughtView;
import test.pageobjects.PO_Client_LoginView;
import test.pageobjects.PO_HomeView;
import test.pageobjects.PO_LoginView;
import test.pageobjects.PO_Messages;
import test.pageobjects.PO_MySales;
import test.pageobjects.PO_NavView;
import test.pageobjects.PO_RegisterView;
import test.pageobjects.PO_SearchView;
import test.pageobjects.PO_UserList;
import test.pageobjects.PO_View;
import test.utils.SeleniumUtils;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SdiActividad2403409ApplicationTests {

	static String PathFirefox64 = "../../FirefoxPortable/FirefoxPortable.exe";

	// Path Miguel
	static String Geckdriver024 = "C:\\Users\\Emilio\\Documents\\SDI\\PL-SDI-Sesión5-material\\PL-SDI-Sesión5-material\\geckodriver024win64.exe";

	static WebDriver driver = getDriver(PathFirefox64, Geckdriver024);
	static String URL = "http://localhost:8081";

	public static WebDriver getDriver(String PathFirefox, String Geckdriver) {
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		System.setProperty("webdriver.gecko.driver", Geckdriver);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}

	// Antes de cada prueba se navega al URL home de la aplicación
	@Before
	public void setUp() throws ParseException {
		JavaMongoDbInsertData javaMongodbInsertData = new JavaMongoDbInsertData();
		javaMongodbInsertData.dataInsertion();
		driver.navigate().to(URL);
	}

	// Después de cada prueba se borran las cookies del navegador
	@After
	public void tearDown() {
		driver.manage().deleteAllCookies();
	}

	// Antes de la primera prueba
	@BeforeClass
	static public void begin() {
	}

	// Al finalizar la última prueba
	@AfterClass
	static public void end() {
		// Cerramos el navegador al finalizar las pruebas
		driver.quit();
	}

	/**
	 * PR01. Registro de Usuario con datos válidos
	 */

	@Test
	public void PR01() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "registrarse", "class",
				"underlineHover");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "test@email.com", "test_", "test_",
				"123456", "123456");
		// Comprobamos que entramos en la sección privada
		PO_RegisterView.checkKey(driver, "Bienvenidos a la aplicación");
	}

	/**
	 * PR02. Registro de Usuario con datos inválidos (email vacío, nombre vacío,
	 * apellidos vacíos)
	 */

	@Test
	public void PR02() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "registrarse", "class",
				"underlineHover");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "", "test_", "test_", "123456",
				"123456");
		// Comprobamos el error de email vacío.
		PO_RegisterView.checkKey(driver, "El email no puede estar vacío");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "test@email.com", "", "test_",
				"123456", "123456");
		// Comprobamos el error de nombre vacío.
		PO_RegisterView.checkKey(driver, "El nombre no puede estar vacío");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "test@email.com", "test_", "",
				"123456", "123456");
		// Comprobamos el error de apellidos vacío.
		PO_RegisterView.checkKey(driver, "El apellido no puede estar vacío");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "test@email.com", "test_", "test_", "",
				"");
		// Comprobamos el error de contraseña vacía.
		PO_RegisterView.checkKey(driver, "La contraseña no puede estar vacía");
	}

	/**
	 * PR03. Registro de Usuario con datos inválidos (repetición de contraseña
	 * inválida).
	 */

	@Test
	public void PR03() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "registrarse", "class",
				"underlineHover");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "test@email.com", "test", "test",
				"1234567890", "1234578901");
		// Comprobamos que el error existe
		PO_RegisterView.checkKey(driver, "Las contraseñas no coinciden");
	}

	/**
	 * PR04. Inicio de sesión con datos válidos.
	 */

	@Test
	public void PR04() {
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "admin@email.com", "admin");
		// Comprobamos que es el admin
		PO_NavView.checkIsAdmin(driver);
	}

	/**
	 * PR05. Inicio de sesión con datos válidos (email existente, pero
	 * contraseña incorrecta).
	 */

	@Test
	public void PR05() {
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "miguel@email.com", "123456789");
		// Comprobamos que el error existe
		PO_LoginView.checkKey(driver, "Email o password incorrecto");
	}

	/**
	 * PR06. Inicio de sesión con datos inválidos (campo email y contraseña
	 * vacíos).
	 */

	@Test
	public void PR06() {
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "   ", "   ");
		// Comprobamos que el error existe
		PO_LoginView.checkKey(driver, "Email o password incorrecto");
	}

	/**
	 * PR07. Inicio de sesión con datos inválidos (email no existente en la
	 * aplicación).
	 */

	@Test
	public void PR07() {
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "noexisto@email.com", "123456");
		// Comprobamos que el error existe
		PO_LoginView.checkKey(driver, "Email o password incorrecto");
	}

	/**
	 * PR08. Hacer click en la opción de salir de sesión y comprobar que se
	 * redirige a la página de inicio de sesión (Login).
	 */

	@Test
	public void PR08() {
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "miguel@email.com", "password");
		// Salimos de sesión
		List<WebElement> elementos = PO_View.checkElement(driver, "free",
				"//li[contains(@id, 'desconexion')]/a");
		elementos.get(0).click();
		// Comprobamos que entramos en la página de login
		PO_LoginView.checkElement(driver, "id", "login");
	}

	/**
	 * PR09. Comprobar que el botón cerrar sesión no está visible si el usuario
	 * no está autenticado.
	 */

	@Test
	public void PR09() {
		PO_View.checkNoKey(driver, "Desconectar");
	}

	/**
	 * PR10. Mostrar el listado de usuarios y comprobar que se muestran todos
	 * los que existen en el sistema.
	 */

	@Test
	public void PR10() {
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "admin@email.com", "admin");
		// Vamos a la lista de usuarios
		PO_UserList.goToPage(driver);
		// Conseguimos los usuarios
		List<WebElement> elementos = PO_UserList.checkElement(driver, "class",
				"checkBox");
		assertTrue(elementos.size() == 5);
		PO_UserList.checkElement(driver, "text", "miguel@email.com");
		PO_UserList.checkElement(driver, "text", "alfredo@email.com");
		PO_UserList.checkElement(driver, "text", "paco@email.com");
		PO_UserList.checkElement(driver, "text", "maria@hotmail.es");
		PO_UserList.checkElement(driver, "text", "alvaro@email.com");
	}

	/**
	 * PR11. Ir a la lista de usuarios, borrar el primer usuario de la lista,
	 * comprobar que la lista se actualiza y dicho usuario desaparece.
	 */

	@Test
	public void PR11() {
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "admin@email.com", "admin");
		// Vamos a la lista de usuarios
		PO_UserList.goToPage(driver);
		// Conseguimos los usuarios
		PO_UserList.deleteUser(driver, 0);
		List<WebElement> elementos = PO_UserList.checkElement(driver, "class",
				"checkBox");
		assertTrue(elementos.size() == 4);
		PO_UserList.checkElement(driver, "text", "alfredo@email.com");
		PO_UserList.checkElement(driver, "text", "paco@email.com");
		PO_UserList.checkElement(driver, "text", "maria@hotmail.es");
		PO_UserList.checkElement(driver, "text", "alvaro@email.com");
	}

	/**
	 * PR12. Ir a la lista de usuarios, borrar el último usuario de la lista,
	 * comprobar que la lista se actualiza y dicho usuario desaparece.
	 */

	@Test
	public void PR12() {
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "admin@email.com", "admin");
		// Vamos a la lista de usuarios
		PO_UserList.goToPage(driver);
		// Conseguimos los usuarios
		PO_UserList.deleteUser(driver, 4);
		List<WebElement> elementos = PO_UserList.checkElement(driver, "class",
				"checkBox");
		assertTrue(elementos.size() == 4);
		PO_UserList.checkElement(driver, "text", "miguel@email.com");
		PO_UserList.checkElement(driver, "text", "alfredo@email.com");
		PO_UserList.checkElement(driver, "text", "paco@email.com");
		PO_UserList.checkElement(driver, "text", "maria@hotmail.es");
	}

	/**
	 * PR13. Ir a la lista de usuarios, borrar 3 usuarios, comprobar que la
	 * lista se actualiza y dichos usuarios desaparecen.
	 */

	@Test
	public void PR13() {
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "admin@email.com", "admin");
		// Vamos a la lista de usuarios
		PO_UserList.goToPage(driver);
		// Conseguimos los usuarios
		PO_UserList.deleteUser(driver, 0, 1, 2);
		List<WebElement> elementos = PO_UserList.checkElement(driver, "class",
				"checkBox");
		assertTrue(elementos.size() == 2);
		PO_UserList.checkElement(driver, "text", "maria@hotmail.es");
		PO_UserList.checkElement(driver, "text", "alvaro@email.com");
	}

	/**
	 * PR14.Ir al formulario de alta de oferta, rellenarla con datos válidos y
	 * pulsar el botón Submit. Comprobar que la oferta sale en el listado de
	 * ofertas de dicho usuario.
	 */

	@Test
	public void PR14() {
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "miguel@email.com", "password");
		// Vamos a la pagina
		PO_AddSale.goToPage(driver);
		// Rellenar
		SeleniumUtils.EsperaCargaPagina(driver, "id", "title", 2);
		PO_AddSale.addSale(driver, "Test", "Oferta para testear", 100.0);
		// Comprobar que ha sido añadida
		PO_MySales.goToPage(driver);
		PO_MySales.checkElement(driver, "text", "Test");
		PO_MySales.checkElement(driver, "text", "Oferta para testear");
	}

	/**
	 * PR15.Ir al formulario de alta de oferta, rellenarla con datos inválidos
	 * (campo título vacío) y pulsar el botón Submit. Comprobar que se muestra
	 * el mensaje de campo obligatorio.
	 */

	@Test
	public void PR15() {
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "miguel@email.com", "password");
		// Vamos a la pagina
		PO_AddSale.goToPage(driver);
		// Rellenar
		SeleniumUtils.EsperaCargaPagina(driver, "id", "title", 2);
		PO_AddSale.addSale(driver, "", "Oferta", 80.0);
		PO_AddSale.checkKey(driver, "El título no puede estar vacío");
		PO_AddSale.addSale(driver, "Test", "", 80.0);
		PO_AddSale.checkKey(driver, "La descripción no puede estar vacía");
		PO_AddSale.addSale(driver, "Test", "Oferta", -200.0);
		PO_AddSale.checkKey(driver, "El precio no puede ser negativo");
	}

	/**
	 * PR16. Mostrar el listado de ofertas para dicho usuario y comprobar que se
	 * muestran todas los que existen para este usuario.
	 */

	@Test
	public void PR16() {
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "miguel@email.com", "password");

		// Nos dirigimos a la lista de nuestras Ofertas
		PO_MySales.goToPage(driver);
		SeleniumUtils.EsperaCargaPagina(driver, "class", "deleteSale", 2);
		SeleniumUtils.textoPresentePagina(driver, "Coche");
		SeleniumUtils.textoPresentePagina(driver, "Botella");
		SeleniumUtils.textoPresentePagina(driver, "Casa");
		SeleniumUtils.textoPresentePagina(driver, "Boligrafo");
		SeleniumUtils.textoPresentePagina(driver, "Ordenador");
		SeleniumUtils.textoPresentePagina(driver, "Vendo Ford Fiesta");
		SeleniumUtils.textoPresentePagina(driver, "Vendo iPhone 4");
		SeleniumUtils.textoPresentePagina(driver, "Calculadora");
	}

	/**
	 * PR17. Ir a la lista de ofertas, borrar la primera oferta de la lista,
	 * comprobar que la lista se actualiza y que la oferta desaparece.
	 */

	@Test
	public void PR17() {
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "miguel@email.com", "password");

		// Nos dirigimos a la lista de nuestras Ofertas
		PO_MySales.goToPage(driver);
		SeleniumUtils.EsperaCargaPagina(driver, "class", "deleteSale", 2);
		// Eliminamos la primera oferta
		// Devuelve true en caso de que se haya eliminado dicha oferta
		assertTrue(PO_MySales.deleteItem(driver, 0));

	}

	/**
	 * PR18. Ir a la lista de ofertas, borrar la última oferta de la lista,
	 * comprobar que la lista se actualiza y que la oferta desaparece.
	 */

	@Test
	public void PR18() {
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "miguel@email.com", "password");

		// Nos dirigimos a la lista de nuestras Ofertas
		PO_MySales.goToPage(driver);
		SeleniumUtils.EsperaCargaPagina(driver, "class", "deleteSale", 2);

		// Cogemos el indice del ultimo objeto eliminable
		int lastIndex = PO_MySales.checkNumberOfDeleteableItems(driver) - 1;

		SeleniumUtils.EsperaCargaPagina(driver, "class", "deleteSale", 2);
		// Eliminamos Ultima oferta y comprobamos que los elementos se
		// redujeron.
		assertTrue(PO_MySales.deleteItem(driver, lastIndex));

	}

	/**
	 * PR19. Hacer una búsqueda con el campo vacío y comprobar que se muestra la
	 * página que corresponde con el listado de las ofertas existentes en el
	 * sistema
	 */

	@Test
	public void PR19() {
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "miguel@email.com", "password");
		PO_SearchView.goToPage(driver);
		// Buscar
		PO_SearchView.searchForSale(driver, "");
		// Comprobar las ofertas
		List<WebElement> sale = PO_SearchView.checkElement(driver, "class",
				"table-light");
		assertTrue(sale.size() == 5);
		SeleniumUtils.textoPresentePagina(driver, "Coche");
		SeleniumUtils.textoPresentePagina(driver, "Consola");
		SeleniumUtils.textoPresentePagina(driver, "Raton");
		SeleniumUtils.textoPresentePagina(driver, "Joya");
		SeleniumUtils.textoPresentePagina(driver, "Botella");
	}

	/**
	 * PR20. Hacer una búsqueda escribiendo en el campo un texto que no exista y
	 * comprobar que se muestra la página que corresponde, con la lista de
	 * ofertas vacía.
	 */

	@Test
	public void PR20() {
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "miguel@email.com", "password");
		PO_SearchView.goToPage(driver);
		// Buscar
		PO_SearchView.searchForSale(driver, "noExisto");
		// Comprobar las ofertas
		SeleniumUtils.textoNoPresentePagina(driver, "Consola");
		SeleniumUtils.textoNoPresentePagina(driver, "Raton");
		SeleniumUtils.textoNoPresentePagina(driver, "Joya");
		SeleniumUtils.textoNoPresentePagina(driver, "Gafas");
		SeleniumUtils.textoNoPresentePagina(driver, "Caña de Pescar");
	}

	/**
	 * PR21. Hacer una búsqueda escribiendo en el campo un texto en minúscula o
	 * mayúscula y comprobar que se muestra la página que corresponde, con la
	 * lista de ofertas que contengan dicho texto, independientemente que el
	 * título esté almacenado en minúsculas o mayúscula.
	 */

	@Test
	public void PR21() {
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "miguel@email.com", "password");
		PO_SearchView.goToPage(driver);
		// Buscar
		PO_SearchView.searchForSale(driver, "RaToN");
		// Comprobar que está
		SeleniumUtils.textoPresentePagina(driver, "Raton");
	}

	/**
	 * PR22. Sobre una búsqueda determinada, comprar una oferta que deja un
	 * saldo positivo en el contador del comprobador. Y comprobar que el
	 * contador se actualiza correctamente en la vista del comprador.
	 */

	@Test
	public void PR22() {
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "miguel@email.com", "password");
		PO_SearchView.goToPage(driver);
		// Buscar
		PO_SearchView.searchForSale(driver, "Raton");
		// Comprar la oferta
		PO_SearchView.buySale(driver, "Raton");
		// Comprobar balance
		PO_HomeView.goToPage(driver);
		double balance = PO_HomeView.getUserBalance(driver);
		assertEquals(50.0, balance, 0.1);
	}

	/**
	 * PR23. Sobre una búsqueda determinada (a elección de desarrollador),
	 * comprar una oferta que deja un saldo 0 en el contador del comprobador. Y
	 * comprobar que el contador se actualiza correctamente en la vista del
	 * comprador.
	 */

	@Test
	public void PR23() {
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "miguel@email.com", "password");
		PO_SearchView.goToPage(driver);
		// Buscar
		PO_SearchView.searchForSale(driver, "Vendo Cosa Random 2");
		// Comprar la oferta
		PO_SearchView.buySale(driver, "Vendo Cosa Random 2");
		SeleniumUtils.EsperaCargaPagina(driver, "class", "form-group", 2);
		// Comprobar balance
		PO_HomeView.goToPage(driver);
		double balance = PO_HomeView.getUserBalance(driver);
		assertEquals(0.0, balance, 0.1);
	}

	/**
	 * PR24. Sobre una búsqueda determinada (a elección de desarrollador),
	 * intentar comprar una oferta que está por encima de saldo disponible del
	 * comprador. Y comprobar que se muestra el mensaje de saldo no suficiente.
	 */

	@Test
	public void PR24() {
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "miguel@email.com", "password");
		PO_SearchView.goToPage(driver);
		// Buscar
		PO_SearchView.searchForSale(driver, "Consola");
		// Comprar la oferta
		PO_SearchView.buySale(driver, "Consola");
		// Comprobar mensaje
		assertEquals(PO_SearchView.getMessageError(driver),
				"¡Atención! Ha ocurrido un error procesando la oferta");
	}

	/**
	 * PR25. Ir a la opción de ofertas compradas del usuario y mostrar la lista.
	 * Comprobar que aparecen las ofertas que deben aparecer.
	 */
	@Test
	public void PR25() {
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "miguel@email.com", "password");
		PO_BoughtView.goToPage(driver);
		assertEquals(1, PO_BoughtView.checkNumberOfItems(driver));
	}

	/**
	 * PR29. Inicio de sesión con datos válidos.
	 */
	@Test
	public void PR29() {
		driver.navigate().to(URL + "/mensajes.html");
		// Rellenamos el formulario
		PO_Client_LoginView.fillForm(driver, "paco@email.com", "password");
		// Comprobamos que hemos accedido con exito a la lista de ofertas
		PO_Client_LoginView.checkElement(driver, "id", "salesListTitle");
	}

	/**
	 * PR30. Inicio de sesión con datos inválidos (email existente, pero
	 * contraseña incorrecta).
	 */
	@Test
	public void PR30() {
		driver.navigate().to(URL + "/mensajes.html");
		// Rellenamos el formulario
		PO_Client_LoginView.fillForm(driver, "paco@email.com", "nopassword");
		// Comprobamos que seguimos en la página de login
		PO_Client_LoginView.checkElement(driver, "id", "loginTitle");
		// Comprobamos que se ha mostrado el mensaje de error
		PO_Client_LoginView.checkKey(driver, "Usuario no encontrado");
	}

	/**
	 * PR31. Inicio de sesión con datos inválidos (email existente, pero
	 * contraseña incorrecta).
	 */
	@Test
	public void PR31() {
		driver.navigate().to(URL + "/mensajes.html");
		// Rellenamos el formulario con la contraseña vacia
		PO_Client_LoginView.fillForm(driver, "paco@email.com", "");
		// Comprobamos que seguimos en la página de login
		PO_Client_LoginView.checkElement(driver, "id", "loginTitle");
		// Comprobamos que se ha mostrado el mensaje de error
		PO_Client_LoginView.checkKey(driver, "Usuario no encontrado");
	}

	/**
	 * PR32.Mostrar el listado de ofertas disponibles y comprobar que se
	 * muestran todas las que existen, menos las del usuario identificado.
	 */
	@Test
	public void PR32() {
		driver.navigate().to(URL + "/mensajes.html");
		// Rellenamos el formulario
		PO_Client_LoginView.fillForm(driver, "paco@email.com", "password");
		// Comprobamos que hemos accedido con exito a la lista de ofertas
		PO_Client_LoginView.checkElement(driver, "id", "salesListTitle");
		List<WebElement> elementos = PO_UserList.checkElement(driver, "class",
				"saleRow");
		// Comprobamos que están todas las ofertas
		assertEquals(26, elementos.size());
	}

	/**
	 * PR33.Sobre una búsqueda determinada de ofertas (a elección de
	 * desarrollador), enviar un mensaje a una oferta concreta. Se abriría dicha
	 * conversación por primera vez. Comprobar que el mensaje aparece en el
	 * listado de mensajes.
	 */
	@Test
	public void PR33() {
		driver.navigate().to(URL + "/mensajes.html");
		// Rellenamos el formulario
		PO_Client_LoginView.fillForm(driver, "paco@email.com", "password");
		// Comprobamos que hemos accedido con exito a la lista de ofertas
		PO_Client_LoginView.checkElement(driver, "id", "salesListTitle");
		PO_Messages.goToPage(driver);
		// Necesita hacer las comprobaciones y devolver la conversacion
		// correspondiente
		SeleniumUtils.esperarSegundos(driver, 3);
		PO_Messages.sendMessage(driver, "Buenas");
		SeleniumUtils.esperarSegundos(driver, 2);
		List<WebElement> elementos = PO_UserList.checkElement(driver, "class",
				"sent");
		// Comprobamos que está el mensaje enviado
		assertEquals(1, elementos.size());
	}

	/**
	 * PR34.Sobre el listado de conversaciones enviar un mensaje a una
	 * conversación ya abierta. Comprobar que el mensaje aparece en el listado
	 * de mensajes.
	 */
	@Test
	public void PR34() {
		driver.navigate().to(URL + "/mensajes.html");
		// Rellenamos el formulario
		PO_Client_LoginView.fillForm(driver, "paco@email.com", "password");
		// Comprobamos que hemos accedido con exito a la lista de ofertas
		PO_Client_LoginView.checkElement(driver, "id", "salesListTitle");
		PO_Messages.goToPage(driver);
		// Necesita hacer las comprobaciones y devolver la conversacion
		// correspondiente
		SeleniumUtils.esperarSegundos(driver, 3);
		//Enviamos el primer mensaje
		PO_Messages.sendMessage(driver, "Buenas");
		SeleniumUtils.esperarSegundos(driver, 2);
		List<WebElement> elementos = PO_UserList.checkElement(driver, "class",
				"sent");
		// Comprobamos que está el mensaje enviado
		assertEquals(1, elementos.size());
		
		//Vamos a simular que el usuario vuelve a iniciar sesion y envia otro mensaje a la misma oferta 
		driver.navigate().to(URL + "/mensajes.html");
		PO_Client_LoginView.fillForm(driver, "paco@email.com", "password");
		PO_Client_LoginView.checkElement(driver, "id", "salesListTitle");
		PO_Messages.goToPage(driver);
		SeleniumUtils.esperarSegundos(driver, 3);
		//Enviamos el segundo mensaje
		PO_Messages.sendMessage(driver, "Queria saber el precio del coche");
		SeleniumUtils.esperarSegundos(driver, 2);
		elementos = PO_UserList.checkElement(driver, "class",
				"sent");
		// Comprobamos que está el mensaje enviado
		assertEquals(2, elementos.size());
	}

}