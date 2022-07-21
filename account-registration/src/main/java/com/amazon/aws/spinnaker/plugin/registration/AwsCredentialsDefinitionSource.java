package com.amazon.aws.spinnaker.plugin.registration;

import com.google.common.collect.ImmutableList;
import com.netflix.spinnaker.clouddriver.aws.security.config.AccountsConfiguration;
import com.netflix.spinnaker.clouddriver.aws.security.config.CredentialsConfig;
import com.netflix.spinnaker.credentials.definition.CredentialsDefinitionSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AwsCredentialsDefinitionSource implements CredentialsDefinitionSource<AccountsConfiguration.Account> {
    private final AccountsStatus accountsStatus;
    private final AccountsConfiguration credentialsConfig;
    private List<AccountsConfiguration.Account> awsCredentialsDefinitions;

    @Autowired
    AwsCredentialsDefinitionSource(AccountsStatus accountsStatus, AccountsConfiguration credentialsConfig) {
        this.accountsStatus = accountsStatus;
        this.credentialsConfig = credentialsConfig;
    }

    @Override
    public List<AccountsConfiguration.Account> getCredentialsDefinitions() {
        if (awsCredentialsDefinitions == null) {
            awsCredentialsDefinitions = credentialsConfig.getAccounts();
        }
        if (accountsStatus.getDesiredAccounts()) {
            awsCredentialsDefinitions = accountsStatus.getEC2AccountsAsList();
        }
        return ImmutableList.copyOf(awsCredentialsDefinitions);
    }
}
