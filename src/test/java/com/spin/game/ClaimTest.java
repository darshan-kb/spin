package com.spin.game;

import com.spin.game.dto.ClaimDTO;
import com.spin.game.service.ClaimService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ClaimTest {
    @Autowired
    ClaimService claimService;
//    @Test
//    void getClaimTest(){
//        List<ClaimDTO> claims =  claimService.getClaim("darshanbehere@gmail.com");
//        System.out.println(claims);
//    }
}
