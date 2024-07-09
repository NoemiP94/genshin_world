package noemi.genshin_world.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import noemi.genshin_world.entities.Region;
import noemi.genshin_world.entities.User;
import noemi.genshin_world.exceptions.NotFoundException;
import noemi.genshin_world.repositories.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private Cloudinary cloudinary;

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

    public String uploadImage(UUID id, MultipartFile file) throws IOException {
        User user = userDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
        String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        user.setImage(url);
        userDAO.save(user);
        return url;
    }
}
