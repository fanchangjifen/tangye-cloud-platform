package com.angel.common.enums;

public enum BoolQueryEnum {

	MUST{
		@Override
		public String toString() {
			return "must";
		}
	},
	MUST_NOT{
		@Override
		public String toString() {
			return "must_not";
		}
	},
	SHOULD{
		@Override
		public String toString() {
			return "should";
		}
	},
    MINIMUM_SHOULD_MATCH{
	    @Override
        public String toString() {
	        return "minimum_should_match";
        }
    },
	COMPLETION{
		@Override
		public String toString() {
			return "completion";
		}
	}
}
