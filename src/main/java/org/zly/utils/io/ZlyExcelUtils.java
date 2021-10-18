package org.zly.utils.io;

import lombok.Data;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.zly.utils.ZlyReflectUtils;
import org.zly.utils.network.ZlyHttpUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 获取或创建Excle
 */
public class ZlyExcelUtils {
    /**
     * 创建并写入xlsx文件
     *
     * @param excelPath 文件保存路径
     * @param sheetName sheet名称
     * @param dataMap   数据源
     * @return 创建成功或失败
     */
    public synchronized static void createExcelFile(File excelPath,
                                                    String sheetName, Map<Integer,
            Map<String, String>> dataMap) throws IOException {
        if (excelPath == null) throw new NullPointerException();
        if (!excelPath.getName().endsWith(".xlsx")) excelPath = new File(excelPath + ".xlsx");
        if (excelPath.getParentFile() != null && !excelPath.getParentFile().exists()) {
            if (!excelPath.getParentFile().mkdirs()) throw new RuntimeException("创建目录失败:" + excelPath.getParentFile());
        }
        //HSSFWorkbook();//WorkbookFactory.create(inputStream)
        try (Workbook workbook = new XSSFWorkbook();
             FileOutputStream outputStream = new FileOutputStream(excelPath)) {
            // XSSFWork used for .xslx (>= 2007), HSSWorkbook for 03 .xsl
            Sheet sheet = workbook.createSheet(sheetName);
            List<String> titleList = new ArrayList<>();
            //读取出所有map的title
            for (Map.Entry<Integer, Map<String, String>> mapEntry : dataMap.entrySet()) {
                for (Map.Entry<String, String> m : mapEntry.getValue().entrySet()) {
                    if (!titleList.contains(m.getKey())) titleList.add(m.getKey());
                }
            }
            Cell cell;
            Row row0 = sheet.createRow(0);
            //写入标题
            for (int i = 0; i < titleList.size(); i++) {
                cell = row0.createCell(i, CellType.STRING);
                cell.setCellStyle(getCellStyle(workbook));
                cell.setCellValue(titleList.get(i));
                sheet.autoSizeColumn(i);
            }
            int index = 1;
            Row row;
            for (Map.Entry<Integer, Map<String, String>> integerMapEntry : dataMap.entrySet()) {
                //插入数据
                row = sheet.createRow(index);
                for (int i = 0; i < titleList.size(); i++) {
                    setCellValue(row, i, integerMapEntry.getValue().get(titleList.get(i)));
                }
                index++;
            }
            workbook.write(outputStream);
            outputStream.flush();
        }
    }

    private static void setCellValue(Row row, int index, Object value) {
        String v = value == null ? "" : value.toString();
        Cell cell;
        try {
            double d = Double.parseDouble(v);
            cell = row.createCell(index, CellType.NUMERIC);
            cell.setCellValue(d);
        } catch (NumberFormatException e) {
            cell = row.createCell(index, CellType.STRING);
            cell.setCellValue(v);
        }
    }


