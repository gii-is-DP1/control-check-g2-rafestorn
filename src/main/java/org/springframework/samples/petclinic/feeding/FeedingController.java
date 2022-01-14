package org.springframework.samples.petclinic.feeding;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.pet.PetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path="/feeding")
public class FeedingController {
    
	@Autowired
	private FeedingService fs;
	
	@Autowired PetService ps;
	
	private static final String CREATE_OR_UPDATE_FORM="feedings/createOrUpdateFeedingForm";
	
	@GetMapping(path = "/create")
	public String initCreationForm(ModelMap modelMap) {
		String view = CREATE_OR_UPDATE_FORM;
		modelMap.addAttribute("feeding", new Feeding());
		modelMap.addAttribute("feedingTypes", fs.getAllFeedingTypes());
		modelMap.addAttribute("pets", ps.getAllPets());
		return view;
	}
	
	@PostMapping(value = "/create")
	public String processCreationForm(@Valid Feeding feeding, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			model.addAttribute("feeding", feeding);
			model.addAttribute("feedingTypes", fs.getAllFeedingTypes());
			model.addAttribute("pets", ps.getAllPets());
			return CREATE_OR_UPDATE_FORM;
		}
		else {
            try{
            	this.fs.save(feeding);
            }catch(UnfeasibleFeedingException ex){
            	model.addAttribute("feeding", feeding);
    			model.addAttribute("feedingTypes", fs.getAllFeedingTypes());
    			model.addAttribute("pets", ps.getAllPets());
                result.rejectValue("feedingType", "Unfeasible Feeding", "La mascota seleccionada no se le puede aplicar el plan de alimentación especificado.");
                return CREATE_OR_UPDATE_FORM;
            }
            return "redirect:/welcome";
		}
	}
	
}
