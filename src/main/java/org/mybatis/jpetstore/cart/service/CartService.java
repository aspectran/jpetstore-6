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
package org.mybatis.jpetstore.cart.service;

import com.aspectran.core.component.bean.annotation.Autowired;
import com.aspectran.core.component.bean.annotation.Bean;
import com.aspectran.core.component.bean.annotation.Component;
import com.aspectran.core.context.rule.type.ScopeType;
import org.mybatis.jpetstore.cart.domain.Cart;
import org.mybatis.jpetstore.cart.domain.CartItem;
import org.mybatis.jpetstore.catalog.service.CatalogService;
import org.mybatis.jpetstore.order.domain.Item;

import java.util.Iterator;

/**
 * The Class CartAction.
 *
 * @author Eduardo Macarron
 */
@Component
@Bean(id = "cartService", scope = ScopeType.SESSION)
public class CartService {

    @Autowired
    public transient CatalogService catalogService;

    private final Cart cart = new Cart();

    public Cart getCart() {
        return cart;
    }

    public void addItemToCart(String itemId) {
        if (cart.containsItemId(itemId)) {
            cart.incrementQuantityByItemId(itemId);
        } else {
            // isInStock is a "real-time" property that must be updated
            // every time an item is added to the cart, even if other
            // item details are cached.
            boolean isInStock = catalogService.isItemInStock(itemId);
            Item item = catalogService.getItem(itemId);
            cart.addItem(item, isInStock);
        }
    }

    public String removeItemFromCart(String itemId) {
        Item item = cart.removeItemById(itemId);
        if (item == null) {
            return "Attempted to remove null CartItem from Cart.";
        } else {
            return null;
        }
    }

    public Iterator<CartItem> getAllCartItems() {
        return getCart().getAllCartItems();
    }

    public void setQuantityByItemId(String itemId, int quantity) {
        getCart().setQuantityByItemId(itemId, quantity);
    }

}
