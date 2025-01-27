package org.example.hmpractice.stringtype.controller;

import lombok.extern.log4j.Log4j2;
import org.example.hmpractice.stringtype.service.StringService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequestMapping("/string")
@Log4j2
@Controller
public class StringController {

    @Autowired
    StringService stringService;

    @GetMapping("")
    public String stringMainPage() {
        return "string/main";
    }

    // JSON 데이터 추출
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
}
