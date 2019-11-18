package com.example.guide.ui.contact;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.guide.Model.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactViewModel extends AndroidViewModel {

    private MutableLiveData<String> tabSelected;
    private MutableLiveData<List<Contact>> contactList ;
    private List<Contact> filteredListContact = new ArrayList<>();
    private List<Contact> filteredListContactEmergency = new ArrayList<>();
    private List<Contact> contacts = new ArrayList<>();
    private MutableLiveData<Boolean> isEmergency;

    public ContactViewModel(@NonNull Application application) {
        super(application);
        isEmergency = new MutableLiveData<>();
        isEmergency.setValue(Boolean.FALSE);
        this.tabSelected = new MutableLiveData<String>();
        this.tabSelected.setValue("embassy");

    }

    public MutableLiveData<List<Contact>> loadContacts(){
        if(contactList == null){
            contactList = new MutableLiveData<List<Contact>>();
            loadEmbassyContactsList();
        }
        return contactList;
    }



    private void loadEmbassyContactsList() {
        contacts.clear();
        contacts.add(new Contact("Embassy of Bangladesh","01-4390132","bdt",""));
        contacts.add(new Contact("Embassy of Brazil","01-4721462 /01-4721463","brl",""));
        contacts.add(new Contact("Embassy of China","01-4440286","cny",""));
        contacts.add(new Contact("Embassy of Egypt","01-5590166","egp",""));

        contacts.add(new Contact("Embassy of Finland","01-4417221/01-4416636","fin",""));
        contacts.add(new Contact("Embassy of France","01-4412332/01-4414734","fr",""));
        contacts.add(new Contact("Embassy of Germany","01-4412786/01-4416527","ger",""));
        contacts.add(new Contact("Embassy of India","01-4410900","inr",""));
        contacts.add(new Contact("Embassy of Israel","01-4411811/01-4413419","ils",""));
        contacts.add(new Contact("Embassy of Japan","01-4426680","jpy",""));
        contacts.add(new Contact("Embassy of Malaysia","01-5545680/01-5545681","myr",""));
        contacts.add(new Contact("Embassy of Myanmar","01- 5592774/01-5592841","mmk",""));
        contacts.add(new Contact("Embassy of North Korea","01- 5521855/01-5535871","dpr",""));
        contacts.add(new Contact("Embassy of Norway","01- 554 5307","nor",""));
        contacts.add(new Contact("Embassy of Pakistan","01-4374024/01-4374016","pkr",""));
        contacts.add(new Contact("Embassy of Qatar","01-5173161","qar",""));
        contacts.add(new Contact("Embassy of Russia","01-4412155/01-441 1063","rub",""));
        contacts.add(new Contact("Embassy of Saudi Arabia","01-4720891","sar",""));
        contacts.add(new Contact("Embassy of South Korea","01-4270172/01-4270417","skr",""));
        contacts.add(new Contact("Embassy of Srilanka","01-4721389","lkr",""));
        contacts.add(new Contact("Embassy of Switzerland","01-4217008/01-5525358","chf",""));
        contacts.add(new Contact("Embassy of Thailand","01-4371410/01-4371411","thb",""));
        contacts.add(new Contact("Embassy of the United Arab Emirates","01-4237100","aed",""));
        contacts.add(new Contact("Embassy of the United Kingdom","01-4371410/01-4371411","gbp",""));
        contacts.add(new Contact("Embassy of the United States of America","01-4234000","usd",""));

        contactList.setValue(contacts);

    }
    private void loadEmmergencyContactsList() {
        contacts.clear();
        contacts.add(new Contact("Police Control", "100", "police",""));
        contacts.add(new Contact("Emergency Ambulance", "102", "ambulance",""));
        contacts.add(new Contact("Tourist Police", "1144(Toll Free/ Hotline)", "police","Head Quarter-> Bhrikuti Mandap, Kathmandu"));
        contacts.add(new Contact("Bhaktapur Hospital", "01-6610676", "bkthpt","Doodh Pati"));
        contacts.add(new Contact("Bir Hospital", "01-4221119/01-441 1063", "bir","Kathmandu"));
        contacts.add(new Contact("Public Health Care Center", "01-6610317/01-6613146", "polytechnic","Chyamhasingh,Bhaktapur"));
        contacts.add(new Contact("Tilganga Eye Hospital", "01-4423684", "tilganga","Kathmandu"));
        contacts.add(new Contact("TU Teaching Hospital", "01-4412707/01-4412808/01-4412505", "teaching","Maharajgunj,kathmandu"));
        contacts.add(new Contact("Nepal Tourism Board", "01-4256909/014256229", "ntb","Bhrikuti Mandap, Kathmandu"));
        contacts.add(new Contact("Department of Immigration", "01-4223509/01-4222453", "immigration","Kathmandu"));
        contacts.add(new Contact("Tribhuvan International Airport(TIA)", "01-4472256/01-4472257", "tia","Kathmandu"));
        contacts.add(new Contact("International Flight Service", "4470311/4472835(ext )", "aero","Kathmandu"));
        contacts.add(new Contact("Night Taxi Service", "01-4244485/01-4224375", "taxi","Dharmapath"));
        contacts.add(new Contact("Interpol Section", "01-4411210/01-4412602", "interpol","Naxal, Kathmandu"));
        contactList.setValue(contacts);

    }


    public void onContactTextChanged(CharSequence s, int start, int before, int count){

        Toast.makeText(getApplication(), "Whta the he;;", Toast.LENGTH_SHORT).show();
        switch (tabSelected.getValue()) {
            case "embassy":
                loadEmbassyContactsList();
                filter(s.toString());
                break;
            case "emergency":
                loadEmmergencyContactsList();
                filter(s.toString());
                break;
            default:
                break;
        }
    }


    private void filter(String text) {
        filteredListContact.clear();

        for (Contact contact : contacts) {

            if (contact.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredListContact.add(contact);

            }

        }
        contactList.setValue(filteredListContact);
    }

    private void filterEmergency(String text) {
        filteredListContactEmergency.clear();

        for (Contact contact : contacts) {

            if (contact.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredListContactEmergency.add(contact);

            }

        }
        contactList.setValue(filteredListContactEmergency);

    }


    public MutableLiveData<String> getTabSelected() {
        return tabSelected;
    }

    public void setTabSelected(String tabSelected) {
        this.tabSelected = new MutableLiveData<String>();
        this.tabSelected.setValue(tabSelected);

        switch (tabSelected) {
            case "embassy":
               loadEmbassyContactsList();
                break;
            case "emergency":
                loadEmmergencyContactsList();
                break;
            default:
                break;
        }
    }
}
