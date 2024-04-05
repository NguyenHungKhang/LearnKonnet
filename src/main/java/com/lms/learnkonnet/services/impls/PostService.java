package com.lms.learnkonnet.services.impls;

import com.lms.learnkonnet.dtos.requests.post.PostRequestDto;
import com.lms.learnkonnet.dtos.requests.relations.MemberPostRequestDto;
import com.lms.learnkonnet.dtos.responses.common.PageResponse;
import com.lms.learnkonnet.dtos.responses.post.PostResponseDto;
import com.lms.learnkonnet.dtos.responses.topic.TopicBasicInfoResponseDto;
import com.lms.learnkonnet.dtos.responses.topic.TopicDetailResponseDto;
import com.lms.learnkonnet.exceptions.ResourceNotFoundException;
import com.lms.learnkonnet.models.Member;
import com.lms.learnkonnet.models.Post;
import com.lms.learnkonnet.models.Topic;
import com.lms.learnkonnet.models.relations.MemberPost;
import com.lms.learnkonnet.repositories.IMemberRepository;
import com.lms.learnkonnet.repositories.IPostRepository;
import com.lms.learnkonnet.repositories.relations.IMemberPostRepository;
import com.lms.learnkonnet.services.IPostService;
import com.lms.learnkonnet.utils.ModelMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PostService implements IPostService {
    @Autowired
    private IMemberRepository memberRepository;
    @Autowired
    private IMemberPostRepository memberPostRepository;
    @Autowired
    private IPostRepository postRepository;
    @Autowired
    private ModelMapperUtil modelMapperUtil;
    @Override
    public PageResponse<PostResponseDto> getPageableList(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long courseId) {
        Sort sort = Sort.by(sortDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);
        if(sortField == null || sortDir == null) sort = Sort.unsorted();
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<Post> postsPage = postRepository.findByCourseIdAndNameContaining(courseId, keyword, pageable);
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
        List<Post> posts = postRepository.findAllByCourseId(courseId);
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

        Post newPost = modelMapperUtil.mapOne(post, Post.class);
        List<MemberPost> memberPosts = new ArrayList<>();
        for (MemberPostRequestDto m : post.getMembers()) {
            MemberPost memberPost = modelMapperUtil.mapOne(m, MemberPost.class);
            memberPost.setPost(newPost);
            memberPosts.add(memberPost);
        }
        newPost.setCreatedByMember(currentMember);
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
        List<MemberPost> newMemberPosts = new ArrayList<>();
        for (MemberPostRequestDto m : post.getMembers()) {
            MemberPost memberPost = modelMapperUtil.mapOne(m, MemberPost.class);
            memberPost.setPost(existPost);
            newMemberPosts.add(memberPost);
        }

        // So sánh danh sách MemberPost mới và cũ để xác định thao tác cần thực hiện
        List<MemberPost> oldMemberPosts = existPost.getMembers();
        for (MemberPost oldMemberPost : oldMemberPosts) {
            if (!newMemberPosts.contains(oldMemberPost)) {
                // Nếu MemberPost cũ không nằm trong danh sách mới, xóa nó khỏi cơ sở dữ liệu
                memberPostRepository.delete(oldMemberPost);
            }
        }

        // Cập nhật danh sách MemberPost của bài đăng
        existPost.setMembers(newMemberPosts);
        existPost.setUpdatedByMember(currentMember);

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
        existPost.setUpdatedByMember(currentMember);
        Post savedPost = postRepository.save(existPost);
        return savedPost.getIsDeleted();
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }
}
