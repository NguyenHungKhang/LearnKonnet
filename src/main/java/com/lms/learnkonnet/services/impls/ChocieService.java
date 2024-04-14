package com.lms.learnkonnet.services.impls;

import com.lms.learnkonnet.dtos.requests.choice.ChoiceRequestDto;
import com.lms.learnkonnet.dtos.requests.question.QuestionRequestDto;
import com.lms.learnkonnet.dtos.responses.choice.ChoiceDetailForStudentResponseDto;
import com.lms.learnkonnet.dtos.responses.choice.ChoiceDetailForTeacherResponseDto;
import com.lms.learnkonnet.dtos.responses.post.PostResponseDto;
import com.lms.learnkonnet.dtos.responses.question.QuestionDetailForTeacherResponseDto;
import com.lms.learnkonnet.exceptions.ApiException;
import com.lms.learnkonnet.exceptions.ResourceNotFoundException;
import com.lms.learnkonnet.models.*;
import com.lms.learnkonnet.models.enums.MemberStatus;
import com.lms.learnkonnet.models.enums.MemberType;
import com.lms.learnkonnet.repositories.*;
import com.lms.learnkonnet.services.IChoiceService;
import com.lms.learnkonnet.utils.ModelMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class ChocieService implements IChoiceService  {
    @Autowired
    private IMemberRepository memberRepository;
    @Autowired
    private ICourseRepository courseRepository;
    @Autowired
    private IUserRepository userRepository;
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
    public List<ChoiceDetailForStudentResponseDto> getDetailByStudentAndQuestion(Long questionId, Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question", "Id", questionId));
        Course course = courseRepository.findById(question.getQuiz().getExercise().getCourse().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", question.getQuiz().getExercise().getCourse().getId()));
        Optional<Member> currentUserMember = memberRepository.findByUser_IdAndCourse_Id(currentUserId, course.getId());


        if (!question.getQuiz().getExercise().getCourse().getUser().getId().equals(currentUserId) &&
                !(currentUserMember.isPresent() &&
                        currentUserMember.get().getType().equals(MemberType.STUDENT) &&
                        currentUserMember.get().getStatus().equals(MemberStatus.ACTIVED)))
            throw new ApiException("Người dùng không có quyền xem tài nguyên khóa học này");

        List<Choice> choices = choiceRepository.findAllByQuestion_Id(questionId);
        return modelMapperUtil.mapList(choices, ChoiceDetailForStudentResponseDto.class);
    }

    @Override
    public List<ChoiceDetailForTeacherResponseDto> getDetailByTeacherAndQuestion(Long questionId, Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question", "Id", questionId));
        Course course = courseRepository.findById(question.getQuiz().getExercise().getCourse().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", question.getQuiz().getExercise().getCourse().getId()));
        Optional<Member> currentUserMember = memberRepository.findByUser_IdAndCourse_Id(currentUserId, course.getId());


        if (!question.getQuiz().getExercise().getCourse().getUser().getId().equals(currentUserId) &&
                !(currentUserMember.isPresent() &&
                        currentUserMember.get().getType().equals(MemberType.STUDENT) &&
                        currentUserMember.get().getStatus().equals(MemberStatus.ACTIVED)))
            throw new ApiException("Người dùng không có quyền xem tài nguyên khóa học này");


        List<Choice> choices = choiceRepository.findAllByQuestion_Id(questionId);
        return modelMapperUtil.mapList(choices, ChoiceDetailForTeacherResponseDto.class);
    }

    @Override
    public List<ChoiceDetailForTeacherResponseDto> updateMulti(List<ChoiceRequestDto> choices) {


        Long questionId = isValidListChoice(choices);
        Question question = questionRepository.findById(questionId).get();

        List<Choice> existChoices = question.getChoices();

        for (ChoiceRequestDto c : choices) {
            if (c.getId() == null) {
                add(c);
            }
        }

        for (Choice existChoice : existChoices) {
            boolean found = choices.stream()
                    .anyMatch(dto -> dto.getId() != null && dto.getId().equals(existChoice.getId()));
            if (!found) {
                delete(existChoice.getId());
            }
        }


        for (Choice existChoice : existChoices) {
            for (ChoiceRequestDto choiceDto : choices) {
                if (choiceDto.getId() != null && choiceDto.getId().equals(existChoice.getId())) {
                    update(choiceDto.getId(), choiceDto);
                    break;
                }
            }
        }

        return modelMapperUtil.mapList(question.getChoices(), ChoiceDetailForTeacherResponseDto.class);
    }

    @Override
    public ChoiceDetailForTeacherResponseDto add(ChoiceRequestDto choice) {
        Question question = questionRepository.findById(choice.getQuestionId())
                .orElseThrow(() -> new ResourceNotFoundException("Quiz", "Id", choice.getQuestionId()));

        Choice newChoice = modelMapperUtil.mapOne(choice, Choice.class);
        newChoice.setQuestion(question);
        Choice savedChoice = choiceRepository.save(newChoice);
        return modelMapperUtil.mapOne(savedChoice, ChoiceDetailForTeacherResponseDto.class);
    }

    @Override
    public ChoiceDetailForTeacherResponseDto update(Long id, ChoiceRequestDto choice) {
        Choice existChoice = choiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz", "Id", id));

        existChoice.setContent(choice.getContent());
        existChoice.setIsCorrect(choice.getIsCorrect());
        existChoice.setOrder(choice.getOrder());

        Choice savedChoice = choiceRepository.save(existChoice);
        return modelMapperUtil.mapOne(savedChoice, ChoiceDetailForTeacherResponseDto.class);
    }

    @Override
    public Boolean softDelete(Long id) {
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

    public Long isValidListChoice(List<ChoiceRequestDto> choices) {
        HashSet<Integer> orderSet = new HashSet<>();
        Long questionId = choices.getFirst().getQuestionId();

        for (ChoiceRequestDto choice : choices) {
            if (orderSet.contains(choice.getOrder())) {
                throw new ApiException("Số thứ tự câu trả lời không hợp lệ");
            } else {
                orderSet.add(choice.getOrder());
            }

            if (questionId == null) {
                questionId = choice.getQuestionId();
            } else {
                if (!questionId.equals(choice.getQuestionId())) {
                    throw new ApiException("Câu trả lời không tới từ cùng một câu hỏi");
                }
            }
        }
        return questionId;
    }
}
