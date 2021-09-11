package com.api.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

@Getter @Setter
@AllArgsConstructor
public class Human {
    @Min(1)
    private long height; //Значение поля должно быть больше 0
}