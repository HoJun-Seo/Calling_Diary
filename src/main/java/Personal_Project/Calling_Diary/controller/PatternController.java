package Personal_Project.Calling_Diary.controller;

import Personal_Project.Calling_Diary.interfaceGroup.MemberService;
import Personal_Project.Calling_Diary.model.Member;
import Personal_Project.Calling_Diary.repository.MemberRepository;
import Personal_Project.Calling_Diary.xss.XssUtil;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
@RequestMapping("/patterns")
@RequiredArgsConstructor
public class PatternController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @ResponseBody
    @PostMapping("/userid")
    public boolean checkIdPattern(@RequestBody String httpBody){

        JSONObject jsonObject = new JSONObject(httpBody);
        String userid = jsonObject.getString("userid");

        boolean checkStatus = memberService.checkIdPattern(userid);
        return checkStatus;
    }

    @ResponseBody
    @PostMapping("/reduplication/userid")
    @Transactional
    public String checkIdOverlap(@RequestBody String userid){

        userid = XssUtil.cleanXSS(userid);
        JSONObject jsonObject = new JSONObject(userid);
        Optional<Member> findMember = memberRepository.findById(jsonObject.getString("userid"));
        try{
            // 데이터가 없어 익셉션이 throw 된 경우 중복되는 아이디가 없다는 뜻
            Member member = findMember.orElseThrow(() -> new NoSuchElementException());
            return "impossbleId";
        }
        catch (NoSuchElementException ne){
            return "possbleId";
        }
    }

    @ResponseBody
    @PostMapping("/pwd")
    public boolean checkPwdPattern(@RequestBody String httpBody){

        JSONObject jsonObject = new JSONObject(httpBody);
        String pwd = jsonObject.getString("passwd");

        boolean checkStatus = memberService.checkPwdPattern(pwd);
        return checkStatus;
    }

    @ResponseBody
    @PostMapping("/nickname")
    public boolean checkNicknamePattern(@RequestBody String httpBody){

        JSONObject jsonObject = new JSONObject(httpBody);
        String nickname = jsonObject.getString("nickname");

        boolean checkStatus = memberService.checkNicknamePattern(nickname);
        return checkStatus;
    }

    @ResponseBody
    @PostMapping("/phonenumber")
    public boolean checkPhoneNumberPattern(@RequestBody String httpBody, RedirectAttributes redirectAttributes){

        JSONObject jsonObject = new JSONObject(httpBody);
        String phoneNumber = jsonObject.getString("phoneNumber");

        boolean checkStatus = memberService.checkPhoneNumberPattern(phoneNumber);
        return checkStatus;
    }

    @ResponseBody
    @PostMapping("/reduplication/phonenumber")
    @Transactional
    public String checkPhoneNumberOverlap(@RequestBody String httpBody){

        JSONObject jsonObject = new JSONObject(httpBody);
        String phoneNumber = jsonObject.getString("phoneNumber");
        phoneNumber = XssUtil.cleanXSS(phoneNumber);
        String overlap = "";
        Optional<Member> byPNumber = memberRepository.findByPNumber(phoneNumber);

        try{
            Member member = byPNumber.orElseThrow(() -> new NoSuchElementException());
            if (member.getPhonenumber().equals(phoneNumber)){
                // 전화번호가 중복된다는 뜻의 true
                overlap = "true";
            }
        }
        catch (NoSuchElementException ne){
            // 전화번호가 중복되지 않는다는 뜻의 false
            overlap = "false";
        }

        return overlap;
    }
}
