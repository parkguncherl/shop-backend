package com.shop.core.exception;

import com.shop.core.enums.ApiResultCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedRuntimeException;


/**
 * <pre>
 * Description: 커스텀 익셉션
 * Date :
 * Company: smart
 * Author : luckeey
 * </pre>
 */
@Slf4j
public class CustomRuntimeException extends NestedRuntimeException {

	@Getter
	@Setter
	private String majorCode;

	@Getter
	@Setter
	private String minorCode;

	@Getter
	@Setter
	private String message;

	@Getter
	@Setter
	private ApiResultCode resultCode;

	@Getter
	@Setter
	private Object data;

	/**
	 * @param message
	 */
	public CustomRuntimeException(String message) {
		super(message);
		this.message = message;
	}

	public CustomRuntimeException(String majorCode, String minorCode, String message, Throwable cause) {
		super(message, cause);
		this.majorCode = majorCode;
		this.minorCode = minorCode;
		this.message = message;
	}

	public CustomRuntimeException(ApiResultCode resultCode, String majorCode, String minorCode, String message, Throwable cause) {
		super(message, cause);
		this.resultCode = resultCode;
		this.majorCode = majorCode;
		this.minorCode = minorCode;
		this.message = message;
	}

	public CustomRuntimeException(ApiResultCode resultCode, String message, Throwable cause) {
		super(message, cause);
		this.resultCode = resultCode;
		this.majorCode = String.valueOf(resultCode.getResultCode());
		this.minorCode = String.valueOf(resultCode.getResultCode());
		this.message = message;
	}

	public CustomRuntimeException(ApiResultCode resultCode, String message) {
		super(message);
		this.resultCode = resultCode;
		this.majorCode = String.valueOf(resultCode.getResultCode());
		this.minorCode = String.valueOf(resultCode.getResultCode());
		this.message = message;
	}

	public CustomRuntimeException(ApiResultCode resultCode, Throwable cause) {
		super(String.valueOf(resultCode), cause);
		this.resultCode = resultCode;
		this.majorCode = String.valueOf(resultCode.getResultCode());
		this.minorCode = String.valueOf(resultCode.getResultCode());
		this.message = String.valueOf(resultCode);
	}

	public CustomRuntimeException(ApiResultCode resultCode) {
		super(resultCode.getResultMessage());
		this.resultCode = resultCode;
		this.majorCode = String.valueOf(resultCode.getResultCode());
		this.minorCode = String.valueOf(resultCode.getResultCode());
		this.message = String.valueOf(resultCode.getResultMessage());
	}

	public CustomRuntimeException(ApiResultCode resultCode, String majorCode, String minorCode, String message) {
		this(majorCode,minorCode,message);
		this.resultCode = resultCode;
	}

	public CustomRuntimeException(ApiResultCode resultCode, String majorCode, String minorCode, String message, Object data) {
		this(majorCode,minorCode,message);
		this.resultCode = resultCode;
		this.data = data;
	}

	public CustomRuntimeException(String majorCode, String minorCode, String message) {
		super(message);
		this.majorCode = majorCode;
		this.minorCode = minorCode;
		this.message = message;
	}

	public CustomRuntimeException(String majorCode, String message, Throwable cause) {
		this(majorCode, "", message, cause);
	}

	public CustomRuntimeException(String message, Throwable cause) {
		this("", "", message, cause);
	}

	
}
