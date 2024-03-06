package com.thqu1et.e_commerces.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class addItemRequest {

    private Long productId;

    private String size;

    private int quantoty;

    private Integer price;


}
