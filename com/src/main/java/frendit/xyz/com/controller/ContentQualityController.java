package frendit.xyz.com.controller;

import frendit.xyz.com.model.quality.ContentRateForm;
import frendit.xyz.com.service.ContentQualityService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/quality")
public class ContentQualityController {
    private final ContentQualityService contentQualityService;

    public ContentQualityController(ContentQualityService contentQualityService) {
        this.contentQualityService = contentQualityService;
    }

    @PostMapping("/rate")
    public int rateContent (@RequestBody ContentRateForm contentRateForm) {
        return contentQualityService.rateContent(contentRateForm.getText());
    }

    @PostMapping("/tags")
    public List<String> tagContent (@RequestBody ContentRateForm contentRateForm) {
        return contentQualityService.extractTopics(contentRateForm.getText());
    }
}
