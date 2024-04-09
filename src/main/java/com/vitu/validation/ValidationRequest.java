package com.vitu.validation;

import com.vitu.domain.Transacao;
import com.vitu.validation.impl.DescricaoValidationChainImpl;
import com.vitu.validation.impl.NotNullValidationChainImpl;
import com.vitu.validation.impl.TipoValidationChainImpl;
import com.vitu.validation.impl.ValorValidationChainImpl;

public class ValidationRequest {

    private static final ValidationChain notNullChain = new NotNullValidationChainImpl();
    private static final ValidationChain descricaoChain = new DescricaoValidationChainImpl();
    private static final ValidationChain tipoChain = new TipoValidationChainImpl();
    private static final ValorValidationChainImpl valorChain = new ValorValidationChainImpl();

    public static void validate(Transacao transacao) {
        notNullChain.setProximaValidacao(descricaoChain);
        descricaoChain.setProximaValidacao(valorChain);
        valorChain.setProximaValidacao(tipoChain);
        notNullChain.validar(transacao);
    }
}
