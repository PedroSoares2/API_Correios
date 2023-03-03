package correios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import correios.exception.NoContextException;
import correios.exception.NotReadyException;
import correios.models.Address;
import correios.service.CorreiosService;

@RestController
public class CorreiosController {
	
	@Autowired
	private CorreiosService service;
	
	@GetMapping("/status")
	public String getStatus() {
		return "Service Status: " + service.getStatus();
	}
	
	@GetMapping("/error")
	public String getError() {
		return "Erro";
	}
	
	@GetMapping("/zipcode/{zipcode}")
	public Address getAdressByZipcode(@PathVariable("zipcode") String zipcode) throws NoContextException, NotReadyException {
		return this.service.getAddressByZipCode(zipcode);
	}
}
