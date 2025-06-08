package kr.ac.hansung.cse.hellospringdatajpa.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCreateDTO {

    @NotBlank(message = "상품명 입력은 필수입니다.")
    private String name;

    @NotBlank(message = "브랜드명 입력은 필수입니다.")
    private String brand;

    @NotBlank(message = "원산지 입력은 필수입니다.")
    private String madeIn;

    @Min(value = 1, message = "가격은 최소 1원 이상이어야 합니다.")
    private Double price;

}

