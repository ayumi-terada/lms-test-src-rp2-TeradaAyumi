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
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

/**
 * 結合テスト ログイン機能①
 * ケース01
 * @author holy
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース01 ログイン画面への遷移")
public class Case01 {

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

		takeScreenshot("case1_test1_login_page");
	}

}
