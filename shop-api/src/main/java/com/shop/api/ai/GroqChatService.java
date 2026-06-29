package com.shop.api.ai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.shop.core.ai.GroqProperties;
import com.shop.core.ai.vo.AiChatRequest;
import com.shop.core.frontWeb.dao.ProductDao;
import com.shop.core.frontWeb.vo.request.ProductRequest;
import com.shop.core.frontWeb.vo.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GroqChatService {

    private final GroqProperties groqProperties;
    private final ProductDao productDao;
    private final ObjectMapper objectMapper;

    public String chat(AiChatRequest.ProductChat req, Integer partnerId) {
        ProductResponse.ProductDetail product = fetchProduct(req.getProductId(), partnerId);
        String systemPrompt = buildSystemPrompt(product);

        ObjectNode body = objectMapper.createObjectNode();
        body.put("model", groqProperties.getModel());
        body.put("max_tokens", 1024);

        ArrayNode messages = body.putArray("messages");

        ObjectNode systemMsg = messages.addObject();
        systemMsg.put("role", "system");
        systemMsg.put("content", systemPrompt);

        if (req.getMessages() != null) {
            for (AiChatRequest.Message m : req.getMessages()) {
                ObjectNode msg = messages.addObject();
                msg.put("role", m.getRole());
                msg.put("content", m.getContent());
            }
        }

        try {
            RestClient client = RestClient.builder()
                    .baseUrl(groqProperties.getBaseUrl())
                    .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + groqProperties.getApiKey())
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .build();

            String responseBody = client.post()
                    .uri("/chat/completions")
                    .body(body)
                    .retrieve()
                    .body(String.class);

            JsonNode root = objectMapper.readTree(responseBody);
            return root.path("choices").path(0).path("message").path("content").asText();

        } catch (Exception e) {
            log.error("Groq API 호출 실패", e);
            throw new RuntimeException("AI 응답을 가져오는 중 오류가 발생했습니다.");
        }
    }

    private ProductResponse.ProductDetail fetchProduct(Integer productId, Integer partnerId) {
        ProductRequest.ProductDetailParam param = new ProductRequest.ProductDetailParam();
        param.setProductId(productId);
        param.setPartnerId(partnerId);
        ProductResponse.ProductDetail detail = productDao.selectProductDetail(param);
        if (detail != null) {
            List<ProductResponse.ProductDetInfo> detList = productDao.selectProductDetList(param);
            detail.setDetList(detList);
        }
        return detail;
    }

    private String buildSystemPrompt(ProductResponse.ProductDetail product) {
        if (product == null) {
            return "당신은 쇼핑몰 상품 안내 AI 어시스턴트입니다. 친절하고 간결하게 답변해주세요.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("당신은 쇼핑몰 상품 안내 AI 어시스턴트입니다. 아래 상품 정보를 바탕으로 고객의 질문에 친절하고 간결하게 답변해주세요.\n\n");
        sb.append("=== 상품 정보 ===\n");
        sb.append("상품명: ").append(nvl(product.getProdNm())).append("\n");
        sb.append("판매가: ").append(product.getSellAmt() != null ? product.getSellAmt().toPlainString() + "원" : "-").append("\n");
        if (product.getDiscountRate() != null && product.getDiscountRate().intValue() > 0) {
            sb.append("할인율: ").append(product.getDiscountRate().toPlainString()).append("%\n");
        }
        sb.append("구성: ").append(nvl(product.getComposition())).append("\n");

        if (product.getDetList() != null && !product.getDetList().isEmpty()) {
            sb.append("\n=== 옵션(컬러/사이즈/재고) ===\n");
            for (ProductResponse.ProductDetInfo det : product.getDetList()) {
                sb.append("- ").append(nvl(det.getProductDetColor()))
                  .append(" / ").append(nvl(det.getProductDetSize()))
                  .append(" : 재고 ").append(det.getStock() != null ? det.getStock() : 0).append("개\n");
            }
        }

        sb.append("\n재고가 0인 옵션은 현재 구매 불가임을 안내해주세요. 상품 정보에 없는 내용은 추측하지 말고 '정확한 정보는 고객센터에 문의해주세요'라고 안내해주세요.");
        return sb.toString();
    }

    private String nvl(String value) {
        return value != null ? value : "-";
    }
}
