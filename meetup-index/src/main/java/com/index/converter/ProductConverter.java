package com.index.converter;

import com.index.entity.Product;
import com.index.entity.nested.NestedProduct;
import com.index.model.ProductDTO;

import java.util.List;

/**
 * @author David Sapozhnik
 */
public final class ProductConverter {

    private ProductConverter() {
    }

    public static List<ProductDTO> toProductDTOs(List<Product> products) {
        return products.stream()
                .map(ProductConverter::toProductDTO)
                .toList();
    }

    public static ProductDTO toProductDTO(Product user) {
        var productDTO = new ProductDTO();
        productDTO.setProductId(user.getProductId());
        productDTO.setName(user.getName());
        return productDTO;
    }

    public static List<NestedProduct> toNestedProducts(List<ProductDTO> productDTOs) {
        return productDTOs.stream()
                .map(ProductConverter::toNestedProduct)
                .toList();
    }

    public static NestedProduct toNestedProduct(ProductDTO userDTO) {
        var nestedProduct = new NestedProduct();
        nestedProduct.setProductId(userDTO.getProductId());
        nestedProduct.setName(userDTO.getName());
        return nestedProduct;
    }
}
