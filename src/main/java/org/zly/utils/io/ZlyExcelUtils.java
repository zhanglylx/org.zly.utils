package org.zly.utils.io;

import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.zly.utils.ZlyReflectUtils;
import org.zly.utils.function.ThreeFunction;
import org.zly.utils.network.ZlyHttpUtils;


import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 获取或创建Excle
 */
public class ZlyExcelUtils {
    public static void main(String[] args) throws IOException {
        final Map<Integer, Map<String, String>> excelXlsx = ZlyExcelUtils.getExcelXlsx(new File("C:\\Users\\Administrator\\Desktop\\无标题.xlsx"));
        final Map<String, String> map = excelXlsx.get(0);
        excelXlsx.forEach(new BiConsumer<Integer, Map<String, String>>() {
            @Override
            public void accept(Integer integer, Map<String, String> stringStringMap) {
                final String 第三方测量材料确认1 = stringStringMap.get("第三方测量材料确认");
                final String[] split = 第三方测量材料确认1.split(",");
                if(ArrayUtils.isEmpty(split))return;
                StringBuilder sb = new StringBuilder();
                for (String s : split) {
                    switch (s) {
                        case "1":
                            sb.append("测量方案");
                            break;
                        case "2":
                            sb.append("调查问卷");
                            break;
                        case "3":
                            sb.append("实施方案");
                            break;
                        case "4":
                            sb.append("数据库");
                            break;
                        case "5":
                            sb.append("被访者名单");
                            break;
                        case "6":
                            sb.append("第三方测量报告");
                            break;
                        case "undefined":
                            continue;
                        default:
                            if(split.length==1){
                                final String s1 = split[0];
                                if(StringUtils.isBlank(s1))return;
                            }
                            throw new IllegalArgumentException(s);
                    }
                    sb.append(",");
                }
                stringStringMap.put("第三方测量材料确认",StringUtils.chop(sb.toString()));
            }
        });
        excelXlsx.forEach(new BiConsumer<Integer, Map<String, String>>() {
            @Override
            public void accept(Integer integer, Map<String, String> stringStringMap) {
                System.out.println(stringStringMap.get("第三方测量材料确认"));
            }
        });
        ZlyExcelUtils.createExcelFile(new File("C:\\Users\\Administrator\\Desktop\\无标题1.xlsx"),"满意度导出",excelXlsx);
    }

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


//    public static <T> void createExcelFile(HttpServletResponse response, List<T> dataList, Class<T> entityClass, boolean outputStreamClose, String fileName) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
//        fileName = ZlyHttpUtils.getUrlEncoder(fileName);
//        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
//        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//        createExcelFile(dataList, entityClass, response.getOutputStream(), outputStreamClose);
//    }

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

            final Map<Field, Method> memberPublicMethods = ZlyReflectUtils.getMemberPublicMethods(entityClass, true);
            final Set<Map.Entry<Field, Method>> entries = memberPublicMethods.entrySet();
//            创建标题行
            int index = 0;
            Row row = sheet.createRow(0);
            for (Map.Entry<Field, Method> entry : entries) {
                String name = entry.getKey().getName();
                titles = ArrayUtils.add(titles, name);
                row.createCell(index).setCellValue(name);
                sheet.autoSizeColumn(index);
                index++;
            }
//            赋值
            T data;
            for (int i = 0; i < dataList.size(); ++i) {
                row = sheet.createRow(i + 1);
                data = dataList.get(i);
                int rowIndex = 0;
                for (Map.Entry<Field, Method> entry : entries) {
                    setCellValue(row, rowIndex++, entry.getValue().invoke(data));
                }
            }
            workbook.write(outputStream);
            outputStream.flush();
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
        return getExcelXlsx(file, null);
    }

    public static Map<Integer, Map<String, String>> getExcelXlsx(File file, ExcelReadFunction dataProcessor) {
        if (!file.exists()) throw new RuntimeException("文件不存在:" + file.getPath());
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            return getExcelXlsx(fileInputStream, dataProcessor, 0);
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
    public static Map<Integer, Map<String, String>> getExcelXlsx(InputStream inputStream, ExcelReadFunction dataProcessor, int sheetNum) {

        return useXlsxWorkbook(inputStream, new Function<Workbook, Map<Integer, Map<String, String>>>() {
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
                int index = 1;
                boolean notBlank;
                Row r;
                Cell c;
                for (int rowIndex = 1; rowIndex <= row; rowIndex++) {
                    valuesMap = new LinkedHashMap<>();
                    notBlank = false;
                    for (int columnIndex = 0; columnIndex < column; columnIndex++) {
                        title = titles.get(columnIndex);
                        if (dataProcessor != null) {
                            try {
                                r = xssfSheet.getRow(rowIndex);
                                c = null;
                                if (r != null) c = r.getCell(columnIndex);
                                value = dataProcessor.accept(title, rowIndex, columnIndex, valuesMap, xssfSheet, r, c);
                            } catch (NullPointerException ignore) {
                                value = "";
                            }
                        } else {
                            value = getCellValue(xssfSheet, rowIndex, columnIndex);
                        }
                        if (StringUtils.isNotBlank(value)) notBlank = true;
                        valuesMap.put(title, value);
                    }
//                    如果整行都是空的，则不插入
                    if (!notBlank) {
                        index--;
                        continue;
                    }
                    rowMap.put(rowIndex - index, valuesMap);
                }
                return rowMap;
            }
        });
    }


    public static <T> List<T> getExcelXlsxByClass(File File, Class<T> tClass) {
        return getExcelXlsxByClass(File, 0, null, tClass);
    }

    public static <T> List<T> getExcelXlsxByClass(File File, ExcelReadFunction dataProcessor, Class<T> tClass) {
        return getExcelXlsxByClass(File, 0, dataProcessor, tClass);
    }

    @SneakyThrows
    public static <T> List<T> getExcelXlsxByClass(File File, int sheetNum, ExcelReadFunction dataProcessor, Class<T> tClass) {
        return getExcelXlsxByClass(new FileInputStream(File), sheetNum, dataProcessor, tClass);
    }

    public static <T> List<T> getExcelXlsxByClass(InputStream inputStream, int sheetNum, ExcelReadFunction dataProcessor, Class<T> tClass) {
        final Map<Integer, Map<String, String>> excelXlsx = getExcelXlsx(inputStream, dataProcessor, sheetNum);
        List<T> list = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        excelXlsx.forEach(new BiConsumer<Integer, Map<String, String>>() {
            @SneakyThrows
            @Override
            public void accept(Integer integer, Map<String, String> dataMap) {
                final byte[] bytes = mapper.writeValueAsBytes(dataMap);
                list.add(mapper.readValue(bytes, tClass));
            }
        });
        return list;
    }

    private static String getCellValue(Sheet sheet, int rownum, int columnIndex) {
        Row row = sheet.getRow(rownum);
        if (row == null) return null;
        Cell cell = row.getCell(columnIndex);
//        如果是无法解析的CellType，使用CellType.valueOf将会报错，告知使用者
        if (cell != null) CellType.valueOf(cell.getCellType().name());
        return cell == null ? "" : cell.toString();
    }


    @Data
    public static class AA {
        @JsonProperty("AAA是")
        private String a;
        @JsonProperty("BBB")
        private String b;
    }

}
