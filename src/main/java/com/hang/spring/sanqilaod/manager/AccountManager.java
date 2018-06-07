package com.hang.spring.sanqilaod.manager;

import com.hang.spring.sanqilaod.resource.AccountResource;
import com.hang.spring.sanqilaod.reflect.Storge;
import com.hang.spring.sanqilaod.annotation.Manager;
import com.hang.spring.sanqilaod.annotation.Static;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author ZhangHang
 * @create 2018-06-04 19:52
 **/
@Component
@Manager
public class AccountManager {
    @Static
    private Storge<String, AccountResource> storges;

    public AccountResource getById(String serverId) {
        return storges.getById(serverId);
    }

    public Collection<AccountResource> getAll() {
        return storges.getAll();
    }
}
