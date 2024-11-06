package com.mycalendar.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EventRequestDto {

    String userId;

    @NotNull
    String password;

    //최대 200자 이내 제한 필수값 처리
    @NotNull
    @Size(max = 200)
    String title;
    String updated_date;


    String name;

}
