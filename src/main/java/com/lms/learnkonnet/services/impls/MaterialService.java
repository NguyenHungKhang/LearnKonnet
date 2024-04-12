package com.lms.learnkonnet.services.impls;

import com.lms.learnkonnet.dtos.requests.material.MaterialRequestDto;
import com.lms.learnkonnet.dtos.responses.common.PageResponse;
import com.lms.learnkonnet.dtos.responses.material.MaterialDetailResponseDto;
import com.lms.learnkonnet.dtos.responses.section.SectionDetailResponseDto;
import com.lms.learnkonnet.dtos.responses.topic.TopicDetailResponseDto;
import com.lms.learnkonnet.exceptions.ApiException;
import com.lms.learnkonnet.exceptions.ResourceNotFoundException;
import com.lms.learnkonnet.models.*;
import com.lms.learnkonnet.models.enums.MemberStatus;
import com.lms.learnkonnet.models.enums.MemberType;
import com.lms.learnkonnet.models.enums.Status;
import com.lms.learnkonnet.repositories.*;
import com.lms.learnkonnet.services.IFileService;
import com.lms.learnkonnet.services.IMaterialService;
import com.lms.learnkonnet.utils.ModelMapperUtil;
import com.lms.learnkonnet.utils.SlugUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class MaterialService implements IMaterialService {
    @Autowired
    private IMemberRepository memberRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IMaterialRepository materialRepository;
    @Autowired
    private ISectionRepository sectionRepository;
    @Autowired
    private IFileRepository fileRepository;
    @Autowired
    private ICourseRepository courseRepository;
    @Autowired
    private ModelMapperUtil modelMapperUtil;
    @Override
    public PageResponse<MaterialDetailResponseDto> getPageableListByCourse(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long courseId,  Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", courseId));
        Optional<Member> currentUserMember = memberRepository.findByUser_IdAndCourse_Id(currentUserId, course.getId());
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());


        if (!course.getUser().getId().equals(currentUserId) &&
                !(currentUserMember.isPresent() &&
                        currentUserMember.get().getStatus().equals(MemberStatus.ACTIVED)))
            throw new ApiException("Người dùng không có quyền chỉnh sửa xem tài nguyên khóa học này");

        Sort sort = Sort.by(sortDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);
        if(sortField == null || sortDir == null) sort = Sort.unsorted();
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<Material> materialsPage;

        if(currentUserMember.isPresent() && currentUserMember.get().getType().equals(MemberType.STUDENT))
            materialsPage = materialRepository.findMaterialsByCourseIdAndStatus(courseId, Status.AVAIABLE, keyword, pageable);
        else
            materialsPage = materialRepository.findByCourse_IdAndNameContaining(courseId, keyword, pageable);
        List<MaterialDetailResponseDto> materialsDtoPage = modelMapperUtil.mapList(materialsPage.getContent(), MaterialDetailResponseDto.class);

        return new PageResponse<>(
                materialsDtoPage,
                materialsPage.getNumber(),
                materialsPage.getSize(),
                materialsPage.getTotalElements(),
                materialsPage.getTotalPages(),
                materialsPage.isLast()
        );
    }

    @Override
    public PageResponse<MaterialDetailResponseDto> getPageableListBySection(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long sectionId,  Long currentUserId) {
//        Sort sort = Sort.by(sortDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);
//        if(sortField == null || sortDir == null) sort = Sort.unsorted();
//        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
//        Page<Material> materialsPage = materialRepository.findAllByMaterialSections_SectionIdAndNameContaining(sectionId, keyword, pageable);
//        List<MaterialDetailResponseDto> materialsDtoPage = modelMapperUtil.mapList(materialsPage.getContent(), MaterialDetailResponseDto.class);
//
//        return new PageResponse<>(
//                materialsDtoPage,
//                materialsPage.getNumber(),
//                materialsPage.getSize(),
//                materialsPage.getTotalElements(),
//                materialsPage.getTotalPages(),
//                materialsPage.isLast()
//        );
        return null;
    }

    @Override
    public List<MaterialDetailResponseDto> getAllByCourse(Long courseId, Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", courseId));
        Optional<Member> currentUserMember = memberRepository.findByUser_IdAndCourse_Id(currentUserId, course.getId());
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());


        if (!course.getUser().getId().equals(currentUserId) &&
                !(currentUserMember.isPresent() &&
                        currentUserMember.get().getStatus().equals(MemberStatus.ACTIVED)))
            throw new ApiException("Người dùng không có quyền chỉnh sửa xem tài nguyên khóa học này");

        List<Material> materials;

        if(currentUserMember.isPresent() && currentUserMember.get().getType().equals(MemberType.STUDENT))
            materials = materialRepository.findMaterialsByCourseIdAndStatus(courseId, Status.AVAIABLE);
        else
            materials = materialRepository.findAllByCourse_Id(courseId);

        return modelMapperUtil.mapList(materials, MaterialDetailResponseDto.class);
    }

    @Override
    public List<MaterialDetailResponseDto> getAllBySection(Long sectionId) {
        List<Material> materials = materialRepository.findAllBySections_Section_Id(sectionId);
        return modelMapperUtil.mapList(materials, MaterialDetailResponseDto.class);
    }

    @Override
    public MaterialDetailResponseDto getById(Long id, Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Material", "id", id));
        Course course = courseRepository.findById(material.getCourse().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", material.getCourse().getId()));
        Optional<Member> currentUserMember = memberRepository.findByUser_IdAndCourse_Id(currentUserId, course.getId());
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());


        if (!course.getUser().getId().equals(currentUserId) &&
                !(currentUserMember.isPresent() &&
                        currentUserMember.get().getStatus().equals(MemberStatus.ACTIVED)))
            throw new ApiException("Người dùng không có quyền chỉnh sửa xem tài nguyên khóa học này");

        Material existMaterial;
        if(currentUserMember.isPresent() && currentUserMember.get().getType().equals(MemberType.STUDENT))
            existMaterial = materialRepository.findByIdAndStatusAndIsDeletedFalse(id, Status.AVAIABLE)
                    .orElseThrow(() -> new ApiException("Học sinh không thể xem tài nguyên này"));
        else
            existMaterial = material;

        return modelMapperUtil.mapOne(material, MaterialDetailResponseDto.class);
    }

    @Override
    public MaterialDetailResponseDto add(MaterialRequestDto material, Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Course course = courseRepository.findById(material.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", material.getCourseId()));
        File file = fileRepository.findById(material.getFileId())
                .orElseThrow(() -> new ResourceNotFoundException("File", "Id", material.getFileId()));
        Optional<Member> currentUserMember = memberRepository.findByUser_IdAndCourse_Id(currentUserId, material.getCourseId());
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        if (!course.getUser().getId().equals(currentUserId) &&
                !(currentUserMember.isPresent() &&
                        currentUserMember.get().getType().equals(MemberType.TEACHER) &&
                        currentUserMember.get().getStatus().equals(MemberStatus.ACTIVED)))
            throw new ApiException("Người dùng không có quyền thêm tài nguyên khóa học");

        if(!material.getStartedAt().before(material.getEndedAt()))
            throw new ApiException("Thời gian bắt đầu phải trước thời gian kết thúc");

        if (currentTimestamp.before(material.getStartedAt())) {
            material.setStatus(Status.NOT_STARTED);
        } else if ((currentTimestamp.after(material.getStartedAt()) || currentTimestamp.equals(material.getStartedAt())) &&
                currentTimestamp.before(material.getEndedAt()) &&
                !material.getStatus().equals(Status.SUSPENDED)) {
            material.setStatus(Status.AVAIABLE);
        } else if (currentTimestamp.after(material.getEndedAt()) || currentTimestamp.equals((material.getEndedAt()))){
            material.setStatus(Status.ENDED);
        }

        Material newMaterial = modelMapperUtil.mapOne(material, Material.class);
        newMaterial.setFile(file);
        newMaterial.setCourse(course);
        newMaterial.setSlug(SlugUtils.generateSlug(newMaterial.getName()));
        Material savedMaterial = materialRepository.save(newMaterial);
        return modelMapperUtil.mapOne(savedMaterial, MaterialDetailResponseDto.class);
    }

    @Override
    public MaterialDetailResponseDto update(Long id, MaterialRequestDto material, Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Course course = courseRepository.findById(material.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", material.getCourseId()));
        File file = fileRepository.findById(material.getFileId())
                .orElseThrow(() -> new ResourceNotFoundException("File", "Id", material.getFileId()));
        Optional<Member> currentUserMember = memberRepository.findByUser_IdAndCourse_Id(currentUserId, material.getCourseId());
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        Material existMaterial = materialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Material", "Id", id));

        if (!course.getUser().getId().equals(currentUserId) &&
                !(currentUserMember.isPresent() &&
                        currentUserMember.get().getType().equals(MemberType.TEACHER) &&
                        currentUserMember.get().getStatus().equals(MemberStatus.ACTIVED)))
            throw new ApiException("Người dùng không có quyền chỉnh sửa tài nguyên khóa học");

        if(!material.getStartedAt().before(material.getEndedAt()))
            throw new ApiException("Thời gian bắt đầu phải trước thời gian kết thúc");

        if (currentTimestamp.before(material.getStartedAt())) {
            material.setStatus(Status.NOT_STARTED);
        } else if ((currentTimestamp.after(material.getStartedAt()) || currentTimestamp.equals(material.getStartedAt())) &&
                currentTimestamp.before(material.getEndedAt()) &&
                !material.getStatus().equals(Status.SUSPENDED)) {
            material.setStatus(Status.AVAIABLE);
        } else if (currentTimestamp.after(material.getEndedAt()) || currentTimestamp.equals((material.getEndedAt()))){
            material.setStatus(Status.ENDED);
        }

        existMaterial.setName(material.getName());
        existMaterial.setDesc(material.getDesc());
        existMaterial.setSlug(SlugUtils.generateSlug(existMaterial.getName()));
        existMaterial.setStartedAt(material.getStartedAt());
        existMaterial.setEndedAt(material.getEndedAt());
        existMaterial.setStatus(material.getStatus());

        Material savedMaterial = materialRepository.save(existMaterial);
        return modelMapperUtil.mapOne(savedMaterial, MaterialDetailResponseDto.class);
    }

    @Override
    public Boolean softDelete(Long id, Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Material existMaterial = materialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Material", "Id", id));
        Course course = courseRepository.findById(existMaterial.getCourse().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", existMaterial.getCourse().getId()));
        File file = fileRepository.findById(existMaterial.getFile().getId())
                .orElseThrow(() -> new ResourceNotFoundException("File", "Id", existMaterial.getFile().getId()));
        Optional<Member> currentUserMember = memberRepository.findByUser_IdAndCourse_Id(currentUserId, course.getId());
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());


        if (!course.getUser().getId().equals(currentUserId) &&
                !(currentUserMember.isPresent() &&
                        currentUserMember.get().getType().equals(MemberType.TEACHER) &&
                        currentUserMember.get().getStatus().equals(MemberStatus.ACTIVED)))
            throw new ApiException("Người dùng không có quyền chỉnh sửa tài nguyên khóa học");

        existMaterial.setIsDeleted(!existMaterial.getIsDeleted());
        Material savedMaterial = materialRepository.save(existMaterial);
        return savedMaterial.getIsDeleted();
    }

    @Override
    public Boolean delete(Long id,  Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Material existMaterial = materialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Material", "Id", id));
        Course course = courseRepository.findById(existMaterial.getCourse().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", existMaterial.getCourse().getId()));
        File file = fileRepository.findById(existMaterial.getFile().getId())
                .orElseThrow(() -> new ResourceNotFoundException("File", "Id", existMaterial.getFile().getId()));
        Optional<Member> currentUserMember = memberRepository.findByUser_IdAndCourse_Id(currentUserId, course.getId());
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());


        if (!course.getUser().getId().equals(currentUserId) &&
                !(currentUserMember.isPresent() &&
                        currentUserMember.get().getType().equals(MemberType.TEACHER) &&
                        currentUserMember.get().getStatus().equals(MemberStatus.ACTIVED)))
            throw new ApiException("Người dùng không có quyền chỉnh sửa tài nguyên khóa học");

        materialRepository.delete(existMaterial);
        return true;
    }
}
