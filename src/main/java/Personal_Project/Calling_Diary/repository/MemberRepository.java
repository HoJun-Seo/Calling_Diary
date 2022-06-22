package Personal_Project.Calling_Diary.repository;

import Personal_Project.Calling_Diary.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {
    @Query("select m from Member m where m.phonenumber= :phonenumber")
    public Optional<Member> findByPNumber(@Param("phonenumber") String pNumber);

    @Query("select m from Member m where m.userid= :userid and m.phonenumber= :phonenumber")
    public Optional<Member> findPwd(@Param("userid")String userid, @Param("phonenumber")String pNumber);

    @Modifying
    @Query("update Member m set m.passwd= :passwd where m.userid= :userid")
    public void updatePwd(@Param("passwd") String passwd, @Param("userid") String userid);

    @Query("select m.memberdesc from Member m where m.userid= :userid")
    public Optional<String> findDesc(@Param("userid") String userid);

    @Modifying
    @Query("update Member m set m.memberdesc= :memberdesc where m.userid= :userid")
    void update(@Param("memberdesc")String memberdesc, @Param("userid")String userid);
}
