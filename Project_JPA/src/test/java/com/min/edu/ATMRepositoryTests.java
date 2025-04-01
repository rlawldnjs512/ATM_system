package com.min.edu;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.min.edu.service.ATMRepository;
import com.min.edu.service.BoothRepository;
import com.min.edu.vo.ATM;
import com.min.edu.vo.Booth;

import jakarta.transaction.Transactional;

@SpringBootTest
class ATMRepositoryTests {

	@Autowired
	private ATMRepository atmRepository;
	
	@Autowired
	private BoothRepository boothRepository;

	/**
	 * Test를 위한 ATM 정보 등록<br>
	 * @BeforeAll을 통해서 클래스 단위에서 한번만 실행되도록 한다. 
	 */
	
//	@BeforeAll
//	@DisplayName("JUnit Test를 위한 Sample Data 입력")
//	public static void setUp(@Autowired ATMRepository atmRepository,
//							 @Autowired BoothRepository boothRepository) {
//		
//		Booth booth = new Booth("점내미니형","Green");
//		boothRepository.save(booth);
//		
//		atmRepository.save(new ATM("NEOTEC","ATM","N","N",86000,2025,booth));
//		atmRepository.save(new ATM("PULOON","CD","Y","Y",20000,2023,booth));
//		atmRepository.save(new ATM("PULOON","CD","Y","N",30000,2023,booth));
//		atmRepository.save(new ATM("ATEC","ATM","N","Y",50000,2022,booth));
//		atmRepository.save(new ATM("NEOTEC","CD","N","Y",35000,2024,booth));
//		
//	}
	
//	@Transactional
//	@Test
	@DisplayName("Brand를 통한 ATM 검색")
	public void findByBrand() {
		
		List<ATM> atmList = atmRepository.findByBrand("PULOON");
		assertNotNull(atmList,"ATM 목록 조회결과가 null은 안됩니다.");
		assertFalse(atmList.isEmpty(),"PULOON 브랜드 ATM가 하나도 반환되지 않았습니다.");
		atmList.forEach(atm->{
			System.out.println("검색된 ATM Model : "+atm.getModel());
			System.out.println("해당 ATM의 소유자 : "+atm.getBooth());
			System.out.println("--------------------------------");
		});
		
	}
	
//	@Test
	@DisplayName("Model을 통한 ATM 검색")
	public void testFindByModel() {
		
		List<ATM> atmList = atmRepository.findByModel("ATM");
		assertNotNull(atmList);
		assertTrue(atmList.size()>0);
		atmList.forEach(atm->assertEquals("ATM", atm.getModel()));
		
	}
	
//	@Test
	@DisplayName("ProductYear를 통한 ATM 검색")
	public void testFindByProductYear() {
		
		List<ATM> atmList = atmRepository.findByProductYear(2023);
		assertNotNull(atmList);
		assertTrue(atmList.size()>0);
		atmList.forEach(atm->assertEquals(2023, atm.getProductYear()));
		
	}
	
//	@Test
	@DisplayName("Brand와 Model을 통한 ATM 검색")
	public void testFindByBrandAndModel() {
		
		List<ATM> atmList = atmRepository.findByBrandAndModel("PULOON", "CD");
		assertEquals("PULOON", atmList.get(0).getBrand());
		assertEquals("CD", atmList.get(0).getModel());
		
	}
	
//	@Test
	@DisplayName("Brand로 검색하고 ProductYear로 오름차순 정렬하기")
	public void testFindByBrandOrderByProductYearAsc() {
		
		List<ATM> atmList = atmRepository.findByBrandOrderByProductYearAsc("NEOTEC");
		assertTrue(atmList.size()>0);
		atmList.forEach(atm->{
			System.out.println("검색된 ATM 정보 : "+atm.getModel() + "/" + atm.getProductYear());
			System.out.println("--------------------------------");
		});
		
	}
	
//	@Test
	@DisplayName("@Query Annotation을 통한 Brand로 ATM 검색하기")
	public void testFindByBrandQuery() {
		
		List<ATM> atmList = atmRepository.findByBrandQuery("HYOSUNG");
		assertTrue(atmList.size()>0);
		atmList.forEach(atm->{
			assertEquals("HYOSUNG", atm.getBrand());
		});
		
	}
	
//	@Test
	@DisplayName("@Query Annotation을 통한 Like문을 사용해서 Brand 검색하기")
	public void testFindByBrandEndsWith() {
		
		List<ATM> atmList = atmRepository.findByBrandEndsWith("HYOSUNG");
		assertTrue(atmList.size()>0);
		atmList.forEach(atm->{
			assertEquals("HYOSUNG", atm.getBrand());
		});
		
	}
	
//	@ParameterizedTest
//	@ValueSource(longs = {1004})
	@DisplayName("ATM ID를 통한 Parameter 테스트하기")
	public void testDeleteAtmById(Long id) {
		
		ATM atm = atmRepository.findById(id).orElse(null);
		assertNotNull(atm,"ATM을 삭제하기 위한 정보가 없습니다.");
		
		atmRepository.deleteById(id);
		assertFalse(atmRepository.findById(id).isPresent(),"ATM 정보가 삭제되어 존재하지 않습니다.");
		
	}
	
//	@Test
	@DisplayName("존재하는 Booth에 ATM 정보를 입력하는 테스트")
	public void testAtmList() {
		
		Booth booth = boothRepository.findById(2L).orElseThrow();
		ATM atm = new ATM("NEOTEC", "CD", "Y", "Y", 45000, 2017, booth);
		ATM saveAtm = atmRepository.save(atm);
		assertNotNull(saveAtm.getId()); // 저장 후 ID가 NULL이 아니어야 함
		assertEquals("NEOTEC", saveAtm.getBrand()); // Brand 확인
		assertEquals("CD", saveAtm.getModel()); // Model 확인
		assertNotNull(saveAtm.getBooth()); // 해당 Booth가 존재하는지
		assertEquals("인출회전형", saveAtm.getBooth().getType());
		
	}
	
//	@ParameterizedTest
	@ValueSource(longs = {1052})
	@DisplayName("ATM 정보의 Booth 정보를 변경")
	public void testUpdateAtmBooth(Long AtmId) {
		
		Long newBoothId = 3L; // 새로운 부스의 ID
		ATM atm = atmRepository.findById(AtmId)
				               .orElseThrow(()->new RuntimeException("ATM 조회 결과 없습니다."));
		Booth newBooth = boothRepository.findById(newBoothId)
										.orElseThrow(()->new RuntimeException("Booth 조회 결과 없습니다."));
		atm.setBooth(newBooth);
		
		// ATM 정보를 저장하여 Booth 변경을 DB에 반영한다. (test 때문)
		// Dirty Cache 를 통해서 처리하지 않고 명시적으로 save() 메소드를 flush 호출하여 즉시 DB에 반영한다.
		atmRepository.save(atm);
		
		ATM updateAtm = atmRepository.findById(AtmId)
									 .orElseThrow(()->new RuntimeException("ATM이 존재하지 않습니다."));
		assertEquals(newBoothId, updateAtm.getBooth().getBoothid(),"ATM에 사용되는 Booth 정보가 업데이트 되었습니다.");
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
