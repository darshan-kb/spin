package com.spin.game;

import com.spin.game.dto.TicketReportDTO;
import com.spin.game.entities.BetCategory;
import com.spin.game.entities.BetValuesMap;
import com.spin.game.entities.Ticket;
import com.spin.game.model.TicketModel;
import com.spin.game.model.TicketRecordModel;
import com.spin.game.repository.BetCategoryRepository;
import com.spin.game.repository.BetValuesMapRepository;
import com.spin.game.repository.GameRepo;
import com.spin.game.service.CalculateResultService;
import com.spin.game.service.GameReportService;
import com.spin.game.service.InitGameService;
import com.spin.game.service.TicketService;
import com.spin.game.serviceclass.ValueMap;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
class GameApplicationTests {

//	@Autowired
//	private OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager;

	@Autowired
	BetCategoryRepository betrepo;

//	@Autowired
//	UserRepository userRepository;
	@Autowired
	InitGameService igs;
	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	TicketService ticketService;

	@Autowired
	CalculateResultService calculateResultService;

	@Autowired
	ValueMap valueMap;
	@Autowired
	BetValuesMapRepository betValuesMap;
	@Autowired
	GameReportService gameReportService;
	@Autowired
	GameRepo gameRepo;


	@Test
	void contextLoads() {
	}

	@Test
	void getLatestGame(){
		System.out.println(gameRepo.findLatestGame());
	}
	@Test
	@Transactional
	void getLatestTenGame(){
		System.out.println(gameRepo.findLastTenGame());
	}

	@Test
	@Transactional
	void getTicket(){
		List<TicketReportDTO> tickets = gameReportService.getTickets(0,"darshanbehere@gmail.com");
		System.out.println(tickets);
	}

//	@Test
//	String getToken(){
//		OAuth2AuthorizeRequest request = OAuth2AuthorizeRequest
//				.withClientRegistrationId("2")
//				.principal("spin-client")
//				.build();
//
//		OAuth2AuthorizedClient client = oAuth2AuthorizedClientManager.authorize(request);
//		System.out.println(client.getAccessToken().getTokenValue());
//		return client.getAccessToken().getTokenValue();
//	}

//	@Test
//	public void test(){
//		RestTemplate restTemplate = new RestTemplate();
//		String resourseURL = "http://localhost:7070/account/balance/darshanbehere@gmail.com";
//		HttpHeaders httpHeaders = new HttpHeaders();
//		httpHeaders.add("Authorization","Bearer "+getToken());
//		HttpEntity<String> http = new HttpEntity<>(httpHeaders);
//		ResponseEntity<String> response = restTemplate.exchange(resourseURL, HttpMethod.GET, http, String.class);
//		System.out.println(response.getBody());
//	}

//	@Test
//	void add100Values(){
//		for(int i=0;i<100;i++){
//			betrepo.save(new BetCategory(i));
//		}
//	}

//	@Test
//	void testInitGame(){
//		System.out.println(igs.gameInit());
//	}
//
//	@Test
//	void addUser(){
//		//userRepository.save(new User("Arun","Dharme",passwordEncoder.encode("Arun@1212"),"arundharme@gmail.com",false,false,"ROLE_USER,ROLE_ADMIN"));
//	}
//
//	@Test
//	void addTicket(){
//		String email = "darshanbehere@gmail.com";
//		List<TicketRecordModel> lst = new ArrayList<>();
//		lst.add(new TicketRecordModel(1,50));
//		lst.add(new TicketRecordModel(8,500));
//		lst.add(new TicketRecordModel(45,1000));
//		lst.add(new TicketRecordModel(89,20));
//		lst.add(new TicketRecordModel(78,100));
//		TicketModel tm = new TicketModel();
//		tm.setTicketRecords(lst);
////		ticketService.saveTicket(email);
//	}
//
//	@Test
//	void getAllBets(){
//		calculateResultService.getCurrentGameResult(152);
//	}
//
//	@Test
//	void showValueMap(){
//		System.out.println(valueMap+"Here");
//	}
//
//	@Test
//	void secondLevelCache(){
//		calculateResultService.getElements("corner",2);
//		calculateResultService.getElements("corner",2);
//	}
}
