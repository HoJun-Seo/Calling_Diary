package Personal_Project.Calling_Diary.interfaceGroup;

import Personal_Project.Calling_Diary.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberDAO extends JpaRepository<Member, String> {
}
