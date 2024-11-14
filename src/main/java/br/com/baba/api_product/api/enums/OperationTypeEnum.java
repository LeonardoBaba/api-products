package br.com.baba.api_product.api.enums;

import java.math.BigDecimal;

public enum OperationTypeEnum {
    INPUT {
        @Override
        public BigDecimal apply(BigDecimal currentQuantity, BigDecimal transactionQuantity) {
            return currentQuantity.add(transactionQuantity);
        }
    },
    OUTPUT {
        @Override
        public BigDecimal apply(BigDecimal currentQuantity, BigDecimal transactionQuantity) {
            return currentQuantity.subtract(transactionQuantity);
        }
    };

    public abstract BigDecimal apply(BigDecimal currentQuantity, BigDecimal transactionQuantity);
}