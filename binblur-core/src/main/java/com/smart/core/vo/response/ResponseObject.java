package com.binblur.core.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.GsonBuilder;
import com.binblur.core.entity.adapter.LocalDateSerializer;
import com.binblur.core.entity.adapter.LocalDateTimeSerializer;
import com.binblur.core.enums.ApiResultCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <pre>
 * Description : JSON Response 형태
 * Date : 2023/01/26 12:35 PM
 * Company : smart
 * Author : luckeey
 * </pre>
 */
@Slf4j
@Setter
@Getter
public class ResponseObject<T> {
	
	private ApiResultCode resultCode;
	
	private String resultMessage;

	private T body;

	public ResponseObject(T body) {
		this.setBody(body);
	}

	public ResponseObject() {
	}

	public void setBody(T body) {
		this.body = body;

		if (this.resultCode == null) {
			this.resultCode = ApiResultCode.SUCCESS;
		}
	}

	public ApiResultCode getResultCode() {
		return resultCode;
	}

	public void setResultCode(ApiResultCode resultCode) {
		this.resultCode = resultCode;
		this.resultMessage = resultCode.getResultMessage();
	}

	@JsonProperty("resultCode")
	public void setResultCodeValue(Integer resultCode) {
		this.setResultCode(ApiResultCode.get(resultCode));
	}

	@JsonProperty("resultCode")
	public Integer getResultCodeValue() {
		return this.resultCode != null ? this.resultCode.getResultCode() : null;
	}

	@Override
	public String toString() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
		gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

		return gsonBuilder.setPrettyPrinting().create().toJson(this);
	}
}
