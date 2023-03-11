package borad.HIP.model;

import borad.HIP.domain.AlarmArgs;
import borad.HIP.domain.AlarmEntity;
import borad.HIP.domain.AlarmType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.java.Log;

import java.sql.Timestamp;
@Getter
@AllArgsConstructor
public class Alarm {

    private Long id;
    private AlarmType alarmType;
//    private AlarmArgs args;
    private Timestamp registeredAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;

    public static Alarm fromEntity(AlarmEntity entity){
        return new Alarm(
                entity.getId(),
                entity.getAlarmType(),
//                entity.getArgs(),
                entity.getRegisteredAt(),
                entity.getUpdatedAt(),
                entity.getDeletedAt()
        );
    }
}
