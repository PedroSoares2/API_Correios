package correios.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import correios.Main;
import correios.exception.NoContextException;
import correios.exception.NotReadyException;
import correios.models.Address;
import correios.models.AddressStatus;
import correios.models.Status;
import correios.repository.AddressRepository;
import correios.repository.AddressStatusRepository;
import correios.repository.SetupRepository;

@Service
public class CorreiosService {
	
	private static Logger logger = LoggerFactory.getLogger(CorreiosService.class);
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private AddressStatusRepository addressStatusRepository;
	
	@Autowired
	private SetupRepository setupRepository;

	
	public Status getStatus() {
		return this.addressStatusRepository.findById(AddressStatus.DEFAULT_ID)
				.orElse(AddressStatus.builder().status(Status.NEED_SETUP).build())
				.getStatus();
	}
	
	public Address getAddressByZipCode(String zipcode) throws NoContextException, NotReadyException {
		if(!this.getStatus().equals(Status.READY)) {
			throw new NotReadyException();
		}
		return addressRepository.findById(zipcode)
				.orElseThrow(NoContextException::new);
	}
	
	private void saveStatus(Status status) {
		this.addressStatusRepository.save(AddressStatus.builder()
				.id(AddressStatus.DEFAULT_ID).status(status).build());
	}
	
	@EventListener(ApplicationStartedEvent.class)
	protected void setupOnStartup() {
		try {
			this.setup();
		} catch (Exception exc) {
			Main.close(999);
			logger.error(".setupOnStartup() - Exception", exc);
		}
	}
	
	public void setup() throws Exception {
		logger.info("-----");
		logger.info("-----");
		logger.info("----- SETUP RUNNING");
		logger.info("-----");
		logger.info("-----");
		
		
		if(this.getStatus().equals(Status.NEED_SETUP)) {
			this.saveStatus(Status.SETUP_RUNNING);
			try {
				this.addressRepository.saveAll(this.setupRepository.listAdressesFromOrigin());
				} catch(Exception exc) {
				this.saveStatus(Status.NEED_SETUP);
				throw exc;
			}
			
			this.saveStatus(Status.READY); 
		}
		
		logger.info("-----");
		logger.info("-----");
		logger.info("----- SERVICE READY");
		logger.info("-----");
		logger.info("-----");
		
	}
	
	
	//JUST 100 ZIPCODES TO TEST
		/*
		if(this.getStatus().equals(Status.NEED_SETUP)) {
			this.saveStatus(Status.SETUP_RUNNING);
			try {
				List<Address> address = this.setupRepository.listAdressesFromOrigin();
				for(int i = 0; i < 100; i++) {
					this.addressRepository.save(address.get(i));
			   	}			
				} catch(Exception exc) {
				this.saveStatus(Status.NEED_SETUP);
				throw exc;
			}
		
			this.saveStatus(Status.READY);
			*/ 
}

