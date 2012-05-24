package com.clouway.gwt.bank.client;

import com.clouway.gwt.bank.shared.User;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("BankService")
public interface BankService extends RemoteService {
  User registerUser(User user);
}