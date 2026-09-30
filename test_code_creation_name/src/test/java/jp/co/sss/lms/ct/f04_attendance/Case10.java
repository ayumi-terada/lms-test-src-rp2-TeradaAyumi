package jp.co.sss.lms.ct.f04_attendance;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebElement;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 結合テスト 勤怠管理機能
 * ケース10
 * @author holy
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース10 受講生 勤怠登録 正常系")
public class Case10 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		//ログイン画面のURLに遷移する
		goTo("http://localhost:8080/lms/");

		//タイトルとURLが正しいか検証する
		assertEquals("ログイン | LMS", webDriver.getTitle());
		assertEquals("http://localhost:8080/lms/", webDriver.getCurrentUrl());

		//test1のエビデンスを取得する
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		//ユーザー情報を入力
		webDriver.findElement(By.id("loginId")).sendKeys("StudentAA01");
		webDriver.findElement(By.id("password")).sendKeys("Password12345");

		//ログインボタンを押す
		webDriver.findElement(By.className("btn-primary")).click();

		//画面が表示されるまで5秒待機
		visibilityTimeout(By.tagName("h2"), 5);

		//タイトルとURLが正しいか検証する
		assertEquals("コース詳細 | LMS", webDriver.getTitle());
		assertEquals("http://localhost:8080/lms/course/detail", webDriver.getCurrentUrl());

		//test2のエビデンスを取得する
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「勤怠」リンクから勤怠管理画面に遷移")
	void test03() {
		//勤怠ボタンを押す
		webDriver.findElement(By.partialLinkText("勤怠")).click();

		//アラートがある場合許可する処理
		try {
			Alert alert = webDriver.switchTo().alert();
			alert.accept();
		} catch (NoAlertPresentException e) {
		}

		//画面が表示されるまで5秒待機
		visibilityTimeout(By.tagName("h2"), 5);

		//タイトルとURLが正しいか検証する
		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());
		assertTrue(webDriver.getCurrentUrl().contains("http://localhost:8080/lms/attendance/detail"));

		//test3のエビデンスを取得する
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「出勤」ボタンを押下し出勤時間を登録")
	void test04() {
		//出勤ボタンを押す
		webDriver.findElement(By.cssSelector("input[value='出勤']")).click();

		//アラートがある場合許可する処理
		try {
			Alert alert = webDriver.switchTo().alert();
			alert.accept();
		} catch (NoAlertPresentException e) {
		}

		//画面が表示されるまで5秒待機
		visibilityTimeout(By.tagName("h2"), 5);

		//タイトルとURLが正しいか検証する
		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());
		assertTrue(webDriver.getCurrentUrl().contains("http://localhost:8080/lms/attendance/detail"));

		//当日の出勤情報が未入力ではないか検証
		WebElement startTimeCheck = webDriver.findElement(By.cssSelector("tbody tr.info td:nth-child(3)"));
		String startTime = startTimeCheck.getText();
		assertTrue(!startTime.isEmpty());

		//test4のエビデンスを取得する
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 「退勤」ボタンを押下し退勤時間を登録")
	void test05() {

		//退勤ボタンを押す
		webDriver.findElement(By.cssSelector("input[value='退勤']")).click();

		//アラートがある場合許可する処理
		try {
			Alert alert = webDriver.switchTo().alert();
			alert.accept();
		} catch (NoAlertPresentException e) {
		}

		//画面が表示されるまで5秒待機
		visibilityTimeout(By.tagName("h2"), 5);

		//タイトルとURLが正しいか検証する
		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());
		assertTrue(webDriver.getCurrentUrl().contains("http://localhost:8080/lms/attendance/detail"));

		//当日の退勤情報が未入力ではないか検証
		WebElement endTimeCheck = webDriver.findElement(By.cssSelector("tbody tr.info td:nth-child(4)"));
		String endTime = endTimeCheck.getText();
		assertTrue(!endTime.isEmpty());

		//test5のエビデンスを取得する
		getEvidence(new Object() {
		});

	}

}
