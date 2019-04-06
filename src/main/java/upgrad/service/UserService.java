package upgrad.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upgrad.model.User;
import upgrad.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;
    public User login(User user){
        User existingUser = repository.checkCredentials(user.getUsername(),user.getPassword());
        if(existingUser != null){
            return existingUser;
        }else {
            return null;
        }
    }

    public void registerUser(User user){
        repository.registerUser(user);
    }

}
