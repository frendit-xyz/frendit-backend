package frendit.xyz.com.controller;

import frendit.xyz.com.entity.ProfileEntity;
import frendit.xyz.com.service.AuthService;
import frendit.xyz.com.service.ProfileService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    private final ProfileService profileService;

    private final AuthService authService;

    public ProfileController(ProfileService profileService, AuthService authService) {
        this.profileService = profileService;
        this.authService = authService;
    }

    @GetMapping("/test")
    public String TestProfile(){
        return "Profile OK";
    }

    @GetMapping("/me")
    public ProfileEntity findProfile() throws Exception {
        String email = authService.getEmailOfLoggedUser();
        return profileService.findByEmail(email);
    }
}
