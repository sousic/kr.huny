package kr.huny.repository;

import kr.huny.model.db.Board;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by sousic on 2017-07-28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:appConfig.xml"})
public class BoardRepositoryTest {
    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    BoardRepository boardRepository;

    Board board;

    @Before
    public void setUp() throws Exception {
        board = Board.builder().boardName("자유게시판")
                .boardInfo("자유게시판")
                .isCommentType((byte)1)
                .isRecommentType((byte)1)
                .build();
    }

    @Test
    public void boardAddTest() throws Exception
    {
        boardRepository.save(board);
        long count = boardRepository.count();

        assertThat(1L, is(count));
    }
}