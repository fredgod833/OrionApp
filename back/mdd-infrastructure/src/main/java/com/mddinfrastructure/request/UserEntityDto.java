package com.mddinfrastructure.request;

import com.mddcore.usecases.dto.SubscriptionDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserEntityDto {
    private Long id;
    private String email;
    private String username;
    private String password;
    private List<SubscriptionDto> subscriptionList;
}
