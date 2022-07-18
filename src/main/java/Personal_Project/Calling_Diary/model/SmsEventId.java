package Personal_Project.Calling_Diary.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class SmsEventId implements Serializable {

    private Event event;
    private String groupid;
}
