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
import org.springframework.ui.Model;
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

            // 즐겨 찾기가 체크되어 있을경우 리턴 : on
            // 즐겨 찾기가 체크되어 있지않을 경우 리턴 : null
            Event event = new Event();
            event.setMember(member);
            event.setTitle(eventForm.getTitle());
            event.setStart(eventForm.getStartDate());
            event.setEnd(eventForm.getEndDate());
            event.setEventdesc(eventForm.getEventDesc());
            event.setFavoritestatus(eventForm.getFavorite());

            if (event.getFavoritestatus() != null) {
                if (event.getFavoritestatus().equals("on")) {
                    event.setColor("#008000");
                    event.setTextColor("#FFFF00");
                }
            }
            else{
                event.setColor("#0000FF");
                event.setTextColor("#FFFFFF");
            }

            eventRepository.save(event);
        }
        catch (IllegalStateException ie){
            return "eventCreateFail";
        }
        return "redirect:/calendarPage";
    }

    @ResponseBody
    @DeleteMapping("/{uid}/{eventid}")
    public String eventDelete(@PathVariable("uid") String uid, @PathVariable("eventid") String eventid){

        try{

            Optional<Member> findmember = memberRepository.findByUid(uid);
            Member member = findmember.orElseThrow(() -> new IllegalStateException());

            Long eventKey = Long.parseLong(eventid);
            Optional<Event> findEvent = eventRepository.findById(eventKey);
            Event event = findEvent.orElseThrow(() -> new NoSuchElementException());

            if (event.getMember().getUserid().equals(member.getUserid())){
                eventRepository.delete(event);
            }
        }
        catch (IllegalStateException ie){
            return "sessionNotExist";
        }
        catch (NoSuchElementException ne){
            return "eventNotExist";
        }
        return "deleteSuccess";
    }

    @PostMapping("/pagination")
    public String pagination(@RequestBody String httpbody, Model model){

        JSONObject jsonObject = new JSONObject(httpbody);
        String event = jsonObject.getString("event");
        int index = jsonObject.getInt("index");

        JSONArray jsonArray = new JSONArray(event);
        JSONArray pageArray = new JSONArray();

        // 배열의 길이에 따른 페이징은 이미 되어있으므로 인덱스 값을 활용하여 배열을 새로 만들어준 뒤 html 파일에 넘겨주는 것만 구현한다.
        //첫 페이지, 또는 중간 페이지인 경우
        if(index*5 < jsonArray.length()){
            for (int i = jsonArray.length()-((index*5)-4); i >= jsonArray.length()-(index*5); i--){
                pageArray.put(jsonArray.get(i));
            }
        }
        // 마지막 페이지인 경우
        else{
            System.out.println("마지막 페이지");
            for (int i = jsonArray.length()-((index*5)-4); i >= 0; i--){
                pageArray.put(jsonArray.get(i));
            }
        }

        String pagination = pageArray.toString();
        pagination = pagination.replace("\"", "\\\"");
        model.addAttribute("pageArray", pagination);

        return "calendar/eventPagination";
    }
}
