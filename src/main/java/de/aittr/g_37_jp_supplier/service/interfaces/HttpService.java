package de.aittr.g_37_jp_supplier.service.interfaces;

import de.aittr.g_37_jp_shop.domain.dto.ProductSupplyDto;

import java.util.List;

public interface HttpService {

    List<ProductSupplyDto> getAvailableProducts();
}