/**
 * Copyright 2010-2018 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
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
import org.mybatis.jpetstore.order.domain.Item;
import org.mybatis.jpetstore.catalog.domain.Product;
import org.mybatis.jpetstore.common.mybatis.mapper.CategoryMapper;
import org.mybatis.jpetstore.common.mybatis.mapper.ItemMapper;
import org.mybatis.jpetstore.common.mybatis.mapper.ProductMapper;
import org.mybatis.jpetstore.common.mybatis.SimpleSqlSession;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class CatalogService.
 *
 * @author Eduardo Macarron
 */
@Component
@Bean("catalogService")
public class CatalogService {

    public SqlSession sqlSession;

    @Autowired
    public CatalogService(SimpleSqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<Category> getCategoryList() {
        return CategoryMapper.getInstance(sqlSession).getCategoryList();
    }

    public Category getCategory(String categoryId) {
        return CategoryMapper.getInstance(sqlSession).getCategory(categoryId);
    }

    public Product getProduct(String productId) {
        return ProductMapper.getInstance(sqlSession).getProduct(productId);
    }

    public List<Product> getProductListByCategory(String categoryId) {
        return ProductMapper.getInstance(sqlSession).getProductListByCategory(categoryId);
    }

    /**
     * Search product list.
     *
     * @param keywords the keywords
     * @return the list
     */
    public List<Product> searchProductList(String keywords) {
        ProductMapper productMapper = ProductMapper.getInstance(sqlSession);
        List<Product> products = new ArrayList<>();
        for (String keyword : keywords.split("\\s+")) {
            products.addAll(productMapper.searchProductList("%" + keyword.toLowerCase() + "%"));
        }
        return products;
    }

    public List<Item> getItemListByProduct(String productId) {
        return ItemMapper.getInstance(sqlSession).getItemListByProduct(productId);
    }

    public Item getItem(String itemId) {
        return ItemMapper.getInstance(sqlSession).getItem(itemId);
    }

    public boolean isItemInStock(String itemId) {
        return ItemMapper.getInstance(sqlSession).getInventoryQuantity(itemId) > 0;
    }

}