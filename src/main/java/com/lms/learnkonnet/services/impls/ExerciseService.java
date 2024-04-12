package com.lms.learnkonnet.services.impls;

import com.lms.learnkonnet.dtos.requests.excercise.ExerciseAccessWPasswordRequestDto;
import com.lms.learnkonnet.dtos.requests.excercise.ExerciseRequestDto;
import com.lms.learnkonnet.dtos.responses.common.PageResponse;
import com.lms.learnkonnet.dtos.responses.excercise.ExerciseDetailResponseDto;
import com.lms.learnkonnet.dtos.responses.excercise.ExerciseInfoResponseDto;
import com.lms.learnkonnet.dtos.responses.excercise.ExerciseSumaryResponseDto;
import com.lms.learnkonnet.dtos.responses.excercise.PasswordAccessExerciseResponseDto;
import com.lms.learnkonnet.exceptions.ApiException;
import com.lms.learnkonnet.exceptions.ResourceNotFoundException;
import com.lms.learnkonnet.models.Course;
import com.lms.learnkonnet.models.Exercise;
import com.lms.learnkonnet.models.Member;
import com.lms.learnkonnet.models.User;
import com.lms.learnkonnet.models.enums.MemberStatus;
import com.lms.learnkonnet.models.enums.MemberType;
import com.lms.learnkonnet.models.enums.Status;
import com.lms.learnkonnet.repositories.ICourseRepository;
import com.lms.learnkonnet.repositories.IExerciseRepository;
import com.lms.learnkonnet.repositories.IMemberRepository;
import com.lms.learnkonnet.repositories.IUserRepository;
import com.lms.learnkonnet.services.IExerciseService;
import com.lms.learnkonnet.utils.ModelMapperUtil;
import com.lms.learnkonnet.utils.SlugUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Service
public class ExerciseService implements IExerciseService {
    @Autowired
    private IMemberRepository memberRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IExerciseRepository exerciseRepository;
    @Autowired
    private ICourseRepository courseRepository;
    @Autowired
    private ModelMapperUtil modelMapperUtil;

    @Override
    public String getPasswordExercise(Long id, Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("exercise", "Id", id));
        Course course = courseRepository.findById(exercise.getCourse().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", exercise.getCourse().getId()));
        Optional<Member> currentUserMember = memberRepository.findByUser_IdAndCourse_Id(currentUserId, course.getId());

        if (!course.getUser().getId().equals(currentUserId) &&
                !(currentUserMember.isPresent() &&
                        currentUserMember.get().getType().equals(MemberType.TEACHER) &&
                        currentUserMember.get().getStatus().equals(MemberStatus.ACTIVED)))
            throw new ApiException("Người dùng không có quyền xem mật khẩu bài tập");

        return exerciseRepository.findPasswordById(id);
    }

    @Override
    public Boolean accessExcersiseWPassword(Long id, ExerciseAccessWPasswordRequestDto exerciseAccessWPassword, Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("exercise", "Id", id));
        Course course = courseRepository.findById(exercise.getCourse().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", exercise.getCourse().getId()));
        Optional<Member> currentUserMember = memberRepository.findByUser_IdAndCourse_Id(currentUserId, course.getId());

        if (!(currentUserMember.isPresent() &&
                currentUserMember.get().getType().equals(MemberType.STUDENT) &&
                currentUserMember.get().getStatus().equals(MemberStatus.ACTIVED)))
            throw new ApiException("Chỉ học sinh mới có quyền truy xuất bài tập bằng cách nhập mật khẩu");

        String password = exerciseAccessWPassword.getPassword();
        String exercisePassword = exerciseRepository.findPasswordById(id);
        return exercisePassword != null && exercisePassword.equals(password);
    }

