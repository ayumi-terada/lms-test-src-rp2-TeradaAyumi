package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

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
 * 結合テスト よくある質問機能
 * ケース06
 * @author holy
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース06 カテゴリ検索 正常系")
public class Case06 {

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
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		//ヘッダーの「機能」ボタンを押す
		WebElement headerMenu = webDriver.findElement(By.className("caret"));
		headerMenu.click();

		//「ヘルプ」ボタンを押す
		WebElement helpButton = webDriver.findElement(By.cssSelector("a[href*='/help']"));
		helpButton.click();

		//画面が表示されるまで5秒待機
		visibilityTimeout(By.tagName("h2"), 5);

		//タイトルとURLが正しいか検証する		
		assertEquals("ヘルプ | LMS", webDriver.getTitle());
		assertEquals("http://localhost:8080/lms/help", webDriver.getCurrentUrl());

		//test3のエビデンスを取得する
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		//「よくある質問」リンクを押す
		WebElement faq = webDriver.findElement(By.linkText("よくある質問"));
		faq.click();

		//新しいタブを開く
		for (String windowHandleString : webDriver.getWindowHandles()) {
			webDriver.switchTo().window(windowHandleString);
		}

		//画面が表示されるまで5秒待機
		visibilityTimeout(By.tagName("h2"), 5);

		//タイトルとURLが正しいか検証する
		assertEquals("よくある質問 | LMS", webDriver.getTitle());
		assertEquals("http://localhost:8080/lms/faq", webDriver.getCurrentUrl());

		//test4のエビデンスを取得する
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 カテゴリ検索で該当カテゴリの検索結果だけ表示")
	void test05() {
		//検索欄にキーワード入力
		WebElement searchBox = webDriver.findElement(By.name("keyword"));
		searchBox.sendKeys("研修");

		//検索ボタンを押す
		WebElement searchButton = webDriver.findElement(By.cssSelector("input[value='検索']"));
		searchButton.click();

		//URL内にキーワードが含まれているか検証
		assertTrue(webDriver.getCurrentUrl().contains("keyword="));

		//スクリーンショットに結果を残すため
		scrollBy("250");

		//test5のエビデンスを取得する
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 検索結果の質問をクリックしその回答を表示")
	void test06() {
		//検索結果を押す
		WebElement toggleAnswer = webDriver.findElement(By.cssSelector("[id^=question-h]"));
		toggleAnswer.click();

		//検索結果が表示されているか検証
		WebElement answer = webDriver.findElement(By.cssSelector("[id^=answer-h]"));
		assertTrue(answer.isDisplayed());

		//test6のエビデンスを取得
		getEvidence(new Object() {
		});
	}
}
