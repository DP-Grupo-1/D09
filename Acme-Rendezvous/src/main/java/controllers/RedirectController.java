/* WelcomeController.java
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/redirect")
public class RedirectController extends AbstractController {

	// Constructors -----------------------------------------------------------
	
	public RedirectController() {
		super();
	}
		
	// Index ------------------------------------------------------------------		

	@RequestMapping(value = "/terms", method = RequestMethod.GET)
	public ModelAndView terms() {
		ModelAndView result;
		
		result = new ModelAndView("terms/terms");
		return result;
	}
	
	@RequestMapping(value = "/cookies", method = RequestMethod.GET)
	public ModelAndView cookies() {
		ModelAndView result;
		
		result = new ModelAndView("terms/cookies");
		return result;
	}
}