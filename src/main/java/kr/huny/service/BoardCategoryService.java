package kr.huny.service;

import kr.huny.model.db.BoardCategory;
import kr.huny.model.db.common.BoardInfo;
import kr.huny.repository.BoardCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.*;

/**
 * Created by sousic on 2017-08-01.
 */
@Service
@Slf4j
public class BoardCategoryService {
    @Autowired
    BoardCategoryRepository boardCategoryRepository;

    public void getCategoryList(Model model, Locale locale, BoardInfo boardInfo) {
        List<BoardCategory> boardCategoryList = new ArrayList<>();

        Sort sort = new Sort(Sort.Direction.DESC, Arrays.asList("categorySeq"));
        Pageable pageable = new PageRequest(boardInfo.getPage()-1, boardInfo.getSize(), sort);

        if(Objects.nonNull(boardInfo.getCategory()))
        {
            Page<BoardCategory> tmpList = boardCategoryRepository.findAll(pageable);

            boardCategoryList = tmpList.getContent();
        }
        else
        {

        }
        model.addAttribute(boardCategoryList);
    }
}
