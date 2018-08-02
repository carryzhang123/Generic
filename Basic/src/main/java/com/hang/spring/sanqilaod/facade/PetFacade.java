package com.hang.spring.sanqilaod.facade;

import com.hang.spring.sanqilaod.annotation.ReceiveEvent;
import com.hang.spring.sanqilaod.event.PetEvent;
import com.hang.spring.sanqilaod.model.PetInfo;
import org.springframework.stereotype.Component;

/**
 * @author ZhangHang
 * @create 2018-06-07 15:45
 **/
@Component
public class PetFacade {
    @ReceiveEvent
    public void petInfo(PetEvent event){
        PetInfo petInfo= (PetInfo) event.getObject();
        System.out.println(petInfo.getLevel()+"    "+petInfo.getExp());
    }
}
