package kr.huny.repository;

import kr.huny.model.db.BoardCategory;
import kr.huny.model.db.BoardFree;
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
public class BoardFreeRepositoryTest {
    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    BoardFreeRepository boardFreeRepository;
    @Autowired
    BoardCategoryRepository boardCategoryRepository;

    BoardCategory boardCategory;
    BoardFree boardFree;

    @Before
    public void setUp() throws Exception {
        boardCategory = BoardCategory.builder()
                .categoryName("자유게시판")
                .createCount(10)
                .removeCount(0)
                .isUsed(true)
                .build();

        boardCategoryRepository.save(boardCategory);

        boardFree = BoardFree.builder()
                .title("제목입니다.")
                .context("내용입니다.")
                .userSeq(1L)
                .username("홍길동")
                .build();
    }

    @Test
    public void boardAddTest() throws Exception
    {
        boardFree.setBoardCategory(boardCategory);
        boardFreeRepository.save(boardFree);
        long count = boardFreeRepository.count();

        assertThat(1L, is(count));
    }

    @Test
    public void boardAdd10Test() throws Exception
    {
        for(int i =0;i<10;i++) {
            BoardFree tmpBoardFree = BoardFree.builder()
                    .title("제목입니다.")
                    .context("내용입니다.")
                    .userSeq(1L)
                    .boardCategory(boardCategory)
                    .username("홍길동")
                    .build();

            boardFreeRepository.save(tmpBoardFree);
        }
        long count = boardFreeRepository.count();

        assertThat(10L, is(count));
    }

    @Test
    public void boardSelectOneTest() throws Exception
    {
        boardFree.setBoardCategory(boardCategory);
        boardFreeRepository.save(boardFree);

        BoardFree tmpBoardFree = boardFreeRepository.findOne(boardFree.getBoardSeq());

        System.out.println(tmpBoardFree.toString());
    }
}