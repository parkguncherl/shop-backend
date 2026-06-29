package com.shop.api.ai;

import com.shop.core.ai.vo.AiChatRequest;
import com.shop.core.frontWeb.dao.ProductDao;
import com.shop.core.frontWeb.vo.request.ProductRequest;
import com.shop.core.frontWeb.vo.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GroqChatService {

    private final ChatClient.Builder chatClientBuilder;
    private final ProductDao productDao;

    public String chat(AiChatRequest.ProductChat req, Integer partnerId) {
        ProductResponse.ProductDetail product = fetchProduct(req.getProductId(), partnerId);
        String systemPrompt = buildSystemPrompt(product);

        List<Message> history = req.getMessages() == null ? List.of() :
            req.getMessages().stream()
                .map(m -> "user".equals(m.getRole())
                    ? (Message) new UserMessage(m.getContent())
                    : new AssistantMessage(m.getContent()))
                .collect(Collectors.toList());

        return chatClientBuilder.build()
            .prompt()
            .system(systemPrompt)
            .messages(history)
            .call()
            .content();
    }

    private ProductResponse.ProductDetail fetchProduct(Integer productId, Integer partnerId) {
        ProductRequest.ProductDetailParam param = new ProductRequest.ProductDetailParam();
        param.setProductId(productId);
        param.setPartnerId(partnerId);
        ProductResponse.ProductDetail detail = productDao.selectProductDetail(param);
        if (detail != null) {
            detail.setDetList(productDao.selectProductDetList(param));
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
