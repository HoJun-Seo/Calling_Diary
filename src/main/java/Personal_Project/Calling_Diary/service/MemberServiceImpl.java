package Personal_Project.Calling_Diary.service;

import Personal_Project.Calling_Diary.interfaceGroup.MemberService;
import Personal_Project.Calling_Diary.model.Member;
import Personal_Project.Calling_Diary.xss.XssUtil;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.springframework.stereotype.Service;

import java.util.Random;
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

    @Override
    public boolean checkNicknamePattern(String nickname) {

        String pattern = "^[a-zA-Zㄱ-힣0-9]{2,20}$";
        return Pattern.matches(pattern,nickname);
    }

    @Override
    public String checkPhoneNumber(String phoneNumber) throws CoolsmsException {

        // 원활한 개발을 위해 프로젝트 완성 때까지 휴대폰 문자인증 기능 주석 처리
//        String apiKey = "NCSNFWSD3AINHHIM";
//        String apiSecretKey = "5NN4H1KIMCH5I3KDRXSHI0R8J2DYAR9H";
//        Message coolsms = new Message(apiKey, apiSecretKey);
        
        Random random = new Random();
        String numString = "";
        for (int i = 0; i < 4; i++){
            String randomNumber = Integer.toString(random.nextInt(10));
            numString += randomNumber;
        }

//        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("to", phoneNumber);
//        params.put("from", "01050623007");
//        params.put("type", "sms");
//        params.put("text", "Calling_Diary 휴대폰 인증번호는 [" + numString + "] 입니다.");
//        coolsms.send(params);

        return numString;
    }

    @Override
    public boolean checkPhoneNumberPattern(String phoneNumber) {
        
        // 숫자만 허용
        String pattern = "^[0-9]+$";
        return Pattern.matches(pattern, phoneNumber);
    }

    @Override
    public Member cleanXssRegister(Member member) {

        member.setUserid(XssUtil.cleanXSS(member.getUserid()));
        member.setPasswd(XssUtil.cleanXSS(member.getPasswd()));
        member.setNickname(XssUtil.cleanXSS(member.getNickname()));
        member.setPhonenumber(XssUtil.cleanXSS(member.getPhonenumber()));

        return member;
    }

    @Override
    public Member cleanXssLogin(Member member) {

        member.setUserid(XssUtil.cleanXSS(member.getUserid()));
        member.setPasswd(XssUtil.cleanXSS(member.getPasswd()));
        return member;
    }

    @Override
    public Member cleanXssfindPwd(Member member) {

        member.setUserid(XssUtil.cleanXSS(member.getUserid()));
        member.setPhonenumber(XssUtil.cleanXSS(member.getPhonenumber()));
        return member;
    }
}
