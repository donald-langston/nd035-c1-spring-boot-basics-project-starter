package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;


	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			this.driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		
		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
		//driver.navigate().to("http://localhost:" + this.port + "/login");
		//driver.get("http://localhost:" + this.port + "/login");
		//webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success-msg")));
		//Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
	}

	
	
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	private void addNote() {
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");

		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("http://localhost:" + this.port + "/home", driver.getCurrentUrl());

		WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
		notesTab.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("notes-button")));
		WebElement notesButton = driver.findElement(By.id("notes-button"));
		notesButton.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		WebElement noteTitle = driver.findElement(By.id("note-title"));
		noteTitle.click();
		noteTitle.sendKeys("test note");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		WebElement noteDescription = driver.findElement(By.id("note-description"));
		noteDescription.click();
		noteDescription.sendKeys("test description");

		WebElement notesSubmitButton = driver.findElement(By.xpath("//button[text()='Save changes']"));
		notesSubmitButton.click();
	}

	private void addCredential() {
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");

		WebDriverWait webDriverWait = new WebDriverWait(driver, 5);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement credentialsTab = driver.findElement(By.id("nav-credentials-tab"));
		credentialsTab.click();


		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-button")));
		WebElement credentialButton = driver.findElement(By.id("credential-button"));
		credentialButton.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		WebElement credentialUrl = driver.findElement(By.id("credential-url"));
		credentialUrl.click();
		credentialUrl.sendKeys("google.com");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
		WebElement credentialUsername = driver.findElement(By.id("credential-username"));
		credentialUsername.click();
		credentialUsername.sendKeys("testuser");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
		WebElement credentialPassword = driver.findElement(By.id("credential-password"));
		credentialPassword.click();
		credentialPassword.sendKeys("password");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-submit-button")));
		WebElement credentialSubmitButton = driver.findElement(By.xpath("//button[@id='credential-submit-button']"));
		credentialSubmitButton.click();

	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric: 
	 * https://review.udacity.com/#!/rubrics/2724/view 
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");
		
		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at: 
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");

		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * 
	 * Read more about file size limits here: 
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}

	// test unathorized users can only access login and signup pages
	@Test
	public void testUnauthorizedAccess() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());

		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("http://localhost:" + this.port + "/signup", driver.getCurrentUrl());

		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	// test home page is only accessible after login
	@Test
	public void testAuthorizedAccess() {
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("http://localhost:" + this.port + "/home", driver.getCurrentUrl());

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout-button")));
		WebElement logoutButton = driver.findElement(By.id("logout-button"));
		logoutButton.click();

		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	@Test
	public void testAddNote() {
		addNote();
		Assertions.assertTrue(driver.getPageSource().contains("test note"));
		Assertions.assertTrue(driver.getPageSource().contains("test description"));

	}

	@Test
	public void testEditNote() {
		addNote();

		Assertions.assertTrue(driver.getPageSource().contains("test note"));
		Assertions.assertTrue(driver.getPageSource().contains("test description"));

		//webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
		notesTab.click();

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("edit-button-1")));
		WebElement editButton = driver.findElement(By.id("edit-button-1"));
		editButton.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		WebElement noteDescription = driver.findElement(By.id("note-description"));
		noteDescription.click();
		noteDescription.sendKeys("!");

		WebElement notesSubmitButton = driver.findElement(By.xpath("//button[text()='Save changes']"));
		notesSubmitButton.click();

		Assertions.assertTrue(driver.getPageSource().contains("test note"));
		Assertions.assertTrue(driver.getPageSource().contains("test description!"));
	}

	@Test
	public void testDeleteNote() {
		addNote();

		Assertions.assertTrue(driver.getPageSource().contains("test note"));
		Assertions.assertTrue(driver.getPageSource().contains("test description"));

		//webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
		notesTab.click();

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("delete-button-1")));
		WebElement deleteButton = driver.findElement(By.id("delete-button-1"));
		deleteButton.click();

		Assertions.assertFalse(driver.getPageSource().contains("delete-button-1"));
	}

	@Test
	public void testCreateCredentials() {
		addCredential();

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement credentialsTab = driver.findElement(By.id("nav-credentials-tab"));
		credentialsTab.click();


		WebElement credentialPassword = driver.findElement(By.xpath("//*[@id=\"credentialTable\"]/tbody/tr/td[3]"));
		String encryptedPassword = credentialPassword.getAttribute("innerHTML");
		System.out.println("Encrypted password is: " + encryptedPassword);
	}

	@Test
	public void editCredentials() {
		addCredential();

		WebElement credentialsTab = driver.findElement(By.id("nav-credentials-tab"));
		credentialsTab.click();

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-edit-2")));
		WebElement editButton = driver.findElement(By.id("credential-edit-2"));
		editButton.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
		WebElement credentialPassword = driver.findElement(By.id("credential-password"));
		String plainTextPassword = credentialPassword.getAttribute("value");
		System.out.println("Plaintext password is: " + plainTextPassword);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
		WebElement credentialUsername = driver.findElement(By.id("credential-username"));
		credentialUsername.click();
		credentialUsername.sendKeys("1");
		WebElement credentialsSubmitButton = driver.findElement(By.xpath("//button[@id='credential-submit-button']"));
		credentialsSubmitButton.click();

		Assertions.assertTrue(driver.getPageSource().contains("testuser1"));

	}

	@Test
	public void testDeleteCredential() {
		addCredential();

		WebElement credentialsTab = driver.findElement(By.id("nav-credentials-tab"));
		credentialsTab.click();

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-delete-1")));
		WebElement deleteButton = driver.findElement(By.id("credential-delete-1"));
		deleteButton.click();

		Assertions.assertFalse(driver.getPageSource().contains("credential-delete-1"));
	}
}
