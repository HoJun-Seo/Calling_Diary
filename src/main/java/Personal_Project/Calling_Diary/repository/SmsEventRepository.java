package Personal_Project.Calling_Diary.repository;

import Personal_Project.Calling_Diary.model.SmsEvent;
import Personal_Project.Calling_Diary.model.SmsEventId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmsEventRepository extends JpaRepository<SmsEvent, SmsEventId> {
}
