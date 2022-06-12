package Personal_Project.Calling_Diary.controller;

import Personal_Project.Calling_Diary.interfaceGroup.MemberDAO;
import Personal_Project.Calling_Diary.interfaceGroup.MemberService;
import Personal_Project.Calling_Diary.model.Member;
import lombok.RequiredArgsConstructor;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final MemberDAO memberDAO;

    @ResponseBody
    @PostMapping("/checkId_pattern")
    public boolean checkIdPattern(@RequestBody String httpBody){

        JSONObject jsonObject = new JSONObject(httpBody);
        String userid = jsonObject.getString("userid");

        boolean checkStatus = memberService.checkIdPattern(userid);
        return checkStatus;
    }

    @ResponseBody
    @PostMapping("/checkPwd_pattern")
    public boolean checkPwdPattern(@RequestBody String httpBody){

        JSONObject jsonObject = new JSONObject(httpBody);
        String pwd = jsonObject.getString("passwd");

        boolean checkStatus = memberService.checkPwdPattern(pwd);
        return checkStatus;
    }

    @ResponseBody
    @PostMapping("/checkNickname_pattern")
    public boolean checkNicknamePattern(@RequestBody String httpBody){

        JSONObject jsonObject = new JSONObject(httpBody);
        String nickname = jsonObject.getString("nickname");

        boolean checkStatus = memberService.checkNicknamePattern(nickname);
        return checkStatus;
    }

    @ResponseBody
    @PostMapping("/checkPhoneNumber_pattern")
    public boolean checkPhoneNumberPattern(@RequestBody String httpBody){

        JSONObject jsonObject = new JSONObject(httpBody);
        String phoneNumber = jsonObject.getString("phoneNumber");

        boolean checkStatus = memberService.checkPhoneNumberPattern(phoneNumber);
        return checkStatus;
    }

    @ResponseBody
    @PostMapping("/check_phoneNumber")
    public String checkPhoneNumber(@RequestBody String httpBody) throws CoolsmsException {

        JSONObject jsonObject = new JSONObject(httpBody);
        String phoneNumber = jsonObject.getString("phoneNumber");
        String numString = memberService.checkPhoneNumber(phoneNumber);

        return numString;
    }

    @PostMapping("/register")
    public String memberRegister(Member member){

        return null;
    }
}
