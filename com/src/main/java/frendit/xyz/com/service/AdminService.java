package frendit.xyz.com.service;

import frendit.xyz.com.repository.postgres.AdminRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private final AdminRepository adminRepository;

    private final AuthService authService;

    public AdminService(AdminRepository adminRepository, AuthService authService) {
        this.adminRepository = adminRepository;
        this.authService = authService;
    }

    public String getRoleByEmail(String email){
        return adminRepository.getRoleByEmail(email);
    }

    public void isAdmin(){
        String email = authService.getEmailOfLoggedUser();
        String role = adminRepository.getRoleByEmail(email);
        if(role == null || !role.equals("ADMIN")) throw new RuntimeException("Only admins can access this route");
    }
}
