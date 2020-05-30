package pl.coderslab.splendor_angular_connection.user;

public interface UserService {
 
    User findByUserName(String name);
 
    void saveUser(User user);
}