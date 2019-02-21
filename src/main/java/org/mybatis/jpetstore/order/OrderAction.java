/**
 * Copyright 2010-2018 the original author or authors.
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
package org.mybatis.jpetstore.order;

import com.aspectran.core.activity.Translet;
import com.aspectran.core.component.bean.annotation.Action;
import com.aspectran.core.component.bean.annotation.Autowired;
import com.aspectran.core.component.bean.annotation.Bean;
import com.aspectran.core.component.bean.annotation.Component;
import com.aspectran.core.component.bean.annotation.Dispatch;
import com.aspectran.core.component.bean.annotation.Request;
import org.mybatis.jpetstore.account.domain.Account;
import org.mybatis.jpetstore.cart.domain.Cart;
import org.mybatis.jpetstore.cart.service.CartService;
import org.mybatis.jpetstore.common.user.UserSessionManager;
import org.mybatis.jpetstore.order.domain.Order;
import org.mybatis.jpetstore.order.service.OrderService;

import java.util.HashMap;
import java.util.List;

/**
 * The Class OrderAction.
 *
 * @author Eduardo Macarron
 */
@Component
@Bean("orderAction")
public class OrderAction {

    @Autowired
    public OrderService orderService;

    @Autowired
    public CartService cartService;

    @Autowired
    public UserSessionManager sessionManager;

    /**
     * List orders.
     */
    @Request("/order/listOrders")
    @Dispatch("order/ListOrders")
    @Action("orderList")
    public List<Order> listOrders() {
        Account account = sessionManager.getUserSession().getAccount();
        return orderService.getOrdersByUsername(account.getUsername());
    }

    /**
     * New order form.
     */
    @Request("/order/newOrderForm")
    @Dispatch("order/NewOrderForm")
    @Action("order")
    public Order newOrderForm(Translet translet) {
        translet.setAttribute("staticCodes", translet.getProperty("staticCodes"));
        Account account = sessionManager.getUserSession().getAccount();
        Cart cart = cartService.getCart();
        if (cart != null) {
            Order order = new Order();
            order.initOrder(account, cart);
            return order;
        } else {
            String message = "An order could not be created because a cart could not be found.";
            translet.setAttribute("message", message);
            translet.dispatch("error");
            return null;
        }
    }

    /**
     * New order.
     */
    @Request("/order/newOrder")
    public void newOrder(Translet translet,
                         Order order,
                         boolean shippingAddressRequired,
                         boolean confirmed
    ) {
        if (shippingAddressRequired) {
            translet.dispatch("order/ShippingForm");
        } else if (!confirmed) {
            translet.dispatch("order/ConfirmOrder");
        } else {
            orderService.insertOrder(order);
            Cart cart = cartService.getCart();
            cart.clear();

            translet.redirect("/viewOrder", new HashMap<String, String>() {{
                put("orderId", Integer.toString(order.getOrderId()));
            }});
        }
    }

    /**
     * View order.
     *
     * @return the resolution
     */
    @Request("/order/viewOrder")
    @Dispatch("order/ViewOrder")
    @Action("order")
    public Order viewOrder(int orderId) {
        return orderService.getOrder(orderId);
    }

}
