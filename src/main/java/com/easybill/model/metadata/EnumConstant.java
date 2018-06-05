package com.easybill.model.metadata;

public class EnumConstant {
	public static final String UNITS = "enum ('PC', 'BOX', 'BAG')";
	public static final String STATUS = "enum ('ACTIVE', 'INACTIVE')";
	public static final String STATUS_DEFAULT = "ACTIVE";
	public static final String USER_TYPE = "enum ('DISTRIBUTOR', 'WHOLESALER')";

	public enum RoleName {
		ROLE_DISTRIBUTOR, ROLE_WHOLESALER, ROLE_VISITOR
	}

	public enum UserType {
		WHOLESALER(1), DISTRIBUTOR(2);
		
		private int rank;

		private UserType(int rank) {
			this.rank = rank;
		}
		
		public int getRank() {
			return rank;
		}
	}

	public enum Status {
		ACTIVE, INACTIVE
	}

	public enum Unit {
		PC(0), BOX(1), BAG(1);

		int order;

		private Unit(int order) {
			this.order = order;
		}

		public int getOrder() {
			return order;
		}
	}

}
