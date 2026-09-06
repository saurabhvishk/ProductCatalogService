package com.example.productcatalogservice.clients;

import com.example.productcatalogservice.dtos.FakeStoreProductDto;
import com.example.productcatalogservice.services.FakeStoreProductService;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
public class FakeStoreApiClient {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    public FakeStoreProductDto getProductById(Long id) {
        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity = requestForEntity("https://fakestoreapi.com/products/{id}",HttpMethod.GET,null,FakeStoreProductDto.class,id);
        return fakeStoreProductDtoResponseEntity.getBody();
    }

    public List<FakeStoreProductDto> getAllProducts() {
        ResponseEntity<FakeStoreProductDto[]> fakeStoreProductDtoResponseEntity = requestForEntity("https://fakestoreapi.com/products",HttpMethod.GET,null, FakeStoreProductDto[].class);
        return new ArrayList<>(Arrays.asList(Objects.requireNonNull(
                fakeStoreProductDtoResponseEntity.getBody())));
    }

    public FakeStoreProductDto replaceProduct(Long id, FakeStoreProductDto fakeStoreProductDto) {
        return requestForEntity("https://fakestoreapi.com/products/{id}",HttpMethod.PUT,fakeStoreProductDto,FakeStoreProductDto.class,id).getBody();
    }

    public FakeStoreProductDto createProduct(FakeStoreProductDto fakeStoreProductDto){
        return requestForEntity("https://fakestoreapi.com/products",HttpMethod.POST,fakeStoreProductDto,
                FakeStoreProductDto.class).getBody();
    }

    private <T> ResponseEntity<T> requestForEntity(String url, HttpMethod httpMethod, @Nullable Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
    }
}
