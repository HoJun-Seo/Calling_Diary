package Personal_Project.Calling_Diary.repository;

import Personal_Project.Calling_Diary.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {
}
