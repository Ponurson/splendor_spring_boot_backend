package pl.coderslab.splendor_angular_connection.user;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.coderslab.splendor_angular_connection.auth.CurrentUser;
import pl.coderslab.splendor_angular_connection.auth.Role;
import pl.coderslab.splendor_angular_connection.auth.RoleRepository;
import pl.coderslab.splendor_angular_connection.game.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final GameStateRepository gameStateRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final PlayerRepository playerRepository;
    private final GameService gameService;

    @Override
    public User findByUserName(String username) {
        return userRepository.findByUsername(username).get();
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(1);
        user.setUserState("logged_out");
        Role userRole = roleRepository.findByName("ROLE_USER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(user);
    }

    @Override
    public void startChallenge(Map<String, Object> invitation, CurrentUser currentUser) {
        List<Long> playerBoard = new ArrayList<>();
        HashMap<String, String> properInvitation = (HashMap<String, String>) invitation.get("invitation");
        Set<String> keySet = properInvitation.keySet();
        for (String key : keySet) {
            Optional<User> player = userRepository.findByUsername(properInvitation.get(key));
            player.map(user -> {
                if ("idle".equals(user.getUserState())) {
                    user.setUserState("challenged");
                    ArrayList<Long> users = new ArrayList<>();
                    users.add(currentUser.getUser().getId());
                    user.setCurrentlyInteractingUsers(users);
                    userRepository.save(user);
                    playerBoard.add(user.getId());
                }
                return null;
            });
        }
        currentUser.getUser().setCurrentlyInteractingUsers(playerBoard);
        changeState(currentUser, "host");
    }


    @Override
    public void changeState(CurrentUser customUser, String newState) {
        User user = customUser.getUser();
        user.setUserState(newState);
        userRepository.save(user);
    }

    @Override
    public void resignFromGame(HashMap<String, Object> data, CurrentUser currentUser) {
        User user1 = currentUser.getUser();
        user1.setCurrentlyInteractingUsers(null);
        user1.setUserState("idle");
        userRepository.save(user1);
    }

    @Override
    public void joinGame(HashMap<String, Object> data, CurrentUser currentUser) {
        User user1 = currentUser.getUser();
//        user1.setCurrentlyInteractingUsers(null);
        user1.setUserState("waiting");
        userRepository.save(user1);
    }

    @Override
    public void checkInvites(User user) {
        List<Long> userList = user.getCurrentlyInteractingUsers();
        List<User> challenged = userList.stream()
                .map(aLong -> userRepository.findById(aLong).get())
                .filter(user1 -> user1.getUserState().equals("challenged") || user1.getUserState().equals("waiting"))
                .collect(Collectors.toList());
//        if (challenged.size() == 0) {
//            user.setUserState("idle");
//            user.setCurrentlyInteractingUsers(null);
//            userRepository.save(user);
//            return;
//        }
        user.setCurrentlyInteractingUsers(challenged.stream().map(User::getId).collect(Collectors.toList()));
        if (challenged.stream().allMatch(user1 -> user1.getUserState().equals("waiting"))) {
            challenged.add(user);
            GameState gameState = gameService.startGame(challenged);
            challenged.stream().forEach(user1 -> {
                        user1.setUserState("playing");
                        user1.setCurrentlyInteractingUsers(null);
                        user1.setGameState(gameState);
                        userRepository.save(user1);
                    }
            );
        }
    }

    @Override
    public void clearPreviousGames(CurrentUser customUser) {
        User user = customUser.getUser();
        GameState gameState = user.getGameState();
        if (gameState != null) {
            List<Player> players = gameState.getPlayers();
            Player player = gameService.getPlayerFromGameState(customUser, gameState);
            if (player != null) {
                user.setGameState(null);
                userRepository.save(user);
                players.remove(player);
                if (players.size()==0){
                    gameStateRepository.delete(gameState);
                }else{
                    gameState.setPlayers(players);
                    gameStateRepository.save(gameState);
                }
                playerRepository.delete(player);
            }
        }
    }
}