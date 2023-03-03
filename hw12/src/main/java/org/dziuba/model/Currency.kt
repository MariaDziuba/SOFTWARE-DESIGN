package org.dziuba.model

enum class Currency(private val cost: Double) {
    RUB(1.0),
    EUR(0.0125),
    USD(0.0133);

    companion object {
        fun Currency.convert(to: Currency, value: Double) = value * to.cost / cost
    }
}