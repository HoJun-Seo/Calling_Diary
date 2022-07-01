package Personal_Project.Calling_Diary.service;

import Personal_Project.Calling_Diary.form.EventForm;
import Personal_Project.Calling_Diary.interfaceGroup.EventService;
import Personal_Project.Calling_Diary.model.Event;
import org.springframework.stereotype.Service;

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
}