    @Override
    public PageResponse<ExerciseSumaryResponseDto> getPageableListByCourse(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long currentUserId, Long courseId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", courseId));
        Optional<Member> currentUserMember = memberRepository.findByUser_IdAndCourse_Id(currentUserId, course.getId());
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        if (!course.getUser().getId().equals(currentUserId) &&
                !(currentUserMember.isPresent() &&
                        currentUserMember.get().getStatus().equals(MemberStatus.ACTIVED)))
            throw new ApiException("Người dùng không có quyền xem danh sách bài tập");


        Sort sort = Sort.by(sortDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);
        if (sortField == null || sortDir == null) sort = Sort.unsorted();
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<Exercise> exercisesPage;
        if (currentUserMember.isPresent() && currentUserMember.get().getType().equals(MemberType.STUDENT))
            exercisesPage = exerciseRepository.findExercisesByCourseIdAndStatus(courseId, Status.AVAIABLE, keyword, pageable);
        else
            exercisesPage = exerciseRepository.findByCourse_IdAndNameContaining(courseId, keyword, pageable);

        List<ExerciseSumaryResponseDto> exercisesDtoPage = modelMapperUtil.mapList(exercisesPage.getContent(), ExerciseSumaryResponseDto.class);

        return new PageResponse<>(
                exercisesDtoPage,
                exercisesPage.getNumber(),
                exercisesPage.getSize(),
                exercisesPage.getTotalElements(),
                exercisesPage.getTotalPages(),
                exercisesPage.isLast()
        );
    }

    @Override
    public PageResponse<ExerciseSumaryResponseDto> getPageableListBySection(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long sectionId) {


//        Sort sort = Sort.by(sortDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);
//        if (sortField == null || sortDir == null) sort = Sort.unsorted();
//        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
//        Page<Exercise> exercisesPage = exerciseRepository.findAllByExerciseSections_SectionIdAndNameContaining(sectionId, keyword, pageable);
//        List<ExerciseSumaryResponseDto> exercisesDtoPage = modelMapperUtil.mapList(exercisesPage.getContent(), ExerciseSumaryResponseDto.class);
//
//        return new PageResponse<>(
//                exercisesDtoPage,
//                exercisesPage.getNumber(),
//                exercisesPage.getSize(),
//                exercisesPage.getTotalElements(),
//                exercisesPage.getTotalPages(),
//                exercisesPage.isLast()
//        );
        return null;
    }

    @Override
    public List<ExerciseSumaryResponseDto> getAllByCourse(Long courseId, Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", courseId));
        Optional<Member> currentUserMember = memberRepository.findByUser_IdAndCourse_Id(currentUserId, course.getId());
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        if (!course.getUser().getId().equals(currentUserId) &&
                !(currentUserMember.isPresent() &&
                        currentUserMember.get().getStatus().equals(MemberStatus.ACTIVED)))
            throw new ApiException("Người dùng không có quyền xem danh sách bài tập");

        List<Exercise> exercises;
        if (currentUserMember.isPresent() && currentUserMember.get().getType().equals(MemberType.STUDENT))
            exercises = exerciseRepository.findExercisesByCourseIdAndStatus(courseId, Status.AVAIABLE);
        else
            exercises = exerciseRepository.findAllByCourse_Id(courseId);

        return modelMapperUtil.mapList(exercises, ExerciseSumaryResponseDto.class);
    }

    @Override
    public List<ExerciseSumaryResponseDto> getAllBySection(Long sectionId) {
//        List<Exercise> exercises = exerciseRepository.findAllByExerciseSections_SectionId(sectionId);
//        return modelMapperUtil.mapList(exercises, ExerciseSumaryResponseDto.class);
        return null;
    }

    @Override
    public ExerciseDetailResponseDto getDetailById(Long id, Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("exercise", "Id", id));
        Course course = courseRepository.findById(exercise.getCourse().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", exercise.getCourse().getId()));
        Optional<Member> currentUserMember = memberRepository.findByUser_IdAndCourse_Id(currentUserId, course.getId());
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        if (!course.getUser().getId().equals(currentUserId) &&
                !(currentUserMember.isPresent() &&
                        currentUserMember.get().getStatus().equals(MemberStatus.ACTIVED)))
            throw new ApiException("Người dùng không có quyền xem bài tập");

        Exercise existExercise;
        if (currentUserMember.isPresent() && currentUserMember.get().getType().equals(MemberType.STUDENT))
            existExercise = exerciseRepository.findByIdAndStatusAndIsDeletedFalse(id, Status.AVAIABLE)
                    .orElseThrow(() -> new ApiException("Học sinh không thể xem bài tập này"));
        else
            existExercise = exercise;

        return modelMapperUtil.mapOne(existExercise, ExerciseDetailResponseDto.class);
    }

