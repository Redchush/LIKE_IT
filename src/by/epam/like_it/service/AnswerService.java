package by.epam.like_it.service;


import by.epam.like_it.exception.service.system.ServiceSystemException;
import by.epam.like_it.model.vo.db_vo.AnswerVO;

import java.util.LinkedHashMap;

public interface AnswerService {

    LinkedHashMap<Long, AnswerVO> getAnswerVoMapByPostId(Long id) throws ServiceSystemException;
}
