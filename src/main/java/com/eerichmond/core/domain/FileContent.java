package com.eerichmond.core.domain;

import com.google.common.base.Preconditions;

import java.net.URLConnection;
import java.util.Arrays;

/**
 * Contains the contents (bytes) for a file
 */
public class FileContent extends BaseObject<BusinessKey> {

	private static final long serialVersionUID = 1L;
	
	@BusinessKey
	private final byte[] bytes;
	private final String fileName;
	private final String contentType;
	
	public FileContent(String fileName, byte[] bytes) {
		super();
		
		Preconditions.checkNotNull(fileName);

		this.fileName = fileName;
		this.contentType = URLConnection.getFileNameMap().getContentTypeFor(fileName);
		
		// Copy the bytes to prevent modification outside of this class
		this.bytes = bytes == null ? null : Arrays.copyOf(bytes, bytes.length);
	}
	
	public byte[] getBytes() { return bytes == null ? null : Arrays.copyOf(bytes, bytes.length); }
	public String getContentType() { return contentType; }
	public String getFileName() { return fileName; }
}
