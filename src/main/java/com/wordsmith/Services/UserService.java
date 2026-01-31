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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.wordsmith.Util.EmailMasker;

@Service
public class UserService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // --------------------------------------------------------------
    // USER REGISTRATION
    // --------------------------------------------------------------
    public String registerUser(User user) {

        String maskedEmail = EmailMasker.mask(user.getEmail());
        String maskedUsername = EmailMasker.mask(user.getUsername());

        log.info("USER_REGISTER_ATTEMPT username={} email={}",
                maskedUsername, maskedEmail);

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            log.warn("USER_REGISTER_EMAIL_EXISTS email={}", maskedEmail);
            return "Email already in use!";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        log.info("USER_REGISTER_SUCCESS username={} email={}",
                maskedUsername, maskedEmail);

        return "User registered successfully!";
    }

    // --------------------------------------------------------------
    // USER AUTHENTICATION
    // --------------------------------------------------------------
    public boolean authenticateUser(String identifier, String password) {

        String maskedIdentifier = EmailMasker.mask(identifier);
        log.debug("USER_AUTH_ATTEMPT identifier={}", maskedIdentifier);

        Optional<User> userOptional = userRepository.findByEmail(identifier);

        if (!userOptional.isPresent()) {
            userOptional = userRepository.findByUsername(identifier);
        }

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            boolean match = passwordEncoder.matches(password, user.getPassword());

            log.info("USER_AUTH_RESULT identifier={} success={}",
                    maskedIdentifier, match);

            return match;
        }

        log.warn("USER_AUTH_NOT_FOUND identifier={}", maskedIdentifier);
        return false;
    }

    // --------------------------------------------------------------
    // FIND USERS
    // --------------------------------------------------------------
    public User findByIdentifier(String identifier) {
        String maskedIdentifier = EmailMasker.mask(identifier);
        log.debug("USER_FIND_BY_IDENTIFIER identifier={}", maskedIdentifier);

        return userRepository.findByEmail(identifier)
                .orElseGet(() -> userRepository.findByUsername(identifier).orElse(null));
    }

    public User findByEmail(String email) {
        String maskedEmail = EmailMasker.mask(email);
        log.debug("USER_FIND_BY_EMAIL email={}", maskedEmail);
        return userRepository.findByEmail(email).orElse(null);
    }

    public User findByUsername(String username) {
        String maskedUsername = EmailMasker.mask(username);
        log.debug("USER_FIND_BY_USERNAME username={}", maskedUsername);
        return userRepository.findByUsername(username).orElse(null);
    }

    public List<User> findallusers() {
        log.debug("USER_FIND_ALL");
        return userRepository.findAllByOrderByLastLoginTimeDesc();
    }

    // --------------------------------------------------------------
    // SAVE USER (REGISTRATION STEP 2)
    // --------------------------------------------------------------
    public void saveUser(String email, String username, String password, byte[] profilePicture) throws IOException {

        log.info("USER_SAVE_START username={} email={}",
                EmailMasker.mask(username), EmailMasker.mask(email));

        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setProfilePicture(profilePicture);

        userRepository.save(user);

        log.info("USER_SAVE_SUCCESS username={} email={}",
                EmailMasker.mask(username), EmailMasker.mask(email));
    }

    // --------------------------------------------------------------
    // PASSWORD UPDATE
    // --------------------------------------------------------------
    public void updatePassword(String email, String newPassword) {

        String maskedEmail = EmailMasker.mask(email);
        log.info("USER_PASSWORD_UPDATE_ATTEMPT email={}", maskedEmail);

        User user = userRepository.findByEmail(email).orElse(null);

        if (user != null) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);

            log.info("USER_PASSWORD_UPDATE_SUCCESS email={}", maskedEmail);
        } else {
            log.warn("USER_PASSWORD_UPDATE_USER_NOT_FOUND email={}", maskedEmail);
        }
    }

    // --------------------------------------------------------------
    // SPRING SECURITY USER LOADING
    // --------------------------------------------------------------
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String maskedUsername = EmailMasker.mask(username);
        log.debug("USER_LOAD_USER_DETAILS username={}", maskedUsername);

        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            log.warn("USER_LOAD_USER_DETAILS_NOT_FOUND username={}", maskedUsername);
            throw new UsernameNotFoundException("User not found");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }

    // --------------------------------------------------------------
    // PROFILE PICTURE UPDATE
    // --------------------------------------------------------------
    public void updateProfilePicture(String username, MultipartFile profilePicture) throws IllegalStateException, IOException {

        String maskedUsername = EmailMasker.mask(username);
        log.info("USER_PROFILE_PIC_UPDATE_ATTEMPT username={}", maskedUsername);

        User user = userRepository.findByUsername(username).orElse(null);

        if (user != null) {
            byte[] compressedBytes = compressImage(profilePicture);
            user.setProfilePicture(compressedBytes);
            userRepository.save(user);

            log.info("USER_PROFILE_PIC_UPDATE_SUCCESS username={}", maskedUsername);
        } else {
            log.warn("USER_PROFILE_PIC_UPDATE_USER_NOT_FOUND username={}", maskedUsername);
        }
    }

    // --------------------------------------------------------------
    // JPEG COMPRESSION
    // --------------------------------------------------------------
    public byte[] compressImage(MultipartFile file) throws IOException {

        if (file == null || file.isEmpty()) {
            log.warn("IMAGE_COMPRESS_SKIPPED: No image uploaded");
            return null;  // return null so DB can store no image
        }

        BufferedImage originalImage = ImageIO.read(file.getInputStream());

        if (originalImage == null) {
            log.warn("IMAGE_COMPRESS_SKIPPED: Invalid or empty image file");
            return null;
        }

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
        jpgWriteParam.setCompressionQuality(0.7f);

        jpgWriter.setOutput(ImageIO.createImageOutputStream(baos));
        jpgWriter.write(null, new IIOImage(newImage, null, null), jpgWriteParam);
        jpgWriter.dispose();

        return baos.toByteArray();
    }

}
