package Personal_Project.Calling_Diary.service;

import Personal_Project.Calling_Diary.interfaceGroup.SmsEventService;
import Personal_Project.Calling_Diary.model.SmsEvent;
import io.swagger.models.auth.In;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class SmsEventServiceImpl implements SmsEventService {

    // CoolSMS 에서 apiKey, apiSecretKey 를 발급 받은 후 직접 값을 할당하여 사용하세요
    private final String apiKey = "xxxxxxxxxxxx";
    private final String apiSecretKey = "xxxxxxxxxxxxxxxxxxxxxxxx";

    @Override
    public SmsEvent smsReservation(SmsEvent smsEvent) throws CoolsmsException {

        Message coolsms = new Message(apiKey, apiSecretKey);

        // start, reservationTime 변수 변수 양식에 맞게 변환
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", smsEvent.getPhonenumber());
        params.put("from", "01050623007"); // 개발사 번호 입력
        params.put("type", "sms");
        params.put("text", smsEvent.getMessageText());
        params.put("datetime", smsEvent.getStart()+smsEvent.getReservationTime());

        JSONObject sendResponse = coolsms.send(params);
        smsEvent.setGroupid((String) sendResponse.get("group_id"));

        return smsEvent;
    }

    @Override
    public String cancel(SmsEvent smsEvent) throws CoolsmsException {

        Message coolsms = new Message(apiKey, apiSecretKey);

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("gid", smsEvent.getGroupid());

        coolsms.cancel(params);

        return "cancelSuccess";
    }
}
