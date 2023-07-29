package com.sku.elcoco.dummy;

import com.sku.elcoco.domain.board.Board;
import com.sku.elcoco.domain.comment.Comment;
import com.sku.elcoco.domain.member.Member;

import com.sku.elcoco.domain.message.dto.MessageRequestDto;
import com.sku.elcoco.repository.board.BoardRepository;
import com.sku.elcoco.repository.board.BoardRepositoryCustom;
import com.sku.elcoco.repository.comment.CommentRepository;
import com.sku.elcoco.repository.member.MemberRepository;
import com.sku.elcoco.service.board.BoardService;
import com.sku.elcoco.service.comment.CommentService;
import com.sku.elcoco.service.member.MemberService;
import com.sku.elcoco.service.message.MessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class DummyDataTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BoardService boardService;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    CommentService commentService;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    MessageService messageService;



    @Test
    void createMember() {
        List<Member> memberList = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            Member member = Member.builder()
                    .email("kswlsgh" + i + "@naver.com")
                    .password("1234" + i)
                    .nickname("닉네임" + i)
                    .build();
            memberList.add(member);
        }
        memberRepository.saveAll(memberList);
    }

    @Test
    void createBoard() {
        for (int i = 1; i <= 50; i++) {
            Board board = Board.builder()
                    .title("제목" + i)
                    .content("본문" + i)
                    .writer("닉네임" + i)
                    .hits(0)
                    .deleteYn('N')
                    .build();
            boardRepository.save(board);
        }

    }
    @Test
    void createComment() {

        for (Long i = 1L; i < 50L; i++) {
            Board board = boardRepository.findById(i).get();
            Comment comment = Comment.builder()
                    .board(board)
                    .content("댓글" + i)
                    .writer("닉네임" + i)
                    .deleteYn('N')
                    .build();
        commentRepository.save(comment);
        }

    }

    @Test
    void createMessage() {

        for (int i = 1; i <= 25; i++) {
            MessageRequestDto.CREATE build = MessageRequestDto.CREATE.builder()
                    .receiverName("닉네임10")
                    .senderName("닉네임1")
                    .title("안녕 닉네임10?")
                    .content("나는 닉네임1이라고 해")
                    .build();
            messageService.createMessage(build);
        }

        for (int i = 1; i <= 25; i++) {
            MessageRequestDto.CREATE build = MessageRequestDto.CREATE.builder()
                    .receiverName("닉네임1")
                    .senderName("닉네임10")
                    .title("안녕 닉네임1?")
                    .content("나는 닉네임10이라고 해")
                    .build();
            messageService.createMessage(build);
        }

        for (int i = 2; i <= 10; i++) {
            MessageRequestDto.CREATE build = MessageRequestDto.CREATE.builder()
                    .receiverName("닉네임"+i)
                    .senderName("닉네임1")
                    .title("안녕 닉네임"+i+"?")
                    .content("나는 닉네임1이고 2~10까지 보낼거야")
                    .build();
            messageService.createMessage(build);
        }
    }
}
