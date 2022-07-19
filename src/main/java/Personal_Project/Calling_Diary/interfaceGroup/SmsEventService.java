package Personal_Project.Calling_Diary.interfaceGroup;

import Personal_Project.Calling_Diary.model.SmsEvent;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.JSONObject;

public interface SmsEventService {

    public SmsEvent smsReservation(SmsEvent smsEvent) throws CoolsmsException;
}
