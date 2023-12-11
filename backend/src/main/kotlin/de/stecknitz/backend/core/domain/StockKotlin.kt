package de.stecknitz.backend.core.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import lombok.Builder

@Entity
@Table(name = "stock")
@Builder
class StockKotlin(
    @Id
    @Column(name = "isin")
    var isin: String,
    @Column(name = "symbol")
    var symbol: String,
    @Column(name = "wkn")
    var wkn: String,
    @Column(name = "name")
    var name: String,
    @Column(name = "current_price")
    var currentPrice: Float
)
