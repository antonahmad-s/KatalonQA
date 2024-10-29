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
import com.kms.katalon.core.annotation.Keyword

//def urlData = findTestData('Data Files/Link URL')
//
//String url = urlData.getValue('Link', 1)
//
//// Cek apakah URL ditemukan atau kosong
//if ((url == null) || url.isEmpty()) {
//    WebUI.comment('URL tidak ditemukan atau kosong dalam file Excel.')
//
//    return // Hentikan eksekusi jika tidak ada URL
//}
//
//// Buka browser dan akses URL
//WebUI.openBrowser('')
//
//WebUI.maximizeWindow()
//
//WebUI.navigateToUrl(url)

def mainURL = new OpenBrowserURL()

mainURL.openBrowserFromExcel('Data Files/Link URL')

// Klik tombol contact us
WebUI.click(findTestObject('Object Repository/Contact Us Page/h1_CONTACT US'))

WebUI.delay(1)

WebUI.switchToWindowTitle('WebDriver | Contact Us')

String zoomOutScript = "document.body.style.zoom='80%';"
WebUI.executeJavaScript(zoomOutScript, null)

// Ambil data dari file Excel Login
def ContactUsData = findTestData('Data Files/Data ContactUs')

for (int i = 1; i <= ContactUsData.getRowNumbers(); i++) {
	String first_name = ContactUsData.getValue('First Name', i)

	String last_name = ContactUsData.getValue('Last Name', i)
	
	String email = ContactUsData.getValue('Email', i)
	
	String comment = ContactUsData.getValue('Comment', i)

	String expectedLink = ContactUsData.getValue('Status', i)

	if (first_name != null) {
		WebUI.setText(findTestObject('Object Repository/Contact Us Page/input_first_name'), first_name)
	}
	
	if (last_name != null) {
		WebUI.setText(findTestObject('Object Repository/Contact Us Page/input_last_name'), last_name)
	}
	
	if (email != null) {
		WebUI.setText(findTestObject('Object Repository/Contact Us Page/input_email'), email)
	}
	
	if (comment != null) {
		WebUI.setText(findTestObject('Object Repository/Contact Us Page/input_message'), comment)
	}
	
	WebUI.click(findTestObject('Object Repository/Contact Us Page/button_submit'))
	
	WebUI.delay(2)
	
	// Dapatkan URL halaman yang sedang terbuka setelah submit
	String currentUrl = WebUI.getUrl()
	
	// Bandingkan URL saat ini dengan link yang diharapkan
	if (currentUrl.equals(expectedLink)) {
		WebUI.comment("Test Passed: Halaman yang dibuka sesuai dengan ekspektasi (${expectedLink}).")
	} else {
		WebUI.comment("Test Failed: Halaman yang dibuka (${currentUrl}) tidak sesuai dengan ekspektasi (${expectedLink}).")
	}

	WebUI.delay(1)
	
	WebUI.back()
	
	WebUI.delay(1)
	
	WebUI.waitForElementVisible(findTestObject('Object Repository/Contact Us Page/button_reset'), 5)
	
	WebUI.click(findTestObject('Object Repository/Contact Us Page/button_reset'))
	
//	 Menggunakan JavaScript untuk mereset form
//		String resetScript = "setTimeout(function() { document.querySelector('input[value=\"RESET\"]').click();}, 5000);"
//		WebUI.executeJavaScript(resetScript, null)
//	
//		String script = "setTimeout(function() { document.querySelector('input[value=\"RESET\"]').style.backgroundColor = ''; }, 5000);"
//		WebUI.executeJavaScript(script, null)
	
}

WebUI.closeBrowser()

