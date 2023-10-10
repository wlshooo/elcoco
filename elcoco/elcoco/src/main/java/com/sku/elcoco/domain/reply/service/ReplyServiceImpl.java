package com.sku.elcoco.domain.reply.service;


import com.sku.elcoco.domain.member.entity.Member;
import com.sku.elcoco.domain.member.repository.MemberRepository;
import com.sku.elcoco.domain.post.entity.Post;
import com.sku.elcoco.domain.post.repository.PostRepository;
import com.sku.elcoco.domain.reply.dto.ReplyRequestDto;
import com.sku.elcoco.domain.reply.dto.ReplyResponseDto;
import com.sku.elcoco.domain.reply.entity.Reply;
import com.sku.elcoco.domain.reply.repository.ReplyRepository;
import com.sku.elcoco.domain.reply.repository.ReplySearchRepository;
import com.sku.elcoco.global.exception.NotFoundException;
import com.sku.elcoco.global.model.ResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReplyServiceImpl implements ReplyService {

    private final MemberRepository memberRepository;

    private final PostRepository postRepository;

    private final ReplyRepository replyRepository;

    private final ReplySearchRepository replySearchRepository;

    @Override
    @Transactional
    public Long createReply(String email, Long postId, ReplyRequestDto.CREATE create) {
        Optional<Member> findMember = memberRepository.findMemberByEmailAndDeleteAtFalse(email);

        isMember(findMember);

        Optional<Post> findPost = postRepository.findPostByIdAndDeleteAtFalse(postId);

        isPost(findPost);

        Reply reply = toEntity(create, findMember.get(), findPost.get());

        replyRepository.save(reply);

        return reply.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReplyResponseDto.READ> getAllReplies(Long postId) {
        List<Reply> list = replyRepository.findRepliesByPostIdAndDeleteAtFalse(postId);

        return list.stream()
                .map(this::toReadDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReplyResponseDto.READ> getSearchReplies(ReplyRequestDto.CONDITION condition) {
        final List<Reply> posts = replySearchRepository.find(condition);

        return posts.stream()
                .map(this::toReadDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Long deleteReply(String email, Long postId, Long replyId) {
        Optional<Reply> reply = replyRepository.findReplyByIdAndDeleteAtFalse(replyId);

        isReply(reply);

        reply.get().changeDeleteAt();

        replyRepository.save(reply.get());

        return reply.get().getId();
    }

    private void isMember(Optional<Member> member) {
        if (member.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_MEMBER_NOT_FOUND);
        }
    }

    private void isPost(Optional<Post> post) {
        if (post.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_POST_NOT_FOUND);
        }
    }

    private void isReply(Optional<Reply> reply) {
        if (reply.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_REPLY_NOT_FOUND);
        }
    }

    private static Reply toEntity(ReplyRequestDto.CREATE create, Member member, Post post) {
        Reply reply = Reply.builder()
                .post(post)
                .member(member)
                .content(create.getContent())
                .likeCount(create.getLikeCount())
                .build();
        return reply;
    }

    private ReplyResponseDto.READ toReadDto(Reply reply) {
        ReplyResponseDto.READ dto = ReplyResponseDto.READ.builder()
                .replyId(reply.getId())
                .memberNickname(reply.getMember().getNickname())
                .postId(reply.getPost().getId())
                .memberRole(reply.getMember().getRole())
                .content(reply.getContent())
                .regDate(reply.getRegDate())
                .likeCount(reply.getLikeCount())
                .build();
        return dto;
    }
}
