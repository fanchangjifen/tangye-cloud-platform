package com.angel.common.enums;

public enum HighlightEnum {

	PRE_TAGS{
		@Override
		public String toString() {
			return "pre_tags";
		}
	},
	POST_TAGS{
		@Override
		public String toString() {
			return "post_tags";
		}
	},
	REQUIRE_FIELD_MATCH{
		@Override
		public String toString() {
			return "require_field_match";
		}
	},
}
