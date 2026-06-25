package org.yearup.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.yearup.models.Product;
import org.yearup.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    public void search_withNoFilters_shouldReturnAllProducts() {
        // arrange
        Product smartPhone = new Product(1, "Smartphone", 499.99, 1, "phone", "Black", 50, false, "smartphone.jpg");
        Product godOfWar = new Product(2, "God of War", 49.99, 1, "game", "Action", 40, true, "god-of-war.jpg");
        Product haloInfinite = new Product(3, "Halo Infinite", 59.99, 1, "game", "Shooter", 35, false, "halo-infinite.jpg");
        when(productRepository.findAll()).thenReturn(List.of(smartPhone, godOfWar, haloInfinite));

        // act
        List<Product> found = productService.search(null, null, null, null);

        // assert
        assertEquals(3, found.size());
    }

    @Test
    public void update_withNewStockValue_shouldSaveStock()
    {
        // arrange
        Product existing = new Product(1, "Smartphone", 499.99, 1, "desc", "Black", 50, false, "smartphone.jpg");
        Product newData  = new Product(1, "Smartphone", 499.99, 1, "desc", "Black", 999, false, "smartphone.jpg");

        when(productRepository.findById(1)).thenReturn(Optional.of(existing));
        when(productRepository.save(existing)).thenReturn(existing);

        // act
        Product result = productService.update(1, newData);

        // assert
        assertEquals(999, result.getStock(), "Because update() should copy the new stock value onto the existing product.");
        verify(productRepository).save(existing);
    }
}