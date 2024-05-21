package de.aittr.g_37_jp_supplier.service;

import de.aittr.g_37_jp_shop.domain.dto.ProductSupplyDto;
import de.aittr.g_37_jp_shop.domain.entity.User;
import de.aittr.g_37_jp_shop.security.sec_dto.TokenResponseDto;
import de.aittr.g_37_jp_supplier.service.interfaces.HttpService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class HttpServiceImpl implements HttpService {

    private RestTemplate template = new RestTemplate();

    @Value("${username}")
    private String username;

    @Value("${password}")
    private String password;

    @Override
    public List<ProductSupplyDto> getAvailableProducts() {
        String token = "Bearer " + login();

        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", List.of(token));
        HttpEntity<Void> request = new HttpEntity<>(headers);
        String url = "http://localhost:8080/system/products";

        ResponseEntity<ProductSupplyDto[]> response = template
                .exchange(url, HttpMethod.GET, request, ProductSupplyDto[].class);

        if (!response.hasBody()) {
            throw new RuntimeException("All products response has no body");
        }

        return Arrays.asList(response.getBody());
    }

    private String login() {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<User> request = new HttpEntity<>(user, headers);
        String url = "http://localhost:8080/auth/login";

        ResponseEntity<TokenResponseDto> response = template
                .postForEntity(url, request, TokenResponseDto.class);

        if (!response.hasBody()) {
            throw new RuntimeException("Auth response has no body");
        }

        return response.getBody().getAccessToken();
    }
}