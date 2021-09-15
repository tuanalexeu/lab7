package com.api.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import java.io.Serializable;

@Getter @Setter
@AllArgsConstructor
public class Human implements Serializable {
    @Min(1)
    private long height; //Значение поля должно быть больше 0

    @Override
    public String toString() {
        return String.valueOf(height);
    }
}