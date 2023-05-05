package com.humanresource.domain;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class OrderJob implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@JsonFormat(pattern="dd/MM/yyyy HH:mm")
	private Date instante;
	
	@OneToOne(cascade=CascadeType.ALL, mappedBy="orderjob")
	private Payment payment;

	@ManyToOne
	@JoinColumn(name="customer_id")
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name="endereco_de_entrega_id")
	private Address enderecoDeEntrega;
	
	@OneToMany(mappedBy="id.orderjob")
	private Set<ItemOrder> itens = new HashSet<>();
	
	public OrderJob() {
	}

	public OrderJob(Integer id, Date instante, Customer customer, Address enderecoDeEntrega) {
		super();
		this.id = id;
		this.instante = instante;
		this.customer = customer;
		this.enderecoDeEntrega = enderecoDeEntrega;
	}

	public double getValorTotal() {
		double soma = 0.0;
		for (ItemOrder ip : itens) {
			soma = soma + ip.getSubTotal();
		}
		return soma;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getInstante() {
		return instante;
	}

	public void setInstante(Date instante) {
		this.instante = instante;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Address getEnderecoDeEntrega() {
		return enderecoDeEntrega;
	}

	public void setEnderecoDeEntrega(Address enderecoDeEntrega) {
		this.enderecoDeEntrega = enderecoDeEntrega;
	}

	public Set<ItemOrder> getItens() {
		return itens;
	}

	public void setItens(Set<ItemOrder> itens) {
		this.itens = itens;
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
		OrderJob other = (OrderJob) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		StringBuilder builder = new StringBuilder();
		builder.append("Order número: ");
		builder.append(getId());
		builder.append(", Instante: ");
		builder.append(sdf.format(getInstante()));
		builder.append(", Customer: ");
		builder.append(getCustomer().getName());
		builder.append(", Situação do pagamento: ");
		builder.append(getPayment().getState().getDescription());
		builder.append("\nDetalhes:\n");
		for (ItemOrder ip : getItens()) {
			builder.append(ip.toString());
		}
		builder.append("Valor total: ");
		builder.append(nf.format(getValorTotal()));
		return builder.toString();
	}
}
