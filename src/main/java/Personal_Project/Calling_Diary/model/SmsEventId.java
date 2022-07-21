package Personal_Project.Calling_Diary.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SmsEventId implements Serializable {

    private Long event;
    private String phonenumber;
}
