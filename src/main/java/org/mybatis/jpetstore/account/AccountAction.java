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
package org.mybatis.jpetstore.account;

import com.aspectran.core.activity.Translet;
import com.aspectran.core.component.bean.annotation.Autowired;
import com.aspectran.core.component.bean.annotation.Bean;
import com.aspectran.core.component.bean.annotation.Component;
import com.aspectran.core.component.bean.annotation.Dispatch;
import com.aspectran.core.component.bean.annotation.Redirect;
import com.aspectran.core.component.bean.annotation.Request;
import com.aspectran.core.component.bean.annotation.RequestToPost;
import org.mybatis.jpetstore.account.domain.Account;
import org.mybatis.jpetstore.account.service.AccountService;
import org.mybatis.jpetstore.catalog.domain.Product;
import org.mybatis.jpetstore.catalog.service.CatalogService;
import org.mybatis.jpetstore.common.user.UserSession;
import org.mybatis.jpetstore.common.user.UserSessionManager;

import java.util.List;

/**
 * The Class AccountAction.
 *
 * @author Eduardo Macarron
 */
@Component
@Bean("accountAction")
public class AccountAction {

    @Autowired
    public AccountService accountService;

    @Autowired
    public CatalogService catalogService;

    @Autowired
    public UserSessionManager sessionManager;

    @Request("/account/newAccountForm")
    @Dispatch("account/NewAccountForm")
    public void newAccountForm(Translet translet) {
        translet.setAttribute("staticCodes", translet.getProperty("staticCodes"));
    }

    /**
     * New account.
     */
    @Request("/account/newAccount")
    @Redirect("/viewMain")
    public void newAccount(Account account) {
        accountService.insertAccount(account);
        account = accountService.getAccount(account.getUsername());
        List<Product> products = catalogService.getProductListByCategory(account.getFavouriteCategoryId());
        UserSession userSession = new UserSession();
        userSession.setAccount(account);
        userSession.setProducts(products);
        sessionManager.save(userSession);
    }

    /**
     * Edits the account form.
     */
    @Request("/account/editAccountForm")
    @Dispatch("account/EditAccountForm")
    public void editAccountForm(Translet translet) {
        translet.setAttribute("staticCodes", translet.getProperty("staticCodes"));
    }

    /**
     * Edits the account.
     */
    @RequestToPost("/account/editAccount")
    @Redirect("/catalog/")
    public void editAccount(Account account) {
        String username = sessionManager.getUserSession().getAccount().getUsername();
        account.setUsername(username);
        accountService.updateAccount(account);
        account = accountService.getAccount(account.getUsername());
        List<Product> products = catalogService.getProductListByCategory(account.getFavouriteCategoryId());
        UserSession userSession = sessionManager.getUserSession();
        userSession.setAccount(account);
        userSession.setProducts(products);
        sessionManager.save(userSession);
    }

    /**
     * Signon form.
     */
    @Request("/account/signonForm")
    @Dispatch("account/SignonForm")
    public void signonForm() {
    }

    /**
     * Signon.
     */
    @RequestToPost("/account/signon")
    @Redirect("/catalog/")
    public void signon(Translet translet, String username, String password) {
        Account account = accountService.getAccount(username, password);
        if (account == null) {
            translet.redirect("/account/signonForm?retry=true");
        } else {
            account.setPassword(null);
            List<Product> products = catalogService.getProductListByCategory(account.getFavouriteCategoryId());
            UserSession userSession = new UserSession();
            userSession.setAccount(account);
            userSession.setProducts(products);
            sessionManager.save(userSession);
        }
    }

    /**
     * Signoff.
     */
    @Request("/account/signoff")
    @Redirect("/catalog/")
    public void signoff() {
        sessionManager.expire();
    }

}
