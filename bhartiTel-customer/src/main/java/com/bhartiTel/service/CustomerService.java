package com.bhartiTel.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.bhartiTel.dto.CallDetailsDTO;
import com.bhartiTel.dto.CustomerDTO;
import com.bhartiTel.dto.FriendFamilyDTO;
import com.bhartiTel.dto.LoginDTO;
import com.bhartiTel.entity.CallDetails;
import com.bhartiTel.entity.Customer;
import com.bhartiTel.entity.FriendFamily;
import com.bhartiTel.entity.Plan;
import com.bhartiTel.repository.CallDetailsRepository;
import com.bhartiTel.repository.CustomerRepository;
@Service
public class CustomerService {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	CustomerRepository custRepo;

	@Autowired
	CallDetailsRepository callDetailsRepo;

	// Create a new customer
	public void createCustomer(CustomerDTO custDTO) {
		logger.info("Creation request for customer {}", custDTO);
		Customer cust = custDTO.createEntity();
		Plan p = new Plan();
		p.setPlanId(custDTO.getCurrentPlan().getPlanId());
		cust.setPlan(p);
		custRepo.save(cust);
	}

	// Login
	public boolean login(LoginDTO loginDTO) {
		Boolean flag=false;
		logger.info("Login request for customer {} with password {}", loginDTO.getPhoneNo(),loginDTO.getPassword());
		Optional<Customer> cust;
		cust=custRepo.findById(loginDTO.getPhoneNo());
		if(cust.isPresent() && cust.get() != null && cust.get().getPassword().equals(loginDTO.getPassword())) {
			flag= true;
		}
		return flag;
	}

	// Fetches full profile of a specific customer
	public CustomerDTO getCustomerProfile(Long phoneNo) {
		CustomerDTO custDTO=null;
		logger.info("Profile request for customer {}", phoneNo);
		Optional<Customer> cust = custRepo.findById(phoneNo);
		if(cust.isPresent())
			custDTO = CustomerDTO.valueOf(cust.get());

		logger.info("Profile for customer : {}", custDTO);
		return custDTO;
	}

	// Fetches call details of a specific customer
	public List<CallDetailsDTO> getCustomerCallDetails(@PathVariable long phoneNo) {

		logger.info("Calldetails request for customer {}", phoneNo);

		List<CallDetails> callDetails = callDetailsRepo.findByCalledBy(phoneNo);
		List<CallDetailsDTO> callsDTO = new ArrayList<>();

		for (CallDetails call : callDetails) {
			callsDTO.add(CallDetailsDTO.valueOf(call));
		}
		logger.info("Calldetails for customer : {}", callDetails);

		return callsDTO;
	}

	// Save the friend details of a specific customer
	public void saveFriend(Long phoneNo, FriendFamilyDTO friendDTO) {
		logger.info("Creation request for customer {} with data {}", phoneNo, friendDTO);
		friendDTO.setPhoneNo(phoneNo);
		FriendFamily friend = friendDTO.createFriend();
		List<FriendFamily> friendList= new ArrayList<>();
		friendList.add(friend);
		
		Customer c=custRepo.getOne(phoneNo);
		c.getFriends().add(friend);
		c.setFriends(c.getFriends());
		custRepo.save(c);
	}

}
