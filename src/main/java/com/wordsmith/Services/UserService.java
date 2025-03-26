package com.wordsmith.Services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wordsmith.Entity.User;
import com.wordsmith.Repositories.UserRepository;

@Service
public class UserService implements UserDetailsService{
	private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public String registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return "Email already in use!";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword())); // Hash Password
        userRepository.save(user);
        return "User registered successfully!";
    }
    
    public boolean authenticateUser(String identifier, String password) {
        Optional<User> userOptional = userRepository.findByEmail(identifier);
        
        if (!userOptional.isPresent()) {
            userOptional = userRepository.findByUsername(identifier); // Check by username if email not found
        }

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return passwordEncoder.matches(password, user.getPassword()); // Compare hashed password
        }
        return false;
    }

    public User findByIdentifier(String identifier) {
        return userRepository.findByEmail(identifier)
                .orElseGet(() -> userRepository.findByUsername(identifier).orElse(null));
    }
    
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
    
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
    
    public List<User> findallusers() {
        return userRepository.findAll();
    }

    public void saveUser(String email, String username, String password) {
        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }
    
    public void updatePassword(String email, String newPassword) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user != null) {
            user.setPassword(passwordEncoder.encode(newPassword)); // 🔐 Encrypt new password
            userRepository.save(user);
        }
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(
            user.getUsername(), user.getPassword(), Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}
