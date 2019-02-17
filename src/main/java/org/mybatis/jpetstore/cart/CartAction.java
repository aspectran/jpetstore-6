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
package org.mybatis.jpetstore.cart;

import com.aspectran.core.activity.Translet;
import com.aspectran.core.component.bean.annotation.Action;
import com.aspectran.core.component.bean.annotation.Bean;
import com.aspectran.core.component.bean.annotation.Component;
import com.aspectran.core.component.bean.annotation.Dispatch;
import com.aspectran.core.component.bean.annotation.Request;
import org.mybatis.jpetstore.cart.domain.CartItem;
import org.mybatis.jpetstore.cart.service.CartService;

import java.util.Iterator;

/**
 * The Class CartAction.
 *
 * @author Eduardo Macarron
 */
@Component
@Bean("cartAction")
public class CartAction {

    /**
     * Adds the item to cart.
     */
    @Request("/addItemToCart")
    @Dispatch("cart/Cart")
    public void addItemToCart(Translet translet) {
        String itemId = translet.getParameter("itemId");
        CartService cartService = translet.getBean("cartService");
        cartService.addItemToCart(itemId);
    }

    /**
     * Removes the item from cart.
     */
    @Request("/addItemToCart")
    @Action("result")
    @Dispatch("cart/Cart")
    public String removeItemFromCart(Translet translet) {
        String itemId = translet.getParameter("itemId");
        CartService cartService = translet.getBean("cartService");
        return cartService.removeItemFromCart(itemId);
    }

    /**
     * Update cart quantities.
     */
    @Request("/updateCartQuantities")
    @Dispatch("cart/Cart")
    public void updateCartQuantities(Translet translet) {
        CartService cartService = translet.getBean("cartService");
        Iterator<CartItem> cartItems = cartService.getAllCartItems();
        while (cartItems.hasNext()) {
            CartItem cartItem = cartItems.next();
            String itemId = cartItem.getItem().getItemId();
            try {
                int quantity = Integer.parseInt(translet.getParameter(itemId));
                cartService.setQuantityByItemId(itemId, quantity);
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

}
