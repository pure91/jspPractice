package org.example.hmpractice.stringtype.controller;

import lombok.extern.log4j.Log4j2;
import org.example.hmpractice.stringtype.service.StringService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URL;
import java.net.URLDecoder;
import java.util.*;

@RequestMapping("/string")
@Log4j2
@Controller
public class StringController {

    @Autowired
    StringService stringService;

    // 메인 페이지
    @GetMapping("")
    public String stringMainPage() {
        return "string/main";
    }

    // 문제별 핸들러
    @PostMapping("/example")
    public String handleExample(@RequestParam("sendName") String questionValue) {
        switch (questionValue) {
            case "first":
                return "redirect:/string/first";
            case "second":
                return "redirect:/string/second";
            case "third":
                return "redirect:/string/third";
            case "four":
                return "redirect:/string/four";
            case "five":
                return "redirect:/string/five";
            case "six":
                return "redirect:/string/six";
            case "seven":
                return "redirect:/string/seven";
            case "eight":
                return "redirect:/string/eight";
            case "nine":
                return "redirect:/string/nine";
            case "ten":
                return "redirect:/string/ten";
            default:
                return "string/main";
        }
    }

    // 1번째 문제 : JSON 파싱 후 데이터 추출
    @GetMapping("/first")
//    @ResponseBody // 값만 확인하려고 사용 -> 메인으로 다시 돌려주려면 그냥 view로 보내야겠다.
    public String theFirstQuestion(Model model) {
        String jsonData = """
                {
                    "users": [
                    {
                        "id":"user1",
                        "name":"Alice",
                        "email":"alice@example.com",
                    },
                    {
                        "id":"user2",
                        "name":"Bob",
                        "email":"bob@example.com",
                    },
                    ]
                }
                """;

        // JSON 데이터 파싱
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONArray usersArray = jsonObject.getJSONArray("users");

        // 결과 저장 리스트, Set 생성
        List<String> jsonList = new ArrayList<>();
        Set<String> domainSet = new HashSet<>();

        for (int i = 0; i < usersArray.length(); i++) {
            JSONObject user = usersArray.getJSONObject(i);

            // id와 name 추출해서 list에 저장
            String id = user.getString("id");
            String name = user.getString("name");
            jsonList.add(id + ": " + name);

            // email에서 domain만 추출해서 중복이없는 Set 인터페이스에 저장
            String email = user.getString("email");
            String domain = email.split("@")[1];
            domainSet.add(domain);
        }

        model.addAttribute("jsonList", jsonList);
        model.addAttribute("domainSet", domainSet);

//        return "1번째 문제 json 파싱 리스트 :" + jsonList + "\n도메인 추출:" +domainSet;
        return "string/first";
    }

    // 2번째 문제 : 긴 문자열 split하기(id, role, reason 값 추출)
    @GetMapping("/second")
    public String theSecondQuestion(Model model) {
        String Logs = """
                INFO - 2025-01-27 10:00:00 - User logged in: id=user1, role=admin
                INFO - 2025-01-27 10:01:00 - User logged in: id=user2, role=editor
                ERROR - 2025-01-27 10:02:00 - Login failed: id=user3, reason=wrong_password
                """;

        // split의 "반환타입은 배열"이 라는걸 까먹지말자!!
        // 일단 줄바꿈 기준으로 배열방에 넣어놓고
        String[] logsArray = Logs.split("\n");
        log.debug("logsArray: {}", (Object[]) logsArray);

        // 각 로그방 마다 꺼내서 리스트Map에 담아
        List<Map<String, Object>> logsList = new ArrayList<>();

        for (String logLine : logsArray) {
            Map<String, Object> logMap = new HashMap<>();

            // 'id='와 'role=' 또는 'reason='이 있는 로그 항목만 처리
            if (logLine.contains("id=")) {
                String id = extractValue(logLine, "id=");
                logMap.put("id", id);
                log.debug("id:{}", id);
            }

            if (logLine.contains("role=")) {
                String role = extractValue(logLine, "role=");
                logMap.put("role", role);
                log.debug("role:{}", role);
            }

            if (logLine.contains("reason=")) {
                String reason = extractValue(logLine, "reason=");
                logMap.put("reason", reason);
                log.debug("reason:{}", reason);
            }
            // 각 로그별 map을 리스트에 추가
            logsList.add(logMap);
            log.info("logsList:{}", logsList);
        }

        // jsp에 뿌려주려면 model에 추가해줘야함
        model.addAttribute("logsList", logsList);

        return "string/second";
    }

