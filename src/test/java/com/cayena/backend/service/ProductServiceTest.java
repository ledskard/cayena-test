package com.cayena.backend.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.cayena.backend.entity.Product;
import com.cayena.backend.exceptions.NotFoundException;
import com.cayena.backend.repository.ProductRepository;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;
    

   
    @Test
    public void createProductTest() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Product Test");
        product.setStockQuantity(10);
        product.setUnitPrice(50);
        productService.create(product);
        verify(productRepository, times(1)).save(product);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createProductWithNullNameTest() {
        Product product = new Product();
        product.setId(1L);
        product.setStockQuantity(10);
        product.setUnitPrice(50);
        product.setName(null);
        productService.create(product);
    }

    @Test
    public void findAllProductsTest() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Product Test");
        product.setStockQuantity(10);
        product.setUnitPrice(50);
        productService.create(product);
        List<Product> productList = new ArrayList<>();
        productList.add(product);

        when(productRepository.findAll()).thenReturn(productList);

        List<Product> result = productService.findAll();
        assertEquals(1, result.size());
        assertEquals(product, result.get(0));
    }

    @Test
    public void findProductByIdTest() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Product Test");
        product.setStockQuantity(10);
        product.setUnitPrice(50);
        productService.create(product);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product result = productService.findById(1L);

        assertEquals(product, result);
    }

    @Test(expected = NotFoundException.class)
    public void findProductByIdNotFoundTest() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        productService.findById(1L);
    }

    @Test
    public void updateProductTest() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Product Test");
        product.setStockQuantity(10);
        product.setUnitPrice(50);
        productService.create(product);
        Product newProductInfo = new Product();
        newProductInfo.setName("Updated Name");
        newProductInfo.setStockQuantity(20);
        newProductInfo.setUnitPrice(100);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        productService.update(1L, newProductInfo);

        assertEquals("Updated Name", product.getName());
        assertEquals(20, product.getStockQuantity().intValue());
        assertEquals(100, product.getUnitPrice().intValue());
    }


    @Test
    public void updateStockQuantityTest() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Product Test");
        product.setStockQuantity(10);
        product.setUnitPrice(50);
        productService.create(product);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product result = productService.updateStockQuantity(1L, 5);

        assertEquals(15, result.getStockQuantity().intValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateStockQuantityWithNegativeValueTest() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Product Test");
        product.setStockQuantity(10);
        product.setUnitPrice(50);
        productService.create(product);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        productService.updateStockQuantity(1L, -15);
    }

    @Test(expected = NotFoundException.class)
    public void updateStockQuantityNotFoundTest() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        productService.updateStockQuantity(1L, 5);
    }

    @Test(expected = NotFoundException.class)
    public void deleteProductNotFoundTest() {
        when(productRepository.existsById(1L)).thenReturn(false);

        productService.delete(1L);
    }

    @Test
    public void deleteProductTest() {
        when(productRepository.existsById(1L)).thenReturn(true);

        productService.delete(1L);
        verify(productRepository, times(1)).deleteById(1L);
    }
}