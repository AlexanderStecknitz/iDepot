package de.stecknitz.backend.web.resources.dto.mapper;

import de.stecknitz.backend.core.domain.Stock;
import de.stecknitz.backend.web.resources.dto.StockDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface StockMapper {

    StockDTO toStockDto(Stock stock);

    Stock toStock(StockDTO stockDTO);

}
