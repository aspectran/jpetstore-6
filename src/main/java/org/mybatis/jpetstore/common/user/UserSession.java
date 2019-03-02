/*
 * Copyright (c) 2008-2019 The Aspectran Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mybatis.jpetstore.common.user;

import org.mybatis.jpetstore.account.domain.Account;
import org.mybatis.jpetstore.cart.domain.Cart;
import org.mybatis.jpetstore.catalog.domain.Product;
import org.mybatis.jpetstore.order.domain.Order;

import java.io.Serializable;
import java.util.List;

public class UserSession implements Serializable {

    private static final long serialVersionUID = 7058892251493063652L;

    private Account account;

    private List<Product> products;

    private boolean authenticated;

    private Cart cart = new Cart();

    private Order order;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void clearOrder() {
        this.order = null;
    }

}
