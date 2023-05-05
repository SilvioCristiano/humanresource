package com.humanresource.domain;
import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Embeddable
public class ItemOrderPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name="orderjob_id")
	private OrderJob orderjob;
	
	@ManyToOne
	@JoinColumn(name="product_id")
	private Product product;
	
	public OrderJob getOrder() {
		return orderjob;
	}
	public void setOrder(OrderJob orderjob) {
		this.orderjob = orderjob;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((orderjob == null) ? 0 : orderjob.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
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
		ItemOrderPK other = (ItemOrderPK) obj;
		if (orderjob == null) {
			if (other.orderjob != null)
				return false;
		} else if (!orderjob.equals(other.orderjob))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		return true;
	}
	
	
	
}
