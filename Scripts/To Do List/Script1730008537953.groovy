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
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.testobject.ConditionType

def mainURL = new OpenBrowserURL()

mainURL.openBrowserFromExcel('Data Files/Link URL')

// Klik tombol contact us
WebUI.click(findTestObject('Object Repository/To Do List Page/h1_TO DO LIST'))

WebUI.delay(1)

WebUI.switchToWindowTitle('WebDriver | To Do List')

//String zoomOutScript = "document.body.style.zoom='80%';"
//WebUI.executeJavaScript(zoomOutScript, null)

//int itemCount = 3
//
//for (int i = 1; i <= itemCount; i++) {
//    // Buat TestObject baru dengan XPath dinamis
//    String xpath = "//div[@id='container']/ul/li[${i}]/span/i"
//    TestObject deleteButton = new TestObject("dynamicDeleteButton")
//    deleteButton.addProperty("xpath", ConditionType.EQUALS, xpath)
//
//    // Tambahkan log untuk memastikan XPath yang dihasilkan benar
//    WebUI.comment("Menggunakan XPath untuk tombol delete: ${xpath}")
//
//    // Cek apakah elemen dengan XPath tersebut ada sebelum mengkliknya
//    if (WebUI.waitForElementPresent(deleteButton, 10)) {
//        WebUI.click(deleteButton)
//        WebUI.delay(2) 
//    } else {
//        WebUI.comment("Elemen dengan XPath ${xpath} tidak ditemukan.")
//    }
//}

int itemCount = 3 // Set jumlah elemen yang ingin Anda hapus
int counter = 0

while (counter < itemCount) {
	String xpath = "//div[@id='container']/ul/li[1]/span/i" // Selalu mengacu ke elemen pertama
	TestObject deleteButton = new TestObject("dynamicDeleteButton")
	deleteButton.addProperty("xpath", ConditionType.EQUALS, xpath)

	// Tambahkan log untuk memastikan XPath yang dihasilkan benar
	WebUI.comment("Menggunakan XPath untuk tombol delete: ${xpath}")

	// Cek apakah elemen pertama ada sebelum mengkliknya
	if (WebUI.waitForElementPresent(deleteButton, 10)) {
		WebUI.click(deleteButton)
		WebUI.delay(1)
		counter++ // Tambah penghitung setiap elemen dihapus
	} else {
		WebUI.comment("Elemen dengan XPath ${xpath} tidak ditemukan.")
		break // Keluar dari loop jika elemen tidak ada
	}
}

//WebUI.click(findTestObject('Object Repository/To Do List Page/button_add'))

def listData = findTestData ('Data Files/Data ToDoList')

for (int i = 1; i <= listData.getRowNumbers(); i++) {
	String listToDo = listData.getValue ('List',i)
//	String notes = listData.getValue('Notes', i)
	
//	WebUI.click(findTestObject('Object Repository/To Do List Page/button_add'))
	
	WebUI.setText(findTestObject('Object Repository/To Do List Page/input_list'), listToDo)
	WebUI.sendKeys(findTestObject('Object Repository/To Do List Page/input_list'), Keys.chord(Keys.ENTER))
	
	WebUI.delay(1)
	
	}
	
for (int i = 1; i <= listData.getRowNumbers(); i++) {
	String notes = listData.getValue('Notes', i)
	
	if (notes.equalsIgnoreCase('completed')) {
		// Buat TestObject dengan XPath dinamis berdasarkan indeks
		String xpath = "//div[@id='container']/ul/li[${i}]"
		TestObject completedItem = new TestObject("completedItem")
		completedItem.addProperty("xpath", ConditionType.EQUALS, xpath)

		// Tunggu hingga elemen terlihat dan dapat diinteraksi
		if (WebUI.waitForElementVisible(completedItem, 10)) {
			WebUI.click(completedItem) // Klik item
			WebUI.delay(1)
		} else {
			WebUI.comment("Elemen dengan XPath '${xpath}' tidak ditemukan atau tidak dapat diinteraksi.")
		}
	}
}

WebUI.delay(2)
WebUI.closeBrowser()

