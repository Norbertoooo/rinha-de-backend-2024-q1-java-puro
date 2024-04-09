package com.vitu.validation.impl;

import com.vitu.domain.Transacao;
import com.vitu.exception.CorpoInvalidoException;
import com.vitu.validation.ValidationChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class TipoValidationChainImpl implements ValidationChain {

    private static final Logger logger = LoggerFactory.getLogger(TipoValidationChainImpl.class);
    private ValidationChain validationChain;

    @Override
    public void setProximaValidacao(ValidationChain chain) {
        validationChain = chain;
    }

    @Override
    public void validar(Transacao transacao) {
        logger.info("Iniciando Validação do campo tipo");
        String tipo = transacao.tipo();

        if (tipo.isEmpty() || Boolean.FALSE.equals("c".equalsIgnoreCase(tipo)) &&
                Boolean.FALSE.equals("d".equalsIgnoreCase(tipo))) {
            logger.error("Erro ao validar campo tipo: {} - tipo não suportado", tipo);
            throw new CorpoInvalidoException();
        }
        if (Objects.nonNull(validationChain)) {
            logger.info("Validação do campo tipo finalizada, passando para a próxima validação");
            validationChain.validar(transacao);
        } else {
            logger.info("Validação do campo tipo finalizada, corrente de validação finalizada");
        }

    }
}
