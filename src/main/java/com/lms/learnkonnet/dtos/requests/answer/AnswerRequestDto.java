package com.lms.learnkonnet.dtos.requests.answer;

import com.lms.learnkonnet.models.relations.MemberExercise;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AnswerRequestDto {
    private Long memberExerciseId;
}
