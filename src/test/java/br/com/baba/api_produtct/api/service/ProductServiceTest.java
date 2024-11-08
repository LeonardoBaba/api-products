package br.com.baba.api_produtct.api.service;

import br.com.baba.api_produtct.api.dto.ProductFormDTO;
import br.com.baba.api_produtct.api.dto.ProductUpdateDTO;
import br.com.baba.api_produtct.api.model.Product;
import br.com.baba.api_produtct.api.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private Product product;

    @Mock
    private ProductFormDTO productFormDTO;

    @Captor
    private ArgumentCaptor<Product> productCaptor;

    @Mock
    private ProductRepository productRepository;

    @Test
    void shouldCreateProductWithNoQuantity() {
        productFormDTO = new ProductFormDTO("name", BigDecimal.TEN, null);
        product = new Product(productFormDTO);

        productService.createProduct(productFormDTO);

        then(productRepository).should().save(productCaptor.capture());
        var result = productCaptor.getValue();
        assertEquals(product.getName(), result.getName());
        assertEquals(product.getPrice(), result.getPrice());
        assertEquals(BigDecimal.ZERO, result.getQuantity());
    }

    @Test
    void shouldCreateProduct() {
        productFormDTO = new ProductFormDTO("name", BigDecimal.TEN, BigDecimal.ONE);
        product = new Product(productFormDTO);

        productService.createProduct(productFormDTO);

        then(productRepository).should().save(productCaptor.capture());
        var result = productCaptor.getValue();
        assertEquals(product.getName(), result.getName());
        assertEquals(product.getPrice(), result.getPrice());
        assertEquals(BigDecimal.ONE, result.getQuantity());
    }

    @Test
    void shouldReturnPagedProducts() {
        Pageable pageable = PageRequest.of(0, 5);
        List<Product> products = Arrays.asList(new Product(), new Product());
        Page<Product> page = new PageImpl<>(products, pageable, products.size());

        when(productRepository.findAll(pageable)).thenReturn(page);

        Page<Product> result = productService.findAll(pageable);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals(products.size(), result.getContent().size());

        verify(productRepository, times(1)).findAll(pageable);
    }

    @Test
    void shouldReturnEmptyPageWhenNoProducts() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Product> emptyPage = Page.empty(pageable);

        when(productRepository.findAll(pageable)).thenReturn(emptyPage);

        Page<Product> result = productService.findAll(pageable);

        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
        assertTrue(result.isEmpty());

        verify(productRepository, times(1)).findAll(pageable);
    }

    @Test
    void shouldReturnProductById() {
        Long productId = 1L;
        Product mockProduct = new Product();
        mockProduct.setId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));

        Product result = productService.findById(productId);

        assertNotNull(result);
        assertEquals(productId, result.getId());
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void shouldUpdateProductPriceWhenValidPriceIsGiven() {
        Long productId = 1L;
        BigDecimal newPrice = BigDecimal.valueOf(100.00);
        ProductUpdateDTO updateDTO = new ProductUpdateDTO(productId, newPrice);

        Product product = new Product();
        product.setId(productId);
        product.setPrice(BigDecimal.valueOf(50.00));

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Product updatedProduct = productService.updateProduct(updateDTO);

        assertNotNull(updatedProduct);
        assertEquals(newPrice, updatedProduct.getPrice());
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void shouldNotUpdateProductPriceWhenInvalidPriceIsGiven() {
        Long productId = 1L;
        BigDecimal invalidPrice = BigDecimal.valueOf(-10.00);
        ProductUpdateDTO updateDTO = new ProductUpdateDTO(productId, invalidPrice);

        Product product = new Product();
        product.setId(productId);
        product.setPrice(BigDecimal.valueOf(50.00));

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Product updatedProduct = productService.updateProduct(updateDTO);

        assertNotNull(updatedProduct);
        assertEquals(BigDecimal.valueOf(50.00), updatedProduct.getPrice());
        verify(productRepository, times(1)).findById(productId);
    }

}