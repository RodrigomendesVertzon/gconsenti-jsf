package br.com.vertzon.gconsenti.controller.jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.vertzon.gconsenti.domain.exception.BusinessRuleException;
import br.com.vertzon.gconsenti.domain.model.Finality;
import br.com.vertzon.gconsenti.domain.model.JsfLabel;
import br.com.vertzon.gconsenti.domain.model.enumerator.JSFBeanEnum;
import br.com.vertzon.gconsenti.domain.repository.JsfLabelRepository;
import br.com.vertzon.gconsenti.domain.service.FinalityService;
import br.com.vertzon.gconsenti.util.jsf.FacesUtil;

@Component
@Scope("view")
public class RegisterFinalityBean implements Serializable {
	private static final long serialVersionUID = 7274054190849026027L;
	
	@Autowired
	private FinalityService finalityService;
	@Autowired
	private RouterBean router;
	@Autowired
	private JsfLabelRepository jsfLabelRepository;
	
	private Finality finality;
	private List<String> labels = new ArrayList<String>();
	private List<JsfLabel> jsfLabels = new ArrayList<>();
	
	@PostConstruct
	public void init() {
		this.finality = new Finality();
		jsfLabels = jsfLabelRepository.findAllByJsfBean(JSFBeanEnum.FINALITY_REG);
		
		for (JsfLabel jsfLabel : jsfLabels) {
			labels.add(jsfLabel.getLabel());
		}
	}
	
	public void register() throws BusinessRuleException {		
		try {
			this.finalityService.register(finality);
			FacesUtil.submitSuccess("A finalidade foi cadastrada com sucesso!", router.getSearchFinalitiesPage());
		} catch (BusinessRuleException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	public Finality getFinality() {
		return finality;
	}
	public void setFinality(Finality finality) {
		this.finality = finality;
	}
}
