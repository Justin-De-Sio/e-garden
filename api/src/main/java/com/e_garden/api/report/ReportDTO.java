package com.e_garden.api.report;

/**
 * Le type Report dto.
 */
public class ReportDTO {
    private String content;
    private boolean validated;

    /**
     * Instancie un nouveau Report dto.
     */
    public ReportDTO() {
    }

    /**
     * Instancie un nouveau Report dto.
     *
     * @param validated le validated
     * @param content   le content
     */
    public ReportDTO(boolean validated, String content) {
        this.validated = validated;
        this.content = content;
    }

    /**
     * Gets content.
     *
     * @return le content
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets content.
     *
     * @param content le content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Is validated boolean.
     *
     * @return le boolean
     */
    public boolean isValidated() {
        return validated;
    }

    /**
     * Sets validated.
     *
     * @param validated le validated
     */
    public void setValidated(boolean validated) {
        this.validated = validated;
    }
}
