package com.lms.learnkonnet.services.impls;

import com.lms.learnkonnet.dtos.requests.post.PostRequestDto;
import com.lms.learnkonnet.dtos.requests.relations.MemberPostRequestDto;
import com.lms.learnkonnet.dtos.responses.common.PageResponse;
import com.lms.learnkonnet.dtos.responses.post.PostResponseDto;
import com.lms.learnkonnet.exceptions.ResourceNotFoundException;
import com.lms.learnkonnet.models.Course;
import com.lms.learnkonnet.models.Member;
import com.lms.learnkonnet.models.Post;
import com.lms.learnkonnet.repositories.ICourseRepository;
import com.lms.learnkonnet.repositories.IMemberRepository;
import com.lms.learnkonnet.repositories.IPostRepository;
import com.lms.learnkonnet.services.IPostService;
import com.lms.learnkonnet.utils.ModelMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService implements IPostService {
    @Autowired
    private IMemberRepository memberRepository;
    @Autowired
    private ICourseRepository courseRepository;
    @Autowired
    private IPostRepository postRepository;
    @Autowired
    private ModelMapperUtil modelMapperUtil;
    @Override
    public PageResponse<PostResponseDto> getPageableList(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long courseId) {
        Sort sort = Sort.by(sortDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);
        if(sortField == null || sortDir == null) sort = Sort.unsorted();
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<Post> postsPage = postRepository.findByCourse_IdAndContentContaining(courseId, keyword, pageable);
        List<PostResponseDto> postsDtoPage = modelMapperUtil.mapList(postsPage.getContent(), PostResponseDto.class);

        return new PageResponse<>(
                postsDtoPage,
                postsPage.getNumber(),
                postsPage.getSize(),
                postsPage.getTotalElements(),
                postsPage.getTotalPages(),
                postsPage.isLast()
        );
    }

    @Override
    public List<PostResponseDto> getAll(Long courseId) {
        List<Post> posts = postRepository.findAllByCourse_Id(courseId);
        return modelMapperUtil.mapList(posts, PostResponseDto.class);
    }

    @Override
    public PostResponseDto getById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return modelMapperUtil.mapOne(post, PostResponseDto.class);
    }

    @Override
    public PostResponseDto add(PostRequestDto post, Long currentMemberId) {
        Member currentMember = memberRepository.findById(currentMemberId)
                .orElseThrow(() -> new ResourceNotFoundException("Current member", "Id", currentMemberId));
        Course course = courseRepository.findById(post.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", post.getCourseId()));

        Post newPost = modelMapperUtil.mapOne(post, Post.class);

        newPost.setCourse(course);
        newPost.setMember(currentMember);
        Post savedPost = postRepository.save(newPost);
        return modelMapperUtil.mapOne(savedPost, PostResponseDto.class);
    }

    @Override
    public PostResponseDto update(Long id, PostRequestDto post, Long currentMemberId) {
        Member currentMember = memberRepository.findById(currentMemberId)
                .orElseThrow(() -> new ResourceNotFoundException("Current member", "Id", currentMemberId));
        Post existPost = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Id", id));

        // Tạo danh sách MemberPost mới từ danh sách MemberPostRequestDto


        // Lưu bài đăng đã cập nhật vào cơ sở dữ liệu
        Post savedPost = postRepository.save(existPost);

        // Chuyển đổi bài đăng đã cập nhật sang PostResponseDto và trả về
        return modelMapperUtil.mapOne(savedPost, PostResponseDto.class);
    }

    @Override
    public Boolean softDelete(Long id, Long currentMemberId) {
        Member currentMember = memberRepository.findById(currentMemberId)
                .orElseThrow(() -> new ResourceNotFoundException("Current member", "Id", currentMemberId));
        Post existPost = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Id", id));
        existPost.setIsDeleted(!existPost.getIsDeleted());
        Post savedPost = postRepository.save(existPost);
        return savedPost.getIsDeleted();
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }
}
