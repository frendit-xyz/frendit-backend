package frendit.xyz.com.controller;

import frendit.xyz.com.enums.TestStatus;
import frendit.xyz.com.model.quality.ContentCensorModel;
import frendit.xyz.com.model.quality.ContentRateForm;
import frendit.xyz.com.service.ContentQualityService;
import frendit.xyz.com.service.PythonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/quality")
public class ContentQualityController {
    private final ContentQualityService contentQualityService;

    private final PythonService pythonService;

    public ContentQualityController(ContentQualityService contentQualityService, PythonService pythonService) {
        this.contentQualityService = contentQualityService;
        this.pythonService = pythonService;
    }

    @PostMapping("/rate")
    public int rateContent(@RequestBody ContentRateForm contentRateForm) {
        return contentQualityService.rateContent(contentRateForm.getText());
    }

    @PostMapping("/check")
    public ResponseEntity<ContentCensorModel> checkContent(@RequestBody ContentRateForm contentRateForm) throws IOException, InterruptedException {
        System.out.println(pythonService.analyzePost(contentRateForm.getText()));
        ContentCensorModel contentCensorModel = contentQualityService.shouldCensor(contentRateForm.getText());
        if (contentCensorModel.getStatus().equals(TestStatus.PASSED)) {
            return ResponseEntity.ok(contentCensorModel);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(contentCensorModel);
        }
    }

    @PostMapping("/tags")
    public List<String> tagContent(@RequestBody ContentRateForm contentRateForm) {
        return contentQualityService.extractTopics(contentRateForm.getText());
    }

}
