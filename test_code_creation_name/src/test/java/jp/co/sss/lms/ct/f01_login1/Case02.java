package jp.co.sss.lms.ct.f01_login1;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

/**
 * 結合テスト ログイン機能①
 * ケース02
 * @author holy
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース02 受講生 ログイン 認証失敗")
public class Case02 {

	@LocalServerPort
	private int port;
	private static final String SCREENSHOT_DIR = "./evidence/";

	/** 前処理 
	 * @throws IOException */
	@BeforeAll
	static void before() throws IOException {
		createDriver();

		webDriver.manage().window().maximize();
		Path path = Paths.get(SCREENSHOT_DIR);

		if (!Files.exists(path)) {
			Files.createDirectories(path);
		}
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	private void takeScreenshot(String fileNameBase) {

		TakesScreenshot takeScr = (TakesScreenshot) webDriver;

		File screenFile = takeScr.getScreenshotAs(OutputType.FILE);

		String timestampString = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

		String fileName = fileNameBase + "_" + timestampString + ".png";

		Path PathPlace = Paths.get(SCREENSHOT_DIR + fileName);

		//
		try {
			Files.copy(screenFile.toPath(), PathPlace);
			System.out.println("スクリーンショットを保存しました：" + PathPlace);
		} catch (IOException e) {
			e.printStackTrace();
			fail("スクリーンショットの保存に失敗しました：" + e.getMessage());
		}
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		webDriver.get("http://localhost:" + port + "/lms/");

		assertEquals("ログイン | LMS", webDriver.getTitle());
		takeScreenshot("case2_test1_login_page");
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 DBに登録されていないユーザーでログイン")
	void test02() {
		webDriver.get("http://localhost:" + port + "/lms/");

		webDriver.findElement(By.id("loginId")).sendKeys("wrongId");
		webDriver.findElement(By.id("password")).sendKeys("wrongPassword123");

		webDriver.findElement(By.className("btn-primary")).click();

		WebElement errorMsg = webDriver.findElement(By.className("help-inline"));
		assertTrue(errorMsg.isDisplayed(), "エラーメッセージが表示されること");

		takeScreenshot("case2_test2_login_error_page");

	}

}
