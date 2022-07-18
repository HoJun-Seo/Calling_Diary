package Personal_Project.Calling_Diary.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@IdClass(SmsEventId.class)
@Table(name = "smsevent")
public class SmsEvent implements Serializable {

    @Id
    @ManyToOne(targetEntity = Event.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "eventid")
    private Event event;

    @Id
    @Column(name = "groupid")
    private String groupid;

    @Column(name = "start")
    private String start;

    @Column(name = "reservationTime")
    private String reservationTime;

    @Column(name = "messageText")
    private String messageText;


}
