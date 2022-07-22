package com.amazon.aws.spinnaker.plugin.registration;

import com.google.common.collect.ImmutableList;
import com.netflix.spinnaker.clouddriver.aws.security.config.AccountsConfiguration;
import com.netflix.spinnaker.credentials.definition.CredentialsDefinitionSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AwsCredentialsDefinitionSource implements CredentialsDefinitionSource<AccountsConfiguration.Account> {
    private final AccountsStatus accountsStatus;
    private final AccountsConfiguration AccountsConfiguration;
    private List<AccountsConfiguration.Account> awsCredentialsDefinitions;

    @Autowired
    AwsCredentialsDefinitionSource(AccountsStatus accountsStatus, AccountsConfiguration AccountsConfiguration) {
        this.accountsStatus = accountsStatus;
        this.AccountsConfiguration = AccountsConfiguration;
    }

    @Override
    public List<AccountsConfiguration.Account> getCredentialsDefinitions() {
        if (awsCredentialsDefinitions == null) {
            awsCredentialsDefinitions = AccountsConfiguration.getAccounts();
        }
        if (accountsStatus.getDesiredAccounts()) {
            awsCredentialsDefinitions = accountsStatus.getEC2AccountsAsList();
        }
        return ImmutableList.copyOf(awsCredentialsDefinitions);
    }
}
