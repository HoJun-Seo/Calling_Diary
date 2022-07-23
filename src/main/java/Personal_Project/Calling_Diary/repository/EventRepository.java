package Personal_Project.Calling_Diary.repository;

import Personal_Project.Calling_Diary.model.Event;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query(value = "select json_object(\"eventid\",eventid, \"title\",title, \"start\",start, \"end\",end, \"eventdesc\",eventdesc, \"favoritestatus\",favoritestatus, \"textColor\",textColor, \"color\",color, \"sms\",sms) from event where userid= :userid", nativeQuery = true)
    public Optional<List<String>> findEventByJSONArray(@Param("userid") String userid);

    @Query(value = "select json_object(\"eventid\",eventid, \"title\",title, \"start\",start, \"end\",end, \"eventdesc\",eventdesc, \"favoritestatus\",favoritestatus, \"textColor\",textColor, \"color\",color, \"sms\",sms) from event where userid= :userid and favoritestatus = \"on\"", nativeQuery = true)
    public Optional<List<String>> findFavEventByJSONArray(@Param("userid") String userid);
}
