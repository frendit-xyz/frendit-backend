package frendit.xyz.com.controller;

import frendit.xyz.com.model.category.CreateCategoryEntity;
import frendit.xyz.com.helpers.Respond;
import frendit.xyz.com.service.AdminService;
import frendit.xyz.com.service.AuthService;
import frendit.xyz.com.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;
    private final AdminService adminService;
    private final Respond respond;

    public CategoryController(CategoryService categoryService, AdminService adminService, Respond respond) {
        this.categoryService = categoryService;
        this.adminService = adminService;
        this.respond = respond;
    }

    @GetMapping("/test")
    public String TestCategory(){
        return "Category OK";
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> CreateCategory(@Valid @RequestBody CreateCategoryEntity createCategoryEntity) throws Exception {
        adminService.isAdmin();
        try{
            categoryService.CreateCategory(createCategoryEntity);
            return respond.sendResponse(HttpStatus.CREATED, "Category created successfully");
        } catch(Exception e){
            throw new Exception("Category couldn't be created with provided data");
        }
    }
}
