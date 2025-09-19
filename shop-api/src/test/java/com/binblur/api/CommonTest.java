package com.shop.api;

import java.util.Optional;
import java.util.OptionalInt;

/**
 * <pre>
 * Description:
 * Date: 2023/02/04 4:36 PM
 * Company: smart
 * Author: luckeey
 * </pre>
 */
public class CommonTest {

    public static void main(String[] args) {

        Integer val = null;
        Optional optVal = Optional.ofNullable(val);
        System.out.println(">>>>>>> " + optVal.isEmpty());


        /* occure error.
        Integer val = null;
        OptionalInt optVal = OptionalInt.of(val);
        System.out.println(">>>>>>> " + optVal.isEmpty());*/
    }
}
