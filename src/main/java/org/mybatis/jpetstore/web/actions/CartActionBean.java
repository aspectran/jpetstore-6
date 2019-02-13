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
package org.mybatis.jpetstore.web.actions;

import com.aspectran.core.activity.Translet;
import com.aspectran.core.component.bean.annotation.Action;
import com.aspectran.core.component.bean.annotation.Autowired;
import com.aspectran.core.component.bean.annotation.Bean;
import com.aspectran.core.component.bean.annotation.Component;
import com.aspectran.core.component.bean.annotation.Dispatch;
import com.aspectran.core.component.bean.annotation.Request;
import com.aspectran.core.context.rule.type.ScopeType;
import org.mybatis.jpetstore.domain.Cart;
import org.mybatis.jpetstore.domain.CartItem;
import org.mybatis.jpetstore.domain.Item;
import org.mybatis.jpetstore.service.CatalogService;

import java.util.Iterator;

/**
 * The Class CartActionBean.
 *
 * @author Eduardo Macarron
 */
@Component
@Bean(id = "cartActionBean", scope = ScopeType.SESSION)
public class CartActionBean {

    @Autowired
    public transient CatalogService catalogService;

    private Cart cart = new Cart();
    private String workingItemId;

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void setWorkingItemId(String workingItemId) {
        this.workingItemId = workingItemId;
    }

    /**
     * Adds the item to cart.
     */
    @Request("/addItemToCart")
    @Dispatch("cart/Cart")
    public void addItemToCart() {
        if (cart.containsItemId(workingItemId)) {
            cart.incrementQuantityByItemId(workingItemId);
        } else {
            // isInStock is a "real-time" property that must be updated
            // every time an item is added to the cart, even if other
            // item details are cached.
            boolean isInStock = catalogService.isItemInStock(workingItemId);
            Item item = catalogService.getItem(workingItemId);
            cart.addItem(item, isInStock);
        }
    }

    /**
     * Removes the item from cart.
     */
    @Request("/addItemToCart")
    @Action(id = "result")
    @Dispatch("cart/Cart")
    public String removeItemFromCart() {
        Item item = cart.removeItemById(workingItemId);
        if (item == null) {
            return "Attempted to remove null CartItem from Cart.";
        } else {
            return null;
        }
    }

    /**
     * Update cart quantities.
     */
    @Request("/updateCartQuantities")
    @Dispatch("cart/Cart")
    public void updateCartQuantities(Translet translet) {
        Iterator<CartItem> cartItems = getCart().getAllCartItems();
        while (cartItems.hasNext()) {
            CartItem cartItem = cartItems.next();
            String itemId = cartItem.getItem().getItemId();
            try {
                int quantity = Integer.parseInt(translet.getParameter(itemId));
                getCart().setQuantityByItemId(itemId, quantity);
                if (quantity < 1) {
                    cartItems.remove();
                }
            } catch (Exception e) {
                // ignore parse exceptions on purpose
            }
        }
    }

    @Request("/viewCart")
    @Dispatch("cart/Cart")
    public void viewCart() {
    }

    @Request("/checkOut")
    @Dispatch("cart/Checkout")
    public void checkOut() {
    }

    public void clear() {
        cart = new Cart();
        workingItemId = null;
    }

}
