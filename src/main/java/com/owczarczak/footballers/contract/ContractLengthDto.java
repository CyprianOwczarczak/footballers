package com.owczarczak.footballers.contract;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
@Data
@Builder
public class ContractLengthDto {
    private int clubId;
    private int averageLength;
}
