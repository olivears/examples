package com.clouway.gwt.bank.server;

import com.clouway.gwt.bank.server.account.AccountRepository;
import com.clouway.gwt.bank.server.account.AccountRepositoryImpl;
import com.clouway.gwt.bank.server.account.AccountServiceImpl;
import com.clouway.gwt.bank.server.login.LoginServiceImpl;
import com.clouway.gwt.bank.server.register.RegisterServiceImpl;
import com.clouway.gwt.bank.server.session.SessionRepository;
import com.clouway.gwt.bank.server.session.SessionRepositoryImpl;
import com.clouway.gwt.bank.server.session.SessionServiceImpl;
import com.clouway.gwt.bank.server.user.UserRepository;
import com.clouway.gwt.bank.server.user.UserRepositoryImpl;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.google.inject.matcher.Matchers;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;

import java.sql.Connection;

/**
 * @author Ivan Lazov <darkpain1989@gmail.com>
 */
public class GuiceConfig extends GuiceServletContextListener {
  protected Injector getInjector() {

    return Guice.createInjector(new ServletModule() {

      protected void configureServlets() {

        TransactionInterceptor transactionInterceptor = new TransactionInterceptor();
        requestInjection(transactionInterceptor);
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(ExecuteInTransaction.class), transactionInterceptor);

        bind(DatabaseHelper.class).in(Singleton.class);
        bind(UserRepository.class).to(UserRepositoryImpl.class);
        bind(AccountRepository.class).to(AccountRepositoryImpl.class);
        bind(SessionRepository.class).to(SessionRepositoryImpl.class);
        bind(Connection.class).toProvider(ConnectionProvider.class);

        serve("/Bank/registerService").with(RegisterServiceImpl.class);
        serve("/Bank/loginService").with(LoginServiceImpl.class);
        serve("/Bank/accountService").with(AccountServiceImpl.class);
        serve("/Bank.html").with(MainPageServlet.class);
        serve("/Bank/pageService").with(PageServiceImpl.class);
        serve("/Bank/sessionService").with(SessionServiceImpl.class);

        filter("/*").through(ConnectionFilter.class);
      }
    });
  }
}
