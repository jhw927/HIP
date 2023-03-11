package borad.HIP.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlarmArgs {
    //알람을 발생시킨 사람
    private Long fromUserId;
    //게시글이나 댓글등의 아이디
    private Long targetId;

}
