package com.humanresource.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.humanresource.domain.enums.StatePayment;


@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@type")
public abstract class Payment implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer id;
	private Integer state;

	@JsonIgnore
	@OneToOne
	@JoinColumn(name="orderjob_id")
	@MapsId
	private OrderJob orderjob;
	
	public Payment() {
	}

	public Payment(Integer id, StatePayment state, OrderJob orderjob) {
		super();
		this.id = id;
		this.state = (state==null) ? null : state.getCod();
		this.orderjob = orderjob;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public StatePayment getState() {
		return StatePayment.toEnum(state);
	}

	public void setState(StatePayment state) {
		this.state = state.getCod();
	}

	public OrderJob getOrder() {
		return orderjob;
	}

	public void setOrder(OrderJob orderjob) {
		this.orderjob = orderjob;
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
		Payment other = (Payment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
