package jp.co.sss.lms.ct.f03_report;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

/**
 * 結合テスト レポート機能
 * ケース07
 * @author holy
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース07 受講生 レポート新規登録(日報) 正常系")
public class Case07 {

	@LocalServerPort
	private int port;

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

		//ログインボタンを押すr
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
	@DisplayName("テスト03 未提出の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		//テーブルを取得してリストに入れる
		List<WebElement> courseTable = webDriver.findElements(By.cssSelector("table.sctionList tr"));

		//1行ずつ確認
		for (WebElement row : courseTable) {

			//もし未提出の文字がある場合
			if (row.getText().contains("未提出")) {

				//詳細ボタンを押す
				WebElement detailButton = row.findElement(By.cssSelector("input[value='詳細']"));
				detailButton.click();

				//処理を終了
				break;
			}
		}
		//画面が表示されるまで5秒待機
		visibilityTimeout(By.tagName("h2"), 5);

		////タイトルとURLが正しいか検証する
		assertEquals("セクション詳細 | LMS", webDriver.getTitle());
		assertEquals("http://localhost:8080/lms/section/detail", webDriver.getCurrentUrl());

		//test3のエビデンスを取得する
		getEvidence(new Object() {
		});

	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「提出する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		//提出ボタンを押す
		WebElement submit = webDriver.findElement(By.cssSelector("input[value$='提出する']"));
		submit.click();

		//画面が表示されるまで5秒待機
		visibilityTimeout(By.tagName("h2"), 5);

		//タイトルとURLが正しいか検証する
		assertEquals("レポート登録 | LMS", webDriver.getTitle());
		assertEquals("http://localhost:8080/lms/report/regist", webDriver.getCurrentUrl());

		//test4のエビデンスを取得する
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を入力して「提出する」ボタンを押下し確認ボタン名が更新される")
	void test05() {
		//テキストボックスに入力する
		WebElement dailyReport = webDriver.findElement(By.cssSelector("div textarea"));
		dailyReport.sendKeys("日報を提出");

		//提出ボタンを押す
		WebElement dailyReportSubmit = webDriver.findElement(By.cssSelector("button[type='submit']"));
		dailyReportSubmit.click();

		//画面が表示されるまで5秒待機
		visibilityTimeout(By.tagName("h2"), 5);

		//タイトルとURLが正しいか検証する
		assertEquals("セクション詳細 | LMS", webDriver.getTitle());
		assertTrue(webDriver.getCurrentUrl().contains("http://localhost:8080/lms/section/detail"));

		//提出済みボタン・htmlで表示されている文字列を取得する
		WebElement submited = webDriver.findElement(By.cssSelector("input[value^='提出済み']"));
		String buttonTextString = submited.getAttribute("value");
		//ボタンが変化しているか検証する
		assertTrue(buttonTextString.contains("提出済み"));

		//test5のエビデンスを取得する
		getEvidence(new Object() {
		});

	}

}
