package pl.coderslab.splendor_angular_connection.game;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NobleRepository extends JpaRepository<Noble, Long> {
}
