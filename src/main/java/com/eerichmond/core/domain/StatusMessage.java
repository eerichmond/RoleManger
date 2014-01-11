package com.eerichmond.core.domain;

import com.eerichmond.core.codes.Severity;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Locale;

public class StatusMessage extends BaseObject<BusinessKey> {
	private static final long serialVersionUID = 1L;
	
	private Severity severity = Severity.INFO;
	private String code;
	private String message;
	
	/**
	 * Empty constructor for proxy/ORM/serialization libraries.
	 */
	public StatusMessage(){ super(); }
	
	public StatusMessage(String message) {
		super();
		this.message = message;
	}
	
	public StatusMessage(String message, String code) {
		super();
		this.message = message;
		this.code = code;
	}
	
	public StatusMessage(String message, Severity severity) {
		super();
		this.severity = severity;
		this.message = message;
	}
	
	public StatusMessage(String message, String code, Severity severity) {
		super();
		this.severity = severity;
		this.message = message;
		this.code = code;
	}

	/**
	 * Loads the binding result field errors into the status message and sets the status severity to ERROR.
	 * @param messageSource the message source use to look up the friendly message associated with the field error
	 * @param bindingResult the binding result to check for errors in
	 */
	public StatusMessage(MessageSource messageSource, BindingResult bindingResult) {
		super();

		StringBuilder sb = new StringBuilder();
		boolean first = true;

		if (bindingResult.hasFieldErrors()) {
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				if (!first) {
					sb.append(" ");
				}

				sb.append(messageSource.getMessage(fieldError, Locale.getDefault()));

				first = false;
			}

			this.setMessage(sb.toString());
			this.setSeverity(Severity.ERROR);
		}
	}

	/**
	 * Status severity
	 */
	@BusinessKey @JsonProperty
	public Severity getSeverity() { return severity; }
	public StatusMessage setSeverity(Severity severity) { this.severity = severity; return this; }
	
	/**
	 * Optional code to uniquely identify this status
	 */
	@BusinessKey @JsonProperty
	public String getCode() { return code; }
	public StatusMessage setCode(String code) { this.code = code; return this; }
	
	/**
	 * Human readable message.
	 */
	@BusinessKey @JsonProperty
	public String getMessage() { return message; }
	public StatusMessage setMessage(String message) { this.message = message; return this; }
}
