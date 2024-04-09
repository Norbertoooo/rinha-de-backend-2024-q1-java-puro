package com.vitu.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record Extrato(Saldo saldo, @JsonProperty("ultimas_transacoes") List<Transacao> ultimasTransacoes) {
}
