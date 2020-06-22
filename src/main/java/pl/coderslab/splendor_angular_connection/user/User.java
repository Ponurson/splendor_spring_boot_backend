package pl.coderslab.splendor_angular_connection.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.coderslab.splendor_angular_connection.auth.Role;
import pl.coderslab.splendor_angular_connection.game.GameState;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(nullable = false, unique = true, length = 60, name = "user_name")
    private String username;
    private String password;
    private int enabled;
    @Column(name = "user_state")
    private String userState;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
                    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Long> currentlyInteractingUsers;
    @ManyToOne
    private GameState gameState;
    private LocalDateTime lastOnline;
}