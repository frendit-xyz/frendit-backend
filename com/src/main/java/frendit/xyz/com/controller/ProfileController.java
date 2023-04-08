package frendit.xyz.com.controller;

import frendit.xyz.com.entity.ProfileEntity;
import frendit.xyz.com.service.ProfileService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/test")
    public String TestProfile(){
        return "Profile OK";
    }

    @GetMapping("/retrieve")
    public ProfileEntity findProfile(
            @RequestParam(defaultValue = "") String email, @RequestParam(defaultValue = "") String username
    ) throws Exception {
        if(email != ""){
            return profileService.findByEmail(email);
        }
        if(username != ""){
            return profileService.findByUsername(username);
        }
        throw new Exception("Not Allowed!");
    }
}
