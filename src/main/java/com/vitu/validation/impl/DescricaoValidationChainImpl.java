package com.vitu.validation.impl;

import com.vitu.domain.Transacao;
import com.vitu.exception.CorpoInvalidoException;
import com.vitu.validation.ValidationChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class DescricaoValidationChainImpl implements ValidationChain {

    private static final Logger logger = LoggerFactory.getLogger(DescricaoValidationChainImpl.class);
    private ValidationChain validationChain;
    @Override
    public void setProximaValidacao(ValidationChain chain) {
        validationChain = chain;
    }

    @Override
    public void validar(Transacao transacao) {
        logger.info("Validando campo descrição");
        if (transacao.descricao().isEmpty() || transacao.descricao().length() > 10) {
            throw new CorpoInvalidoException();
        }
        if (Objects.nonNull(validationChain)) {
            logger.info("Validação de campo descrição finalizada, passando para a próxima validação");
            validationChain.validar(transacao);
        }
    }
}
