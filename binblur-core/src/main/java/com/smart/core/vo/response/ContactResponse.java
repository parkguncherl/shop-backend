package com.binblur.core.vo.response;

import com.binblur.core.entity.Contact;
import com.binblur.core.entity.Menu;
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
