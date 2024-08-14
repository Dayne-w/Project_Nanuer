package server.nanuer_server.domain.listener;

import java.time.LocalDateTime;
// createdAt과 updatedAt을 설정해줄 때 사용하는 인터페이스
public interface Auditable {
    LocalDateTime getCreatedAt();
    LocalDateTime getUpdatedAt();

    void setCreatedAt(LocalDateTime createdAt);
    void setUpdatedAt(LocalDateTime updatedAt);
}
