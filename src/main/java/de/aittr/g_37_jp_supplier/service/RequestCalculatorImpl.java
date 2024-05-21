package de.aittr.g_37_jp_supplier.service;

import de.aittr.g_37_jp_shop.domain.dto.ProductSupplyDto;
import de.aittr.g_37_jp_supplier.service.interfaces.RequestCalculator;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RequestCalculatorImpl implements RequestCalculator {

    private final Map<String, Integer> requiredQuantities = Map.of(
            "Banana", 15,
            "Apple", 20,
            "Orange", 17,
            "Coconut", 8,
            "Peach", 23
    );

    @Override
    public Map<String, Integer> calculateRequest(List<ProductSupplyDto> products) {

//        Map<String, Integer> supplyRequest = new HashMap<>();
//
//        for (ProductSupplyDto currentProduct : products) {
//            int availableQuantity = currentProduct.getQuantity();
//            String productTitle = currentProduct.getTitle();
//            int requiredQuantity = requiredQuantities.get(productTitle);
//            int quantityToRequest = requiredQuantity - availableQuantity;
//
//            supplyRequest.put(productTitle, quantityToRequest);
//        }
//
//        return supplyRequest;

        return products.stream()
                .collect(Collectors.toMap(
                        ProductSupplyDto::getTitle,
                        x -> requiredQuantities.get(x.getTitle()) - x.getQuantity()));
    }
}