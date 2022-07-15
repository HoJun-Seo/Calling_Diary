package Personal_Project.Calling_Diary.controller;

import Personal_Project.Calling_Diary.form.EventForm;
import Personal_Project.Calling_Diary.interfaceGroup.EventService;
import Personal_Project.Calling_Diary.model.Event;
import Personal_Project.Calling_Diary.model.Member;
import Personal_Project.Calling_Diary.repository.EventRepository;
import Personal_Project.Calling_Diary.repository.MemberRepository;
import Personal_Project.Calling_Diary.xss.XssUtil;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
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
    @Transactional
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
    @Transactional
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
    @Transactional
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

        JSONArray pageArray = eventService.makePageArray(event, index);

        String pagination = pageArray.toString();
        pagination = pagination.replace("\"", "\\\"");
        model.addAttribute("pageArray", pagination);

        return "calendar/eventPagination";
    }

    @PostMapping("/pagination/favorite")
    public String paginationFav(@RequestBody String httpbody, Model model){

        JSONObject jsonObject = new JSONObject(httpbody);
        String event = jsonObject.getString("event");
        int index = jsonObject.getInt("index");

        JSONArray pageArray = eventService.makePageArray(event, index);

        String pagination = pageArray.toString();
        pagination = pagination.replace("\"", "\\\"");
        model.addAttribute("pageArray", pagination);

        return "calendar/eventPaginationFav";
    }

    @GetMapping("/{uid}/favorite")
    @ResponseBody
    @Transactional
    public String selectFavoriteEvent(@PathVariable String uid){

        Optional<Member> findMember = memberRepository.findByUid(uid);
        try{
            Member member = findMember.orElseThrow(() -> new IllegalStateException());

            Optional<List<String>> findeventList = eventRepository.findFavEventByJSONArray(member.getUserid());
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

    @PatchMapping("/{uid}")
    @ResponseBody
    @Transactional
    public String eventUpdate(@PathVariable("uid") String uid, @RequestBody String httpbody){

        Optional<Member> findMember = memberRepository.findByUid(uid);
        try{
            Member member = findMember.orElseThrow(() -> new IllegalStateException());

            JSONObject jsonObject = new JSONObject(httpbody);

            // 서비스에서 입력값 검증
            Event event = new Event();
            event.setMember(member);
            event.setEventid(Long.parseLong(jsonObject.getString("eventid")));
            event.setStart(jsonObject.getString("start"));
            event.setEnd(jsonObject.getString("end"));

            String title = XssUtil.cleanXSS(jsonObject.getString("title"));
            event.setTitle(title);
            String eventdesc = XssUtil.cleanXSS(jsonObject.getString("eventdesc"));
            event.setEventdesc(eventdesc);

            boolean favoriteCheck = jsonObject.getBoolean("favoriteCheck");
            if (favoriteCheck){
                event.setFavoritestatus("on");
                event.setColor("#008000");
                event.setTextColor("#FFFF00");
            }
            else {
                event.setFavoritestatus(null);
                event.setColor("#0000FF");
                event.setTextColor("#FFFFFF");
            }

            String inputCheck = eventService.eventInputCheck(event);
            if (inputCheck.equals("inputCheckSuccess"))
                eventRepository.save(event);

            return "updateSuccess";
        }
        catch (IllegalStateException ie){
            return "sessionNotExist";
        }
        catch (Exception ne){
            if (ne.getMessage().equals("textInputError")){
                return "textInputError";
            }
            else {
                return "dateinputError";
            }
        }
    }
}
