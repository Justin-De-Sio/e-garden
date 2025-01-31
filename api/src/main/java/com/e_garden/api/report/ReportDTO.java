package com.e_garden.api.report;

public class ReportDTO {
    private String content;
    private boolean validated;

    public ReportDTO() {
    }

    public ReportDTO(boolean validated, String content) {
        this.validated = validated;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }
}
