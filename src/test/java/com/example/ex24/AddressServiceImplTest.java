package com.example.ex24;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressServiceImplTest {

    @Autowired
    private AddressService addressService;

    @Test
    void getAll() {
        List<Address> list=addressService.getAll();
        Assertions.assertEquals(1,list.size());
    }

    @Test
    void findByZip() {
        boolean isFound;
        List<Address> list1=addressService.findByZip("012345");
        if (list1!=null){
            isFound=true;
        }else {isFound=false;}
        Assertions.assertTrue(isFound);
    }

    @Test
    void findByText() {
        boolean isFound;
        List<Address> list2=addressService.findByZip("Giza");
        if (list2!=null){
            isFound=true;
        }else {isFound=false;}
        Assertions.assertTrue(isFound);
    }

}
