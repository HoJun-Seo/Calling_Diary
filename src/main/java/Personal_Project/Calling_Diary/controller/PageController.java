package Personal_Project.Calling_Diary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/move")
public class PageController {
    
    @GetMapping("/login")
    public String moveLogin(){
        return "member/login";
    }

    @GetMapping("/registerTerm")
    public String openRegisterTerm(){
        return "member/registerTerm";
    }

    @GetMapping("/registerForm")
    public String moveRegisterForm(){
        return "member/registerForm";
    }

    @GetMapping("/findIdForm")
    public String moveFindIdForm(){
        return "member/findIdForm";
    }

    @GetMapping("/findPwdForm")
    public String moveFindPwdForm(){
        return "member/findPwdForm";
    }

    @GetMapping("/findPwdSuccesPage")
    public String findPwdSuccesPage(){
        return "member/findPwdSuccesPage";
    }

    @GetMapping("/mypage")
    public String moveMypage(){
        return "member/mypage";
    }

    @GetMapping("/updateMemberDataPage")
    public String updateMemberDataPage(){
        return "member/updateMemberDataPage";
    }

    @GetMapping("/uidChangePage")
    public String moveUidChange(){
        return "member/uidChangePage";
    }

    @GetMapping("/pwdChangePage")
    public String pwdChangePage(){
        return "member/pwdChangePage";
    }

    @GetMapping("/nicknameChangePage")
    public String nicknameChangePage(){
        return "member/nicknameChangePage";
    }
}
