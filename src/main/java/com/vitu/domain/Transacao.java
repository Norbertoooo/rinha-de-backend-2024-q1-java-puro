package com.vitu.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;

public record Transacao(Integer valor, String tipo, String descricao,
                        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'") @JsonProperty("realizado_em") ZonedDateTime realizadoEm) {
}
