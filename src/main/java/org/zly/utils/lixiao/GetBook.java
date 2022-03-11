package org.zly.utils.lixiao;

import org.apache.http.client.utils.URIBuilder;
import org.zly.utils.network.ZlyHttpUtils;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhanglianyu
 * @date 2022-02-12 22:59
 */
public class GetBook {
    public static void main(String[] args) throws IOException, URISyntaxException {
        String dirName = "17-附录，参考文献 -原书教材p483-503";
        String url = "https://mp.weixin.qq.com/s?__biz=MzI3NDk5MzA4NA==&mid=2247485340&idx=1&sn=7541dec5056c918f0e734cceee0f3118&chksm=eb0add4fdc7d5459aa301a2276d43a3cceacc5d14205e184e45e7130b6ad89138b3cd146894d&scene=21#wechat_redirect";
        String dir = "C:\\Users\\Administrator\\Desktop\\lixiao\\";
        dir += dirName;
        final String s = ZlyHttpUtils.doGet(
                url, "");
//        final String s = ZlyFileUtils.readerFileAll(new File("C:\\Users\\Administrator\\Desktop\\y.txt"));
//        System.out.println(s);
        List<String> list = new ArrayList<>();
//        src="https://mmbiz.qpic.cn/mmbiz_png/h0YMicmQ7DYBXD0ibOTXxSIcDI8ZKf5M8swZ3pouKhSmr5CJdQlELicoDb7AI9lBsxiat3U2uQefqLHK94eQd2SqTg/640?wx_fmt=png"
        Pattern p = Pattern.compile("data-src=\"https://mmbiz\\.qpic\\.cn/mmbiz_png/([0-9a-zA-Z/]+)\\?[0-9a-zA-Z=_]+\"");
        Matcher m = p.matcher(s);
        while (m.find()) {
            String s1 = m.group();
            list.add(s1.substring("data-src=\"".length(), s1.length() - 1));
        }
        int index = 1;
        File file = new File(dir);
        if (!file.exists()) {
            boolean b = file.mkdir();
            if(!b)throw new IllegalArgumentException();
        }
        for (String s1 : list) {
            final byte[] bytes = ZlyHttpUtils.doGetByte(new URIBuilder(s1).build(), null, null);
            OutputStream outputStream = new FileOutputStream(dir+ "\\" + (index++) + ".png");
            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();
        }


    }
}
