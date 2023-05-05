package com.humanresource.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.humanresource.domain.enums.Role;
import com.humanresource.domain.enums.TypeCustomer;

@Entity
public class Customer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String name;
	
	@Column(unique=true)
	private String email;
	private String cpfOuCnpj;
	private Integer type;
	
	@JsonIgnore
	private String password;
	
	@OneToMany(mappedBy="customer", cascade=CascadeType.ALL)
	private List<Address> addresses = new ArrayList<>();
	
	@ElementCollection
	@CollectionTable(name="PHONE")
	private Set<String> phones = new HashSet<>();
	
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name="ROLES")
	private Set<Integer> roles = new HashSet<>();
	
	@JsonIgnore
	@OneToMany(mappedBy="customer")
	private List<OrderJob> orders = new ArrayList<>();
	
	public Customer() {
		addRole(Role.CUSTOMER);
	}

	public Customer(Integer id, String name, String email, String cpfOuCnpj, TypeCustomer tipo, String password) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.cpfOuCnpj = cpfOuCnpj;
		this.type = (type==null) ? null : tipo.getCod();
		this.password = password;
		addRole(Role.CUSTOMER);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}

	

	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public Set<String> getPhones() {
		return phones;
	}

	public void setPhones(Set<String> phones) {
		this.phones = phones;
	}

	public Set<Integer> getRoles() {
		return roles;
	}

	public void setRoles(Set<Integer> roles) {
		this.roles = roles;
	}

	public List<OrderJob> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderJob> orders) {
		this.orders = orders;
	}

	public Set<Role> getPerfis() {
		return roles.stream().map(x -> Role.toEnum(x)).collect(Collectors.toSet());
	}
	
	public void addRole(Role role) {
		roles.add(role.getCod());
	}
	





	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}	
}