    public static <T> void createExcelFile(HttpServletResponse response, List<T> dataList, Class<T> entityClass, boolean outputStreamClose, String fileName) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        fileName = ZlyHttpUtils.getUrlEncoder(fileName);
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        createExcelFile(dataList, entityClass, response.getOutputStream(), outputStreamClose);
    }

    public static <T> void createExcelFile(List<T> dataList, Class<T> entityClass, File saveFile) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        createExcelFile(dataList, entityClass, saveFile, true);
    }

    public static <T> void createExcelFile(List<T> dataList, Class<T> entityClass, File saveFile, boolean outputStreamClose) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (!saveFile.getName().endsWith(".xlsx")) saveFile = new File(saveFile + ".xlsx");
        createExcelFile(dataList, entityClass, new FileOutputStream(saveFile), outputStreamClose);
    }

    public static <T> void createExcelFile(List<T> dataList, Class<T> entityClass, OutputStream outputStream) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        createExcelFile(dataList, entityClass, outputStream, true);
    }

    public static <T> void createExcelFile(List<T> dataList, Class<T> entityClass, OutputStream outputStream, boolean outputStreamClose) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        try (Workbook workbook = new XSSFWorkbook()) {
            String[] titles = new String[]{};
            Sheet sheet = workbook.createSheet();
            Row row = sheet.createRow(0);
            int index = 0;
            for (Method titleMethod : ZlyReflectUtils.getMemberPublicMethods(entityClass, true).values()) {
                String name = titleMethod.getName();
                titles = ArrayUtils.add(titles, name);
                name = name.substring(3);
                if (name.length() > 1) {
                    row.createCell(index).setCellValue(
                            Character.toLowerCase(name.charAt(0)) + name.substring(1));
                } else {
                    row.createCell(index).setCellValue(name.toLowerCase());
                }
                sheet.autoSizeColumn(index);
                index++;

            }
            Method dataMethod;
            T data;
            for (int i = 0; i < dataList.size(); ++i) {
                row = sheet.createRow(i + 1);
                for (int titleIndex = 0; titleIndex < titles.length; ++titleIndex) {
                    data = dataList.get(i);
                    dataMethod = data.getClass().getDeclaredMethod(titles[titleIndex]);
                    setCellValue(row, titleIndex, dataMethod.invoke(data));
                }
            }
            workbook.write(outputStream);
            outputStream.flush();
        } catch (IOException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw e;
        } finally {
            if (outputStreamClose) outputStream.close();
        }
    }

    private static CellStyle getCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        // 设置单元格字体
        Font headerFont = workbook.createFont(); // 字体
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(Font.COLOR_RED);
        headerFont.setFontName("宋体");
        style.setFont(headerFont);
        style.setWrapText(true);
        // 设置单元格边框及颜色
        style.setBorderBottom(BorderStyle.valueOf((short) 1));
        style.setBorderLeft(BorderStyle.valueOf((short) 1));
        style.setBorderRight(BorderStyle.valueOf((short) 1));
        style.setBorderTop(BorderStyle.valueOf((short) 1));
        style.setWrapText(true);
        return style;
    }


    public static Map<Integer, Map<String, String>> getExcelXlsx(File file) {
        if (!file.exists()) throw new RuntimeException("文件不存在:" + file.getPath());
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            return getExcelXlsx(fileInputStream, 0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getExcelSheetNumbergetException(File file) {
        if (!file.exists()) throw new RuntimeException("文件不存在:" + file.getPath());
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            return getExcelSheetNumbergetException(fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getExcelSheetNumbergetException(InputStream inputStream) {
        return useXlsxWorkbook(inputStream, new Function<Workbook, Integer>() {
            @Override
            public Integer apply(Workbook workbook) {
                return workbook.getNumberOfSheets();
            }
        });
    }

    public static <R> R useXlsxWorkbook(InputStream inputStream, Function<Workbook, R> function) {
        try (Workbook xssfWorkbook = new XSSFWorkbook(inputStream)) {
            return function.apply(xssfWorkbook);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取xlsx文件
     *
     * @param inputStream 输入流，这里未关闭输入流，需要用户关闭
     */
    @SuppressWarnings("unchecked")
    public static Map<Integer, Map<String, String>> getExcelXlsx(InputStream inputStream, int sheetNum) {

      return   useXlsxWorkbook(inputStream,  new Function<Workbook, Map<Integer, Map<String, String>>>() {
            @Override
            public Map<Integer, Map<String, String>> apply(Workbook workbook) {
                Map<Integer, Map<String, String>> rowMap = new LinkedHashMap<>();//所有行的总值
                Map<String, String> valuesMap;//每一行的总值
                Sheet xssfSheet = workbook.getSheetAt(sheetNum);
                int column = xssfSheet.getRow(0).getPhysicalNumberOfCells();// 获取所有的列
                int row = xssfSheet.getLastRowNum(); // 获取所有的行
//            提前缓存标题
                List<String> titles = new ArrayList<>();
                for (int columnIndex = 0; columnIndex < column; columnIndex++) {
                    titles.add(getCellValue(xssfSheet, 0, columnIndex));
                }
                String title;
                String value;
                for (int rowIndex = 1; rowIndex <= row; rowIndex++) {
                    valuesMap = new LinkedHashMap<>();
                    for (int columnIndex = 0; columnIndex < column; columnIndex++) {
                        title = titles.get(columnIndex);
                        value = getCellValue(xssfSheet, rowIndex, columnIndex);
                        valuesMap.put(title, value);
                    }
                    rowMap.put(rowIndex - 1, valuesMap);
                }
              return rowMap;
            }
        });
    }

    private static String getCellValue(Sheet sheet, int rownum, int columnIndex) {
        Row row = sheet.getRow(rownum);
        if (row == null) return "";
        Cell cell = row.getCell(columnIndex);
//        如果是无法解析的CellType，使用CellType.valueOf将会报错，告知使用者
        if (cell != null) CellType.valueOf(cell.getCellType().name());
        return cell == null ? "" : cell.toString();
    }

}
