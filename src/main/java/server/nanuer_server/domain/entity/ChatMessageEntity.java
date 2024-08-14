package server.nanuer_server.domain.entity;

import lombok.*;


import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Setter
public class ChatMessageEntity {
    public enum Type {
        ENTER, TALK, QUIT
    }
    private Type type;
    private int sender;
    private int roomId;
    private String data;
    private String profileImg;
    private String nickName;
    private List<Integer> userIdList;


    public void setSender(int sender) {this.sender = sender;}
    public void setData(String data){
        this.data=data;
    }


}
