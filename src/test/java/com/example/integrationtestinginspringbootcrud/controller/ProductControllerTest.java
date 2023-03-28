package com.example.integrationtestinginspringbootcrud.controller;



import com.example.integrationtestinginspringbootcrud.entity.Product;
import com.example.integrationtestinginspringbootcrud.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllProducts() throws Exception {
        List<Product> products = Arrays.asList(
                new Product(1L, "Product 1", "Description 1",10.0),
                new Product(2L, "Product 2", "Description 2",1.0),
                new Product(3L, "Product 3", "Description 3",2.0)
        );

        when(productService.getAllProducts()).thenReturn(products);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Product 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("Description 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Product 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].description").value("Description 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].id").value(3L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name").value("Product 3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].description").value("Description 3"))
                .andDo(MockMvcResultHandlers.print());

        verify(productService, times(1)).getAllProducts();
        verifyNoMoreInteractions(productService);
    }

    @Test
    void testGetProductById() throws Exception {
        Product product = new Product(1L, "Product 1", "Description 1",1.0);

        when(productService.getProductById(1L)).thenReturn(product);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Product 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Description 1"))
                .andDo(MockMvcResultHandlers.print());

        verify(productService, times(1)).getProductById(1L);
        verifyNoMoreInteractions(productService);
    }

    @Test
    public void testAddProduct() throws Exception {
        Product product = new Product();
        product.setName("Test Product");
        product.setDescription("This is a test product.");
        product.setPrice(9.99);

        String requestBody = objectMapper.writeValueAsString(product);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/products")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateProduct() throws Exception {
        Product product = new Product();
        product.setName("Updated Product");
        product.setDescription("This product has been updated.");
        product.setPrice(19.99);

        String requestBody = objectMapper.writeValueAsString(product);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/products/1")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteProduct() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/products/1"))
                .andExpect(status().isOk());
    }
}



