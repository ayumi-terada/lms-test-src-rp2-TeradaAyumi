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
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

/**
 * 結合テスト レポート機能
 * ケース09
 * @author holy
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース09 受講生 レポート登録 入力チェック")
public class Case09 {

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
	@DisplayName("テスト03 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test03() {
		//ユーザー詳細ボタンを押す
		WebElement userDate = webDriver.findElement(By.partialLinkText("ようこそ"));
		userDate.click();

		//タイトルとURLが正しいか検証する
		assertEquals("ユーザー詳細", webDriver.getTitle());
		assertTrue(webDriver.getCurrentUrl().contains("http://localhost:8080/lms/user/detail"));

		//test6のエビデンスを取得する
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 該当レポートの「修正する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		//テーブルを取得してリストに入れる
		List<WebElement> sectionRows = webDriver.findElements(By.cssSelector("table.table-hover tr"));

		//編集のために画面を下へ移動
		scrollBy("400");

		//1行ずつ確認
		for (WebElement row : sectionRows) {
			String rowText = row.getText();

			//"週報"と"2022年10月2日"を含む列がある場合
			if (row.getText().contains("週報") && row.getText().contains("2022年10月2日")) {

				//詳細ボタンを押す
				WebElement detailButton = row.findElement(By.cssSelector("input[value='修正する']"));
				detailButton.click();

				//処理を終了
				break;
			}
		}
		//画面が表示されるまで5秒待機
		visibilityTimeout(By.tagName("h2"), 5);

		//タイトルとURLが正しいか検証する
		assertEquals("レポート登録 | LMS", webDriver.getTitle());
		assertEquals("http://localhost:8080/lms/report/regist", webDriver.getCurrentUrl());

		//test7のエビデンスを取得する
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しエラー表示：学習項目が未入力")
	void test05() {
		//学習項目を未入力状態にする
		WebElement learningBox = webDriver.findElement(By.id("intFieldName_0"));
		learningBox.clear();

		//編集のために画面を下へ移動
		scrollBy("400");

		//提出ボタンを押す
		WebElement weekReportSubmit = webDriver.findElement(By.cssSelector("button[type='submit']"));
		weekReportSubmit.click();

		//画面が表示されるまで5秒待機
		visibilityTimeout(By.tagName("h2"), 5);

		//エラーの表示確認
		WebElement error = webDriver.findElement(By.cssSelector(".form-control.errorInput"));
		assertTrue(error.isDisplayed());

		//test5のエビデンスを取得する
		getEvidence(new Object() {
		});

	}

	@Test
	@Order(6)
	@DisplayName("テスト06 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：理解度が未入力")
	void test06() {
		//学習項目を未入力状態にする
		WebElement learningBox = webDriver.findElement(By.id("intFieldName_0"));
		learningBox.sendKeys("ITリテラシー①");
		//学習項目を未入力状態にする

		new Select(webDriver.findElement(By.id("intFieldValue_0"))).selectByIndex(0);

		//編集のために画面を下へ移動
		scrollBy("400");

		//提出ボタンを押す
		WebElement weekReportSubmit = webDriver.findElement(By.cssSelector("button[type='submit']"));
		weekReportSubmit.click();

		//画面が表示されるまで5秒待機
		visibilityTimeout(By.tagName("h2"), 5);

		//エラーの表示確認
		WebElement error = webDriver.findElement(By.cssSelector(".form-control.errorInput"));
		assertTrue(error.isDisplayed());

		//test2のエビデンスを取得する
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が数値以外")
	void test07() {
		// TODO ここに追加
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が範囲外")
	void test08() {
		// TODO ここに追加
	}

	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度・所感が未入力")
	void test09() {
		// TODO ここに追加
	}

	@Test
	@Order(10)
	@DisplayName("テスト10 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：所感・一週間の振り返りが2000文字超")
	void test10() {
		// TODO ここに追加
	}

}
