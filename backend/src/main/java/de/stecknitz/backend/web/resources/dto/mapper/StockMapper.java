package de.stecknitz.backend.web.resources.dto.mapper;

import de.stecknitz.backend.core.domain.StockKotlin;
import de.stecknitz.backend.web.resources.dto.StockDTOKotlin;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface StockMapper {

    StockDTOKotlin toStockDto(StockKotlin stock);

    StockKotlin toStock(StockDTOKotlin stockDTO);

}
