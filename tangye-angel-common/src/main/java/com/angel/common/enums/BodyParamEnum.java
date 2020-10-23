package com.angel.common.enums;

/**
 * 查询体参数枚举
 */
public interface BodyParamEnum {

	enum Parameter implements BodyParamEnum{
		FROM{
			@Override
			public String toString() {
				return "from";
			}
		},
		SIZE{
			@Override
			public String toString() {
				return "size";
			}
		},
		SCORE{
			@Override
			public String toString() {
				return "_score";
			}
		},
		SOURCE{
			@Override
			public String toString() {
				return "_source";
			}
		},
        DESC{
            @Override
            public String toString() {
                return "desc";
            }
        }
	}
	enum Body implements BodyParamEnum{
		QUERY{
			@Override
			public String toString() {
				return "query";
			}
		},
        BOOL{
            @Override
            public String toString() {
                return "bool";
            }
        },
		NESTED{
            @Override
            public String toString() {
                return "nested";
            }
        },
        PATH{
            @Override
            public String toString() {
                return "path";
            }
        },
        HAS_CHILD{
            @Override
            public String toString() {
                return "has_child";
            }
        },
        HAS_PARENT{
            @Override
            public String toString() {
                return "has_parent";
            }
        },
        TYPE{
            @Override
            public String toString() {
                return "type";
            }
        },
        PARENT_TYPE{
            @Override
            public String toString() {
                return "parent_type";
            }
        },
        FIELDS{
            @Override
            public String toString() {
                return "fields";
            }
        },
        FIELD{
            @Override
            public String toString() {
                return "field";
            }
        },
		DEFAULT_FIELD{
			@Override
			public String toString() {
				return "default_field";
			}
		},
		AGGS{
			@Override
			public String toString() {
				return "aggs";
			}
		},
		HIGHLIGHT{
			@Override
			public String toString() {
				return "highlight";
			}
		},
		COLLAPSE{
			@Override
			public String toString() {
				return "collapse";
			}
		},
		SORT{
			@Override
			public String toString() {
				return "sort";
			}
		},
		ORDER{
			@Override
			public String toString() {
				return "order";
			}
		},
		FILTER{
		    @Override
            public String toString() {
		        return "filter";
            }
        },
		BOOST{
			@Override
			public String toString() {
				return "boost";
			}
		},
		SUGGEST{
			@Override
			public String toString() {
				return "suggest";
			}
		}
	}
}
