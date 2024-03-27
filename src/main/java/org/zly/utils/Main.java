package org.zly.utils;

import org.zly.utils.io.ZlyExcelUtils;

import java.io.File;
import java.io.IOException;
import java.time.ZoneId;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println(ZoneId.systemDefault());
//        System.out.println("-Duser.timezone=GMT+8".matches(".*-Duser\\.timezone=.+"));
//        long time = System.currentTimeMillis();
//        System.out.println( ZlyExcelUtils.getExcelXlsx(new File("C:\\Users\\Administrator\\Desktop\\问题反馈.xlsx")));
//        System.out.println(System.currentTimeMillis()-time);
//        final Map<Integer, Map<String, String>> amOutput = ExcelUtils.getExcelXlsx(new File("C:\\Users\\Administrator\\Desktop\\23333333333333333333333333333333.xlsx"));
//        final Map<Integer, Map<String, String>> excelXlsx = ExcelUtils.getExcelXlsx(new File("C:\\Users\\Administrator\\Desktop\\最新\\物流最终版本.xlsx"));
//        System.out.println(excelXlsx.size());
//        System.out.println(amOutput.size());
//        List<String> amOutputList = new ArrayList<>();
//        amOutput.forEach(new BiConsumer<Integer, Map<String, String>>() {
//            @Override
//            public void accept(Integer integer, Map<String, String> stringStringMap) {
//                amOutputList.add(stringStringMap.get("组件Code"));
//            }
//        });
//        System.out.println(amOutputList.size());
//        final List<Object> excelXlsxList = excelXlsx.entrySet().stream().map(new Function<Map.Entry<Integer, Map<String, String>>, Object>() {
//            @Override
//            public Object apply(Map.Entry<Integer, Map<String, String>> integerMapEntry) {
//                return integerMapEntry.getValue().get("组件Code");
//            }
//        }).collect(Collectors.toList());
//        System.out.println(excelXlsxList.removeAll(amOutputList));
//        System.out.println(excelXlsxList.size());
//        System.out.println(excelXlsxList);
//        excelXlsx.entrySet().removeIf(next -> !excelXlsxList.contains(next.getValue().get("组件Code")));
//
//        System.out.println(excelXlsx);
//        ExcelUtils.createExcelFile(new File("C:\\Users\\Administrator\\Desktop\\LKSDLFSKDFLS"),"TEST",excelXlsx);
//        final Map<Integer, Map<String, String>> excelXlsx = ExcelUtils.getExcelXlsx(new File("C:\\Users\\Administrator\\Desktop\\33333.xlsx"));
//        File file1 = new File("C:\\Users\\Administrator\\Desktop\\aaa\\1.json");
//        File file2 = new File("C:\\Users\\Administrator\\Desktop\\aaa\\2.json");
//        File file3 = new File("C:\\Users\\Administrator\\Desktop\\aaa\\3.json");
//        List<File> fileList = new ArrayList<>();
//        fileList.add(file1);
//        fileList.add(file2);
//        fileList.add(file3);
//        List<String> list = new ArrayList<>();
//        for(File file : fileList){
//            JSONObject json = JSONObject.parseObject( ZlyFileUtils.readerFileAll(file));
//            final JSONArray jsonArray = json.getJSONObject("data").getJSONArray("list");
//            for (int i = 0; i < jsonArray.size(); i++) {
//                list.add(jsonArray.getJSONObject(i).getString("name"));
//            }
//        }
//        List<String> list1 = new ArrayList<>();
//        for (Integer integer : excelXlsx.keySet()) {
//            boolean bool = list.contains(excelXlsx.get(integer).get("组件名称"));
//            list1.add(excelXlsx.get(integer).get("组件名称"));
//            if(!bool) System.out.println(bool+"---错啦");
//        }
//        System.out.println(list1.size());
//        for (String s : list) {
//            if(!list1.contains(s)) System.out.println(s);
//        }
//        list1.sort(new Comparator<String>() {
//            @Override
//            public int compare(String o1, String o2) {
//                return o1.compareTo(o2);
//            }
//        });
//        list.sort(new Comparator<String>() {
//            @Override
//            public int compare(String o1, String o2) {
//                return o1.compareTo(o2);
//            }
//        });
//        for(int i = 0; i < list.size(); i++){
//            System.out.println(list.get(i) + " ==  " +list1.get(i));
//        }
//        System.out.println(list.size());
//
//        String json = "{\"id\":220,\"name\":\"赵**\",\"weChatNumber\":null,\"authUserId\":300,\"customerId\":1,\"oaAccount\":\"zhaojian\",\"buildingIdList\":[],\"status\":\"NORMAL\",\"role\":\"CUSTOMER_NORMAL\",\"permissions\":[\"INVOICE_QUERY\",\"RESOURCE_DELETE\",\"CREATE_FOLLOW_RECORD\",\"PROPOSAL_PRINT\",\"CREATE_OFFER_LETTER\",\"ADJUSTMENT_CREATED\",\"CONTRACT_SELF_TERMINATION\",\"CONTRACT_SELF_OTHER_FEE\",\"CASH_FLOW_CLOSE\",\"CASH_FLOW_QUERY\",\"RECEIPT_QUERY\",\"MANUAL_BILL_DELETE\",\"TENANT\",\"TENANT_REPORT_EXPORT\",\"VOUCHER_CREATE\",\"VOUCHER_VIEW\",\"RECEIPT_SEND\",\"BUILDING_EDIT\",\"CONTRACT_ALL_OTHER_FEE\",\"RECEIPT_PRINT\",\"CONTRACT_REPORT_EXPORT\",\"MARKETING_CLIENT_FILE_DOWNLOAD\",\"CONTRACT_ASSIGN\",\"RECEIPT_REPORT_EXPORT\",\"VOUCHER_REPORT_EXPORT\",\"READ_FOLLOW_RECORD\",\"CONTRACT_ALL_RENEW\",\"VOUCHER_SETTLE\",\"CASH_FLOW_PREDICTION_EXPORT\",\"UPDATE_CONFIRMATIONLETTE\",\"CONTRACT_SELF_VIEW_FILES\",\"AGENCY_REPORT_EXPORT\",\"CONFIRMATIONLETTE_DELETE_FILES\",\"UPDATE_BROKERAGE\",\"CONFIRMATIONLETTE_UPLOAD_FILES\",\"RESOURCE_REPORT_EXPORT\",\"CASH_FLOW_REPORT_EXPORT\",\"DELETE_BROKERGE\",\"CONFIRMATIONLETTE_SELF_FILES\",\"OVER_DUE_FINE_OBTAIN\",\"MANUAL_BILL_COMFIRM\",\"VOUCHER_EXPORT\",\"CONTRACT_ALL\",\"INVOICE_NULLIFICATION\",\"BROKERGAGE_SELE_DETAIL\",\"ATTACHMENT_DOWNLOAD\",\"OFFER_LETTER_PRINT\",\"CASH_FLOW_PREDICTION\",\"CREATE_AGENCY\",\"INVOICE_UPDATE\",\"CONFIRMATIONLETTE_DOWNLOAD_FILES\",\"CONTRACT_ALL_VIEW_FILES\",\"MANUAL_BILL_EDIT\",\"ADJUSTMENT_NULLIFICATION\",\"CREATE_CONFIRMATIONLETTE\",\"REALIZATION_REPORT_EXPORT\",\"CONTRACT_ALL_BUSINESS_FEE\",\"CARRY_RECORD_NULLIFICATION\",\"CONTRACT_SELF\",\"CREATE_AGENT\",\"REALIZATION_QUERY\",\"DELETE_CONFIRMATIONLETTE\",\"CASH_FLOW_CREATE\",\"CONTRACT_SELF_RENEW\",\"MARKETING_CLIENT_EDIT\",\"MARKETING_CLIENT_FILE_DELETE\",\"MARKETING_CLIENT_CREATE\",\"CREATE_PROPOSAL\",\"NEW_PRICE_SYSTEM_EXPORT\",\"RESOURCE_CREATE\",\"RESOURCE_EDIT\",\"CONTRACT_PRINT\",\"INCOME_REPORT_EXPORT\",\"CONTRACT_ALL_NULLIFICATION\",\"AGENT_REPORT_EXPORT\",\"MARKETING_CLIENT_REPORT_EXPORT\",\"CONTRACT_SELF_BUSINESS_FEE\",\"MARKETING_CLIENT_ASSIGN\",\"INVOICE_DELETE\",\"CONTRACT_SELF_HYDROELECTRIC_FEE\",\"READ_AGENT\",\"INVOICE_REPORT_EXPORT\",\"CONFIRMATIONLETTE_SELE_DETAIL\",\"CONTRACT_ALL_HYDROELECTRIC_FEE\",\"MANUAL_BILL_APPROVAL_INFO\",\"READ_AGENCY\",\"READ_PROPOSAL\",\"MANUAL_BILL_APPROVAL\",\"DELETE_FOLLOW_RECORD\",\"BUILDING_DELETE\",\"CONTRACT_CREATE\",\"BUILDING_REPORT_EXPORT\",\"MARKETING_CLIENT_DELETE\",\"ATTACHMENT_UPLOAD\",\"NEW_PRICE_SYSTEM_LIST\",\"VOUCHER_DELETE\",\"MANUAL_BILL_CREATE\",\"INCOME_VIEW\",\"CREATE_BROKERAGE\",\"INCOME_EDIT\",\"CONTRACT_ALL_CHANGE\",\"DELETE_AGENCY\",\"INVOICE_ISSUE\",\"CONFIRMATIONLETTE_MANAGE\",\"MARKETING_CLIENT_FILE_UPLOAD\",\"DELETE_AGENT\",\"MARKETING_CLIENT_FILE\",\"DELETE_PROPOSAL\",\"CONTRACT_SELF_CHANGE\",\"BUILDING_QUERY_ALL\",\"ATTACHMENT_DELETE\",\"RECEIPT_ISSUE\",\"RECEIPT_NULLIFICATION\",\"REALIZATION_UN_MATCH_CASH_FLOW\",\"CONTRACT_ALL_TERMINATION\",\"READ_OFFER_LETTER\",\"ATTACHMENT_VIEW\",\"CONTRACT_SELF_NULLIFICATION\",\"OVER_DUE_FINE_OBTAIN_NULLIFICATION\",\"CARRY_RECORD_CREATE\",\"DELETE_OFFER_LETTER\",\"REALIZATION_MATCH_CASH_FLOW\",\"NEW_PRICE_SYSTEM_QUERY\",\"BROKERAGE_MANAGE\"],\"modules\":[\"MODULE_BUILDING\",\"MODULE_CONTRACT\",\"MODULE_CLIENT\",\"MODULE_TENANT\",\"MODULE_REALIZATION\",\"MODULE_RESOURCE\",\"MODULE_OPERATE\",\"MODULE_VOUCHER\",\"MODULE_INCOME\",\"MODULE_BROKEAGE\",\"MODULE_CONFIRMATIONLETTE\",\"MODULE_INCOME_BUDGET\",\"MODULE_TERMINATION_SETTLEMENT\",\"MODULE_PRICE_SYSTEM\",\"MODULE_TEMPORARY_BILL\",\"MODULE_RESOURCE_SETTING\",\"MODULE_PROJECT_SETTING\",\"MODULE_CUSTOM_SETTING\",\"MODULE_RECEIPT_SETTING\",\"MODULE_CONTRACT_SETTING\",\"MODULE_REALIZATION_SETTING\",\"MODULE_FINANCE_SETTING\",\"MODULE_MARKETING_SETTING\",\"MODULE_SYSTEM_MAINTAIN\",\"MODULE_PROJECT_MANAGEMENT\",\"MODULE_REPORT_CENTER\",\"MODULE_PRICESYSTEM\"],\"dataAccessLevel\":null,\"creatorId\":null,\"dateCreate\":1634304827000,\"riskstorm\":false,\"email\":\"z*******@longfor.com\",\"userTel\":null,\"invitedUrl\":null,\"departmentId\":1,\"department\":null,\"buildings\":[],\"departmentLeader\":false,\"tel\":null}";
//        System.out.println(JSONObject.parseObject(json).getJSONArray("permissions").size());
//        final Map<Integer, Map<String, String>> excelXlsx = ExcelUtils.getExcelXlsx(new File("C:\\Users\\Administrator\\Desktop\\最新\\物流最终版本.xlsx"));
//        int count = 0;
//        for (int i = 0; i < excelXlsx.size(); i++){
//            if(StringUtils.isNoneBlank(excelXlsx.get(i).get("集团赋能平台物流产城空间创新中心负责人(R_AM_04012)")))count++;
//        }
//        System.out.println(count);
    }
}
