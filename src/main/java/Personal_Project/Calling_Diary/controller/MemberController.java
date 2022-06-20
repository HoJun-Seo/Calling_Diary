package Personal_Project.Calling_Diary.controller;

import Personal_Project.Calling_Diary.interfaceGroup.MemberService;
import Personal_Project.Calling_Diary.form.FindIdForm;
import Personal_Project.Calling_Diary.form.FindPwdForm;
import Personal_Project.Calling_Diary.form.LoginForm;
import Personal_Project.Calling_Diary.model.Member;
import Personal_Project.Calling_Diary.repository.MemberRepository;
import Personal_Project.Calling_Diary.xss.XssUtil;
import lombok.RequiredArgsConstructor;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    @GetMapping("/checkId_overlap")
    @Transactional
    public String checkIdOverlap(@RequestBody String userid){

        userid = XssUtil.cleanXSS(userid);
        Optional<Member> findMember = memberRepository.findById(userid);
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
    public boolean checkPhoneNumberPattern(@RequestBody String httpBody, RedirectAttributes redirectAttributes){

        JSONObject jsonObject = new JSONObject(httpBody);
        String phoneNumber = jsonObject.getString("phoneNumber");

        boolean checkStatus = memberService.checkPhoneNumberPattern(phoneNumber);
        return checkStatus;
    }

    @ResponseBody
    @PostMapping("/checkPhoneNumber_overlap")
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


    @ResponseBody
    @PostMapping("/check_phoneNumber")
    public String checkPhoneNumber(@RequestBody String httpBody) throws CoolsmsException {

        JSONObject jsonObject = new JSONObject(httpBody);
        String phoneNumber = jsonObject.getString("phoneNumber");
        phoneNumber = XssUtil.cleanXSS(phoneNumber);
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



    // 전화번호 중복 체크 메소드 구현 필요(회원가입시)

    @PostMapping("/findId")
    @Transactional
    public String findId(FindIdForm findIdForm, Model model){

        String pNumber = findIdForm.getPhonenumber();
        pNumber = XssUtil.cleanXSS(pNumber);

        Optional<Member> findmember = memberRepository.findByPNumber(pNumber);


        // 전화번호 입력 과정에서 회원 정보에 등록되어 있지 않은 전화번호를 입력 받을 경우 본인인증 및 아이디 찾기 요청이 수행되지 않는다.
        // 즉, 입력한 전화번호가 회원 정보상에 존재해야만 아이디 찾기 요청이 기능하므로, 컨트롤러로 요청이 넘어왔다는 것은 해당 전화번호로 회원 정보를 조회 시 반드시 정보가 존재한다는 것을 의미한다.
        // 그렇기 때문에 Optional 메소드를 통해 회원 정보를 조회할 때 조회해온 결과값을 orElseThrow() 가 아닌 get() 메소드로 받는다.(반드시 정보가 존재할 것이므로)
        Member member = findmember.get();
        model.addAttribute("userid", member.getUserid());
        return "member/findIdSucessPage";

    }

    @PostMapping("/findPwd")
    @Transactional
    public String findPwd(FindPwdForm findPwdForm, Model model){

        Member member = new Member();
        member.setUserid(findPwdForm.getUserid());
        member.setPhonenumber(findPwdForm.getPhonenumber());
        member = memberService.cleanXssfindPwd(member);

        Optional<Member> findmember = memberRepository.findPwd(member.getUserid(), member.getPhonenumber());

        try {
            member = findmember.orElseThrow(() -> new NoSuchElementException());

            // 비밀번호 찾기에 성공했을 경우 새로운 비밀번호를 설정해주는 창으로 이동
            // 최종적으로 새로운 비밀번호 입력 완료 후 확인 버튼까지 눌러야만 기존 비밀번호 초기화 및 새로운 비밀번호로 업데이트
            model.addAttribute("member", member); // 새로운 비밀번호로 변경시 어떤 회원의 비밀번호가 변경되는지 확인해야 하므로 회원 객체 데이터 자체를 전달 
            return "member/newPasswdSet";
        }
        catch (NoSuchElementException ne){
            // 입력한 정보에 해당하는 회원이 존재하지 않는 경우
            model.addAttribute("finafailAccount", "passwd");
            return "member/findAccountFail";
        }
    }

    @PostMapping("/newPwdSet")
    @Transactional
    public String newPwdSet(Member member){

        member.setPasswd(XssUtil.cleanXSS(member.getPasswd()));
        memberRepository.updatePwd(member.getPasswd(), member.getUserid());

        return "member/findPwdSuccesPage";
    }

    @ResponseBody
    @DeleteMapping("/logout")
    public String logout(){
        try{
            session.removeAttribute("member");
            session.invalidate();
        }catch (IllegalStateException se){
            
            // 이미 시간이 지나 세션이 만료 되었을 경우에도 메인 인덱스 페이지로 리다이렉트
            return "logoutSuccess";
        }
        return "logoutSuccess";
    }
}
