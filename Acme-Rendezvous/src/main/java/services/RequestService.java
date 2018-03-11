package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.RequestRepository;
import domain.Benefit;
import domain.Request;
import domain.User;

@Service
@Transactional
public class RequestService {

	@Autowired
	private RequestRepository	requestRepository;

	@Autowired
	private UserService				userService;


	public RequestService() {
		super();
	}

	public Request create() {
		Request result;
		result = new Request();

		return result;
	}
	public Request findOne(final int requestId) {
		Assert.isTrue(requestId != 0);

		Request result;

		result = this.requestRepository.findOne(requestId);
		Assert.notNull(result);

		return result;
	}

	public Request save(final Request request, final Benefit benefit) {
		Assert.notNull(request);
		Assert.isTrue(benefit.getFlag().equals("ACTIVE"));
		Request result;
		request.setBenefit(benefit);
		
		result = this.requestRepository.save(request);
		final User principal = this.userService.findByPrincipal();
		Collection<Request> requests = principal.getRequests();
		requests.add(result);
		principal.setRequests(requests);
		this.userService.save(principal);

		return result;
	}

	// Auxiliary Methods

	public void flush() {
		this.requestRepository.flush();
	}
	
	public Collection<Request> findAllByBenefit(int benefitId){
		return this.requestRepository.findAllByBenefit(benefitId);
	}

}