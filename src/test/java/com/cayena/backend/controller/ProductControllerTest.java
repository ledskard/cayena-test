package com.cayena.backend.controller;

import com.cayena.backend.entity.Product;
import com.cayena.backend.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

@WebMvcTest(ProductController.class)
@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @Test
    public void shouldCreateProduct() throws Exception {
        Product product = new Product();
        product.setId(1L);
        product.setName("Product A");
        product.setStockQuantity(10);
        product.setUnitPrice(20);
        product.setCreatedAt(new Date());
        product.setUpdatedAt(new Date());

        doNothing().when(productService).create(ArgumentMatchers.any(Product.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        verify(productService, times(1)).create(ArgumentMatchers.any(Product.class));
    }

    @Test
    public void shouldFindAllProducts() throws Exception {
        Product product = new Product();
        product.setId(1L);
        product.setName("Product A");
        product.setStockQuantity(10);
        product.setUnitPrice(20);
        product.setCreatedAt(new Date());
        product.setUpdatedAt(new Date());

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Product B");
        product2.setStockQuantity(20);
        product2.setUnitPrice(30);
        product2.setCreatedAt(new Date());
        product2.setUpdatedAt(new Date());

        List<Product> products = Arrays.asList(product, product2);

        when(productService.findAll()).thenReturn(products);

        mockMvc.perform(MockMvcRequestBuilders.get("/products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Product A"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].stockQuantity").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].unitPrice").value(20))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Product B"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].stockQuantity").value(20))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].unitPrice").value(30));

        verify(productService, times(1)).findAll();
    }


    @Test
    void testUpdateStockQuantity() throws Exception {
        Long id = 1L;
        Integer quantity = 10;

        Product product = new Product();
        product.setId(1L);
        product.setName("Product A");
        product.setStockQuantity(10);
        product.setUnitPrice(20);
        product.setCreatedAt(new Date());
        product.setUpdatedAt(new Date());
        when(productService.updateStockQuantity(id, quantity)).thenReturn(product);

        mockMvc.perform(MockMvcRequestBuilders.patch("/products/{id}", id)
                .param("quantity", quantity.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Product A"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stockQuantity").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$.unitPrice").value(20));
        verify(productService, times(1)).updateStockQuantity(id, quantity);
    }

    @Test
    public void shouldFindById() throws Exception {
        Product product = new Product(1L, "Product", 10, 100, new Date(), new Date(), null);
        when(productService.findById(1L)).thenReturn(product);

        mockMvc.perform(MockMvcRequestBuilders.get("/products/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Product"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stockQuantity").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$.unitPrice").value(100));
    }

    @Test
    public void shouldDelete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/products/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void shouldUpdate() throws Exception {
        String updatedName = "Updated Product";
        Integer updatedStockQuantity = 15;
        Integer updatedUnitPrice = 1500;
        Product updatedProduct = new Product(1L, updatedName, updatedStockQuantity, updatedUnitPrice, new Date(), new Date(), null);
    
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(updatedProduct);
    
        doNothing().when(productService).update(anyLong(), any(Product.class));
    
        mockMvc.perform(MockMvcRequestBuilders.put("/products/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isAccepted());
    
        verify(productService, times(1)).update(anyLong(), any(Product.class));
    }

}


