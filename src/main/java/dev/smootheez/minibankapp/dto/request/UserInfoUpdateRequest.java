package dev.smootheez.minibankapp.dto.request;

import lombok.*;

@Getter
@Builder
public class UserInfoUpdateRequest {
    private String firstName;
    private String lastName;
}
