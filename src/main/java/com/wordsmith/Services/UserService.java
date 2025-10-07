package com.wordsmith.Services;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    public void saveUser(String email, String username, String password, byte[] profilePicture) throws IOException {
        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setProfilePicture(profilePicture);
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

    public void updateProfilePicture(String username, MultipartFile profilePicture) throws IllegalStateException, IOException {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null) {
            byte[] compressedBytes = compressImage(profilePicture);
            user.setProfilePicture(compressedBytes);
            userRepository.save(user);
        }
    }

    public byte[] compressImage(MultipartFile file) throws IOException {
    BufferedImage originalImage = ImageIO.read(file.getInputStream());

    // Convert to RGB to avoid "Bogus input colorspace"
    BufferedImage newImage = new BufferedImage(
            originalImage.getWidth(),
            originalImage.getHeight(),
            BufferedImage.TYPE_INT_RGB
    );
    newImage.createGraphics().drawImage(originalImage, 0, 0, java.awt.Color.WHITE, null);

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ImageWriter jpgWriter = ImageIO.getImageWritersByFormatName("jpg").next();
    ImageWriteParam jpgWriteParam = jpgWriter.getDefaultWriteParam();

    jpgWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
    jpgWriteParam.setCompressionQuality(0.7f); // 70% quality

    jpgWriter.setOutput(ImageIO.createImageOutputStream(baos));
    jpgWriter.write(null, new IIOImage(newImage, null, null), jpgWriteParam);
    jpgWriter.dispose();

    return baos.toByteArray();
}
}
