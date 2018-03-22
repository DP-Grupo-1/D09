
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.Validator;

import repositories.RequestRepository;
import domain.Benefit;
import domain.Rendezvous;
import domain.Request;
import domain.User;

@Service
@Transactional
public class RequestService {

	@Autowired
	private RequestRepository	requestRepository;

	@Autowired
	private UserService			userService;
	@Autowired
	private Validator			validator;


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

	public Request save(final Request request, final Benefit benefit, final Rendezvous rendezvous) {
		Assert.notNull(request);
		Assert.notNull(benefit);
		Assert.notNull(rendezvous);
		Assert.isTrue(benefit.getFlag().equals("ACTIVE"));
		Request result;
		request.setBenefit(benefit);

		result = this.requestRepository.save(request);
		final User principal = this.userService.findByPrincipal();
		Assert.isTrue(rendezvous.getCreator().equals(principal));
		final Collection<Request> requests = principal.getRequests();
		requests.add(result);
		principal.setRequests(requests);
		this.userService.save(principal);
		final Collection<Rendezvous> rendezvouses = benefit.getRendezvouses();
		rendezvouses.add(rendezvous);
		benefit.setRendezvouses(rendezvouses);

		return result;
	}

	// Auxiliary Methods

	public void flush() {
		this.requestRepository.flush();
	}

	public Collection<Request> findAllByBenefit(final int benefitId) {
		return this.requestRepository.findAllByBenefit(benefitId);
	}

	public Collection<Request> findAllByBenefit(final Benefit benefit) {
		// TODO Auto-generated method stub
		return this.requestRepository.findAllByBenefit(benefit.getId());
	}
	public Collection<Request> findAll() {
		return this.requestRepository.findAll();
	}
	public void delete(final Request request) {
		Assert.notNull(request);
		final User u = this.userService.findByRequestId(request.getId());
		u.getRequests().remove(request);
		this.requestRepository.delete(request);
	}
	//	public Request reconstruct(final Request request, final BindingResult binding) {
	//		Request res;
	//		if (request.getId() == 0)
	//			res = request;
	//		else {
	//			res = this.requestRepository.findOne(request.getId());
	//			res.setComment(request.getComment());
	//		}
	//		return res;
	//
	//	}

}
