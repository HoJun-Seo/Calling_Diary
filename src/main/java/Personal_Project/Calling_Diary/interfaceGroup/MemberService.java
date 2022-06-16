package Personal_Project.Calling_Diary.interfaceGroup;

import Personal_Project.Calling_Diary.model.Member;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

public interface MemberService {

    public boolean checkIdPattern(String userid);

    public boolean checkPwdPattern(String pwd);

    public boolean checkNicknamePattern(String nickname);

    public String checkPhoneNumber(String phoneNumber) throws CoolsmsException;

    public boolean checkPhoneNumberPattern(String phoneNumber);

    public Member cleanXssRegister(Member member);

    public Member cleanXssLogin(Member member);

    public Member cleanXssfindPwd(Member member);
}
