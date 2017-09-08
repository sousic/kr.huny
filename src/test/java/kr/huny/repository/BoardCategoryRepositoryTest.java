package kr.huny.repository;

import kr.huny.model.db.BoardCategory;
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
 * Created by sousic on 2017-07-31.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:appConfig.xml"})
public class BoardCategoryRepositoryTest {
    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    BoardCategoryRepository boardCategoryRepository;

    BoardCategory boardCategory;

    @Before
    public void setUp() throws Exception {
        boardCategory = BoardCategory.builder()
                .categoryName("자유게시판")
                .createCount(10)
                .removeCount(0)
                .used(true)
                .build();
    }

    @Test
    public void testBoardCategoryAdd() throws Exception {
        boardCategoryRepository.deleteAll();

        boardCategoryRepository.save(boardCategory);

        long totalCount = boardCategoryRepository.count();

        assertThat(1L, is(totalCount));
    }
}