    @Override
    public ExerciseSumaryResponseDto getSumaryById(Long id, Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("exercise", "Id", id));
        Course course = courseRepository.findById(exercise.getCourse().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", exercise.getCourse().getId()));
        Optional<Member> currentUserMember = memberRepository.findByUser_IdAndCourse_Id(currentUserId, course.getId());
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        if (!course.getUser().getId().equals(currentUserId) &&
                !(currentUserMember.isPresent() &&
                        currentUserMember.get().getStatus().equals(MemberStatus.ACTIVED)))
            throw new ApiException("Người dùng không có quyền xem bài tập");

        Exercise existExercise;
        if (currentUserMember.isPresent() && currentUserMember.get().getType().equals(MemberType.STUDENT))
            existExercise = exerciseRepository.findByIdAndStatusAndIsDeletedFalse(id, Status.AVAIABLE)
                    .orElseThrow(() -> new ApiException("Học sinh không thể xem bài tập này"));
        else
            existExercise = exercise;
        return modelMapperUtil.mapOne(existExercise, ExerciseSumaryResponseDto.class);
    }

    @Override
    public ExerciseInfoResponseDto getInfoById(Long id, Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("exercise", "Id", id));
        Course course = courseRepository.findById(exercise.getCourse().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", exercise.getCourse().getId()));
        Optional<Member> currentUserMember = memberRepository.findByUser_IdAndCourse_Id(currentUserId, course.getId());
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        if (!course.getUser().getId().equals(currentUserId) &&
                !(currentUserMember.isPresent() &&
                        currentUserMember.get().getStatus().equals(MemberStatus.ACTIVED)))
            throw new ApiException("Người dùng không có quyền xem bài tập");

        Exercise existExercise;
        if (currentUserMember.isPresent() && currentUserMember.get().getType().equals(MemberType.STUDENT))
            existExercise = exerciseRepository.findByIdAndStatusAndIsDeletedFalse(id, Status.AVAIABLE)
                    .orElseThrow(() -> new ApiException("Học sinh không thể xem bài tập này"));
        else
            existExercise = exercise;
        return modelMapperUtil.mapOne(existExercise, ExerciseInfoResponseDto.class);
    }

