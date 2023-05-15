package com.cayena.backend.service;

import com.cayena.backend.entity.Product;
import com.cayena.backend.exceptions.NotFoundException;
import com.cayena.backend.repository.ProductRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;


    public void create(Product product){
        if (product.getName() == null || product.getName().isEmpty()) {
            throw new IllegalArgumentException("Product field 'name' is required");
        }
        productRepository.save(product);
    }
    public List<Product> findAll(){
        return productRepository.findAll();
    }
    public Product findById(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));
        return product;
    }
    public void update(Long id, Product newProductInfo) {
        Product productToUpdate = productRepository.findById(id).get();
        
        BeanUtils.copyProperties(newProductInfo, productToUpdate);
        productRepository.save(productToUpdate);
    }
    public Product updateStockQuantity(Long id, Integer quantity) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        int newStock = product.getStockQuantity() + quantity;
        if (newStock < 0) {
            throw new IllegalArgumentException("Invalid quantity");
        }

        product.setStockQuantity(newStock);
        productRepository.save(product);
        return product;
    }
    public void delete(Long id){
        if(!productRepository.existsById(id)) {
            throw new NotFoundException("Product not found with id " + id);
        }
        productRepository.deleteById(id);
    }
}   
