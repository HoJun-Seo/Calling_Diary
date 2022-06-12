package Personal_Project.Calling_Diary.interfaceGroup;

import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.springframework.stereotype.Service;

public interface MemberService {

    public boolean checkIdPattern(String userid);

    public boolean checkPwdPattern(String pwd);

    public boolean checkNicknamePattern(String nickname);

    public String checkPhoneNumber(String phoneNumber) throws CoolsmsException;

    public boolean checkPhoneNumberPattern(String phoneNumber);
}
