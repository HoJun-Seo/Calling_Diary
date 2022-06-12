package Personal_Project.Calling_Diary.interfaceGroup;

import org.springframework.stereotype.Service;

public interface MemberService {

    public boolean checkIdPattern(String userid);

    public boolean checkPwdPattern(String pwd);

    public boolean checkNicknamePattern(String nickname);
}
