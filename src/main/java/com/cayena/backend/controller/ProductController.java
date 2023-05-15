package com.cayena.backend.controller;

import com.cayena.backend.entity.Product;
import com.cayena.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping()
    public ResponseEntity<String> create(@RequestBody Product product){
        productService.create(product);
        return ResponseEntity.status(HttpStatus.CREATED).body("created");
    }

    @GetMapping()
    public ResponseEntity<List<Product>> findAll(){
        List<Product> products = productService.findAll();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable("id") Long id){
        Product product = productService.findById(id);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody Product product){
        productService.update(id, product);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Updated");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id){
        productService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Product> updateStockQuantity(@PathVariable Long id, @RequestParam Integer quantity) {
        Product updatedProduct = productService.updateStockQuantity(id, quantity);
        return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
    }

}