    // 2번 문제의 id, role, reason을 추출하기 위한 메소드
    private String extractValue(String logLine, String key) {
        // indexOf가 지정된 문자의 "처음 위치를 반환"함
        int startIndex = logLine.indexOf(key) + key.length(); // key("id=")뒤의 시작위치
        int endIndex = logLine.indexOf(",", startIndex); // key뒤에 나오는 "," 위치 9번을 찾음
        // ","가 없으면 -1반환
        if (endIndex == -1) {
            endIndex = logLine.length(); // ','가 없으면 문자열 끝까지
        }
        return logLine.substring(startIndex, endIndex).trim(); // 시작 인덱스부터 끝 인덱스까지 찾고 양 끝 공백 지우고 값 추출

        // substring 주의할게 substring(3,9)면 3~9가 아니고 4~8임
        // indexOf는 안에 넣은 문자열이 몇번째 위치인지 찾음 ex) id=user1이면 indexOf("id=")는 0번째임
    }

    // 3번째 문제 : 사용자의 다양한 요청 URL 쿼리 파라미터를 추출해서 출력하기
    @GetMapping("/third")
    public String theThirdQuestion(Model model) throws Exception {
        // 요청 URL 생성
        String requestUrl = "https://example.com/search?q=Java+MVC+pattern+example&sort=asc&category=programming";

        // 도메인 추출
        String domain = extractDomain(requestUrl);
        model.addAttribute("domain", domain);
        log.debug("domain: {}", domain);

        // 쿼리 파라미터 추출 후 map 저장
        Map<String, String> queryParams = extractQueryParams(requestUrl);
        model.addAttribute("queryParams", queryParams);
        log.debug("queryParams: {}", queryParams);

        // q 파라미터를 분리해서 List에 저장
        String qParam = queryParams.get("q");
        if (qParam != null) {
            List<String> keywords = Arrays.asList(qParam.split("\\+")); // "\\+"의 의미는 => java의 문자열 안에서 \\두개는 정규식의 \역슬래시 1개로 표현됨
            // 그래서 정규식에서 \+라고 나오는건 문자 그대로의 +를 의미함, 즉 여기서는 +기준으로 문자열을 나눈다는거임
            // why? 그냥 + 쓰면 특수문자로 더하기나 패턴 반복으로 인식하기 때문
            model.addAttribute("keywords", keywords);
            log.debug("keywords: {}", keywords);
        }
        return "string/third";
    }

    // 3번 문제의 요청 도메인 추출
    private String extractDomain(String requestUrl) throws Exception {
        URL urlObject = new URL(requestUrl); // URL 객체 변환
        return urlObject.getHost(); // hostname 추출(example.com)
    }

    // 3번 문제의 쿼리 파라미터 추출 메소드
    private Map<String, String> extractQueryParams(String requestUrl) throws Exception {
        Map<String, String> queryParams = new HashMap<>();
        URL urlObject = new URL(requestUrl);
        String query = urlObject.getQuery(); // 쿼리 스트링 추출 "q=Java+MVC+pattern+example&sort=asc&category=programming"

        if (query != null) {
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                String[] keyValue = pair.split("="); // 키 밸류 나누기
                // 입력된 문자열 keyValue[0]를 웹에서 사용할 수 있게 "UTF-8"형식으로 변경
                String key = URLDecoder.decode(keyValue[0], "UTF-8");
                String value = keyValue.length > 1 ? URLDecoder.decode(keyValue[1], "UTF-8") : "";
                queryParams.put(key, value);
            }
        }
        return queryParams;
    }
}
