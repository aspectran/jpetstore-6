/**
 *    Copyright 2010-2018 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.jpetstore.service;

import com.aspectran.core.component.bean.annotation.Autowired;
import com.aspectran.core.component.bean.annotation.Bean;
import com.aspectran.core.component.bean.annotation.Component;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.jpetstore.dao.SimpleSqlSession;
import org.mybatis.jpetstore.domain.Category;
import org.mybatis.jpetstore.domain.Item;
import org.mybatis.jpetstore.domain.Product;
import org.mybatis.jpetstore.mapper.CategoryMapper;
import org.mybatis.jpetstore.mapper.ItemMapper;
import org.mybatis.jpetstore.mapper.ProductMapper;

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

  private final SqlSession sqlSession;

  @Autowired
  public CatalogService(SimpleSqlSession sqlSession) {
    this.sqlSession = sqlSession;
  }

  public List<Category> getCategoryList() {
    CategoryMapper categoryMapper = sqlSession.getMapper(CategoryMapper.class);
    return categoryMapper.getCategoryList();
  }

  public Category getCategory(String categoryId) {
    CategoryMapper categoryMapper = sqlSession.getMapper(CategoryMapper.class);
    return categoryMapper.getCategory(categoryId);
  }

  public Product getProduct(String productId) {
    ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
    return productMapper.getProduct(productId);
  }

  public List<Product> getProductListByCategory(String categoryId) {
    ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
    return productMapper.getProductListByCategory(categoryId);
  }

  /**
   * Search product list.
   *
   * @param keywords
   *          the keywords
   * @return the list
   */
  public List<Product> searchProductList(String keywords) {
    ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
    List<Product> products = new ArrayList<>();
    for (String keyword : keywords.split("\\s+")) {
      products.addAll(productMapper.searchProductList("%" + keyword.toLowerCase() + "%"));
    }
    return products;
  }

  public List<Item> getItemListByProduct(String productId) {
    ItemMapper itemMapper = sqlSession.getMapper(ItemMapper.class);
    return itemMapper.getItemListByProduct(productId);
  }

  public Item getItem(String itemId) {
    ItemMapper itemMapper = sqlSession.getMapper(ItemMapper.class);
    return itemMapper.getItem(itemId);
  }

  public boolean isItemInStock(String itemId) {
    ItemMapper itemMapper = sqlSession.getMapper(ItemMapper.class);
    return itemMapper.getInventoryQuantity(itemId) > 0;
  }
}