package br.com.baba.api_product.api.controller;

import br.com.baba.api_product.api.dto.ProductDetailDTO;
import br.com.baba.api_product.api.dto.ProductFormDTO;
import br.com.baba.api_product.api.dto.ProductUpdateDTO;
import br.com.baba.api_product.api.model.Product;
import br.com.baba.api_product.api.service.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/product")
@SecurityRequirement(name = "bearer-key")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity getProductById(@PathVariable Long id) {
        var product = productService.findById(id);
        return ResponseEntity.ok().body(new ProductDetailDTO(product));
    }

    @GetMapping
    public ResponseEntity getAllProducts(@PageableDefault(size = 20) Pageable pageable) {
        Page<Product> products = productService.findAll(pageable);
        return ResponseEntity.ok().body(products.map(m -> new ProductDetailDTO(m)).stream().collect(Collectors.toList()));
    }

    @PostMapping
    @Transactional
    public ResponseEntity createProduct(@Valid @RequestBody ProductFormDTO productFormDTO, UriComponentsBuilder uriBuilder) {
        var product = productService.createProduct(productFormDTO);
        var uri = uriBuilder.path("/api/product/{id}").buildAndExpand(product.getId()).toUri();
        return ResponseEntity.created(uri).body(new ProductDetailDTO(product));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    @PutMapping
    @Transactional
    public ResponseEntity updateProduct(@Valid @RequestBody ProductUpdateDTO productUpdateDTO) {
        var product = productService.updateProduct(productUpdateDTO);
        return ResponseEntity.ok().body(new ProductDetailDTO(product));
    }

}
