package com.example.pokeapi_mirror.model.util;

import java.util.List;

import org.springframework.data.domain.Page;

public class PageResult<T> {
	private List<T> content;
	private Integer totalPages;
	private Long totalElements;
	
	public PageResult(Page<T> page) {
		this.content = page.getContent();
		this.totalPages = page.getTotalPages();
		this.totalElements = page.getTotalElements();
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	public Long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(Long totalElements) {
		this.totalElements = totalElements;
	}
}