package frendit.xyz.com.model.quality;

import frendit.xyz.com.enums.TestStatus;
import lombok.Data;

@Data
public class ContentCensorModel {
    private String text;
    private TestStatus status;
}
