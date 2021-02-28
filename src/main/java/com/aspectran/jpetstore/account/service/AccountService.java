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
package com.aspectran.jpetstore.account.service;

import com.aspectran.core.component.bean.annotation.Autowired;
import com.aspectran.core.component.bean.annotation.Bean;
import com.aspectran.core.component.bean.annotation.Component;
import com.aspectran.jpetstore.account.domain.Account;
import com.aspectran.jpetstore.common.mybatis.SimpleSqlSession;
import com.aspectran.jpetstore.common.mybatis.mapper.AccountMapper;
import org.apache.ibatis.session.SqlSession;

import java.util.Optional;

/**
 * The Class AccountService.
 *
 * @author Juho Jeong
 */
@Component
@Bean("accountService")
public class AccountService {

    private final SqlSession sqlSession;

    @Autowired
    public AccountService(SimpleSqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public Account getAccount(String username) {
        return AccountMapper.getMapper(sqlSession).getAccountByUsername(username);
    }

    public Account getAccount(String username, String password) {
        return AccountMapper.getMapper(sqlSession).getAccountByUsernameAndPassword(username, password);
    }

    /**
     * Insert account.
     * @param account the account
     */
    public void insertAccount(Account account) {
        AccountMapper accountMapper = AccountMapper.getMapper(sqlSession);
        accountMapper.insertAccount(account);
        accountMapper.insertProfile(account);
        accountMapper.insertSignon(account);
    }

    /**
     * Update account.
     * @param account the account
     */
    public void updateAccount(Account account) {
        AccountMapper accountMapper = AccountMapper.getMapper(sqlSession);
        accountMapper.updateAccount(account);
        accountMapper.updateProfile(account);

        Optional.ofNullable(account.getPassword()).filter(password -> password.length() > 0)
                .ifPresent(password -> accountMapper.updateSignon(account));
    }

}
