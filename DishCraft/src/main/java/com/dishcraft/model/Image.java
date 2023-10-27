package com.dishcraft.model;

import jakarta.persistence.*;
import lombok.Builder;

@Entity
@Builder
public class Image {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Lob
    private byte[] data;
    private String fileName;
    private String fileType;
    private String systemName;
    
    private Image() {}
    
	public Image(Long id, byte[] data, String fileName, String fileType, String systemName) {
		super();
		this.id = id;
		this.data = data;
		this.fileName = fileName;
		this.fileType = fileType;
		this.systemName = systemName;
	}
    
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getSystemName() {
		return systemName;
	}
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
}
