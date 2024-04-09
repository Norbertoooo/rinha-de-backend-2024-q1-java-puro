package com.vitu.validation;

import com.vitu.domain.Transacao;

public interface ValidationChain {

    void setProximaValidacao(ValidationChain chain);

    void validar(Transacao transacao);
}
