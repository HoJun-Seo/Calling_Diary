package Personal_Project.Calling_Diary.controller;

import Personal_Project.Calling_Diary.interfaceGroup.MemberService;
import Personal_Project.Calling_Diary.model.LoginForm;
import Personal_Project.Calling_Diary.model.Member;
import Personal_Project.Calling_Diary.repository.MemberRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    private HttpSession session;

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
    @Transactional
    public String memberRegister(Member member){

        // XSS 스크립트 입력 방지 실행
        member = memberService.cleanXssRegister(member);
        memberRepository.save(member);

        return "member/registerComplete";
    }

    @PostMapping("/login")
    @Transactional
    public String login(LoginForm loginForm, HttpServletRequest request, Model model){
        
        // 계정이 존재하지는지 확인하고 조건에 따라 다르게 처리
        Optional<Member> findMember = memberRepository.findById(loginForm.getUserid());
        Member member = null;
        
        try{

            member = findMember.orElseThrow(() -> new NoSuchElementException());

            if (loginForm.getPasswd().equals(member.getPasswd())){
                // 로그인 성공, 세션 생성 후 메인 페이지로 이동
                session = request.getSession();
                session.setAttribute("member", member);
                return "redirect:/";
            }
            else{
                // 로그인 실패, 비밀번호가 맞지 않음
                model.addAttribute("loginFail", "passwdNotCorrect");
                return "member/loginFail";
            }
        }
        catch (NoSuchElementException ne){
            model.addAttribute("loginFail", "notExistAccount");
            System.out.println("존재하지 않는 계정입니다.");
            return "member/loginFail";
        }
    }

    @ResponseBody
    @GetMapping("/findById_overlap")
    @Transactional
    public String findByIdOverlap(@RequestParam String userid){

        Optional<Member> findMember = memberRepository.findById(userid);
        Member member = findMember.orElse(new Member());
        String result = member.getUserid();
        return result;
    }
}
