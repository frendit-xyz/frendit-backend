package frendit.xyz.com.controller;

import frendit.xyz.com.entity.category.createCategory.CategoryCreateRequest;
import frendit.xyz.com.helpers.Respond;
import frendit.xyz.com.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;
    private final Respond respond;

    public CategoryController(CategoryService categoryService, Respond respond) {
        this.categoryService = categoryService;

        this.respond = respond;
    }

    @GetMapping("/test")
    public String TestCategory(){
        return "Category OK";
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> CreateCategory(@RequestBody CategoryCreateRequest categoryCreateRequest) throws Exception {

        try{
            categoryService.CreateCategory(categoryCreateRequest);
            return respond.sendResponse(HttpStatus.CREATED, "Category created successfully");
        } catch(Exception e){
            throw new Exception("Category couldn't be created with provided data");
        }
    }
}
