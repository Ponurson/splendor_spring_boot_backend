package pl.coderslab.splendor_angular_connection.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.coderslab.splendor_angular_connection.auth.CurrentUser;
import pl.coderslab.splendor_angular_connection.auth.Role;
import pl.coderslab.splendor_angular_connection.auth.RoleRepository;
import pl.coderslab.splendor_angular_connection.utils.Utils;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final Utils utils;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           BCryptPasswordEncoder passwordEncoder, Utils utils) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.utils = utils;
    }

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
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, String> newStateMap = new HashMap<>();
        newStateMap.put("state","challenged");
        newStateMap.put("challenger", currentUser.getUsername());
        String newState = utils.stringify(newStateMap);
        HashMap<String, String> playerBoard = new HashMap<>();
        HashMap<String, String> properInvitation = (HashMap<String, String>) invitation.get("invitation");
        Set<String> keySet = properInvitation.keySet();
        for (String key : keySet) {
            String player = properInvitation.get(key);
            if (player.length() > 0) {
                boolean isPlayerChallenged = changeState(player, newState, "idle");
                if (isPlayerChallenged) {
                    playerBoard.put(player, "false");
                }
            }
        }
        changeState(currentUser, utils.stringify(playerBoard));
    }


    @Override
    public boolean changeState(String username, String newState, String stateAllowedForChange) {
        Optional<User> user = userRepository.findByUsername(username);
        Optional<Boolean> booleanOptional = user.map(user1 -> {
            if (utils.jsonify(user1.getUserState())
                    .get("state")
                    .equals(stateAllowedForChange)) {
                user1.setUserState(newState);
                userRepository.save(user1);
                return true;
            } else {
                return false;
            }
        });
        if (booleanOptional.isEmpty() || booleanOptional.get() == false) {
            return false;
        } else {
            return true;
        }

    }

    @Override
    public void changeState(CurrentUser customUser, String newState) {
        User user = customUser.getUser();
        user.setUserState(newState);
        userRepository.save(user);
    }

    @Override
    public void changeState(User user, String newState) {
        user.setUserState(newState);
        userRepository.save(user);
    }

    @Override
    public void resignFromGame(HashMap<String, Object> data, CurrentUser currentUser) {
        String challenger = (String) data.get("challenger");
        User user = findByUserName(challenger);
        Map<String, String> userState = utils.jsonify(user.getUserState());
        userState.remove(currentUser.getUsername());
        changeState(user,utils.stringify(userState));
        HashMap<String, String> newStateMap = new HashMap<>();
        newStateMap.put("state","idle");
        //racing niestety może być
        changeState(currentUser,utils.stringify(newStateMap));

    }

    @Override
    public void joinGame(HashMap<String, Object> data, CurrentUser currentUser) {
        String challenger = (String) data.get("challenger");
        User user = findByUserName(challenger);
        Map<String, String> userState = utils.jsonify(user.getUserState());
        userState.put(currentUser.getUsername(), "true");
        //racing niestety może być
        changeState(user,utils.stringify(userState));
    }


}