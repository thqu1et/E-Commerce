package com.thqu1et.e_commerces.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="sizes")
public class Size {

    @Column(name = "name")
    private String name;

    @Column(name = "quantity")
    private int quantity;

}
