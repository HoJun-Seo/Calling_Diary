package Personal_Project.Calling_Diary.repository;

import Personal_Project.Calling_Diary.model.MemberEvent;
import Personal_Project.Calling_Diary.model.MemberEventId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberEventRepository extends JpaRepository<MemberEvent, MemberEventId> {
}
