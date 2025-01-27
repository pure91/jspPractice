package org.example.hmpractice.excel.controller;

import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.Workbook;
import org.example.hmpractice.excel.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@RequestMapping("/api/excel")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ExcelApiRestController {

    @Autowired
    ExcelService excelService;

    // 데이터 조회
    @GetMapping("/search-excel")
    public List<HashMap<String, Object>> searchExcelData() {
        log.info("searchExcelData");

        List<HashMap<String, Object>> searchExcelAllParam = excelService.searchExcel();

        return searchExcelAllParam;
    }

    // excel 다운로드
    @GetMapping("/downloadExcel")
    public void ExcelDownloadData(HttpServletResponse response, @RequestParam Map<String, String> excelList) throws NumberFormatException, IOException {
        log.info("ExcelDownloadData:" + excelList);

        // jsp에서 요청받은 데이터를 엑셀 파일에 작성함
        Workbook wb = excelService.ExcelDownload(excelList);

        // 컨텐츠 타입, 파일명 지정
        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", "attachment;filename=test.xlsx");

        // excel 파일 저장
        wb.write(response.getOutputStream());
        wb.close();
    }

}
