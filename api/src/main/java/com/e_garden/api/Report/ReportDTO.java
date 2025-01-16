package com.e_garden.api.Report;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportDTO {
    private String content;
    private boolean validated;

    public ReportDTO() {
    }

    public ReportDTO(boolean validated, String content) {
        this.validated = validated;
        this.content = content;
    }
}
