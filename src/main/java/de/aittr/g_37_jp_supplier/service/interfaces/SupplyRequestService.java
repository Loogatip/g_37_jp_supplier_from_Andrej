package de.aittr.g_37_jp_supplier.service.interfaces;

import java.util.Map;

public interface SupplyRequestService {

    void sendSupplyRequest(Map<String, Integer> supplyRequest);
}