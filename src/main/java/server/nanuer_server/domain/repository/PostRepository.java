package server.nanuer_server.domain.repository;

import server.nanuer_server.domain.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Integer> {
    @Query(value = "select p " +
            "from PostEntity p " +
            "left join UserEntity u on p.userEntity.userId =  u.userId " +
            "where u.university = (" +
            "select u.university from u where u.userId = :user_id) " +
            "and p.postStatus = 1 and (p.title like %:query% or p.content like %:query%) order by p.postId desc")
    List<PostEntity> findAll(@Param("user_id") int user_id, @Param("query") String query);

    @Query(value = "select p from PostEntity p where p.postStatus = 1 order by p.postId desc")
    List<PostEntity> findAllOrderByPostIdDesc();

    @Query("select p from PostEntity p where p.postId = :postId")
    PostEntity findByPostId(@Param("postId") int postId);

    @Query("select p from PostEntity p where p.postId in (" +
            "select h.postEntity.postId from HeartEntity h where h.userEntity.userId = :userId)")
    List<PostEntity> findHeartByUserId(@Param("userId") int user_id);

//    @Modifying(clearAutomatically = true)
//    @Query("update PostEntity p set p.progress = 'End' where p.postId = :postId")
//    void updateProgress(@Param("postId") int postId);
}