package com.hang.spring.sanqilaod.manager;

import com.hang.spring.sanqilaod.annotation.Manager;
import com.hang.spring.sanqilaod.annotation.Static;
import com.hang.spring.sanqilaod.reflect.Storge;
import com.hang.spring.sanqilaod.resource.PetResource;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author ZhangHang
 * @create 2018-06-07 15:05
 **/
@Component
@Manager
public class PetManager {
    @Static
    private Storge<String, PetResource> storges;

    public PetResource getById(String account) {
        return storges.getById(account);
    }

    public Collection<PetResource> getAll() {
        return storges.getAll();
    }
}
