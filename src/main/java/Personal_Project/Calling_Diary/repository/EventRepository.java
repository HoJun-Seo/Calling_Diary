package Personal_Project.Calling_Diary.repository;

import Personal_Project.Calling_Diary.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
