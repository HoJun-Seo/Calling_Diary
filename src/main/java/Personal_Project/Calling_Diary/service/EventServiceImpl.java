package Personal_Project.Calling_Diary.service;

import Personal_Project.Calling_Diary.form.EventForm;
import Personal_Project.Calling_Diary.interfaceGroup.EventService;
import Personal_Project.Calling_Diary.model.Event;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EventServiceImpl implements EventService {

    @Override
    public EventForm cleanXssEventForm(EventForm eventForm) {

        eventForm.setTitle(eventForm.getTitle());
        eventForm.setStartDate(eventForm.getStartDate());
        eventForm.setEndDate(eventForm.getEndDate());
        eventForm.setEventDesc(eventForm.getEventDesc());

        return eventForm;
    }

    @Override
    public String makeEventArray(List<String> findevent) {

        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < findevent.size(); i++){
            JSONObject eventObject = new JSONObject(findevent.get(i));
            jsonArray.put(eventObject);
        }
        return jsonArray.toString();
    }

    @Override
    public JSONArray makePageArray(String event, int index) {

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
            for (int i = jsonArray.length()-((index*5)-4); i >= 0; i--){
                pageArray.put(jsonArray.get(i));
            }
        }
        return pageArray;
    }

    @Override
    public String eventInputCheck(Event event) throws Exception {
        
        // 시작일, 종료일, 시작일-종료일간 비교, 제목, 설명에 대한 입력 오류여부 검증

        if (event.getTitle().equals("") || event.getEventdesc().equals(""))
            throw new Exception("textInputError");

        if (event.getStart().equals("") || event.getEnd().equals("")){
            throw new Exception("dateinputError");
        }
        // 날짜 입력은 정상적이나 기간이 정상적이지 않을 경우
        else{
            String[] startArray = event.getStart().split("-");
            String[] endArray = event.getEnd().split("-");

            LocalDate start = LocalDate.of(Integer.parseInt(startArray[0]), Integer.parseInt(startArray[1]), Integer.parseInt(startArray[2]));
            LocalDate end = LocalDate.of(Integer.parseInt(endArray[0]), Integer.parseInt(endArray[1]), Integer.parseInt(endArray[2]));

            if (!end.isAfter(start) && !end.isEqual(start))
                throw new Exception("dateinputError");
        }
        return "inputCheckSuccess";
    }
}
