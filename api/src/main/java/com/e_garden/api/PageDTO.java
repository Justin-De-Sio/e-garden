package com.e_garden.api;

import java.util.List;

/**
 * Class formant une Page, elle contient des objects et des informations sur la position de la page actuelle, le nombre
 * total de pages, le nombre total d'éléments, et la taille de la page.
 *
 * @param <T> objet
 */
public class PageDTO<T> {

    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;

    /**
     * Instancie un nouveau Page dto.
     *
     * @param content       le content
     * @param pageNumber    le page number
     * @param pageSize      le page size
     * @param totalElements le total elements
     * @param totalPages    le total pages
     */
    public PageDTO(List<T> content, int pageNumber, int pageSize, long totalElements, int totalPages) {
        this.content = content;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    /**
     * Gets content.
     *
     * @return le content
     */
    public List<T> getContent() {
        return content;
    }

    /**
     * Sets content.
     *
     * @param content le content
     */
    public void setContent(List<T> content) {
        this.content = content;
    }

    /**
     * Gets page number.
     *
     * @return la page number
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * Sets page number.
     *
     * @param pageNumber la page number
     */
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    /**
     * Gets page size.
     *
     * @return la page size
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * Sets page size.
     *
     * @param pageSize la page size
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Gets total elements.
     *
     * @return le total elements
     */
    public long getTotalElements() {
        return totalElements;
    }

    /**
     * Sets total elements.
     *
     * @param totalElements le total elements
     */
    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    /**
     * Gets total pages.
     *
     * @return le total pages
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     * Sets total pages.
     *
     * @param totalPages le total pages
     */
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
