package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
import test.pageobjects.PO_HomeView;
import test.pageobjects.PO_LoginView;
import test.pageobjects.PO_Messages;
import test.pageobjects.PO_MySales;
import test.pageobjects.PO_NavView;
import test.pageobjects.PO_Properties;
import test.pageobjects.PO_RegisterView;
import test.pageobjects.PO_SearchView;
import test.pageobjects.PO_UserList;
import test.pageobjects.PO_View;
import test.utils.SeleniumUtils;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SdiActividad2403409ApplicationTests {

	// Path Miguel
//	static String PathFirefox65 = "C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe";
//	static String Geckdriver024 = "C:\\Users\\Miguel\\Desktop\\"
//			+ "PL-SDI-Sesion5-material\\geckodriver024win64.exe";

//	// Path Emilio
	static String PathFirefox65 = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
	static String Geckdriver024 = "C:\\Users\\Emilio\\Documents\\SDI\\"
			+ "PL-SDI-SesiÃ³n5-material\\PL-SDI-SesioÌ?n5-material\\geckodriver024win64.exe";

	static WebDriver driver = getDriver(PathFirefox65, Geckdriver024);
	static String URL = "http://localhost:8081";

	public static WebDriver getDriver(String PathFirefox, String Geckdriver) {
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		System.setProperty("webdriver.gecko.driver", Geckdriver);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}

	// Antes de cada prueba se navega al URL home de la aplicaciÃ³nn
	@Before
	public void setUp() {
		driver.navigate().to(URL);
	}


	// DespuÃ©s de cada prueba se borran las cookies del navegador
	@After
	public void tearDown() {
		driver.manage().deleteAllCookies();
	}

	// Antes de la primera prueba
	@BeforeClass
	static public void begin() {
	}

	// Al finalizar la Ãºltima prueba
	@AfterClass
	static public void end() {
		// Cerramos el navegador al finalizar las pruebas
		driver.quit();
	}

	/**
	 * PR01. Registro de Usuario con datos vÃ¡lidos
	 */

	@Test
	public void PR01() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "underlineHover");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "test@email.com", "test_", "test_",
				"123456", "123456");
		// Comprobamos que entramos en la secciÃ³n privada
		PO_RegisterView.checkKey(driver, "welcome.message",
				PO_Properties.getSPANISH());
	}

	/**
	 * PR02. Registro de Usuario con datos invÃ¡lidos (email vacÃ­o, nombre vacÃ­o,
	 * apellidos vacÃ­os)
	 */

	@Test
	public void PR02() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "underlineHover");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, " ", "test_", "test_", "123456",
				"123456");
		// Comprobamos el error de email vacÃ­o.
		PO_RegisterView.checkKey(driver, "Error.signup.email.length",
				PO_Properties.getSPANISH());
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "test@email.com", " ", "test_",
				"123456", "123456");
		// Comprobamos el error de nombre vacÃ­o.
		PO_RegisterView.checkKey(driver, "Error.signup.name.length",
				PO_Properties.getSPANISH());
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "test@email.com", "test_", " ",
				"123456", "123456");
		// Comprobamos el error de apellidos vacÃ­o.
		PO_RegisterView.checkKey(driver, "Error.signup.surname.length",
				PO_Properties.getSPANISH());
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "test@email.com", "test_", "test_",
				" ", " ");
		// Comprobamos el error de contraseÃ±a vacÃ­a.
		PO_RegisterView.checkKey(driver, "Error.signup.password.length",
				PO_Properties.getSPANISH());
	}

	/**
	 * PR03. Registro de Usuario con datos invÃ¡lidos (repeticiÃ³n de contraseÃ±a
	 * invÃ¡lida).
	 */

	@Test
	public void PR03() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "underlineHover");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "test@email.com", "test", "test",
				"123456", "123457");
		// Comprobamos que el error existe
		PO_RegisterView.checkKey(driver, "Error.signup.repassword.coincidence",
				PO_Properties.getSPANISH());
	}

	/**
	 * PR04. Registro de Usuario con datos invÃ¡lidos (email existente).
	 */

	@Test
	public void PR04() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "underlineHover");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "miguel@email.com", "test", "test",
				"123456", "123456");
		// Comprobamos el error de email repetido.
		PO_RegisterView.checkKey(driver, "Error.signup.email.duplicate",
				PO_Properties.getSPANISH());
	}

	/**
	 * PR05. Inicio de sesiÃ³n con datos vÃ¡lidos (administrador).
	 */

	@Test
	public void PR05() {
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "admin@email.com", "admin");
		// Comprobamos que es el admin
		PO_NavView.checkIsAdmin(driver);
	}

	/**
	 * PR06. Inicio de sesiÃ³n con datos vÃ¡lidos (usuario estÃ¡ndar).
	 */

	@Test
	public void PR06() {
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "miguel@email.com", "password");
		// Comprobamos que es el admin
		PO_NavView.checkIsUser(driver);
	}

	/**
	 * PR07. Inicio de sesiÃ³n con datos invÃ¡lidos (usuario estÃ¡ndar, campo email
	 * y contraseÃ±a vacÃ­os).
	 */

	@Test
	public void PR07() {
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "   ", "   ");
		// Comprobamos que el error existe
		PO_LoginView.checkKey(driver, "login.error",
				PO_Properties.getSPANISH());
	}

	/**
	 * PR08. Inicio de sesiÃ³n con datos vÃ¡lidos (usuario estÃ¡ndar, email
	 * existente, pero contraseÃ±a incorrecta).
	 */

	@Test
	public void PR08() {
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "miguel@email.com", "123456789");
		// Comprobamos que el error existe
		PO_LoginView.checkKey(driver, "login.error",
				PO_Properties.getSPANISH());
	}

	/**
	 * PR09. Inicio de sesiÃ³n con datos invÃ¡lidos (usuario estÃ¡ndar, email no
	 * existente en la aplicaciÃ³n).
	 */

	@Test
	public void PR09() {
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "noexisto@email.com", "123456");
		// Comprobamos que el error existe
		PO_LoginView.checkKey(driver, "login.error",
				PO_Properties.getSPANISH());
	}

	/**
	 * PR10. Hacer click en la opciÃ³n de salir de sesiÃ³n y comprobar que se
	 * redirige a la pÃ¡gina de inicio de sesiÃ³n (Login).
	 */

	@Test
	public void PR10() {
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "miguel@email.com", "password");
		// Salimos de sesiÃ³n
		List<WebElement> elementos = PO_View.checkElement(driver, "free",
				"//li[contains(@id, 'desconexion')]/a");
		elementos.get(0).click();
		// Comprobamos que entramos en la pÃ¡gina de login
		PO_LoginView.checkElement(driver, "id", "login");
	}

	/**
	 * PR11. Comprobar que el botÃ³n cerrar sesiÃ³n no estÃ¡ visible si el usuario
	 * no estÃ¡ autenticado.
	 */

	@Test
	public void PR11() {
		PO_View.checkNoKey(driver, "logout.message",
				PO_Properties.getSPANISH());
	}

	/**
	 * PR12. Mostrar el listado de usuarios y comprobar que se muestran todos
	 * los que existen en el sistema.
	 */

	@Test
	public void PR12() {
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
	 * PR13. Ir a la lista de usuarios, borrar el primer usuario de la lista,
	 * comprobar que la lista se actualiza y dicho usuario desaparece.
	 */

	@Test
	public void PR13() {
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
	 * PR14. Ir a la lista de usuarios, borrar el Ãºltimo usuario de la lista,
	 * comprobar que la lista se actualiza y dicho usuario desaparece.
	 */

	@Test
	public void PR14() {
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
	 * PR15. Ir a la lista de usuarios, borrar 3 usuarios, comprobar que la
	 * lista se actualiza y dichos usuarios desaparecen.
	 */

	@Test
	public void PR15() {
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
	 * PR16.Ir al formulario de alta de oferta, rellenarla con datos vÃ¡lidos y
	 * pulsar el botÃ³n Submit. Comprobar que la oferta sale en el listado de
	 * ofertas de dicho usuario.
	 */

	@Test
	public void PR16() {
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "miguel@email.com", "password");
		// Vamos a la pagina
		PO_AddSale.goToPage(driver);
		// Rellenar
		PO_AddSale.addSale(driver, "Test", "Oferta para testear", 100.0);
		// Comprobar que ha sido aÃ±adida
		PO_MySales.goToPage(driver);
		PO_MySales.checkElement(driver, "text", "Test");
		PO_MySales.checkElement(driver, "text", "Oferta para testear");
	}

	/**
	 * PR17.Ir al formulario de alta de oferta, rellenarla con datos invÃ¡lidos
	 * (campo tÃ­tulo vacÃ­o) y pulsar el botÃ³n Submit. Comprobar que se muestra
	 * el mensaje de campo obligatorio.
	 */

	@Test
	public void PR17() {
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "miguel@email.com", "password");
		// Vamos a la pagina
		PO_AddSale.goToPage(driver);
		// Rellenar
		PO_AddSale.addSale(driver, " ", "Oferta", 80.0);
		PO_AddSale.checkKey(driver, "Error.empty", PO_Properties.getSPANISH());
		PO_AddSale.addSale(driver, "Test", " ", 80.0);
		PO_AddSale.checkKey(driver, "Error.empty", PO_Properties.getSPANISH());
		PO_AddSale.addSale(driver, "Test", "Oferta", -200.0);
		PO_AddSale.checkKey(driver, "Error.addSale.price.value",
				PO_Properties.getSPANISH());
	}

	/**
	 * PR18. Mostrar el listado de ofertas para dicho usuario y comprobar que se
	 * muestran todas los que existen para este usuario.
	 */

	@Test
	public void PR18() {
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "miguel@email.com", "password");

		// Nos dirigimos a la lista de nuestras Ofertas
		PO_MySales.goToPage(driver);
		
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
	 * PR19. Ir a la lista de ofertas, borrar la primera oferta de la lista,
	 * comprobar que la lista se actualiza y que la oferta desaparece.
	 */

	@Test
	public void PR19() {
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "miguel@email.com", "password");

		// Nos dirigimos a la lista de nuestras Ofertas
		PO_MySales.goToPage(driver);

		// Eliminamos la primera oferta
		// Devuelve true en caso de que se haya eliminado dicha oferta
		assertTrue(PO_MySales.deleteItem(driver, 0));

	}

	/**
	 * PR20. Ir a la lista de ofertas, borrar la Ãºltima oferta de la lista,
	 * comprobar que la lista se actualiza y que la oferta desaparece.
	 */

	@Test
	public void PR20() {
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "miguel@email.com", "password");

		// Nos dirigimos a la lista de nuestras Ofertas
		PO_MySales.goToPage(driver);

		// Cogemos el indice del ultimo objeto eliminable
		int lastIndex = PO_MySales.checkNumberOfDeleteableItems(driver) - 1;

		SeleniumUtils.esperarSegundos(driver, 2);
		// Eliminamos Ultima oferta y comprobamos que los elementos se
		// redujeron.
		assertTrue(PO_MySales.deleteItem(driver, lastIndex));

	}

	/**
	 * PR21. Hacer una bÃºsqueda con el campo vacÃ­o y comprobar que se muestra la
	 * pÃ¡gina que corresponde con el listado de las ofertas existentes en el
	 * sistema
	 */

	@Test
	public void PR21() {
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "miguel@email.com", "password");
		PO_SearchView.goToPage(driver);
		// Buscar
		PO_SearchView.searchForSale(driver, "");
		// Comprobar las ofertas
		List<WebElement> sale = PO_SearchView.checkElement(driver, "class",
				"table-light");
		assertTrue(sale.size() == 5);
		SeleniumUtils.textoPresentePagina(driver, "Consola");
		SeleniumUtils.textoPresentePagina(driver, "Raton");
		SeleniumUtils.textoPresentePagina(driver, "Joya");
		SeleniumUtils.textoPresentePagina(driver, "Gafas");
		SeleniumUtils.textoPresentePagina(driver, "CaÃ±a de Pescar");
	}

	/**
	 * PR22. Hacer una bÃºsqueda escribiendo en el campo un texto que no exista y
	 * comprobar que se muestra la pÃ¡gina que corresponde, con la lista de
	 * ofertas vacÃ­a.
	 */

	@Test
	public void PR22() {
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
		SeleniumUtils.textoNoPresentePagina(driver, "CaÃ±a de Pescar");
	}

	/**
	 * PR23. Sobre una bÃºsqueda determinada, comprar una oferta que deja un
	 * saldo positivo en el contador del comprobador. Y comprobar que el
	 * contador se actualiza correctamente en la vista del comprador.
	 */

	@Test
	public void PR23() {
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
	 * PR24. Sobre una bÃºsqueda determinada (a elecciÃ³n de desarrollador),
	 * comprar una oferta que deja un saldo 0 en el contador del comprobador. Y
	 * comprobar que el contador se actualiza correctamente en la vista del
	 * comprador.
	 */

	@Test
	public void PR24() {
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "miguel@email.com", "password");
		PO_SearchView.goToPage(driver);
		// Buscar
		PO_SearchView.searchForSale(driver, "Vendo Cosa Random 2");
		// Comprar la oferta
		PO_SearchView.buySale(driver, "Vendo Cosa Random 2");
		// Comprobar balance
		PO_HomeView.goToPage(driver);
		double balance = PO_HomeView.getUserBalance(driver);
		assertEquals(0.0, balance, 0.1);
	}

	/**
	 * PR25. Sobre una bÃºsqueda determinada (a elecciÃ³n de desarrollador),
	 * intentar comprar una oferta que estÃ© por encima de saldo disponible del
	 * comprador. Y comprobar que se muestra el mensaje de saldo no suficiente.
	 */

	@Test
	public void PR25() {
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "miguel@email.com", "password");
		PO_SearchView.goToPage(driver);
		// Buscar
		PO_SearchView.searchForSale(driver, "Consola");
		// Comprar la oferta
		PO_SearchView.buySale(driver, "Consola");
		// Comprobar balance
		PO_HomeView.checkKey(driver, "sale.buy.error",
				PO_Properties.getSPANISH());
	}

	/**
	 * PR26. Ir a la opciÃ³n de ofertas compradas del usuario y mostrar la lista.
	 * Comprobar que aparecen las ofertas que deben aparecer.
	 */
	@Test
	public void PR26() {
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "miguel@email.com", "password");
		PO_BoughtView.goToPage(driver);
		assertEquals(2, PO_BoughtView.checkNumberOfItems(driver));
	}

	/**
	 * PR27. InternalizaciÃ³n
	 */

	@Test
	public void PR27() {
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "miguel@email.com", "password");
		// Comprobamos las vistas de las paginas
		PO_HomeView.goToPage(driver);
		PO_HomeView.checkChangeIdiom(driver, "btnSpanish", "btnEnglish",
				PO_Properties.getSPANISH(), PO_Properties.getENGLISH());
		PO_NavView.checkChangeIdiomUser(driver, "btnSpanish", "btnEnglish",
				PO_Properties.getSPANISH(), PO_Properties.getENGLISH());
		PO_HomeView.goToPage(driver);
		PO_AddSale.goToPage(driver);
		PO_AddSale.checkChangeIdiom(driver, "btnSpanish", "btnEnglish",
				PO_Properties.getSPANISH(), PO_Properties.getENGLISH());
		// Como admin
		List<WebElement> elementos = PO_View.checkElement(driver, "free",
				"//li[contains(@id, 'desconexion')]/a");
		elementos.get(0).click();
		PO_LoginView.fillForm(driver, "admin@email.com", "admin");
		PO_NavView.checkChangeIdiomAdmin(driver, "btnSpanish", "btnEnglish",
				PO_Properties.getSPANISH(), PO_Properties.getENGLISH());
		PO_HomeView.goToPage(driver);
		PO_UserList.goToPage(driver);
		PO_UserList.checkChangeIdiom(driver, "btnSpanish", "btnEnglish",
				PO_Properties.getSPANISH(), PO_Properties.getENGLISH());
	}

	/**
	 * PR28. Intentar acceder sin estar autenticado a la opciÃ³n de listado de
	 * usuarios del administrador. Se deberÃ¡ volver al formulario de login.
	 */

	@Test
	public void PR28() {
		// Vamos a la pagina
		driver.navigate().to(URL + "/user/list/");
		SeleniumUtils.esperarSegundos(driver, 3);
		// Comprobamos que entramos en la pÃ¡gina de login
		PO_LoginView.checkElement(driver, "id", "login");
	}

	/**
	 * PR29. Intentar acceder sin estar autenticado a la opciÃ³n de listado de
	 * ofertas propias de un usuario estÃ¡ndar. Se deberÃ¡ volver al formulario de
	 * login.
	 */

	@Test
	public void PR29() {
		// Vamos a la pagina
		driver.navigate().to(URL + "/sale/list/");
		SeleniumUtils.esperarSegundos(driver, 2);
		// Comprobamos que entramos en la pÃ¡gina de login
		PO_LoginView.checkElement(driver, "id", "login");
	}

	/**
	 * PR30. Estando autenticado como usuario estÃ¡ndar intentar acceder a la
	 * opciÃ³n de listado de usuarios del administrador. Se deberÃ¡ indicar un
	 * mensaje de acciÃ³n prohibida.
	 */

	@Test
	public void PR30() {
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "miguel@email.com", "password");
		// Al ser un usuario estÃ¡ndar, no tiene acceso a gestiÃ³n de usuarios
		SeleniumUtils.textoNoPresentePagina(driver, "GestiÃ³n de usuarios");
		driver.navigate().to(URL + "/user/list/");
		// No podemos acceder con este ROLE, por tanto Forbidden
		SeleniumUtils.textoPresentePagina(driver, "Forbidden");
	}

	/**
	 * Sobre una bÃºsqueda determinada de ofertas (a elecciÃ³n de desarrollador),
	 * enviar un mensaje a una oferta concreta. Se abrirÃ­a dicha conversaciÃ³n
	 * por primera vez. Comprobar que el mensaje aparece en el listado de
	 * mensajes.
	 */
	@Test
	public void PR31() {
		PO_LoginView.fillForm(driver, "miguel@email.com", "password");
		PO_SearchView.goToPage(driver);
		// Realizo una bÃºsqueda de ofertas
		PO_SearchView.searchForSale(driver, "Consola");
		// Inicio una nueva conversaciÃ³n con la primera oferta
		PO_SearchView.sendMessageToSale(driver, "Consola");
		// EnvÃ­o un mensaje en la conversaciÃ³n
		PO_Messages.sendMessage(driver, "Hola");
		List<WebElement> elementos = PO_Messages.checkElement(driver, "class",
				"sent");
		// Compruebo que tan solo hay un mensaje enviado en la conversaciÃ³n
		assertTrue(elementos.size() == 1);

	}

	/**
	 * Sobre el listado de conversaciones enviar un mensaje a una conversaciÃ³n
	 * ya abierta. Comprobar que el mensaje aparece en la lista de mensajes.
	 */
	@Test
	public void PR32() {
		PO_LoginView.fillForm(driver, "miguel@email.com", "password");
		PO_Messages.goToPage(driver);
		// Vamos al chat con id 17
		PO_Messages.goToChat(driver, "17");
		List<WebElement> elementos = PO_Messages.checkElement(driver, "class",
				"sent");
		// Compruebo que ya habÃ­a dos mensajes enviados en la conversaciÃ³n
		assertTrue(elementos.size() == 2);
		PO_Messages.sendMessage(driver, "Hola");
		// Compruebo que ahora hay un mensaje mÃ¡s enviado en la conversaciÃ³n
		elementos = PO_Messages.checkElement(driver, "class", "sent");
		assertTrue(elementos.size() == 3);
	}

	/**
	 * Mostrar el listado de conversaciones ya abiertas. Comprobar que el
	 * listado contiene las conversaciones que deben ser.
	 */
	@Test
	public void PR33() {
		PO_LoginView.fillForm(driver, "miguel@email.com", "password");
		PO_Messages.goToPage(driver);
		List<WebElement> elementos = PO_Messages.checkElement(driver, "class",
				"chatCard");
		// Compruebo que tiene un chat por cada oferta de los otros usuarios (5)
		// y otro chat por su propia oferta
		assertTrue(elementos.size() == 6);
	}

	/**
	 * Sobre el listado de conversaciones ya abiertas. Pinchar el enlace
	 * Eliminar de la primera y comprobar que el listado se actualiza
	 * correctamente.
	 */
	@Test
	public void PR34() {
		PO_LoginView.fillForm(driver, "miguel@email.com", "password");
		PO_Messages.goToPage(driver);
		List<WebElement> elementos = PO_Messages.checkElement(driver, "class",
				"chatCard");
		// Compruebo que tiene un chat por cada oferta de los otros usuarios (5)
		// y otro chat por su propia oferta
		assertTrue(elementos.size() == 6);
		// Elimino el primer mensaje
		PO_Messages.deleteFirstMessage(driver);
		// Compruebo que hay una conversaciÃ³n menos
		elementos = PO_Messages.checkElement(driver, "class", "chatCard");
		assertTrue(elementos.size() == 5);
	}

	/**
	 * Sobre el listado de conversaciones ya abiertas. Pinchar el enlace
	 * Eliminar de la Ãºltima y comprobar que el listado se actualiza
	 * correctamente.
	 */
	@Test
	public void PR35() {
		PO_LoginView.fillForm(driver, "miguel@email.com", "password");
		PO_Messages.goToPage(driver);
		List<WebElement> elementos = PO_Messages.checkElement(driver, "class",
				"chatCard");
		// Compruebo que tiene un chat por cada oferta de los otros usuarios (5)
		// y otro chat por su propia oferta
		assertTrue(elementos.size() == 6);
		// Elimino el primer mensaje
		PO_Messages.deleteLastMessage(driver);
		// Compruebo que hay una conversaciÃ³n menos
		elementos = PO_Messages.checkElement(driver, "class", "chatCard");
		assertTrue(elementos.size() == 5);
	}

}
