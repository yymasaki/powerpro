package com.example.service.info;

import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.domain.InformationsDepartmentLink;
import com.example.mapper.InformationsDepartmentLinkMapper;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class AddInformationsDepartmentLinkServiceTest {

	
	@Rule
	public MockitoRule mockito = MockitoJUnit.rule();

	@InjectMocks
	private AddInformationsDepartmentLinkService target;
	
	@Mock
	private InformationsDepartmentLinkMapper mapper;
	
	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	public static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	public void setUp() throws Exception {
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	/**
	 * mapperクラスが1回呼ばれていれば成功
	 */
	@Test
	public void お知らせと部署関係を追加する() {
		List<InformationsDepartmentLink> informationsDepartmentLinkList=new ArrayList<>(); 
		InformationsDepartmentLink informationsDepartmentLink=new InformationsDepartmentLink();
		informationsDepartmentLink.setDepartmentId(1);
		informationsDepartmentLink.setInformationId(1);
		informationsDepartmentLinkList.add(informationsDepartmentLink);
		target.addInformationsDepartmentLink(informationsDepartmentLinkList);
		verify(mapper,timeout(1)).insert(informationsDepartmentLinkList); 
	}

}
