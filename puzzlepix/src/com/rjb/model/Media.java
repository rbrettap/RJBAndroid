package com.rjb.model;

import java.io.Serializable;

public class Media implements Serializable {
	private static final long serialVersionUID = -274493186617288932L;
	private final String url;
	private int width;
	private int height;
	private String title;
	private String caption;
	private String contentId;
    private long savedDate;

	public Media(String url) {
		this.url = url;
	}

	public boolean equals(Object obj) {
		Media media = (Media) obj;
		return url.equals(media.getUrl());
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getWidth() {
		return width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getHeight() {
		return height;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getCaption() {
		return caption;
	}

	public String getUrl() {
		return url;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public String getContentId() {
		return contentId;
	}

    public long getSavedDate()
    {
        return savedDate;
    }

    public void setSavedDate(long savedDate)
    {
        this.savedDate = savedDate;
    }
}
