package org.mybatis.jpetstore.common.user;

import org.mybatis.jpetstore.account.domain.Account;
import org.mybatis.jpetstore.catalog.domain.Product;

import java.io.Serializable;
import java.util.List;

public class UserSession implements Serializable {

    private static final long serialVersionUID = 7058892251493063652L;

    private Account account;

    private List<Product> products;

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

}
