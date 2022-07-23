package Personal_Project.Calling_Diary.repository;

import Personal_Project.Calling_Diary.model.SmsEvent;
import Personal_Project.Calling_Diary.model.SmsEventId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SmsEventRepository extends JpaRepository<SmsEvent, SmsEventId> {

}
