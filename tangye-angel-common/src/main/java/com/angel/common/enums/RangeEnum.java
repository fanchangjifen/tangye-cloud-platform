package com.angel.common.enums;

public enum RangeEnum {

	FROM{
		@Override
		public String toString() {
			return "gte";
		}
	},

	GT{
		@Override
		public String toString() {
			return "gt";
		}
	},
	
	TO{
		@Override
		public String toString() {
			return "lte";
		}
	},
    LT{
        @Override
        public String toString() {
            return "lt";
        }
    }
}
