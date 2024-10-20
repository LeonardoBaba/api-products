package br.com.baba.api_produtct.api.service;

import br.com.baba.api_produtct.api.dto.ProductFormDTO;
import br.com.baba.api_produtct.api.dto.ProductUpdateDTO;
import br.com.baba.api_produtct.api.model.Product;
import br.com.baba.api_produtct.api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product updateProduct(ProductUpdateDTO productUpdateDTO) {
        var product = productRepository.getReferenceById(productUpdateDTO.id());
        if (productUpdateDTO.price().compareTo(BigDecimal.ZERO) > 0) {
            product.setPrice(productUpdateDTO.price());
        }
        return product;
    }

    public Product findById(Long id) {
        return productRepository.getReferenceById(id);
    }

    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Product createProduct(ProductFormDTO productFormDTO) {
        return productRepository.save(new Product(productFormDTO));
    }

}
