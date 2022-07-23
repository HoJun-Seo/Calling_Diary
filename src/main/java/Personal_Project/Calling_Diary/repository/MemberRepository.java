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
    @Query("update Member m set m.passwd= :passwd where m.uid= :uid")
    public void updatePwd(@Param("passwd") String passwd, @Param("uid") String uid);

    @Query("select m.memberdesc from Member m where m.userid= :userid")
    public Optional<String> findDesc(@Param("userid") String userid);

    @Modifying
    @Query("update Member m set m.memberdesc= :memberdesc where m.uid= :uid")
    public void update(@Param("memberdesc")String memberdesc, @Param("uid")String uid);

    @Modifying
    @Query("update Member m set m.userid= :userid where m.userid= :curUID")
    public void updateId(@Param("userid")String userid, @Param("curUID")String curUID);

    @Modifying
    @Query("update Member m set m.nickname= :nickname where m.userid= :userid")
    public void updateNickname(@Param("nickname")String nickname, @Param("userid")String userid);

    @Modifying
    @Query("update Member m set m.phonenumber= :phonenumber where m.userid= :userid")
    public void updatePhonenumber(@Param("phonenumber")String phonenumber, @Param("userid") String userid);

    @Query("select m from Member m where m.uid= :uid")
    public Optional<Member> findByUid(@Param("uid") String uid);

}
