import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import packageURL.OpenBrowserURL

def mainURL = new OpenBrowserURL()

mainURL.openBrowserFromExcel('Data Files/Link URL')

// Klik tombol login
WebUI.click(findTestObject('Object Repository/LoginPage/h1_LOGIN PORTAL'))

WebUI.delay(1)

// Beralih ke tab baru (tab kedua)
WebUI.switchToWindowTitle('WebDriver | Login Portal')

// Ambil data dari file Excel Login
def loginData = findTestData('Data Files/Data Login')

// Loop untuk setiap baris data login
for (int i = 1; i <= loginData.getRowNumbers(); i++) {
    String username = loginData.getValue('Username', i)

    String password = loginData.getValue('Password', i)

    String expectedStatus = loginData.getValue('Status', i)
	
	if (username != null) {
		WebUI.setText(findTestObject('Object Repository/LoginPage/input_username'), username)
	}
	
	if (password != null) {
		WebUI.setText(findTestObject('Object Repository/LoginPage/input_password'), password)
	}

    // Tunggu hingga tombol login terlihat dan bisa diklik
    WebUI.waitForElementClickable(findTestObject('Object Repository/LoginPage/button_Login'), 10)

    WebUI.click(findTestObject('Object Repository/LoginPage/button_Login'))

    WebUI.delay(2)

    WebUI.waitForAlert(2)

    String actualStatus = WebUI.getAlertText()

    WebUI.comment('Alert message: ' + actualStatus)

    // Bandingkan dengan status yang diharapkan
    if (actualStatus.equals(expectedStatus)) {
        WebUI.comment("Test passed for user: $username, expected: $expectedStatus, actual: $actualStatus")
    } else {
        WebUI.comment("Test failed for user: $username, expected: $expectedStatus, actual: $actualStatus")
    }
    
    WebUI.acceptAlert()

//    WebUI.navigateToUrl(url)
	
//	WebUI.click(findTestObject('Object Repository/LoginPage/h1_LOGIN PORTAL'))
}

WebUI.closeBrowser()

