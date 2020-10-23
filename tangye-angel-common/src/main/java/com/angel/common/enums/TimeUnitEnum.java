package com.angel.common.enums;
/**
 * 时间单位
 */
public enum TimeUnitEnum {
	FOMAT{
		@Override
		public String toString() {
			return "format";
		}
	},
	MONTH{
		@Override
		public String toString() {
			return "month";
		}
	},
	DAY{
		@Override
		public String toString() {
			return "day";
		}
	},
	HOUR{
		@Override
		public String toString() {
			return "hour";
		}
	},
	MINUTE{
		@Override
		public String toString() {
			return "minute";
		}
	},
	SECOND{
		@Override
		public String toString() {
			return "second";
		}
	}
}
