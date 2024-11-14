package br.com.baba.api_product.api.service;

import br.com.baba.api_product.api.dto.ProductFormDTO;
import br.com.baba.api_product.api.dto.ProductUpdateDTO;
import br.com.baba.api_product.api.exception.NotFoundException;
import br.com.baba.api_product.api.model.Product;
import br.com.baba.api_product.api.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
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

    @Spy
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
    void shouldCreateProduct() {
        product = new Product(productFormDTO);

        productService.createProduct(productFormDTO);

        then(productRepository).should().save(productCaptor.capture());
        var result = productCaptor.getValue();
        verify(productRepository, times(1)).save(result);
    }

    @Test
    void shouldCreateProductWithNoQuantity() {
        productFormDTO = new ProductFormDTO("", BigDecimal.TWO, null);
        product = new Product(productFormDTO);

        productService.createProduct(productFormDTO);

        then(productRepository).should().save(productCaptor.capture());
        var result = productCaptor.getValue();
        assertEquals(BigDecimal.ZERO, result.getQuantity());
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
    void shouldFindProductById() {
        Long id = anyLong();
        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        assertDoesNotThrow(() -> productService.findById(id));
        verify(productRepository, times(1)).findById(id);
    }

    @Test
    void shouldThrowNotFoundException() {
        Long id = anyLong();
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> productService.findById(id));
    }

    @Test
    void shouldUpdateProductPriceWhenValidPriceIsGiven() {
        Long productId = 1L;
        BigDecimal newPrice = BigDecimal.valueOf(100.00);
        ProductUpdateDTO updateDTO = new ProductUpdateDTO(productId, newPrice);

        product = new Product();
        product.setPrice(BigDecimal.valueOf(50.00));

        doReturn(product).when(productService).findById(productId);

        Product updatedProduct = productService.updateProduct(updateDTO);

        assertNotNull(updatedProduct);
        assertEquals(newPrice, updatedProduct.getPrice());
    }

    @Test
    void shouldNotUpdateProductPriceWhenInvalidPriceIsGiven() {
        Long productId = 1L;
        ProductUpdateDTO updateDTO = new ProductUpdateDTO(productId, BigDecimal.valueOf(-10.00));
        product = new Product();
        product.setPrice(BigDecimal.valueOf(50.00));

        doReturn(product).when(productService).findById(productId);
        Product updatedProduct = productService.updateProduct(updateDTO);

        assertNotNull(updatedProduct);
        assertEquals(BigDecimal.valueOf(50.00), updatedProduct.getPrice());
    }

}