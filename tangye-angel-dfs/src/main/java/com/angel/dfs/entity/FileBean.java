package com.angel.dfs.entity;

import lombok.Data;

import java.util.Date;

@Data
public class FileBean {
	private String id;
    private String name;
    //private byte[] content;
    private String ext;
    private String md5;
    private String author;
    private Date date;
    private long length;
    private String group;
    private String path;
    private String fullPath;
    private int status;
}