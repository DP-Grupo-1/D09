
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SystemConfigurationRepository;
import security.Authority;
import domain.Administrator;
import domain.SystemConfiguration;

@Service
@Transactional
public class SystemConfigurationService {

	// Managed Repository
	@Autowired
	private SystemConfigurationRepository	systemConfigurationRepository;

	@Autowired
	private AdministratorService			administratorService;

	@Autowired
	private ActorService					actorService;


	// Supporting Services

	// Constructor

	public SystemConfigurationService() {
		super();
	}

	// Simple CRUD methods
	public SystemConfiguration create() {
		SystemConfiguration created;
		created = new SystemConfiguration();

		return created;
	}

	public SystemConfiguration save(final SystemConfiguration sys) {
		Assert.isTrue(this.actorService.checkAuthority(Authority.ADMIN));

		final SystemConfiguration saved = this.systemConfigurationRepository.save(sys);
		return saved;
	}

	// Other business methods
	public Collection<SystemConfiguration> findAll() {
		Collection<SystemConfiguration> result;

		result = this.systemConfigurationRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public SystemConfiguration findOne(final int systemConfigurationId) {
		SystemConfiguration result;
		Assert.isTrue(this.actorService.checkAuthority(Authority.ADMIN));

		result = this.systemConfigurationRepository.findOne(systemConfigurationId);
		Assert.notNull(result);

		return result;
	}
	public SystemConfiguration findMain() {
		final SystemConfiguration systemConfiguration = this.systemConfigurationRepository.findMain().iterator().next();
		return systemConfiguration;
	}
}
