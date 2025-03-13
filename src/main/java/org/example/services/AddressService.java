package org.example.services;

import org.example.dao.AddressDAO;
import org.example.entities.Address;
import jakarta.persistence.EntityManager;

import java.util.List;

public class AddressService {
    private AddressDAO addressDAO;

    public AddressService(EntityManager entityManager) {
        this.addressDAO = new AddressDAO(entityManager);
    }

    public void addAddress(Address address) {
        addressDAO.save(address);
    }

    public Address getAddressById(Long id) {
        return addressDAO.findById(id);
    }

    public List<Address> getAllAddresses() {
        return addressDAO.findAll();
    }

    public void updateAddress(Address address) {
        addressDAO.update(address);
    }

    public void removeAddress(Long id) {
        addressDAO.delete(id);
    }
}
