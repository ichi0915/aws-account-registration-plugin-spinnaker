package com.amazon.aws.spinnaker.plugin.registration;

import com.google.common.collect.ImmutableList;
import com.netflix.spinnaker.clouddriver.ecs.security.ECSCredentialsConfig;
import com.netflix.spinnaker.credentials.definition.CredentialsDefinitionSource;

import java.util.List;

public class EcsCredentialsDefinitionSource implements CredentialsDefinitionSource<ECSCredentialsConfig.Account> {
    private final AccountsStatus accountsStatus;
    private final ECSCredentialsConfig ECSCredentialsConfig;

    public EcsCredentialsDefinitionSource(AccountsStatus accountsStatus, ECSCredentialsConfig ECSCredentialsConfig) {
        this.accountsStatus = accountsStatus;
        this.ECSCredentialsConfig = ECSCredentialsConfig;
    }

    @Override
    public List<ECSCredentialsConfig.Account> getCredentialsDefinitions() {
        List<ECSCredentialsConfig.Account> remoteList = accountsStatus.getECSAccountsAsList();
        List<ECSCredentialsConfig.Account> ecsCredentialsDefinitions;
        if (!remoteList.isEmpty()) {
            ecsCredentialsDefinitions = remoteList;
        } else {
            ecsCredentialsDefinitions = ECSCredentialsConfig.getAccounts();
        }
        return ImmutableList.copyOf(ecsCredentialsDefinitions);
    }
}
