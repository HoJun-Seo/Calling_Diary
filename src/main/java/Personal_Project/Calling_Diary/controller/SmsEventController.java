package Personal_Project.Calling_Diary.controller;

import Personal_Project.Calling_Diary.interfaceGroup.SmsEventService;
import Personal_Project.Calling_Diary.model.Event;
import Personal_Project.Calling_Diary.model.Member;
import Personal_Project.Calling_Diary.model.SmsEvent;
import Personal_Project.Calling_Diary.model.SmsEventId;
import Personal_Project.Calling_Diary.repository.EventRepository;
import Personal_Project.Calling_Diary.repository.MemberRepository;
import Personal_Project.Calling_Diary.repository.SmsEventRepository;
import Personal_Project.Calling_Diary.xss.XssUtil;
import lombok.RequiredArgsConstructor;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;

@Controller
@RequiredArgsConstructor
@RequestMapping("/sms")
public class SmsEventController {

    private final MemberRepository memberRepository;
    private final SmsEventRepository smsEventRepository;
    private final EventRepository eventRepository;

    private final SmsEventService smsEventService;

    @PostMapping("/{uid}")
    @ResponseBody
    @Transactional
    public String smsRegister(@PathVariable("uid") String uid, @RequestBody String httpbody){

        Optional<Member> findMember = memberRepository.findByUid(uid);
        try{
            Member member = findMember.orElseThrow(() -> new IllegalStateException());

            JSONObject jsonObject = new JSONObject(httpbody);

            Optional<Event> findevent = eventRepository.findById(Long.parseLong(jsonObject.getString("eventid")));
            Event event = findevent.orElseThrow(() -> new NoSuchElementException());

            // 일단 coolsms 로 문자 예약 발송신청을 한 다음 반환값으로 smsEvent 객체를 만든다.
            // 문자 발송은 서비스에서 수행한다.
            SmsEvent smsEvent = new SmsEvent();
            smsEvent.setEvent(event);
            smsEvent.setStart(event.getStart().replace("-", ""));
            smsEvent.setReservationTime(jsonObject.getString("reservationTime").replace(":", "") + "00");
            smsEvent.setPhonenumber(member.getPhonenumber());
            smsEvent.setMessageText(XssUtil.cleanXSS(jsonObject.getString("messageText")));
            smsEvent = smsEventService.smsReservation(smsEvent);

            smsEventRepository.save(smsEvent);

        }
        catch (IllegalStateException ie){
            return "sessionNotExist";
        }
        catch (NoSuchElementException ne){
            return "eventNotExist";
        }
        catch (CoolsmsException ce) {
            return "reservationError";
        }
        return "registerSuccess";
    }

    @GetMapping("/{uid}/{eventid}")
    @Transactional
    @ResponseBody
    public String selectSmsEvent(@PathVariable("uid") String uid, @PathVariable("eventid") String eventid){

        Optional<Member> findMember = memberRepository.findByUid(uid);
        try {

            // 세션 여부 확인
            Member member = findMember.orElseThrow(() -> new IllegalStateException());

            SmsEventId smsEventId = new SmsEventId();
            smsEventId.setEvent(Long.parseLong(eventid));
            smsEventId.setPhonenumber(member.getPhonenumber());
            Optional<SmsEvent> findSmsEvent = smsEventRepository.findById(smsEventId);
            SmsEvent smsEvent = findSmsEvent.orElseThrow(() -> new NoSuchElementException());

            JSONObject smsObject = new JSONObject();

            smsObject.put("eventid", smsEvent.getEvent().getEventid());
            smsObject.put("groupid", smsEvent.getGroupid());
            smsObject.put("phonenumber", smsEvent.getPhonenumber());
            smsObject.put("start", smsEvent.getStart());
            smsObject.put("reservationTime", smsEvent.getReservationTime());
            smsObject.put("messageText", smsEvent.getMessageText());

            return smsObject.toString();
        }
        catch (IllegalStateException ie){
            return "sessionNotExist";
        }
        catch (NoSuchElementException ne){
            return "smsEventNotExist";
        }
    }

    @DeleteMapping("/{eventid}")
    @Transactional
    @ResponseBody
    public String deleteSmsEvent(@PathVariable("eventid") String eventid, @RequestBody String httpbody){

        JSONObject jsonObject = new JSONObject(httpbody);

        SmsEventId smsEventId = new SmsEventId();
        smsEventId.setEvent(Long.parseLong(eventid));
        smsEventId.setPhonenumber(jsonObject.getString("phonenumber"));

        Optional<SmsEvent> findSmsEvent = smsEventRepository.findById(smsEventId);

        try{

            SmsEvent smsEvent = findSmsEvent.orElseThrow(() -> new NoSuchElementException());

            String deleteResult = smsEventService.cancel(smsEvent);

            if (deleteResult.equals("cancelSuccess")){
                smsEventRepository.delete(smsEvent);

                Optional<Event> findEvent = eventRepository.findById(Long.parseLong(eventid));
                Event event = findEvent.orElseThrow(() -> new NoSuchElementException());

                event.setSms(null);
                eventRepository.save(event);
            }

            return "cancelSuccess";
        }
        catch (NoSuchElementException ne){
            return "smsEventNotExist";
        }
        catch (CoolsmsException e) {
            return "cancelError";
        }
    }
}
