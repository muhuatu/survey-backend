package com.yuina.survey;

import com.yuina.survey.repository.ResponseDao;
import com.yuina.survey.vo.StatisticsDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SurveyApplicationTests {

	@Autowired
	protected ResponseDao responseDao;

	@Test
	void contextLoads() {
	}

//	@Test
//	public void test1(){
//		List<StatisticsDTO> res = responseDao.getStatisticsByQuizId(1);
//		System.out.println(res.size());
//	}

}
