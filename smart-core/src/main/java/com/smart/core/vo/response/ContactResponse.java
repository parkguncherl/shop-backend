package com.smart.core.vo.response;

import com.smart.core.entity.Contact;
import com.smart.core.entity.Menu;
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
