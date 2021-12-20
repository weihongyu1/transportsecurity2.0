package com.why.transportsecurity.utils.passwordutils;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import java.io.IOException;


/**
 * @ClassName：PhoneUtils
 * @Description：todo
 * @Author: why
 * @DateTime：2021/12/13 19:24
 */
@Slf4j
public class PhoneUtils {
    /**
     * 返送短信
     * @param phoneNumbers 电话号码
    13.2.5 RandomUtils
    产生随机数
     * @param params 短信内容
     */
    public static void sendPhoneMail(String[] phoneNumbers,String[] params){
        // 短信应用 SDK AppID
        int appid = 1400553555; // SDK AppID 以1400开头
        // 短信应用 SDK AppKey
        String appkey = "6d679fda83cd0efbad9f3cc988e6e09f";
        // 短信模板 ID，需要在短信应用中申请
        int templateId = 1053503; // NOTE: 这里的模板 ID`7839`只是示例，真实的模板 ID
        // 签名
        String smsSign = "风雪踏梦行"; // NOTE: 签名参数使用的是`签名内容`，而不是`签名
        try {
            SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
            SmsSingleSenderResult result = ssender.sendWithParam("86",
                    phoneNumbers[0],
                    templateId, params, smsSign, "", "");
            System.out.println(result);
        } catch (JSONException e) {
            // JSON 解析错误
            log.warn("json解析错误");
            e.printStackTrace();
        } catch (IOException e) {
            // 网络 IO 错误
            log.warn("网络IO错误");
            e.printStackTrace();
        } catch (HTTPException e) {
            log.warn("错误");
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        String[] phone = {"15337086013"};
        // String[] phone = {"18694336926"};
        String random = RandomUtils.getRandom();
        String[] params = {random};
        sendPhoneMail(phone,params);
    }
}
