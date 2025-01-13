package org.example.hmpractice.excel.service;

import lombok.extern.log4j.Log4j2;
import org.example.hmpractice.excel.mapper.ExcelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Log4j2
@Service
public class ExcelService {

    @Autowired
    ExcelMapper excelMapper;

    // 데이터 조회
    public List<HashMap<String, Object>> searchExcel() {
        log.info(">>> searchExcel search <<<");
        List<HashMap<String, Object>> searchExcelAllParam = excelMapper.searchExcelAllParam();
        log.info(">>> fin <<<");

        return searchExcelAllParam;
    }

    // 요청 데이터 작성
//    public Workbook excelPrint(Map<String, String> excelList) throws IOException {
//        log.info(">>> excelPrint excel <<<");
//
//    }
}
