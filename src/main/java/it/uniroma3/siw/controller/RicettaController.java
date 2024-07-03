package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.controller.validator.RicettaValidator;
import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.repository.RicettaRepository;
import it.uniroma3.siw.service.RicettaService;
import jakarta.validation.Valid;

@Controller
public class RicettaController {
	
	@Autowired RicettaService ricettaService;
	
	@Autowired 
	private RicettaRepository ricettaRepository;
	
	@Autowired 
	private RicettaValidator ricettaValidator;
	
	  @GetMapping(value="/admin/formNewRicetta")
		public String formNewRicettaAdmin(Model model) {
			model.addAttribute("ricetta", new Ricetta());
			return "admin/formNewRicetta.html";
		}
	  
	  @GetMapping(value="/regi/formNewRicetta")
		public String formNewRicettaRegi(Model model) {
			model.addAttribute("ricetta", new Ricetta());
			return "regi/formNewRicetta.html";
		}
	
	  @GetMapping(value="/admin/formUpdateRicetta/{id}")
		public String formUpdateRicetta(@PathVariable("id") Long id, Model model) {
			model.addAttribute("ricetta", ricettaRepository.findById(id).get());
			return "admin/formUpdateRicetta.html";
		}
	  
	@PostMapping("/regi/ricetta")
	public String newRicetta(@Valid @ModelAttribute("ricetta") Ricetta ricetta, BindingResult bindingResult, Model model) {
		
		this.ricettaValidator.validate(ricetta, bindingResult);
		if (!bindingResult.hasErrors()) {
			this.ricettaRepository.save(ricetta); 
			model.addAttribute("ricetta", ricetta);
			return "ricetta.html";
		} else {
			return "regi/formNewRicetta.html"; 
		}
	}
	  
	@GetMapping("/ricetta/{id}")
	  public String getRicetta(@PathVariable("id") Long id, Model model) {
	    model.addAttribute("ricetta", this.ricettaService.findById(id));
	    return "ricetta.html";
	  }

	  @GetMapping("/ricette")
	  public String showRicette(Model model) {
	    model.addAttribute("ricette", this.ricettaService.findAll());
	    return "ricette.html";
	  }
	  


}
