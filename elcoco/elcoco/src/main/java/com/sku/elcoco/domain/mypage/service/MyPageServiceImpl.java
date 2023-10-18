package com.sku.elcoco.domain.mypage.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sku.elcoco.domain.member.dto.MemberResponseDto;
import com.sku.elcoco.domain.member.entity.Member;
import com.sku.elcoco.domain.member.repository.MemberRepository;
import com.sku.elcoco.domain.post.dto.PostResponseDto;
import com.sku.elcoco.domain.post.entity.Post;
import com.sku.elcoco.domain.post.repository.PostRepository;
import com.sku.elcoco.domain.reply.dto.ReplyResponseDto;
import com.sku.elcoco.domain.reply.entity.Reply;
import com.sku.elcoco.domain.reply.repository.ReplyRepository;
import com.sku.elcoco.global.exception.DuplicatedException;
import com.sku.elcoco.global.model.ResponseStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService {

    private final MemberRepository memberRepository;

    private final PostRepository postRepository;

    private final ReplyRepository replyRepository;

    private final PasswordEncoder passwordEncoder;
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

    @Override
    public boolean checkDuplicateNickname(String nickname) {
        try {
            // Jackson ObjectMapper를 사용하여 JSON 문자열 파싱
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(nickname);

            // JSON에서 "nickname" 속성의 value 값을 가져옴
            String parseNickname = jsonNode.get("nickname").asText();
            return memberRepository.existsMemberByNicknameAndDeleteAtFalse(parseNickname);
        } catch (Exception e) {
            e.printStackTrace();
            return true; // 예외 발생 시 처리
        }
    }

    @Override
    @Transactional
    public void updateNickname(String memberEmail, String nickname) {
        try {
            // Jackson ObjectMapper를 사용하여 JSON 문자열 파싱
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(nickname);

            // JSON에서 "nickname" 속성의 value 값을 가져옴
            String parseNickname = jsonNode.get("nickname").asText();
            //한 번 더 검증
            isNickname(parseNickname);

            Optional<Member> findMember = memberRepository.findMemberByEmailAndDeleteAtFalse(memberEmail);
            Member member = findMember.get();
            member.updateNickname(parseNickname);
            memberRepository.save(member);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    @Transactional
    public void updatePassword(String memberEmail, String password) {
        try {
            // Jackson ObjectMapper를 사용하여 JSON 문자열 파싱
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(password);

            // JSON에서 "nickname" 속성의 value 값을 가져옴
            String parsePassword = jsonNode.get("password").asText();

            Optional<Member> findMember = memberRepository.findMemberByEmailAndDeleteAtFalse(memberEmail);
            Member member = findMember.get();
            member.updatePassword(passwordEncoder.encode(parsePassword));
            memberRepository.save(member);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("password = {}",password);

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

    private void isNickname(String nickname) {
        if (memberRepository.existsMemberByNicknameAndDeleteAtFalse(nickname)) {
            throw new DuplicatedException(ResponseStatus.FAIL_MEMBER_NICKNAME_DUPLICATED);
        }
    }
}
