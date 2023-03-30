package com.owczarczak.footballers.contract;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContractLengthDto {
    private int clubId;
    private int averageLength;
}
