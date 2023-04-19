package frendit.xyz.com.controller;

import frendit.xyz.com.entity.profile.ProfileEntity;
import frendit.xyz.com.entity.profile.update.UpdateProfileRequest;
import frendit.xyz.com.service.AuthService;
import frendit.xyz.com.service.ProfileService;
import org.springframework.web.bind.annotation.*;

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

    /**
     * Update API for Profile Entity,
     *
     * @param request UpdateProfileRequest body
     * @return updated Profile Enity
     * @throws Exception when current user profile cannot be found
     */
    @PostMapping("/update")
    public ProfileEntity updateProfile(@RequestBody UpdateProfileRequest request) throws Exception {
        String currentUserEmail = authService.getEmailOfLoggedUser();
        System.out.println("CurrentUeserEmail");
        System.out.println(currentUserEmail);
        return profileService.updateProfile(currentUserEmail, request);
    }
}
