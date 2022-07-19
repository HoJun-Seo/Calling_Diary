package Personal_Project.Calling_Diary.service;

import Personal_Project.Calling_Diary.interfaceGroup.SmsEventService;
import Personal_Project.Calling_Diary.model.SmsEvent;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class SmsEventServiceImpl implements SmsEventService {

    private final String apiKey = "NCSNFWSD3AINHHIM";
    private final String apiSecretKey = "5NN4H1KIMCH5I3KDRXSHI0R8J2DYAR9H";

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

        System.out.println(smsEvent.toString());
        return smsEvent;
    }
}
