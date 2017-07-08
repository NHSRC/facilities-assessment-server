package org.nhsrc.service;

import org.nhsrc.config.DeploymentConfiguration;
import org.nhsrc.repository.DeploymentConfigurationRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeploymentConfigurationService {
    private DeploymentConfigurationRepository deploymentConfigurationRepository;

    public DeploymentConfigurationService(DeploymentConfigurationRepository deploymentConfigurationRepository) {
        this.deploymentConfigurationRepository = deploymentConfigurationRepository;
    }

    public DeploymentConfiguration getConfiguration() {
        List<DeploymentConfiguration> list = new ArrayList<>();
        deploymentConfigurationRepository.findAll().forEach(list::add);

        return list.size() == 0 ? new DeploymentConfiguration() : list.get(0);
    }
}