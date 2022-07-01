package Personal_Project.Calling_Diary.controller;

import Personal_Project.Calling_Diary.form.EventForm;
import Personal_Project.Calling_Diary.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {

    private final EventRepository eventRepository;

    @PostMapping("/{uid}/creation")
    public String createEvent(@PathVariable("uid")String uid, EventForm eventForm){
        System.out.println();
        System.out.println("==============================");
        System.out.println("요청 접수 확인");
        System.out.println("이벤트 제목 : " + eventForm.getTitle());
        System.out.println("이벤트 시작일 : " + eventForm.getStartDate());
        System.out.println("이벤트 종료일 : " + eventForm.getEndDate());
        System.out.println("이벤트 설명 : " + eventForm.getEventDesc());
        System.out.println("==============================");
        System.out.println();
        return "redirect:/calendarPage";
    }
}
