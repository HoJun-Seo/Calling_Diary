package Personal_Project.Calling_Diary.repository;

import Personal_Project.Calling_Diary.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, String> {
    @Query("select m from Member m where m.phonenumber= :phonenumber")
    public Member findByPNumber(@Param("phonenumber") String pNumber);

    @Query("select m from Member m where m.nickname= :nickname and m.phonenumber= :phonenumber")
    public Member findBy_NickName_pNumber(@Param("nickname")String nickname, @Param("phonenumber")String pNumber);
}
