package Personal_Project.Calling_Diary.controller;

import Personal_Project.Calling_Diary.form.EventForm;
import Personal_Project.Calling_Diary.interfaceGroup.EventService;
import Personal_Project.Calling_Diary.model.Event;
import Personal_Project.Calling_Diary.model.Member;
import Personal_Project.Calling_Diary.repository.EventRepository;
import Personal_Project.Calling_Diary.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {

    private final EventRepository eventRepository;
    private final EventService eventService;
    private final MemberRepository memberRepository;

    @ResponseBody
    @GetMapping("/{uid}")
    public String callEvent(@PathVariable("uid") String uid){

        Optional<Member> findMember = memberRepository.findByUid(uid);
        try{
            Member member = findMember.orElseThrow(() -> new IllegalStateException());

            Optional<List<String>> findeventList = eventRepository.findEventByJSONArray(member.getUserid());
            List<String> findevent = findeventList.orElseThrow(() -> new NoSuchElementException());

            String eventArrayStr = eventService.makeEventArray(findevent);

            return eventArrayStr;
        }
        catch (IllegalStateException ie){
            return "sessionNotExist";
        }
        catch (NoSuchElementException ne){
            return "eventNotExist";
        }
    }

    @PostMapping("/{uid}/creation")
    public String createEvent(@PathVariable("uid")String uid, EventForm eventForm){

        Optional<Member> findMember = memberRepository.findByUid(uid);

        try {
            Member member = findMember.orElseThrow(() -> new IllegalStateException());
            eventForm = eventService.cleanXssEventForm(eventForm);

            Event event = new Event();
            event.setMember(member);
            event.setTitle(eventForm.getTitle());
            event.setStart(eventForm.getStartDate());
            event.setEnd(eventForm.getEndDate());
            event.setEventdesc(eventForm.getEventDesc());

            eventRepository.save(event);
        }
        catch (IllegalStateException ie){
            return "eventCreateFail";
        }
        return "redirect:/calendarPage";
    }
}
