package com.amp.controlers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amp.domain.Client;
import com.amp.service.ClientService;

@Controller
public class ClientController {
	
	protected final Log logger = LogFactory.getLog(getClass());

	ClientService clientService;

	@Autowired
	public ClientController(ClientService clientService) {
		this.clientService = clientService;
	}

	@RequestMapping("clients/")
	public String loadClientsPage(Model m) {

		m.addAttribute("clients", clientService.getClients());

		return "clients";
	}
	
	@RequestMapping(value="clients/add", method=RequestMethod.GET)
	public String addClientPage(Model m) {
		
		logger.info("Nuevo Cliente GET");

		m.addAttribute("client", new Client());

		return "addClient";
	}
	
	@RequestMapping(value="clients/add", method=RequestMethod.POST)
	public String addClientForm(@ModelAttribute Client client, Model m) {

		logger.info("Nuevo Cliente: " + client.getName());
		
		clientService.addClient(client);
		
		m.addAttribute("client", new Client());
		
		return "addClient";
	}

	@RequestMapping("api/client/{id}")
	@ResponseBody
	public Client getById(@PathVariable Integer id) {
		return clientService.getClientById(id);
	}

}
