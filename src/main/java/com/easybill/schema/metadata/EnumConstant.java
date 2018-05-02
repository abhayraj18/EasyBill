package com.easybill.schema.metadata;

public class EnumConstant {
	public static final String UNITS = "enum ('PC', 'BOX', 'BAG')";
	public static final String STATUS = "enum ('ACTIVE', 'INACTIVE')";
	public static final String STATUS_DEFAULT = "ACTIVE";

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
