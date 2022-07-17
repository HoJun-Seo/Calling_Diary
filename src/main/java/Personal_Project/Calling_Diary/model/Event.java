package Personal_Project.Calling_Diary.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eventid")
    private Long eventid;

    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "userid")
    private Member member;

    @Column(name = "title")
    private String title;

    @Column(name = "start")
    private String start;

    @Column(name = "end")
    private String end;

    @Column(name = "eventdesc")
    private String eventdesc;

    @Column(name = "favoritestatus")
    private String favoritestatus;

    @Column(name = "textColor")
    private String textColor;

    @Column(name = "color")
    private String color;

    @Column(name = "sms")
    private String sms;

    @Override
    public String toString() {
        return "{ eventid : " + this.eventid + ", userid : " + this.member.getUserid() +
                ", title : " + this.title + ", start : " + this.start + ", end : " + this.end +
                ", eventdesc : " + this.eventdesc + ", favoritestatus : " + this.favoritestatus + ", sms : " + this.sms +" }";
    }
}
