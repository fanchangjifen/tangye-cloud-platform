package com.angel.common.enums;

/**
 * 查询方式枚举
 */
public interface QueryEnum {

	enum matchAllQuery implements QueryEnum{
		MATCH_ALL{
			@Override
			public String toString() {
				return "match_all";
			}
		},
		MATCH_NONE{
			@Override
			public String toString() {
				return "match_none";
			}
		}
	}
	enum simpleQuery implements QueryEnum{
		MATCH{
			@Override
			public String toString() {
				return "match";
			}
		},
		TERM{
			@Override
			public String toString() {
				return "term";
			}
		},
		WILDCARD{
			@Override
			public String toString() {
				return "wildcard";
			}
		},
		PREFIX{
			@Override
			public String toString() {
				return "prefix";
			}
		},
		MATCH_PHRASE{
			@Override
			public String toString() {
				return "match_phrase";
			}
		},
		REGEXP{
			@Override
			public String toString() {
				return "regexp";
			}
		}
	}
	enum simpleFieldQuery implements QueryEnum{
		COLLAPSE{
			@Override
			public String toString() {
				return "collapse";
			}
		},
		EXISTS{
			@Override
			public String toString() {
				return "exists";
			}
		}
	}
	enum simpleFieldsQuery implements QueryEnum{
		MULTI_MATCH{
			@Override
			public String toString() {
				return "multi_match";
			}
		},
		QUERY_STRING{
			@Override
			public String toString() {
				return "query_string";
			}
		},
		SIMPLE_QUERY_STRING{
			@Override
			public String toString() {
				return "simple_query_string";
			}
		}
	}

    enum completionQuery implements QueryEnum{
        SKIP_DUPLICATES{
            @Override
            public String toString() {
                return "skip_duplicates";
            }
        },
        FUZZY{
            @Override
            public String toString() {
                return "fuzzy";
            }
        },
        FUZZINESS{
            @Override
            public String toString() {
                return "fuzziness";
            }
        },
        MIN_LENGTH{
            @Override
            public String toString() {
                return "min_length";
            }
        },
        PREFIX_LENGTH{
            @Override
            public String toString() {
                return "prefix_length";
            }
        }
    }
	//除简单查询以外的其他查询
	enum otherQuery implements QueryEnum{
		TERMS{
			@Override
			public String toString() {
				return "terms";
			}
		},
		RANGE{
			@Override
			public String toString() {
				return "range";
			}
		}
	}
	
	
}
