/**
 * Copyright 2010-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mybatis.jpetstore.catalog.service;

import com.aspectran.core.component.bean.annotation.Autowired;
import com.aspectran.core.component.bean.annotation.Bean;
import com.aspectran.core.component.bean.annotation.Component;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.jpetstore.catalog.domain.Category;
import org.mybatis.jpetstore.catalog.domain.Product;
import org.mybatis.jpetstore.common.mybatis.SimpleSqlSession;
import org.mybatis.jpetstore.common.mybatis.mapper.CategoryMapper;
import org.mybatis.jpetstore.common.mybatis.mapper.ItemMapper;
import org.mybatis.jpetstore.common.mybatis.mapper.ProductMapper;
import org.mybatis.jpetstore.order.domain.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class CatalogService.
 *
 * @author Juho Jeong
 */
@Component
@Bean("catalogService")
public class CatalogService {

    public final SqlSession sqlSession;

    @Autowired
    public CatalogService(SimpleSqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<Category> getCategoryList() {
        return CategoryMapper.getMapper(sqlSession).getCategoryList();
    }

    public Category getCategory(String categoryId) {
        return CategoryMapper.getMapper(sqlSession).getCategory(categoryId);
    }

    public Product getProduct(String productId) {
        return ProductMapper.getMapper(sqlSession).getProduct(productId);
    }

    public List<Product> getProductListByCategory(String categoryId) {
        return ProductMapper.getMapper(sqlSession).getProductListByCategory(categoryId);
    }

    /**
     * Search product list.
     *
     * @param keywords the keywords
     * @return the list
     */
    public List<Product> searchProductList(String keywords) {
        ProductMapper productMapper = ProductMapper.getMapper(sqlSession);
        List<Product> products = new ArrayList<>();
        for (String keyword : keywords.split("\\s+")) {
            products.addAll(productMapper.searchProductList("%" + keyword.toLowerCase() + "%"));
        }
        return products;
    }

    public List<Item> getItemListByProduct(String productId) {
        return ItemMapper.getMapper(sqlSession).getItemListByProduct(productId);
    }

    public Item getItem(String itemId) {
        return ItemMapper.getMapper(sqlSession).getItem(itemId);
    }

    public boolean isItemInStock(String itemId) {
        return (ItemMapper.getMapper(sqlSession).getInventoryQuantity(itemId) > 0);
    }

}