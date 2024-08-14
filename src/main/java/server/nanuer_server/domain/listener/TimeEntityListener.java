package server.nanuer_server.domain.listener;


import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

//
public class TimeEntityListener {
    @PrePersist // DB에 해당 테이블을 insert 연산을 실행할 때, 같이 실행된다.
    public void setCreatedAtAndUpdatedAt(Object o){
        if(o instanceof Auditable){
            ((Auditable) o).setCreatedAt(LocalDateTime.now());
            ((Auditable) o).setUpdatedAt(LocalDateTime.now());
        }
    }

    @PreUpdate // DB에 해당 테이블 update 연산이 실행될 때, 같이 실행된다.
    public void preUpdate(Object o){
        if(o instanceof Auditable){
            ((Auditable) o).setUpdatedAt(LocalDateTime.now());
        }
    }
}
