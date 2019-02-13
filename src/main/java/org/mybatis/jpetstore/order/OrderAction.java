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
import com.aspectran.core.component.bean.annotation.Dispatch;
import com.aspectran.core.component.bean.annotation.Request;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import org.mybatis.jpetstore.account.domain.Account;
import org.mybatis.jpetstore.cart.CartAction;
import org.mybatis.jpetstore.cart.domain.Cart;
import org.mybatis.jpetstore.cart.service.CartService;
import org.mybatis.jpetstore.common.user.UserSessionManager;
import org.mybatis.jpetstore.order.domain.Order;
import org.mybatis.jpetstore.order.service.OrderService;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * The Class OrderAction.
 *
 * @author Eduardo Macarron
 */
public class OrderAction {

    private static final long serialVersionUID = -6171288227470176272L;

    private static final String CONFIRM_ORDER = "/WEB-INF/jsp/order/ConfirmOrder.jsp";
    private static final String LIST_ORDERS = "/WEB-INF/jsp/order/ListOrders.jsp";
    private static final String NEW_ORDER = "/WEB-INF/jsp/order/NewOrderForm.jsp";
    private static final String SHIPPING = "/WEB-INF/jsp/order/ShippingForm.jsp";
    private static final String VIEW_ORDER = "/WEB-INF/jsp/order/ViewOrder.jsp";

    @Autowired
    public transient OrderService orderService;

    @Autowired
    public transient CartService cartService;

    @Autowired
    public UserSessionManager sessionManager;

    private Order order = new Order();
    private boolean shippingAddressRequired;
    private boolean confirmed;
    private List<Order> orderList;


    public int getOrderId() {
        return order.getOrderId();
    }

    public void setOrderId(int orderId) {
        order.setOrderId(orderId);
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public boolean isShippingAddressRequired() {
        return shippingAddressRequired;
    }

    public void setShippingAddressRequired(boolean shippingAddressRequired) {
        this.shippingAddressRequired = shippingAddressRequired;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public List<String> getCreditCardTypes() {
        return CARD_TYPE_LIST;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    /**
     * List orders.
     */
    @Request("/listOrders")
    @Dispatch("order/ListOrders")
    @Action(id = "orderList")
    public List<Order> listOrders() {
        Account account = sessionManager.getUserSession().getAccount();
        return orderService.getOrdersByUsername(account.getUsername());
    }

    /**
     * New order form.
     */
    @Request("/newOrderForm")
    @Dispatch("order/NewOrderForm")
    @Action(id = "order")
    public Order newOrderForm(Translet translet) {
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
     *
     * @return the resolution
     */
    public Resolution newOrder() {
        HttpSession session = context.getRequest().getSession();

        if (shippingAddressRequired) {
            shippingAddressRequired = false;
            return new ForwardResolution(SHIPPING);
        } else if (!isConfirmed()) {
            return new ForwardResolution(CONFIRM_ORDER);
        } else if (getOrder() != null) {

            orderService.insertOrder(order);

            CartAction cartBean = (CartAction)session.getAttribute("/actions/Cart.action");
            cartBean.clear();

            setMessage("Thank you, your order has been submitted.");

            return new ForwardResolution(VIEW_ORDER);
        } else {
            setMessage("An error occurred processing your order (order was null).");
            return new ForwardResolution(ERROR);
        }
    }

    /**
     * View order.
     *
     * @return the resolution
     */
    public Resolution viewOrder() {
        HttpSession session = context.getRequest().getSession();

        AccountActionBean accountBean = (AccountActionBean)session.getAttribute("accountBean");

        order = orderService.getOrder(order.getOrderId());

        if (accountBean.getAccount().getUsername().equals(order.getUsername())) {
            return new ForwardResolution(VIEW_ORDER);
        } else {
            order = null;
            setMessage("You may only view your own orders.");
            return new ForwardResolution(ERROR);
        }
    }

    /**
     * Clear.
     */
    public void clear() {
        order = new Order();
        shippingAddressRequired = false;
        confirmed = false;
        orderList = null;
    }

}
