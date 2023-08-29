package genericLibraries;
//this is used create all the generic actions
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import pompages.ContactsPage;
import pompages.CreateNewContactPage;
import pompages.CreateNewEventPage;
import pompages.CreateNewLeadPage;
import pompages.CreateNewOrganizationPage;
import pompages.DuplicatingLeadPage;
import pompages.HomePage;
import pompages.LeadsPage;
import pompages.LoginPage;
import pompages.NewContactInfoPage;
import pompages.NewEventInfoPage;
import pompages.NewLeadInfoPage;
import pompages.NewOrgInfoPage;
import pompages.OrganizationsPage;

public class BaseClass {
	protected PropertiesUtility property;
	protected ExcelUtility excel;
	protected JavaUtility jutil;
	protected WebDriverUtility web;
	protected WebDriver driver;
	
	protected LoginPage login;
	protected HomePage home;
	protected OrganizationsPage org;
	protected ContactsPage contacts;
	protected LeadsPage lead;
	protected CreateNewOrganizationPage createOrg;
	protected CreateNewContactPage createContact;
	protected CreateNewEventPage createEvent;
	protected CreateNewLeadPage createLead;
	protected NewOrgInfoPage newOrgInfo;
	protected NewContactInfoPage newContactInfo;
	protected NewLeadInfoPage newLeadInfo;
	protected NewEventInfoPage newEventInfo;
	protected DuplicatingLeadPage duplicatingLead;
	
	public static WebDriver sdriver;
	public static JavaUtility sjutil;
	
	//@BeforeSuite
	//@BeforeTest
	
	@BeforeClass
	public void classSetup() {
		property = new PropertiesUtility();
		excel = new ExcelUtility();
		jutil = new JavaUtility();
		web = new WebDriverUtility();
		
		property.propertiesInitialization(IConstantPath.PROPERTIES_PATH);
		driver = web.launchBrowser(property.readFromProperties("browser"));
		web.maximizeBrowser();
		web.waitTillElementFound(Long.parseLong(property.readFromProperties("time")));
		
		sdriver = driver;
		sjutil = jutil;
	}
	
	@BeforeMethod
	public void methodSetup() {
		excel.excelInitialization(IConstantPath.EXCEL_PATH);
		
		login = new LoginPage(driver);
		home = new HomePage(driver);
		org = new OrganizationsPage(driver);
		contacts = new ContactsPage(driver);
		lead = new LeadsPage(driver);
		createOrg = new CreateNewOrganizationPage(driver);
		createContact = new CreateNewContactPage(driver);
		createEvent = new CreateNewEventPage(driver);
		createLead = new CreateNewLeadPage(driver);
		newOrgInfo = new NewOrgInfoPage(driver);
		newContactInfo = new NewContactInfoPage(driver);
		newLeadInfo = new NewLeadInfoPage(driver);
		newEventInfo = new NewEventInfoPage(driver);
		duplicatingLead = new DuplicatingLeadPage(driver);
		
		web.navigateToApp(property.readFromProperties("url"));
		Assert.assertTrue(driver.getTitle().contains("vtiger"));
		login.loginToApp(property.readFromProperties("username"), 
				property.readFromProperties("password"));
		Assert.assertTrue(driver.getTitle().contains("Home"));
	}
	
	@AfterMethod
	public void methodTearDown() {
		home.signOutOfVtiger(web);
		excel.closeExcel();
	}
	
	@AfterClass
	public void classTearDown() {
		web.quitAllWindows();
	}
	//@AfterTest
	//@AfterSuite
}
