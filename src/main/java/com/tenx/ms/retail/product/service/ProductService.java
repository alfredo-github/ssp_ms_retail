package com.tenx.ms.retail.product.service;

import com.tenx.ms.commons.rest.dto.Paginated;
import com.tenx.ms.retail.product.domain.ProductEntity;
import com.tenx.ms.retail.store.domain.StoreEntity;
import com.tenx.ms.retail.product.repository.ProductRepository;
import com.tenx.ms.retail.store.repository.StoreRepository;
import com.tenx.ms.retail.product.rest.dto.Product;
import com.tenx.ms.retail.stock.rest.dto.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StoreRepository storeRepository;


    public Long createNewProduct(Product productDto) {
        ProductEntity product = convertToProductEntity(productDto);
        product.setCount(0);
        productRepository.save(product);
        return product.getId();
    }

    public void updateStock(Stock stockDto) {
        ProductEntity entity = productRepository.findByIdAndStoreId(stockDto.getProductId(), stockDto.getStoreId()).get();
        entity.setCount(stockDto.getCount());
        productRepository.save(entity);
    }

    public Product getProductByIdAndStoreId(Long productId, Long storeId) {
        ProductEntity entity = productRepository.findByIdAndStoreId(productId, storeId).get();
        return convertToDto(entity);
    }

    public Paginated<Product> getProducts(Long storeId, String nameFilter, Pageable pageable, String baseLinkPath) {
        Page<ProductEntity> curPage = null;

        if (nameFilter != null) {
            curPage = productRepository.findByStoreIdAndNameContaining(storeId, nameFilter, pageable);
        } else {
            curPage = productRepository.findByStoreId(storeId, pageable);
        }
        List<Product> productsList = curPage.getContent().stream().map(this::convertToDto).collect(Collectors.toList());
        return Paginated.wrap(curPage, productsList, baseLinkPath);
    }

    public Product convertToDto(ProductEntity productEntity) {
        Product productDto = new Product();
        productDto.setName(productEntity.getName());
        productDto.setDescription(productEntity.getDescription());
        productDto.setSku(productEntity.getSku());
        productDto.setPrice(productEntity.getPriceAmt());
        productDto.setStoreId(productEntity.getStore().getId());
        return productDto;
    }

    public ProductEntity convertToProductEntity(Product productDto) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(productDto.getName());
        productEntity.setDescription(productDto.getDescription());
        productEntity.setSku(productDto.getSku());
        productEntity.setPriceAmt(productDto.getPrice());
        StoreEntity storeEntity = storeRepository.findById(productDto.getStoreId()).get();
        productEntity.setStore(storeEntity);
        return productEntity;
    }
}
