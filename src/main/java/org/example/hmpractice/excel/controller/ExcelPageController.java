package org.example.hmpractice.excel.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@RequestMapping("/excel")
@Controller
public class ExcelPageController {

    @GetMapping("/showExcel")
    public String showExcel() {
        log.info("showExcel");

        return "excel/excel";
    }
}
