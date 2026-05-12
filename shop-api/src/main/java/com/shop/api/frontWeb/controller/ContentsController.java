package com.shop.api.frontWeb.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 * Description: 컨텐츠 Controller
 * Date: 2026/05/12
 * Author: park junsung
 * </pre>
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/frontWeb/contents")
@Tag(name = "ContentsController", description = "컨텐츠 관련 API")
public class ContentsController {
}
