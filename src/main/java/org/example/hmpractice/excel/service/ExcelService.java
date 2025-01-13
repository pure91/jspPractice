package org.example.hmpractice.excel.service;

import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.hmpractice.excel.mapper.ExcelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
public class ExcelService {

    @Autowired
    ExcelMapper excelMapper;

    String sep = File.separator; // 파일 구분자(운영체제에 상관없이 경로 생성함) => ex) Windwos 역슬래시 \, Unix/Linux/macOS 슬래시 /
    String staticPath = System.getProperty("user.dir") + sep + "src" + sep + "main" + sep + "resources" + sep + "static" + sep; // user.dir는 프로젝트 루트 디렉토리(hmPractice)

    // 데이터 조회
    public List<HashMap<String, Object>> searchExcel() {
        log.info(">>> searchExcel search <<<");
        List<HashMap<String, Object>> searchExcelAllParam = excelMapper.searchExcelAllParam();
        log.info(">>> searchExcel finish <<<");

        return searchExcelAllParam;
    }

    // 엑셀 다운로드
    public Workbook ExcelDownload(Map<String, String> excelList) throws IOException {
        log.info(">>> ExcelDownload search <<<");

        String templatesPath = staticPath + "excelTemplate" + sep + "excelPractice.xlsx"; // 템플릿 파일 경로
        FileInputStream file = new FileInputStream(templatesPath); // 템플릿 파일을 읽어오는 입력 스트림 생성
        Workbook wb = new XSSFWorkbook(file); // Workbook 엑셀 파일 객체를 생성하고 반환함
        Sheet sheet = wb.getSheetAt(0); // 첫번째 시트를 가져온다. 엑셀 하단의 sheet말하는거임

        String excelChkStr = excelList.get("excelChk");
        log.info("excelChkStr:{}", excelChkStr);

        int excelLength = 0;
        if (excelChkStr != null && !excelChkStr.isEmpty()) {
            try {
                excelLength = Integer.valueOf(excelChkStr);
            } catch (NumberFormatException e) {
                log.error("excelChk value is invalid: {}", excelChkStr);
                excelLength = 0;
            }
        }

        if (excelLength > 0) { // 체크박스가 0이 아닌경우만 데이터를 작성한다.
            Row row; // POI 라이브러의 Row클래스
            int excelRow = 1;

            String[] excelA = (excelList.get("excelA")).split(","); // excelList Map 데이터에서 받은 문자열을 쉼표로 분리하여 배열 변환
            String[] excelB = (excelList.get("excelB")).split(",");
            String[] excelC = (excelList.get("excelC")).split(",");

            for (int cnt = 0; cnt < excelLength; cnt++) { // 체크박스(excelChk)에 따라 길이 생성 반복
                row = sheet.createRow(excelRow); // 새로운 행 생성
                row.createCell(0).setCellValue(excelRow);    // 각 셀에 데이터 입력 => 1번째 셀에 행 No. 입력
                row.createCell(1).setCellValue(excelA[cnt]); // excelA[]배열 데이터를 2번째 셀에 입력
                row.createCell(2).setCellValue(excelB[cnt]);
                row.createCell(3).setCellValue(excelC[cnt]);

                excelRow++;
            }
        }

        log.info(">>> ExcelDownload finish <<<");
        return wb; // 작성된 Workbook 엑셀 파일 객체 반환
    }
}
