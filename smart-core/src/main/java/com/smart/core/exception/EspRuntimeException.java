package com.smart.core.exception;

import com.smart.core.enums.ApiResultCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedRuntimeException;


/**
 * <pre>
 * Description : 커스텀 익셉션
 * Date :
 * Company : smart
 * Author : kdonghwa
 * </pre>
 */
@Slf4j
public class EspRuntimeException extends NestedRuntimeException {

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
	public EspRuntimeException(String message) {
		super(message);
		this.message = message;
	}

	public EspRuntimeException(String majorCode, String minorCode, String message, Throwable cause) {
		super(message, cause);
		this.majorCode = majorCode;
		this.minorCode = minorCode;
		this.message = message;
	}

	public EspRuntimeException(ApiResultCode resultCode, String majorCode, String minorCode, String message, Throwable cause) {
		super(message, cause);
		this.resultCode = resultCode;
		this.majorCode = majorCode;
		this.minorCode = minorCode;
		this.message = message;
	}

	public EspRuntimeException(ApiResultCode resultCode, String message, Throwable cause) {
		super(message, cause);
		this.resultCode = resultCode;
		this.majorCode = String.valueOf(resultCode.getResultCode());
		this.minorCode = String.valueOf(resultCode.getResultCode());
		this.message = message;
	}

	public EspRuntimeException(ApiResultCode resultCode, String message) {
		super(message);
		this.resultCode = resultCode;
		this.majorCode = String.valueOf(resultCode.getResultCode());
		this.minorCode = String.valueOf(resultCode.getResultCode());
		this.message = message;
	}

	public EspRuntimeException(ApiResultCode resultCode, Throwable cause) {
		super(String.valueOf(resultCode), cause);
		this.resultCode = resultCode;
		this.majorCode = String.valueOf(resultCode.getResultCode());
		this.minorCode = String.valueOf(resultCode.getResultCode());
		this.message = String.valueOf(resultCode);
	}

	public EspRuntimeException(ApiResultCode resultCode) {
		super(resultCode.getResultMessage());
		this.resultCode = resultCode;
		this.majorCode = String.valueOf(resultCode.getResultCode());
		this.minorCode = String.valueOf(resultCode.getResultCode());
		this.message = String.valueOf(resultCode.getResultMessage());
	}

	public EspRuntimeException(ApiResultCode resultCode, String majorCode, String minorCode, String message) {
		this(majorCode,minorCode,message);
		this.resultCode = resultCode;
	}

	public EspRuntimeException(ApiResultCode resultCode, String majorCode, String minorCode, String message, Object data) {
		this(majorCode,minorCode,message);
		this.resultCode = resultCode;
		this.data = data;
	}

	public EspRuntimeException(String majorCode, String minorCode, String message) {
		super(message);
		this.majorCode = majorCode;
		this.minorCode = minorCode;
		this.message = message;
	}

	public EspRuntimeException(String majorCode, String message, Throwable cause) {
		this(majorCode, "", message, cause);
	}

	public EspRuntimeException(String message, Throwable cause) {
		this("", "", message, cause);
	}

	
}
