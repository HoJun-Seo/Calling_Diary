package Personal_Project.Calling_Diary.interfaceGroup;

import Personal_Project.Calling_Diary.form.EventForm;
import Personal_Project.Calling_Diary.model.Event;

public interface EventService {

    public EventForm cleanXssEventForm(EventForm eventForm);
}
