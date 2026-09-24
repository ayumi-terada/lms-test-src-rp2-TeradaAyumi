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

	//private static final String SCREENSHOT_DIR = "./evidence/";

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
		webDriver.get("http://localhost:8080/lms/");

		//タイトルとURLが正しいか検証する
		assertEquals("ログイン | LMS", webDriver.getTitle());
		assertEquals("http://localhost:8080/lms/", webDriver.getCurrentUrl());

		//test1のエビデンスを取得する
		getEvidence(new Object() {
		});
	}

}
