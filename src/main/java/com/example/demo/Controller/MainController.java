package com.example.demo.Controller;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Controller
@CrossOrigin(origins = "*")
public class MainController {
    @Autowired
    private WebDriver webDriver;
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    @GetMapping("/login")
    public String index() {
        if (!webDriver.getCurrentUrl().startsWith("https://sell.smartstore.naver.com")) {
            // 네이버 로그인 메서드 호출 메서드 작성 필요
        }
        return "index";
    }

    @GetMapping("/api/{keyword}")
    @ResponseBody
    public String api(@PathVariable String keyword) {
        Future<String> future = executorService.submit(() -> {
            return getSearchData(keyword);
        });

        try {
            return future.get();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    private String getSearchData(String keyword) {
        try {
            // 필요시 드라이버를 초기화합니다.
            if (webDriver == null) {
                webDriver = new ChromeDriver();
            }

            // 새 탭을 열어 검색 요청을 수행합니다.
            JavascriptExecutor jsExecutor = (JavascriptExecutor) webDriver;
            int tabCount = webDriver.getWindowHandles().size();
            if (tabCount < 3) {
                jsExecutor.executeScript("window.open('about:blank','_blank');");
                tabCount++;
            }

            // 기존의 검색탭으로 전환
            String searchTab = (String) webDriver.getWindowHandles().toArray()[2];
            webDriver.switchTo().window(searchTab);
            webDriver.get("https://sell.smartstore.naver.com/api/product/shared/product-search-popular?_action=productSearchPopularByKeyword&keyword=" + keyword);

            System.out.println("검색창 열림");

            // 페이지 소스를 가져와 JSON 데이터를 추출합니다.
            String pageSource = webDriver.getPageSource();
            int startIndex = pageSource.indexOf("{");
            int endIndex = pageSource.lastIndexOf("}");
            if (startIndex < 0 || endIndex < 0) {
                return "Error: 데이터가 존재하지 않습니다.";
            }

            String jsonData = pageSource.substring(startIndex, endIndex + 1);
            System.out.println("데이터 추출 완료");

            return jsonData;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}