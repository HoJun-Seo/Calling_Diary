package Personal_Project.Calling_Diary.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Member {

    @Id
    private String userid;

    private String passwd;
    private String nickname;
    private String phoneNumber;
}
