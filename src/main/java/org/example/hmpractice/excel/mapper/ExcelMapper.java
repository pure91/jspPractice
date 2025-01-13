package org.example.hmpractice.excel.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface ExcelMapper {

    // 데이터 조회
    List<HashMap<String, Object>> searchExcelAllParam();

}
