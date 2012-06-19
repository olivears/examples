package com.clouway.gwt.requestfactory.contacts.client;

import com.clouway.gwt.requestfactory.contacts.client.presenter.Presenter;
import com.clouway.gwt.requestfactory.contacts.shared.ContactsRequestFactory;
import com.clouway.gwt.requestfactory.contacts.shared.PersonProxy;
import com.clouway.gwt.requestfactory.contacts.shared.PersonRequest;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Lazov <darkpain1989@gmail.com>
 */
public class ViewContactsPresenter implements Presenter, ViewContacts.Presenter {

  private final ContactsRequestFactory requestFactory;
  private final ViewContacts view;

  public ViewContactsPresenter(ContactsRequestFactory requestFactory, ViewContacts view) {
    this.requestFactory = requestFactory;
    this.view = view;
  }

  public void go(HasWidgets container) {
    this.view.setPresenter(this);
    container.add((Widget) view);
    loadContacts();
  }

  public void loadContacts() {

    view.clearContacts();

    final List<PersonProxy> contacts = new ArrayList<PersonProxy>();
    PersonRequest personRequest = requestFactory.personRequest();

    personRequest.findAll().fire(new Receiver<List<PersonProxy>>() {
      public void onSuccess(List<PersonProxy> response) {
        view.loadContacts(response);
      }
    });
  }
}