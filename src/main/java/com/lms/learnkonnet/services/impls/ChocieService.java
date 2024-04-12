package com.lms.learnkonnet.services.impls;

import com.lms.learnkonnet.dtos.requests.choice.ChoiceRequestDto;
import com.lms.learnkonnet.dtos.requests.question.QuestionRequestDto;
import com.lms.learnkonnet.dtos.responses.choice.ChoiceDetailForStudentResponseDto;
import com.lms.learnkonnet.dtos.responses.choice.ChoiceDetailForTeacherResponseDto;
import com.lms.learnkonnet.dtos.responses.post.PostResponseDto;
import com.lms.learnkonnet.dtos.responses.question.QuestionDetailForTeacherResponseDto;
import com.lms.learnkonnet.exceptions.ResourceNotFoundException;
import com.lms.learnkonnet.models.*;
import com.lms.learnkonnet.repositories.IChoiceRepository;
import com.lms.learnkonnet.repositories.IMemberRepository;
import com.lms.learnkonnet.repositories.IQuestionRepository;
import com.lms.learnkonnet.repositories.IQuizRepository;
import com.lms.learnkonnet.services.IChoiceService;
import com.lms.learnkonnet.utils.ModelMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChocieService implements IChoiceService  {
    @Autowired
    private IMemberRepository memberRepository;
    @Autowired
    private IQuestionRepository questionRepository;
    @Autowired
    private IChoiceRepository choiceRepository;
    @Autowired
    private ModelMapperUtil modelMapperUtil;
    @Override
    public ChoiceDetailForStudentResponseDto getDetailByStudentAndId(Long id) {
        Choice existChoice = choiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz", "Id", id));
        return modelMapperUtil.mapOne(existChoice, ChoiceDetailForStudentResponseDto.class);
    }

    @Override
    public ChoiceDetailForTeacherResponseDto getDetailByTeacherAndId(Long id) {
        Choice existChoice = choiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz", "Id", id));
        return modelMapperUtil.mapOne(existChoice, ChoiceDetailForTeacherResponseDto.class);
    }

    @Override
    public List<ChoiceDetailForStudentResponseDto> getDetailByStudentAndQuestion(Long questionId) {
        List<Choice> choices = choiceRepository.findAllByQuestion_Id(questionId);
        return modelMapperUtil.mapList(choices, ChoiceDetailForStudentResponseDto.class);
    }

    @Override
    public List<ChoiceDetailForTeacherResponseDto> getDetailByTeacherAndQuestion(Long questionId) {
        List<Choice> choices = choiceRepository.findAllByQuestion_Id(questionId);
        return modelMapperUtil.mapList(choices, ChoiceDetailForTeacherResponseDto.class);
    }

    @Override
    public ChoiceDetailForTeacherResponseDto add(ChoiceRequestDto choice, Long currentMemberId) {
        Member currentMember = memberRepository.findById(currentMemberId)
                .orElseThrow(() -> new ResourceNotFoundException("Current member", "Id", currentMemberId));
        Question question = questionRepository.findById(choice.getQuestionId())
                .orElseThrow(() -> new ResourceNotFoundException("Quiz", "Id", choice.getQuestionId()));

        Choice newChoice = modelMapperUtil.mapOne(choice, Choice.class);
        newChoice.setQuestion(question);
        Choice savedChoice = choiceRepository.save(newChoice);
        return modelMapperUtil.mapOne(savedChoice, ChoiceDetailForTeacherResponseDto.class);
    }

    @Override
    public ChoiceDetailForTeacherResponseDto update(Long id, ChoiceRequestDto choice, Long currentMemberId) {
        Member currentMember = memberRepository.findById(currentMemberId)
                .orElseThrow(() -> new ResourceNotFoundException("Current member", "Id", currentMemberId));
        Choice existChoice = choiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz", "Id", id));

        existChoice.setContent(choice.getContent());
        existChoice.setIsCorrect(choice.getIsCorrect());
        existChoice.setOrder(choice.getOrder());

        Choice savedChoice = choiceRepository.save(existChoice);
        return modelMapperUtil.mapOne(savedChoice, ChoiceDetailForTeacherResponseDto.class);
    }

    @Override
    public Boolean softDelete(Long id, Long currentMemberId) {
        Member currentMember = memberRepository.findById(currentMemberId)
                .orElseThrow(() -> new ResourceNotFoundException("Current member", "Id", currentMemberId));
        Choice existChoice = choiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz", "Id", id));

        existChoice.setIsDeleted(!existChoice.getIsDeleted());
        Choice savedChoice = choiceRepository.save(existChoice);
        return savedChoice.getIsDeleted();

    }

    @Override
    public Boolean delete(Long id) {
        Choice existChoice = choiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz", "Id", id));
        choiceRepository.delete(existChoice);
        return true;
    }
}
