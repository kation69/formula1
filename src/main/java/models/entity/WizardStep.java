package models.entity;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component
public abstract class WizardStep {

	protected ModelAndView mav = null;
		
	private WizardStep next = null;
	
	public abstract ModelAndView show();
	
	public WizardStep getNext() {
		return next;
	}

	public void setNext(WizardStep next) {
		this.next = next;
	}
	
	
	
}
