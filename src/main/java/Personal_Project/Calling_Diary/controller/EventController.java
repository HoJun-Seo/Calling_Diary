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
        System.out.println("요청 접수 확인");
        return "redirect:/calendarPage";
    }
}
