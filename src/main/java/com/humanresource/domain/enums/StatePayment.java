package com.humanresource.domain.enums;


public enum StatePayment {
		
		PENDING(1, "Pending"),
		SETTLED(2, "Settled"),
		CANCELED(3, "Canceled");
		
		private int cod;
		private String description;
		
		private StatePayment(int cod, String description) {
			this.cod = cod;
			this.description = description;
		}
		
		public int getCod() {
			return cod;
		}
		
		public String getDescription () {
			return description;
		}
		
		public static StatePayment toEnum(Integer cod) {
			
			if (cod == null) {
				return null;
			}
			
			for (StatePayment x : StatePayment.values()) {
				if (cod.equals(x.getCod())) {
					return x;
				}
			}
			
			throw new IllegalArgumentException("Id invalid: " + cod);
		}

	}
