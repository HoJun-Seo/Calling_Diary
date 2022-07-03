package Personal_Project.Calling_Diary.service;

import Personal_Project.Calling_Diary.form.EventForm;
import Personal_Project.Calling_Diary.interfaceGroup.EventService;
import Personal_Project.Calling_Diary.model.Event;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
}
