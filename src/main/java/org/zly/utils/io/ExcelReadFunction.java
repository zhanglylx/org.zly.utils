package org.zly.utils.io;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.Map;

/**
 * @author zhanglianyu
 * @date 2022-04-11 14:28
 */
public interface ExcelReadFunction {

    String accept(String title, int rowIndex, int columnIndex, Map<String, String> rowValueMap, Sheet sheet, Row row, Cell cell);
}
