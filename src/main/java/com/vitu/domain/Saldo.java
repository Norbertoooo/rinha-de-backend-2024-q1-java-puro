package com.vitu.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.ZonedDateTime;

public record Saldo(Long total, @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'") @JsonProperty("data_extrato") ZonedDateTime dataExtrato, Long limite) {
}
