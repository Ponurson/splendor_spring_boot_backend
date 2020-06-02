package pl.coderslab.splendor_angular_connection.user;

import pl.coderslab.splendor_angular_connection.auth.CurrentUser;

import java.util.HashMap;
import java.util.Map;

public interface UserService {
 
    User findByUserName(String name);
 
    void saveUser(User user);

    void startChallenge(Map<String,Object> invitation, CurrentUser currentUser);

    boolean changeState(String username, String newState, String stateAllowedForChange);
    void changeState(CurrentUser customUser, String newState);
    void changeState(User user, String newState);

    void resignFromGame(HashMap<String, Object> data, CurrentUser currentUser);

    void joinGame(HashMap<String, Object> data, CurrentUser currentUser);
}