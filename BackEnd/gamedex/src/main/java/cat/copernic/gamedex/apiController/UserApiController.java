package cat.copernic.gamedex.apiController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cat.copernic.gamedex.entity.User;
import cat.copernic.gamedex.logic.UserLogic;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserApiController {

    @Autowired
    private UserLogic userLogic;

    @GetMapping("/all/{userId}")
    public List<User> getAllUsersbyUsername(@PathVariable String userId) {
        if (userId.isEmpty() || userId == null) {
            return userLogic.getAllUsers();
        } else {
            return userLogic.getUserByUsername(userId);
        }
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {

        return userLogic.getAllUsers();

    }

    @GetMapping("/byId/{userId}")
    public User getUserById(@PathVariable String userId) {
        return userLogic.getUserById(userId);
    }

    @PutMapping("/update")
    public User updateUser(@RequestBody User user) {
        return userLogic.modifyUser(user);
    }

    @PostMapping("/create")
    public User createUser(@RequestBody User user) {
        return userLogic.createUser(user);
    }

    @DeleteMapping("/delete/{userId}")
    public void deleteUser(@PathVariable String userId) {
        userLogic.deleteUser(userId);
    }
}
