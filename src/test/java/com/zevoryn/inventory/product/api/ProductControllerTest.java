package com.zevoryn.inventory.product.api;

import com.zevoryn.inventory.common.exception.GlobalExceptionHandler;
import com.zevoryn.inventory.product.application.ProductService;
import com.zevoryn.inventory.product.application.command.CreateProductCommand;
import com.zevoryn.inventory.product.domain.Product;
import com.zevoryn.inventory.product.domain.exception.DuplicateSkuException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@Import(GlobalExceptionHandler.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @Test
    void shouldCreateProduct() throws Exception {
        Product product = new Product(
                "IPHONE-16",
                "iPhone 16",
                "Latest iPhone model",
                new BigDecimal("79999.99")
        );

        given(productService.createProduct(any(CreateProductCommand.class)))
                .willReturn(product);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "sku": "IPHONE-16",
                                  "name": "iPhone 16",
                                  "description": "Latest iPhone model",
                                  "unitPrice": 79999.99
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(product.getId().toString()))
                .andExpect(jsonPath("$.sku").value("IPHONE-16"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    void shouldRejectInvalidProductRequest() throws Exception {
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "sku": "invalid sku",
                                  "name": "",
                                  "unitPrice": 0
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_FAILED"))
                .andExpect(jsonPath("$.fieldErrors.sku").exists())
                .andExpect(jsonPath("$.fieldErrors.name").exists())
                .andExpect(jsonPath("$.fieldErrors.unitPrice").exists());
    }

    @Test
    void shouldReturnConflictWhenSkuAlreadyExists() throws Exception {
        given(productService.createProduct(any(CreateProductCommand.class)))
                .willThrow(new DuplicateSkuException("IPHONE-16"));

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "sku": "IPHONE-16",
                              "name": "iPhone 16",
                              "description": "Latest iPhone model",
                              "unitPrice": 79999.99
                            }
                            """))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value("DUPLICATE_SKU"));
    }
}