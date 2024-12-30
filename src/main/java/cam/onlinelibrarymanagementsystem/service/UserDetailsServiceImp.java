package cam.onlinelibrarymanagementsystem.service;

import cam.onlinelibrarymanagementsystem.model.User;
import cam.onlinelibrarymanagementsystem.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    private final UserRepository repository;

    public UserDetailsServiceImp(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
    }

    // Get a user by username
    public User getUserByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }

    // Update the user's profile (name, username, password)
    public User updateUserProfile(String username, User updatedUser) {
        User existingUser = getUserByUsername(username);

        // Update firstname if it's not null or empty
        if (updatedUser.getFirstname() != null && !updatedUser.getFirstname().isEmpty()) {
            existingUser.setFirstname(updatedUser.getFirstname());
        }

        // Update lastname if it's not null or empty
        if (updatedUser.getLastname() != null && !updatedUser.getLastname().isEmpty()) {
            existingUser.setLastname(updatedUser.getLastname());
        }

        // Update username if it's not null or empty
        if (updatedUser.getUsername() != null && !updatedUser.getUsername().isEmpty()) {
            existingUser.setUsername(updatedUser.getUsername());
        }


        return repository.save(existingUser);
    }

    // Get all users (for admin purposes)
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    // Delete a user by id
    public void deleteUser(Integer id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        repository.deleteById(id);
    }

    public Optional<User> getUserByID(Integer id){
        return repository.findById(id);
    }


}