    @Override
    public ExerciseInfoResponseDto add(ExerciseRequestDto exercise, Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Course course = courseRepository.findById(exercise.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", exercise.getCourseId()));
        Optional<Member> currentUserMember = memberRepository.findByUser_IdAndCourse_Id(currentUserId, exercise.getCourseId());
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        if (!course.getUser().getId().equals(currentUserId) &&
                !(currentUserMember.isPresent() &&
                        currentUserMember.get().getType().equals(MemberType.TEACHER) &&
                        currentUserMember.get().getStatus().equals(MemberStatus.ACTIVED)))
            throw new ApiException("Người dùng không có quyền thêm bài tập");

        if (!exercise.getStartedAt().before(exercise.getEndedAt()))
            throw new ApiException("Thời gian bắt đầu phải trước thời gian kết thúc");


        if (currentTimestamp.before(exercise.getStartedAt())) {
            exercise.setStatus(Status.NOT_STARTED);
        } else if ((currentTimestamp.after(exercise.getStartedAt()) || currentTimestamp.equals(exercise.getStartedAt())) &&
                currentTimestamp.before(exercise.getEndedAt()) &&
                !exercise.getStatus().equals(Status.SUSPENDED)) {
            exercise.setStatus(Status.AVAIABLE);
        } else if (currentTimestamp.after(exercise.getEndedAt()) || currentTimestamp.equals((exercise.getEndedAt()))) {
            exercise.setStatus(Status.ENDED);
        }

        if (exercise.getDuration() != null)
            if (Duration.between(exercise.getStartedAt().toInstant(), exercise.getEndedAt().toInstant()).toMinutes() < exercise.getDuration())
                throw new ApiException("Thời gian làm bài ngắn hơn thời gian cho phép");

        Exercise newExercise = modelMapperUtil.mapOne(exercise, Exercise.class);
        newExercise.setSlug(SlugUtils.generateSlug(newExercise.getName()));
        newExercise.setCourse(course);
        Exercise savedExercise = exerciseRepository.save(newExercise);
        return modelMapperUtil.mapOne(savedExercise, ExerciseInfoResponseDto.class);
    }

    @Override
    public ExerciseInfoResponseDto update(Long id, ExerciseRequestDto exercise, Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Course course = courseRepository.findById(exercise.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", exercise.getCourseId()));
        Optional<Member> currentUserMember = memberRepository.findByUser_IdAndCourse_Id(currentUserId, exercise.getCourseId());
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        Exercise existExercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("exercise", "Id", id));

        if (!course.getUser().getId().equals(currentUserId) &&
                !(currentUserMember.isPresent() &&
                        currentUserMember.get().getType().equals(MemberType.TEACHER) &&
                        currentUserMember.get().getStatus().equals(MemberStatus.ACTIVED)))
            throw new ApiException("Người dùng không có quyền chỉnh sửa bài tập");

        if (!exercise.getStartedAt().before(exercise.getEndedAt()))
            throw new ApiException("Thời gian bắt đầu phải trước thời gian kết thúc");


        if (currentTimestamp.before(exercise.getStartedAt())) {
            exercise.setStatus(Status.NOT_STARTED);
        } else if ((currentTimestamp.after(exercise.getStartedAt()) || currentTimestamp.equals(exercise.getStartedAt())) &&
                currentTimestamp.before(exercise.getEndedAt()) &&
                !exercise.getStatus().equals(Status.SUSPENDED)) {
            exercise.setStatus(Status.AVAIABLE);
        } else if (currentTimestamp.after(exercise.getEndedAt()) || currentTimestamp.equals((exercise.getEndedAt()))) {
            exercise.setStatus(Status.ENDED);
        }

        if (exercise.getDuration() != null)
            if (Duration.between(exercise.getStartedAt().toInstant(), exercise.getEndedAt().toInstant()).toMinutes() < exercise.getDuration())
                throw new ApiException("Thời gian làm bài ngắn hơn thời gian cho phép");

        existExercise.setName(exercise.getName());
        existExercise.setDesc(exercise.getDesc());
        existExercise.setSlug(SlugUtils.generateSlug(existExercise.getName()));
        existExercise.setExerciseType(exercise.getExerciseType());
        existExercise.setIsHasPassword(exercise.getIsHasPassword());
        existExercise.setPassword(exercise.getPassword());
        existExercise.setIsReviewed(exercise.getIsReviewed());
        existExercise.setIsShowScore(exercise.getIsShowScore());
        existExercise.setIsShowAnswer(exercise.getIsShowAnswer());
        existExercise.setDuration(exercise.getDuration());
        existExercise.setStartedAt(exercise.getStartedAt());
        existExercise.setEndedAt(exercise.getEndedAt());
        existExercise.setStatus(exercise.getStatus());

        Exercise savedExercise = exerciseRepository.save(existExercise);
        return modelMapperUtil.mapOne(savedExercise, ExerciseInfoResponseDto.class);
    }

    @Override
    public Boolean softDelete(Long id, Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Exercise existExercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("exercise", "Id", id));
        Course course = courseRepository.findById(existExercise.getCourse().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", existExercise.getCourse().getId()));
        Optional<Member> currentUserMember = memberRepository.findByUser_IdAndCourse_Id(currentUserId, course.getId());
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());


        if (!course.getUser().getId().equals(currentUserId) &&
                !(currentUserMember.isPresent() &&
                        currentUserMember.get().getType().equals(MemberType.TEACHER) &&
                        currentUserMember.get().getStatus().equals(MemberStatus.ACTIVED)))
            throw new ApiException("Người dùng không có quyền xóa tạm thời bài tập");

        existExercise.setIsDeleted(!existExercise.getIsDeleted());
        Exercise savedExercise = exerciseRepository.save(existExercise);
        return savedExercise.getIsDeleted();
    }

    @Override
    public Boolean delete(Long id, Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Exercise existExercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("exercise", "Id", id));
        Course course = courseRepository.findById(existExercise.getCourse().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", existExercise.getCourse().getId()));
        Optional<Member> currentUserMember = memberRepository.findByUser_IdAndCourse_Id(currentUserId, course.getId());
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        if (!course.getUser().getId().equals(currentUserId) &&
                !(currentUserMember.isPresent() &&
                        currentUserMember.get().getType().equals(MemberType.TEACHER) &&
                        currentUserMember.get().getStatus().equals(MemberStatus.ACTIVED)))
            throw new ApiException("Người dùng không có quyền xóa bài tập");

        exerciseRepository.delete(existExercise);
        return true;
    }
}
