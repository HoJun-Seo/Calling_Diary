package Personal_Project.Calling_Diary.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id
    @Column(name = "userid")
    private String userid;

    @Column(name = "passwd")
    private String passwd;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "phonenumber")
    private String phonenumber;

    @Override
    public String toString() {
        return "Member : { userid : " + this.userid + ", passwd : " + this.passwd + ", nickname : " + this.nickname + ", phoneNumber : " + this.phonenumber + " }";
    }
}
