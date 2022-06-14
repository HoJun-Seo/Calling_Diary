package Personal_Project.Calling_Diary.controller;

import Personal_Project.Calling_Diary.interfaceGroup.MemberService;
import Personal_Project.Calling_Diary.model.FindIdForm;
import Personal_Project.Calling_Diary.model.FindPwdForm;
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


        Member member = new Member();
        member.setUserid(loginForm.getUserid());
        member.setPasswd(loginForm.getPasswd());
        // 입력 데이터를 받을 경우 반드시 XSS 방지 메소드를 거칠 것!
        member = memberService.cleanXssLogin(member);
        // 계정이 존재하지는지 확인하고 조건에 따라 다르게 처리
        Optional<Member> findMember = memberRepository.findById(loginForm.getUserid());
        
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

    @PostMapping("/findId")
    @Transactional
    public String findId(FindIdForm findIdForm, Model model){

        String pNumber = findIdForm.getPhonenumber();
        Member member = memberRepository.findByPNumber(pNumber);
        
        // 해당하는 전화번호를 가지고 있는 회원이 존재하지 않을 경우
        if (member == null){
            model.addAttribute("finafailAccount", "userid");
            return "member/findAccountFail";
        }
        // 전화번호를 이용해 회원 검색에 성공했을 경우
        else{
            model.addAttribute("userid", member.getUserid());
            return "member/findIdSucessPage";
        }
    }

    @PostMapping("/findPwd")
    @Transactional
    public String findPwd(FindPwdForm findPwdForm, Model model){

        String nickname = findPwdForm.getNickname();
        String pNumber = findPwdForm.getPhonenumber();
        Member member = memberRepository.findBy_NickName_pNumber(nickname,pNumber);
        if (member == null){
            model.addAttribute("finafailAccount", "passwd");
            return "member/findAccountail";
        }
        else{
            // 비밀번호 찾기에 성공했을 경우 새로운 비밀번호를 설정해주는 창으로 이동
            // 최종적으로 새로운 비밀번호 입력 완료 후 확인 버튼까지 눌러야만 기존 비밀번호 초기화 및 새로운 비밀번호로 업데이트
            model.addAttribute("member", member);
            return "member/newPasswdSet";
        }
    }
}
