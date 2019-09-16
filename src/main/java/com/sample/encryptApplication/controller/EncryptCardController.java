package com.sample.encryptApplication.controller;


import com.sample.encryptApplication.usecase.EncryptyCard;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/encrypt")
@RestController
@RequiredArgsConstructor
public class EncryptCardController {

    private final EncryptyCard encriptyCard;

    @ApiOperation(value = "Encrypt a card")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Encrypted with success"),
            @ApiResponse(code = 404, message = "Bad request")
    })
    @GetMapping(value = "/{card}")
    public String encriptyCardApi(@PathVariable final String card){
        return encriptyCard.encrypt(card);
    }

}
