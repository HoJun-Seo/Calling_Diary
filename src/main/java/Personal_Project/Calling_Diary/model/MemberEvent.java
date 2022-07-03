package Personal_Project.Calling_Diary.model;

import Personal_Project.Calling_Diary.converter.JSONObjectConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONArray;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@IdClass(MemberEventId.class)
public class MemberEvent implements Serializable {

    @Id
    @OneToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "userid")
    private Member member;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberEventid;

    @Column(name = "eventArray")
    @Convert(converter = JSONObjectConverter.class)
    private JSONArray eventArray;
}
