package Personal_Project.Calling_Diary.service;

import Personal_Project.Calling_Diary.interfaceGroup.MemberService;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class MemberServiceImpl implements MemberService {

    @Override
    public boolean checkIdPattern(String userid) {

        // 영문 시작, 영문과 숫자를 포함한 4~20자리 검증
        String pattern = "^[A-Za-z]{1}[A-Za-z0-9]{3,19}$";
        return Pattern.matches(pattern, userid);
    }

    @Override
    public boolean checkPwdPattern(String pwd) {

        String pattern = "(?=.*\\d{1,50})(?=.*[~`!@#$%\\^&*()-+=]{1,50})(?=.*[a-zA-Z]{2,50}).{8,50}$";
        return Pattern.matches(pattern, pwd);
    }
}
