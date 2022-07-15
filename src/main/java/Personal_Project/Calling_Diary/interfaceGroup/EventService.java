package Personal_Project.Calling_Diary.interfaceGroup;

import Personal_Project.Calling_Diary.form.EventForm;
import Personal_Project.Calling_Diary.model.Event;
import org.json.JSONArray;

import java.util.List;

public interface EventService {

    public EventForm cleanXssEventForm(EventForm eventForm);

    public String makeEventArray(List<String> findevent);

    public JSONArray makePageArray(String event, int index);

    public String eventInputCheck(Event event) throws Exception;
}
