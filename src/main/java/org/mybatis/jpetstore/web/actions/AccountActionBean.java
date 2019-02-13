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
import com.aspectran.core.component.bean.annotation.Redirect;
import com.aspectran.core.component.bean.annotation.Request;
import com.aspectran.core.context.rule.type.ScopeType;
import org.mybatis.jpetstore.domain.Account;
import org.mybatis.jpetstore.domain.Product;
import org.mybatis.jpetstore.service.AccountService;
import org.mybatis.jpetstore.service.CatalogService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The Class AccountActionBean.
 *
 * @author Eduardo Macarron
 */
@Component
@Bean(id = "accountActionBean", scope = ScopeType.SESSION)
public class AccountActionBean {

    private static final List<String> LANGUAGE_LIST;
    private static final List<String> CATEGORY_LIST;

    @Autowired
    public transient AccountService accountService;
    @Autowired
    public transient CatalogService catalogService;

    private Account account = new Account();
    private List<Product> myList;
    private boolean authenticated;

    static {
        LANGUAGE_LIST = Collections.unmodifiableList(Arrays.asList("english", "japanese"));
        CATEGORY_LIST = Collections.unmodifiableList(Arrays.asList("FISH", "DOGS", "REPTILES", "CATS", "BIRDS"));
    }

    public Account getAccount() {
        return this.account;
    }

    public String getUsername() {
        return account.getUsername();
    }

    public void setUsername(String username) {
        account.setUsername(username);
    }

    public String getPassword() {
        return account.getPassword();
    }

    public void setPassword(String password) {
        account.setPassword(password);
    }

    public List<Product> getMyList() {
        return myList;
    }

    public void setMyList(List<Product> myList) {
        this.myList = myList;
    }

    public List<String> getLanguages() {
        return LANGUAGE_LIST;
    }

    public List<String> getCategories() {
        return CATEGORY_LIST;
    }

    @Request("/newAccountForm")
    @Dispatch("account/NewAccountForm")
    public void newAccountForm() {
    }

    /**
     * New account.
     */
    @Request("/newAccount")
    @Redirect("/viewMain")
    public void newAccount() {
        accountService.insertAccount(account);
        account = accountService.getAccount(account.getUsername());
        myList = catalogService.getProductListByCategory(account.getFavouriteCategoryId());
        authenticated = true;
    }

    /**
     * Edits the account form.
     */
    @Request("/editAccountForm")
    @Dispatch("account/EditAccountForm")
    public void editAccountForm() {
    }

    /**
     * Edits the account.
     */
    @Request("/editAccount")
    @Redirect("/viewMain")
    public void editAccount() {
        accountService.updateAccount(account);
        account = accountService.getAccount(account.getUsername());
        myList = catalogService.getProductListByCategory(account.getFavouriteCategoryId());
    }

    /**
     * Signon form.
     */
    @Request("/signonForm")
    @Redirect("account/SignonForm")
    public void signonForm() {
    }

    /**
     * Signon.
     */
    @Request("/signon")
    @Action(id = "result")
    @Redirect("/viewMain")
    public String signon(Translet translet) {
        account = accountService.getAccount(getUsername(), getPassword());
        if (account == null) {
            String result = "Invalid username or password.  Signon failed.";
            clear();
            return result;
        } else {
            account.setPassword(null);
            myList = catalogService.getProductListByCategory(account.getFavouriteCategoryId());
            authenticated = true;
            translet.getSessionAdapter().setAttribute("accountBean", this);
            return null;
        }
    }

    /**
     * Signoff.
     */
    @Request("/signoff")
    @Redirect("/viewMain")
    public void signoff(Translet translet) {
        translet.getSessionAdapter().invalidate();
        clear();
    }

    /**
     * Checks if is authenticated.
     *
     * @return true, if is authenticated
     */
    public boolean isAuthenticated() {
        return (authenticated && account != null && account.getUsername() != null);
    }

    /**
     * Clear.
     */
    public void clear() {
        account = new Account();
        myList = null;
        authenticated = false;
    }

}
