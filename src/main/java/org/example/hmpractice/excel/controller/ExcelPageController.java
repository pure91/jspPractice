package org.example.hmpractice.excel.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@Controller
public class ExcelPageController {

    @GetMapping("/")
    public String showExcel() {
        return "/excel/excel";
    }
}