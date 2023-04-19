package frendit.xyz.com.controller;

import frendit.xyz.com.enums.TestStatus;
import frendit.xyz.com.model.quality.ContentCensorModel;
import frendit.xyz.com.model.quality.ContentRateForm;
import frendit.xyz.com.service.ContentQualityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/check")
    public ResponseEntity<ContentCensorModel> checkContent(@RequestBody ContentRateForm contentRateForm) {
        ContentCensorModel contentCensorModel = contentQualityService.shouldCensor(contentRateForm.getText());
        if (contentCensorModel.getStatus().equals(TestStatus.PASSED)) {
            return ResponseEntity.ok(contentCensorModel);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(contentCensorModel);
        }
    }

    @PostMapping("/tags")
    public List<String> tagContent (@RequestBody ContentRateForm contentRateForm) {
        return contentQualityService.extractTopics(contentRateForm.getText());
    }
}
