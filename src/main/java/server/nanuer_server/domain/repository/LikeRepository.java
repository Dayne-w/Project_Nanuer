package server.nanuer_server.domain.repository;

import server.nanuer_server.domain.entity.HeartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<HeartEntity,Long> {
}
