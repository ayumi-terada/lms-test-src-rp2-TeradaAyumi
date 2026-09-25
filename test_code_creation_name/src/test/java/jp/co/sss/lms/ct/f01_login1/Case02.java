package jp.co.sss.lms.ct.f01_login1;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

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

	/** 前処理 
	 * @throws IOException */
	@BeforeAll
	static void before() throws IOException {
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

		//タイトルとURLが正しいか検証
		assertEquals("ログイン | LMS", webDriver.getTitle());
		assertEquals("http://localhost:8080/lms/", webDriver.getCurrentUrl());

		//test1のエビデンスを取得する
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 DBに登録されていないユーザーでログイン")
	void test02() {

		//登録されていないユーザー情報を入力
		webDriver.findElement(By.id("loginId")).sendKeys("wrongId");
		webDriver.findElement(By.id("password")).sendKeys("wrongPassword123");

		//ログインボタンを押す
		webDriver.findElement(By.className("btn-primary")).click();

		//エラーメッセージが表示されているか検証
		WebElement loginError = webDriver.findElement(By.cssSelector(".error"));
		assertEquals("* ログインに失敗しました。", loginError.getText());

		//test2のエビデンスを取得する
		getEvidence(new Object() {
		});
	}
}
