/**
 * Copyright 2010-2017 the original author or authors.
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
package org.mybatis.jpetstore.catalog;

import com.aspectran.core.activity.Translet;
import com.aspectran.core.component.bean.annotation.Autowired;
import com.aspectran.core.component.bean.annotation.Bean;
import com.aspectran.core.component.bean.annotation.Component;
import com.aspectran.core.component.bean.annotation.Dispatch;
import com.aspectran.core.component.bean.annotation.Request;
import com.aspectran.core.util.StringUtils;
import org.mybatis.jpetstore.catalog.domain.Category;
import org.mybatis.jpetstore.catalog.domain.Product;
import org.mybatis.jpetstore.catalog.service.CatalogService;
import org.mybatis.jpetstore.order.domain.Item;

import java.util.List;

/**
 * The Class CatalogAction.
 *
 * @author Eduardo Macarron
 */
@Component
@Bean("catalogAction")
public class CatalogAction {

    @Autowired
    public transient CatalogService catalogService;

    @Request("/viewMain")
    @Dispatch("catalog/Main")
    public void viewMain() {
    }

    /**
     * View category.
     */
    @Request("/viewCategory")
    @Dispatch("catalog/Category")
    public void viewCategory(Translet translet) {
        String categoryId = translet.getParameter("categoryId");
        if (categoryId != null) {
            List<Product> productList = catalogService.getProductListByCategory(categoryId);
            Category category = catalogService.getCategory(categoryId);
            translet.setAttribute("productList", productList);
            translet.setAttribute("category", category);
        }
    }

    /**
     * View product.
     */
    @Request("/viewProduct")
    @Dispatch("catalog/Product")
    public void viewProduct(Translet translet) {
        String productId = translet.getParameter("productId");
        if (productId != null) {
            List<Item> itemList = catalogService.getItemListByProduct(productId);
            Product product = catalogService.getProduct(productId);
            translet.setAttribute("itemList", itemList);
            translet.setAttribute("product", product);
        }
    }

    /**
     * View item.
     */
    @Request("/viewItem")
    @Dispatch("catalog/Item")
    public void viewItem(Translet translet) {
        String itemId = translet.getParameter("itemId");
        Item item = catalogService.getItem(itemId);
        Product product = item.getProduct();
        translet.setAttribute("item", item);
        translet.setAttribute("product", product);
    }

    /**
     * Search products.
     */
    @Request("/searchProducts")
    @Dispatch("catalog/SearchProducts")
    public void searchProducts(Translet translet) {
        String keyword = translet.getParameter("keyword");
        if (!StringUtils.isEmpty(keyword)) {
            List<Product> productList = catalogService.searchProductList(keyword.toLowerCase());
            translet.setAttribute("productList", productList);
        }
    }

}
