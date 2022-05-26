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
public class BuildingServiceImplTest {

    @Autowired
    private BuildingService buildingService;

    @Test
    void findByCreationDate() {
        boolean isFound;
        List<Building> list1=buildingService.findByCreationDate("1650");
        if (list1!=null){
            isFound=true;
        }else {isFound=false;}
        Assertions.assertTrue(isFound);
    }

    @Test
    void findByType() {
        boolean isFound;
        List<Building> list2=buildingService.findByType("Manor");
        if (list2!=null){
            isFound=true;
        }else {isFound=false;}
        Assertions.assertTrue(isFound);
    }

}

