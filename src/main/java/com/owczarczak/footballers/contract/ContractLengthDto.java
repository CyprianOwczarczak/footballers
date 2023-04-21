package com.owczarczak.footballers.contract;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContractLengthDto {
    private Long clubId;
    private Long averageLength;
}
