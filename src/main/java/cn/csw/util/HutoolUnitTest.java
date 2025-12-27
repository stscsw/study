package cn.csw.util;

import cn.hutool.core.util.DesensitizedUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.Test;

/**
 * @author Lier
 * @since 2023/7/20 16:19
 */
public class HutoolUnitTest {

    @Test
    public void test1() {
        //身份证脱敏  4***************17
        System.out.println(DesensitizedUtil.idCardNum("440508199304283917", 1, 2));

        //手机号脱敏 145*****8794
        System.out.println(DesensitizedUtil.mobilePhone("145223398794"));

        //全脱敏 保留位数 ******
        System.out.println(DesensitizedUtil.password("123456"));


        System.out.println(StrUtil.hide("21321313", 2, 3));

    }


    /**
     * 请求头UserAgent解析
     */
    @Test
    public void test2(){
        //window10 Chrome
        UserAgent userAgent = UserAgentUtil.parse("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36");
        System.out.println(JSONUtil.toJsonStr(userAgent));

        //window10 edge
        userAgent = UserAgentUtil.parse("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36 Edg/115.0.1901.188");
        System.out.println(JSONUtil.toJsonStr(userAgent));

        //mac  Chrome
        userAgent = UserAgentUtil.parse("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.3 Safari/605.1.15");
        System.out.println(JSONUtil.toJsonStr(userAgent));

        //window10 edge
        userAgent = UserAgentUtil.parse("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36 Edg/115.0.1901.188");
        System.out.println(JSONUtil.toJsonStr(userAgent));

    }
}
