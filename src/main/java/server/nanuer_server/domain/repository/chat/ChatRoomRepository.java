package server.nanuer_server.domain.repository.chat;

import server.nanuer_server.domain.entity.ChatRoomEntity;
import server.nanuer_server.domain.entity.PostEntity;
import server.nanuer_server.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.*;

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Integer> {

    @Query("select h from ChatRoomEntity h join fetch h.postEntity where h.postEntity.postId = :postId")
    Optional<ChatRoomEntity> findByPostId(@Param("postId") int postId);

    @Query("select h from ChatRoomEntity h join fetch h.postEntity where h.postEntity.postId = :postId")
    List<ChatRoomEntity> findAllByPostId(@Param("postId") int postId);

    Optional<ChatRoomEntity> findByUserEntityAndIsWriterAndPostEntity(UserEntity userEntity, Boolean isWriter , PostEntity postEntity);

}