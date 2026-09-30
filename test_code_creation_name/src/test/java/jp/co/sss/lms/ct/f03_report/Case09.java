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

/**
 * 結合テスト レポート機能
 * ケース09
 * @author holy
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース09 受講生 レポート登録 入力チェック")
public class Case09 {

	final String text = "あ".repeat(2001);

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
		webDriver.findElement(By.partialLinkText("ようこそ")).click();

		//タイトルとURLが正しいか検証する
		assertEquals("ユーザー詳細", webDriver.getTitle());
		assertTrue(webDriver.getCurrentUrl().contains("http://localhost:8080/lms/user/detail"));

		//test3のエビデンスを取得する
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 該当レポートの「修正する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		//テーブルを取得してリストに入れる
		List<WebElement> sectionRows = webDriver.findElements(By.cssSelector("table.table-hover tr"));

		//編集のために画面をスクロール
		scrollBy("400");

		//1行ずつ条件を確認する
		for (WebElement row : sectionRows) {
			String rowText = row.getText();

			//"週報"と"2022年10月2日"を含む列がある場合
			if (row.getText().contains("週報") && row.getText().contains("2022年10月2日")) {

				//修正するボタンを押す
				row.findElement(By.cssSelector("input[value='修正する']")).click();

				//処理を終了
				break;
			}
		}
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
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しエラー表示：学習項目が未入力")
	void test05() {
		//学習項目を未入力状態にする
		WebElement learningBox = webDriver.findElement(By.id("intFieldName_0"));
		learningBox.clear();

		//編集のために画面をスクロール
		scrollBy("300");

		//提出ボタンを押す
		webDriver.findElement(By.cssSelector("button[type='submit']")).click();

		//画面が表示されるまで5秒待機
		visibilityTimeout(By.tagName("h2"), 5);

		//エラーの表示を確認する
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
		//学習項目を初期値に戻す
		WebElement learningBox = webDriver.findElement(By.id("intFieldName_0"));
		learningBox.sendKeys("ITリテラシー①");

		//学習理解度を未入力状態にする
		new Select(webDriver.findElement(By.id("intFieldValue_0"))).selectByIndex(0);

		//編集のために画面をスクロール
		scrollBy("300");

		//提出ボタンを押す
		webDriver.findElement(By.cssSelector("button[type='submit']")).click();

		//画面が表示されるまで5秒待機
		visibilityTimeout(By.tagName("h2"), 5);

		//エラーの表示を確認する
		WebElement error = webDriver.findElement(By.cssSelector(".form-control.errorInput"));
		assertTrue(error.isDisplayed());

		//test6のエビデンスを取得する
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が数値以外")
	void test07() {
		//学習項目を初期値に戻す
		new Select(webDriver.findElement(By.id("intFieldValue_0"))).selectByIndex(2);

		//編集のために画面をスクロール
		scrollBy("300");

		//目標の達成度に半角数字以外の値を入力する
		WebElement goalAchievement = webDriver.findElement(By.id("content_0"));
		goalAchievement.clear();
		goalAchievement.sendKeys("目標達成！");

		//提出ボタンを押す
		webDriver.findElement(By.cssSelector("button[type='submit']")).click();

		//画面が表示されるまで5秒待機
		visibilityTimeout(By.tagName("h2"), 5);

		//エラーの表示を確認する
		WebElement error = webDriver.findElement(By.cssSelector(".form-control.errorInput"));
		assertTrue(error.isDisplayed());

		//test7のエビデンスを取得する
		getEvidence(new Object() {
		});

	}

	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が範囲外")
	void test08() {
		//編集のために画面をスクロール
		scrollBy("300");

		//目標の達成度を未入力状態にする
		WebElement goalAchievement = webDriver.findElement(By.id("content_0"));
		goalAchievement.clear();
		goalAchievement.sendKeys("13");

		//提出ボタンを押す
		webDriver.findElement(By.cssSelector("button[type='submit']")).click();

		//画面が表示されるまで5秒待機
		visibilityTimeout(By.tagName("h2"), 5);

		//エラーの表示を確認する
		WebElement error = webDriver.findElement(By.cssSelector(".form-control.errorInput"));
		assertTrue(error.isDisplayed());

		//test8のエビデンスを取得する
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度・所感が未入力")
	void test09() {
		//編集のために画面をスクロール
		scrollBy("300");

		//目標達成度を未入力状態にする
		WebElement goalAchievement = webDriver.findElement(By.id("content_0"));
		goalAchievement.clear();

		//所感を未入力状態にする
		WebElement impressions = webDriver.findElement(By.id("content_1"));
		impressions.clear();

		//提出ボタンを押す
		webDriver.findElement(By.cssSelector("button[type='submit']")).click();

		//画面が表示されるまで5秒待機
		visibilityTimeout(By.tagName("h2"), 5);

		//スクリーンショットに結果を残すためスクロール
		scrollBy("300");

		//エラーの表示を確認する
		WebElement error = webDriver.findElement(By.cssSelector(".form-control.errorInput"));
		assertTrue(error.isDisplayed());

		//test9のエビデンスを取得する
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(10)
	@DisplayName("テスト10 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：所感・一週間の振り返りが2000文字超")
	void test10() {
		//編集のために画面をスクロール
		scrollBy("300");

		//目標達成度を初期値に戻す
		WebElement goalAchievement = webDriver.findElement(By.id("content_0"));
		goalAchievement.sendKeys("5");

		//所感に2001文字入力する
		WebElement impressions = webDriver.findElement(By.id("content_1"));
		impressions.sendKeys(text);

		//一週間の振り返りに2001文字に入力する
		WebElement weeklyReview = webDriver.findElement(By.id("content_2"));
		weeklyReview.clear();
		weeklyReview.sendKeys(text);

		//提出ボタンを押す
		webDriver.findElement(By.cssSelector("button[type='submit']")).click();

		//画面が表示されるまで5秒待機
		visibilityTimeout(By.tagName("h2"), 5);

		//スクリーンショットに結果を残すためスクロール
		scrollBy("300");

		//エラーの表示を確認する
		WebElement error = webDriver.findElement(By.cssSelector(".form-control.errorInput"));
		assertTrue(error.isDisplayed());

		//test10のエビデンスを取得する
		getEvidence(new Object() {
		});
	}
}
