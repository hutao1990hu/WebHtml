package me.wjh.android.htmlui.service;

import java.util.ArrayList;
import java.util.List;

import me.wjh.android.htmlui.domain.Contact;


public class ContactService {
	public List<Contact> getContacts() {
		List<Contact> contacts = new ArrayList<Contact>();
		
		contacts.add(new Contact(1, "wjh", "1111"));
		contacts.add(new Contact(2, "jhw", "1331"));
		contacts.add(new Contact(3, "hwj", "1141"));
		contacts.add(new Contact(4, "hhw", "1511"));
		contacts.add(new Contact(5, "hhj", "1116"));
		contacts.add(new Contact(6, "jww", "1121"));
		
		return contacts;
	}
}
