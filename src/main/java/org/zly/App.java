package org.zly;

import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 */
//@SpringBootApplication
public class App {
    public static void main(String[] args) {
        long l = System.currentTimeMillis();
        String sss = "select decode(-USE_STATUS_INNET,-ACTIVITY_TYPE,-LOWER_USER_TYPE,CUSTOMER_ID) c1,\n" +
                "       length(-USE_STATUS_INNET) c2,\n" +
                "lengthb(-LOWER_USER_TYPE) c3,\n" +
                "       CUSTOMER_ID c4,\n" +
                "       -LOWER_USER_TYPE c5\n" +
                "from unicom_201206_d_number t1where (n_number3+'10',is_innet||'1') in \n" +
                "(select 10||n_number3,activitwherey_type+'6' from unicom_201206_d_number where n_in  where n_int+activity_type < 100000) \n" +
                "and -INNET_MONTH >= -LOWER_USER_TYPE and CUSTOMER_ID like 'where' and like '%where'";
//        true 代表当前在单引号中，不能替换
        boolean 单引号 = false;
        String str = "";
        String whereStr = " where ";
        String re = " 1=1 and ";
        for (char c : sss.toCharArray()) {
            final String s1 = String.valueOf(c);
            str += s1;
            if (s1.equals("'") || s1.equals("\"")) {
                单引号 = !单引号;
            }
            if (!单引号 && str.endsWith(whereStr)) {
                str += re;
            }

        }
        System.out.println(str);
        System.out.println(System.currentTimeMillis()-l);
    }
}
