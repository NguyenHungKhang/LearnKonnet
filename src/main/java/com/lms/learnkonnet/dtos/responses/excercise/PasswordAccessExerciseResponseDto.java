package com.lms.learnkonnet.dtos.responses.excercise;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PasswordAccessExerciseResponseDto {
    private Long id;
    private String password;
}
