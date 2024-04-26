package noemi.genshin_world.services;

import noemi.genshin_world.entities.User;
import noemi.genshin_world.exceptions.NotFoundException;
import noemi.genshin_world.repositories.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    public Page<User> getUsers(int page, int size, String orderBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return userDAO.findAll(pageable);
    }

    public User findById(UUID id) throws NotFoundException{
        return userDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
    }

    public User findByEmail(String email){
        return userDAO.findByEmail(email).orElseThrow(()-> new NotFoundException("User with email " + email + " not found!"));
    }

    public void deleteById(UUID id){
        User user = this.findById(id);
        userDAO.delete(user);
    }

}
