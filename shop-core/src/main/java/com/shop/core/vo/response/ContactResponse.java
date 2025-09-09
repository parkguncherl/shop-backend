package com.shop.core.vo.response;

import com.shop.core.entity.Contact;
import com.shop.core.entity.Menu;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ContactResponse extends Contact {

    /** NO */
    private Integer no;

}
