package com.shop.core.entity;

import com.shop.core.interfaces.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "상품컨텐츠 Entity")
public class Contents extends BaseEntity implements Serializable {

//    @Serial
//    private static final long serialVersionUID = -7404284196980959337L;

    @Schema(description = "아이디(PK)")
    private Integer id;

    @Schema(description = "컨텐츠_유형")
    private String newsType;

    @Schema(description = "컨텐츠_제목")
    private String newsTitle;

    @Schema(description = "컨텐츠_하위_제목")
    private String newsSubTitle;

    @Schema(description = "컨텐츠_본문")
    private String newsContents;

    @Schema(description = "fileId")
    private Integer fileId;
}
