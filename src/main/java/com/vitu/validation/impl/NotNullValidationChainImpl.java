package com.vitu.validation.impl;

import com.vitu.domain.Transacao;
import com.vitu.exception.CorpoInvalidoException;
import com.vitu.validation.ValidationChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class NotNullValidationChainImpl implements ValidationChain {

    private static final Logger logger = LoggerFactory.getLogger(NotNullValidationChainImpl.class);
    private ValidationChain validationChain;

    @Override
    public void setProximaValidacao(ValidationChain chain) {
        validationChain = chain;
    }

    @Override
    public void validar(Transacao transacao) {
        logger.info("Validando se campos do corpo estão nulos");
        if (Objects.isNull(transacao) || Objects.isNull(transacao.descricao()) || Objects.isNull(transacao.valor()) ||
            Objects.isNull(transacao.tipo())
        ) {
          throw new CorpoInvalidoException();
        }
        if (Objects.nonNull(validationChain)) {
            logger.info("Validação de campos nulos finalizada, passando para a próxima validação");
            validationChain.validar(transacao);
        }
    }

}
