package com.sku.elcoco.domain.mypage.service;

import com.sku.elcoco.domain.member.dto.MemberResponseDto;
import com.sku.elcoco.domain.member.entity.Member;
import com.sku.elcoco.domain.member.repository.MemberRepository;
import com.sku.elcoco.domain.post.dto.PostResponseDto;
import com.sku.elcoco.domain.post.entity.Post;
import com.sku.elcoco.domain.post.repository.PostRepository;
import com.sku.elcoco.domain.reply.dto.ReplyResponseDto;
import com.sku.elcoco.domain.reply.entity.Reply;
import com.sku.elcoco.domain.reply.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService {

    private final MemberRepository memberRepository;

    private final PostRepository postRepository;

    private final ReplyRepository replyRepository;
    @Override
    public MemberResponseDto.READ getMemberDetail(String memberEmail) {
        Optional<Member> findMemberByEmail = memberRepository.findMemberByEmail(memberEmail);
        return toReadDto(findMemberByEmail.get());
    }

    @Override
    public List<PostResponseDto.READ> getMemberPosts(String memberEmail) {
        List<Post> posts = postRepository.findPostsByMemberEmailAndDeleteAtFalse(memberEmail);
        return posts.stream().map(this::toReadDto).toList();
    }

    @Override
    public List<ReplyResponseDto.READ> getMemberReplies(String memberEmail) {
        List<Reply> replies = replyRepository.findRepliesByMemberEmailAndDeleteAtFalse(memberEmail);
        return replies.stream().map(this::toReadDto).toList();
    }

    private MemberResponseDto.READ toReadDto(Member member) {
        return MemberResponseDto.READ.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .nickname(member.getNickname())
                .build();
    }

    private PostResponseDto.READ toReadDto(Post post) {
        return PostResponseDto.READ.builder()
                .postId(post.getId())
                .memberEmail(post.getMember().getEmail())
                .memberNickname(post.getMember().getNickname())
                .title(post.getTitle())
                .content(post.getContent())
                .postCategory(post.getPostCategory())
                .viewCount(post.getViewCount())
                .likeCount(postRepository.countLikesByPostId(post.getId()))
                .replyCount(postRepository.countRepliesByPostId(post.getId()))
                .regDate(post.getRegDate())
                .build();
    }

    private ReplyResponseDto.READ toReadDto(Reply reply) {
        ReplyResponseDto.READ dto = ReplyResponseDto.READ.builder()
                .replyId(reply.getId())
                .memberNickname(reply.getMember().getNickname())
                .postId(reply.getPost().getId())
                .content(reply.getContent())
                .regDate(reply.getRegDate())
                .likeCount(reply.getLikeCount())
                .build();
        return dto;
    }
}
